package com.example.rpg.inventory;

import lombok.Data;

@Data
public class InventoryItemDto {

    private Long id;
    private String name;
    private ItemTypeDto itemType;

}
