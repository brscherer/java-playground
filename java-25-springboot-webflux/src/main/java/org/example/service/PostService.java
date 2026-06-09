package org.example.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.event.PostCreatedEvent;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
public class PostService {

    private static final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final KafkaSender<String, PostCreatedEvent> kafkaSender;

    public PostService(PostRepository postRepository,
                       KafkaSender<String, PostCreatedEvent> kafkaSender) {
        this.postRepository = postRepository;
        this.kafkaSender = kafkaSender;
    }

    public Mono<Post> createPost(Long userId, String content) {
        Post post = new Post(userId, content);
        post.setCreatedAt(Instant.now());
        return postRepository.save(post)
            .flatMap(saved -> {
                PostCreatedEvent event = new PostCreatedEvent(
                    saved.getId(), saved.getUserId(), saved.getContent(), saved.getCreatedAt().toEpochMilli());
                ProducerRecord<String, PostCreatedEvent> record =
                    new ProducerRecord<>("post-created", saved.getId().toString(), event);
                return kafkaSender.send(Mono.just(SenderRecord.create(record, saved.getId())))
                    .next()
                    .onErrorResume(e -> {
                        log.warn("Kafka unavailable, fan-out skipped for post {}", saved.getId(), e);
                        return Mono.empty();
                    })
                    .thenReturn(saved);
            });
    }
}
