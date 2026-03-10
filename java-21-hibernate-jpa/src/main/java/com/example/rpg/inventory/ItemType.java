package com.example.rpg.inventory;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ItemType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
