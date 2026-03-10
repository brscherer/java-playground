package com.example.rpg.character;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CharacterStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer strength;

    @Column(nullable = false)
    private Integer agility;

    @Column(nullable = false)
    private Integer intelligence;

}
