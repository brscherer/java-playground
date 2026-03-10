package com.example.rpg.skill;

import com.example.rpg.character.Character;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private Set<Character> characters;

}
