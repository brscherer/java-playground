package org.example.repository;

import org.example.model.Post;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PostRepository extends ReactiveCrudRepository<Post, Long> {

    @Query("SELECT * FROM posts WHERE id IN (:ids) ORDER BY created_at DESC")
    Flux<Post> findAllByIdOrdered(@Param("ids") List<Long> ids);

    @Query("SELECT * FROM posts WHERE user_id IN (:userIds) ORDER BY created_at DESC LIMIT :limit")
    Flux<Post> findPostsByUserIds(@Param("userIds") List<Long> userIds, @Param("limit") int limit);
}
