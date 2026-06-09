package org.example.data;

import org.example.model.Follow;
import org.example.model.Post;
import org.example.model.User;
import org.example.repository.FollowRepository;
import org.example.repository.PostRepository;
import org.example.repository.UserRepository;
import org.example.service.TimelineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Instant;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FollowRepository followRepository;
    private final TimelineService timelineService;

    public DataInitializer(UserRepository userRepository,
                           PostRepository postRepository,
                           FollowRepository followRepository,
                           TimelineService timelineService) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.followRepository = followRepository;
        this.timelineService = timelineService;
    }

    @Override
    public void run(String... args) {
        var users = List.of(
            new User("bruno", "bruno@example.com", "pass", "Bruno Scherer"),
            new User("bob", "bob@example.com", "pass", "Bob Smith"),
            new User("charlie", "charlie@example.com", "pass", "Charlie Davis"),
            new User("diana", "diana@example.com", "pass", "Diana Lee")
        );

        Flux.fromIterable(users)
            .concatMap(userRepository::save)
            .collectList()
            .flatMapMany(saved -> {
                var a = saved.get(0);
                var b = saved.get(1);
                var c = saved.get(2);
                var d = saved.get(3);

                var follows = List.of(
                    new Follow(b.getId(), a.getId()),
                    new Follow(c.getId(), a.getId()),
                    new Follow(d.getId(), a.getId()),
                    new Follow(a.getId(), b.getId()),
                    new Follow(a.getId(), c.getId())
                );

                long now = Instant.now().toEpochMilli();
                var posts = List.of(
                    post(b.getId(), "Just joined this social network! Excited to be here.", now - 300000L),
                    post(c.getId(), "Working on a new side project with Spring Boot and WebFlux. Reactor is amazing!", now - 240000L),
                    post(a.getId(), "Hello from Bruno! Building a social feed PoC with Kafka fan-out and Redis sorted sets.", now - 180000L),
                    post(b.getId(), "HTMX + Thymeleaf feels surprisingly snappy for a social feed UI.", now - 120000L),
                    post(c.getId(), "WebFlux + Kafka is the real deal for async fan-out under load.", now - 60000L)
                );

                return followRepository.saveAll(follows)
                    .thenMany(postRepository.saveAll(posts));
            })
            .flatMap(post ->
                timelineService.addToTimeline(1L, post.getId(), post.getCreatedAt()))
            .then()
            .subscribe(
                null,
                e -> log.error("Data init error", e),
                () -> log.info("Seed data loaded: 4 users, 5 posts, 5 follows")
            );
    }

    private static Post post(Long userId, String content, long epochMillis) {
        Post p = new Post(userId, content);
        p.setCreatedAt(Instant.ofEpochMilli(epochMillis));
        return p;
    }
}
