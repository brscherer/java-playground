package com.example.rpg.character;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCharacterStatsRequest {

    @NotNull
    private Integer strength;

    @NotNull
    private Integer agility;

    @NotNull
    private Integer intelligence;

}
