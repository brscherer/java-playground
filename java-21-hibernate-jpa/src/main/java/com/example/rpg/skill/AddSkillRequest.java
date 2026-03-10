package com.example.rpg.skill;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddSkillRequest {

    @NotNull
    private Long skillId;

}
