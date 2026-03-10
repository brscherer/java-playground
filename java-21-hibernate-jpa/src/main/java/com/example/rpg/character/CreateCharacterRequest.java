package com.example.rpg.character;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCharacterRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer level;

    @NotNull
    private CreateCharacterStatsRequest stats;

}
