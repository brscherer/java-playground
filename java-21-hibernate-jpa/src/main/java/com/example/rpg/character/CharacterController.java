package com.example.rpg.character;

import com.example.rpg.inventory.AddItemRequest;
import com.example.rpg.skill.AddSkillRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDto createCharacter(@Valid @RequestBody CreateCharacterRequest request) {
        return characterService.createCharacter(request);
    }

    @GetMapping("/{id}")
    public CharacterDto getCharacter(@PathVariable Long id) {
        return characterService.getCharacter(id);
    }

    @GetMapping
    public Page<CharacterDto> getCharacters(Pageable pageable) {
        return characterService.getCharacters(pageable);
    }

    @PostMapping("/{id}/skills")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSkill(@PathVariable Long id, @Valid @RequestBody AddSkillRequest request) {
        characterService.addSkill(id, request);
    }

    @PostMapping("/{id}/items")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addItem(@PathVariable Long id, @Valid @RequestBody AddItemRequest request) {
        characterService.addItem(id, request);
    }

    @PostMapping("/{id}/guild")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void joinGuild(@PathVariable Long id, @RequestBody Long guildId) {
        characterService.joinGuild(id, guildId);
    }

}
