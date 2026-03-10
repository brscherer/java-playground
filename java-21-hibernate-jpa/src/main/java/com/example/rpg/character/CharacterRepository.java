package com.example.rpg.character;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("SELECT c FROM Character c JOIN FETCH c.stats WHERE c.id = :id")
    Optional<Character> findByIdWithStats(@Param("id") Long id);

}
