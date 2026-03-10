package com.example.rpg.inventory;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryItemDto toDto(InventoryItem item);

    ItemTypeDto toDto(ItemType itemType);

}
