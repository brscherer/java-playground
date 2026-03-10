package com.example.rpg.guild;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JoinGuildRequest {

    @NotNull
    private Long guildId;

}
