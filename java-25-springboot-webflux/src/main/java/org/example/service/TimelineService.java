package org.example.service;

import org.example.event.PostCreatedEvent;
import org.example.model.Post;
import org.example.repository.FollowRepository;
import org.example.repository.PostRepository;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.Limit;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.time.Instant;

@Service
public class TimelineService {

    private static final String TIMELINE_KEY = "timeline:";
    private static final int PAGE_SIZE = 20;

    private final ReactiveStringRedisTemplate redis;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;

    private final Sinks.Many<PostCreatedEvent> liveSink =
        Sinks.many().multicast().directBestEffort();

    public TimelineService(ReactiveStringRedisTemplate redis,
                           PostRepository postRepository,
                           FollowRepository followRepository) {
        this.redis = redis;
        this.postRepository = postRepository;
        this.followRepository = followRepository;
    }

    public Mono<Boolean> addToTimeline(Long userId, Long postId, Instant timestamp) {
        return redis.opsForZSet()
            .add(TIMELINE_KEY + userId, postId.toString(), timestamp.toEpochMilli());
    }

    public Flux<Post> getTimeline(Long userId, Long beforeTimestamp, int limit) {
        String key = TIMELINE_KEY + userId;
        if (limit <= 0) limit = PAGE_SIZE;
        Flux<String> postIds;
        if (beforeTimestamp == null || beforeTimestamp <= 0) {
            postIds = redis.opsForZSet()
                .reverseRangeByScore(key,
                    Range.of(Range.Bound.inclusive(0.0), Range.Bound.unbounded()),
                    Limit.limit().count(limit + 1));
        } else {
            postIds = redis.opsForZSet()
                .reverseRangeByScore(key,
                    Range.of(Range.Bound.inclusive(0.0),
                             Range.Bound.inclusive((double) beforeTimestamp - 1)),
                    Limit.limit().count(limit + 1));
        }
        return postIds
            .map(Long::valueOf)
            .collectList()
            .flatMapMany(ids -> postRepository.findAllByIdOrdered(ids));
    }

    public Flux<Post> getNewPosts(Long userId, Long afterTimestamp, int limit) {
        String key = TIMELINE_KEY + userId;
        if (limit <= 0) limit = PAGE_SIZE;
        return redis.opsForZSet()
            .rangeByScore(key,
                Range.of(Range.Bound.inclusive((double) afterTimestamp + 1),
                         Range.Bound.unbounded()),
                Limit.limit().count(limit))
            .map(Long::valueOf)
            .collectList()
            .flatMapMany(ids -> postRepository.findAllByIdOrdered(ids));
    }

    public Mono<Long> getLatestTimestamp(Long userId) {
        String key = TIMELINE_KEY + userId;
        return redis.opsForZSet()
            .reverseRangeWithScores(key, Range.just(0L))
            .next()
            .map(ts -> ts.getScore().longValue())
            .defaultIfEmpty(0L);
    }

    public void emitLiveEvent(PostCreatedEvent event) {
        liveSink.tryEmitNext(event);
    }

    public Flux<PostCreatedEvent> liveEvents() {
        return liveSink.asFlux();
    }

    public Flux<Post> fanout(PostCreatedEvent event) {
        var ts = Instant.ofEpochMilli(event.timestamp());
        return addToTimeline(event.userId(), event.postId(), ts)
            .flatMapMany(__ -> followRepository.findFollowerIdsByFolloweeId(event.userId()))
            .flatMap(followerId ->
                addToTimeline(followerId, event.postId(), ts))
            .thenMany(Flux.just(event))
            .flatMap(e -> postRepository.findById(e.postId()));
    }
}
