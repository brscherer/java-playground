package com.example.rpg.config;

import com.example.rpg.character.Character;
import com.example.rpg.character.CharacterRepository;
import com.example.rpg.character.CharacterStats;
import com.example.rpg.guild.Guild;
import com.example.rpg.guild.GuildRepository;
import com.example.rpg.inventory.ItemType;
import com.example.rpg.inventory.ItemTypeRepository;
import com.example.rpg.skill.Skill;
import com.example.rpg.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SkillRepository skillRepository;
    private final ItemTypeRepository itemTypeRepository;
    private final GuildRepository guildRepository;
    private final CharacterRepository characterRepository;

    @Override
    public void run(String... args) throws Exception {
        Skill fireMagic = new Skill();
        fireMagic.setName("Fire Magic");
        skillRepository.save(fireMagic);

        Skill swordsmanship = new Skill();
        swordsmanship.setName("Swordsmanship");
        skillRepository.save(swordsmanship);

        ItemType weapon = new ItemType();
        weapon.setName("Weapon");
        itemTypeRepository.save(weapon);

        ItemType armor = new ItemType();
        armor.setName("Armor");
        itemTypeRepository.save(armor);

        Guild warriors = new Guild();
        warriors.setName("Warriors");
        guildRepository.save(warriors);

        Guild mages = new Guild();
        mages.setName("Mages");
        guildRepository.save(mages);

        CharacterStats stats = new CharacterStats();
        stats.setStrength(10);
        stats.setAgility(8);
        stats.setIntelligence(12);

        Character character = new Character();
        character.setName("Aragorn");
        character.setLevel(5);
        character.setStats(stats);
        characterRepository.save(character);
    }

}
