package com.example.rpg.character;

import com.example.rpg.guild.GuildDto;
import com.example.rpg.inventory.InventoryItemDto;
import com.example.rpg.skill.SkillDto;
import lombok.Data;

import java.util.Set;

@Data
public class CharacterDto {

    private Long id;
    private String name;
    private Integer level;
    private CharacterStatsDto stats;
    private Set<InventoryItemDto> inventoryItems;
    private Set<SkillDto> skills;
    private GuildDto guild;

}
