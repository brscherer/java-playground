package com.example.rpg.character;

import com.example.rpg.guild.Guild;
import com.example.rpg.inventory.InventoryItem;
import com.example.rpg.skill.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    CharacterDto toDto(Character character);

    CharacterStatsDto toDto(CharacterStats stats);

    Character toEntity(CreateCharacterRequest request);

    CharacterStats toEntity(CreateCharacterStatsRequest request);

}
