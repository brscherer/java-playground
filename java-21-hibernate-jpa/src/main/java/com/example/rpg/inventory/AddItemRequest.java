package com.example.rpg.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long itemTypeId;

}
