package org.example.event;

public record PostCreatedEvent(Long postId, Long userId, String content, Long timestamp) {
}
