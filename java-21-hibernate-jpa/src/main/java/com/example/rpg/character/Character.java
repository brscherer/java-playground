package com.example.rpg.character;

import com.example.rpg.guild.Guild;
import com.example.rpg.inventory.InventoryItem;
import com.example.rpg.skill.Skill;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer level;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stats_id")
    private CharacterStats stats;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<InventoryItem> inventoryItems;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "character_skill",
        joinColumns = @JoinColumn(name = "character_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id")
    private Guild guild;

    @Version
    private Long version;

}
