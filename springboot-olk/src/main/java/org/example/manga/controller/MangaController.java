package org.example.manga.controller;

import java.util.List;

import org.example.manga.model.MangaEntryDto;
import org.example.manga.service.MangaService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/manga")
@Validated
public class MangaController {
    private final MangaService service;

    public MangaController(MangaService service) {
        this.service = service;
    }

    record AddRequest(@NotBlank String name, @Positive int chapter) {}

    @PostMapping("/add")
    public MangaEntryDto add(@RequestBody @Valid AddRequest req) {
        return service.addEntry(req.name(), req.chapter());
    }

    @GetMapping("/list")
    public List<MangaEntryDto> list() {
        return service.listEntries();
    }
}
