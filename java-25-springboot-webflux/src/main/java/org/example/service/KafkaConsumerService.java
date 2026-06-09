package org.example.service;

import jakarta.annotation.PostConstruct;
import org.example.event.PostCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);

    private final KafkaReceiver<String, PostCreatedEvent> receiver;
    private final TimelineService timelineService;

    public KafkaConsumerService(KafkaReceiver<String, PostCreatedEvent> receiver,
                                TimelineService timelineService) {
        this.receiver = receiver;
        this.timelineService = timelineService;
    }

    @PostConstruct
    void startConsumer() {
        receiver.receive()
            .flatMap(record -> {
                PostCreatedEvent event = record.value();
                log.info("Fan-out: postId={}, userId={}", event.postId(), event.userId());
                return timelineService.fanout(event)
                    .then(Mono.defer(() -> {
                        record.receiverOffset().acknowledge();
                        timelineService.emitLiveEvent(event);
                        return Mono.empty();
                    }));
            })
            .doOnError(e -> log.warn("Kafka consumer error (will retry)", e))
            .retryWhen(Retry.fixedDelay(Long.MAX_VALUE, Duration.ofSeconds(5))
                .doAfterRetry(rs -> log.warn("Kafka retry attempt {}", rs.totalRetries())))
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
    }
}
