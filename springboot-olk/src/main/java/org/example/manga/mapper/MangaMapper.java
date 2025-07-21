package org.example.manga.mapper;

import org.example.manga.model.MangaEntry;
import org.example.manga.model.MangaEntryDto;

public class MangaMapper {

  public MangaEntryDto toDto(MangaEntry saved) {
    return new MangaEntryDto(saved.getId(), saved.getName(), saved.getChapter());
  }
  
}
