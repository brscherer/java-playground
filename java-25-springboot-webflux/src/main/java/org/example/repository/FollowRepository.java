package org.example.repository;

import org.example.model.Follow;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FollowRepository extends ReactiveCrudRepository<Follow, Long> {

    @Query("SELECT followee_id FROM follows WHERE follower_id = :followerId")
    Flux<Long> findFolloweeIdsByFollowerId(@Param("followerId") Long followerId);

    @Query("SELECT follower_id FROM follows WHERE followee_id = :followeeId")
    Flux<Long> findFollowerIdsByFolloweeId(@Param("followeeId") Long followeeId);

    Mono<Boolean> existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
