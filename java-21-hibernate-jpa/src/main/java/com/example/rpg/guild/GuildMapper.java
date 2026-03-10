package com.example.rpg.guild;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuildMapper {

    GuildDto toDto(Guild guild);

}
