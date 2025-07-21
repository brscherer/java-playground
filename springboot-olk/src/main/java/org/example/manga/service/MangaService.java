package org.example.manga.service;
import java.util.List;

import org.example.manga.model.MangaEntryDto;

public interface MangaService {
    MangaEntryDto addEntry(String name, int chapter);
    List<MangaEntryDto> listEntries();
}