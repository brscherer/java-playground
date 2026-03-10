package com.example.rpg.guild;

import com.example.rpg.character.Character;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "guild", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Character> members;

}
