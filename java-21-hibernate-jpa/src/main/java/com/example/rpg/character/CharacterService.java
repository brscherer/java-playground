package com.example.rpg.character;

import com.example.rpg.guild.Guild;
import com.example.rpg.guild.GuildRepository;
import com.example.rpg.inventory.AddItemRequest;
import com.example.rpg.inventory.InventoryItem;
import com.example.rpg.inventory.InventoryItemRepository;
import com.example.rpg.inventory.ItemType;
import com.example.rpg.inventory.ItemTypeRepository;
import com.example.rpg.skill.AddSkillRequest;
import com.example.rpg.skill.Skill;
import com.example.rpg.skill.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final SkillRepository skillRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final GuildRepository guildRepository;

    @Transactional
    public CharacterDto createCharacter(CreateCharacterRequest request) {
        Character character = characterMapper.toEntity(request);
        character = characterRepository.save(character);
        return characterMapper.toDto(character);
    }

    @Transactional(readOnly = true)
    public CharacterDto getCharacter(Long id) {
        Character character = characterRepository.findByIdWithStats(id)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        return characterMapper.toDto(character);
    }

    @Transactional(readOnly = true)
    public Page<CharacterDto> getCharacters(Pageable pageable) {
        return characterRepository.findAll(pageable).map(characterMapper::toDto);
    }

    @Transactional
    public void addSkill(Long characterId, AddSkillRequest request) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() -> new EntityNotFoundException("Skill not found"));
        character.getSkills().add(skill);
        characterRepository.save(character);
    }

    @Transactional
    public void addItem(Long characterId, AddItemRequest request) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        ItemType itemType = itemTypeRepository.findById(request.getItemTypeId())
                .orElseThrow(() -> new EntityNotFoundException("ItemType not found"));
        InventoryItem item = new InventoryItem();
        item.setName(request.getName());
        item.setCharacter(character);
        item.setItemType(itemType);
        inventoryItemRepository.save(item);
    }

    @Transactional
    public void joinGuild(Long characterId, Long guildId) {
        Character character = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Character not found"));
        Guild guild = guildRepository.findById(guildId)
                .orElseThrow(() -> new EntityNotFoundException("Guild not found"));
        character.setGuild(guild);
        characterRepository.save(character);
    }

}
