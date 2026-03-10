package com.example.rpg.skill;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDto toDto(Skill skill);

}
