package org.example.manga.service;

import java.util.List;
import java.util.stream.Collectors;

import org.example.manga.mapper.MangaMapper;
import org.example.manga.model.MangaEntry;
import org.example.manga.model.MangaEntryDto;
import org.example.manga.repository.MangaEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class MangaServiceImpl implements MangaService {
    private final MangaEntryRepository repository;
    private final MangaMapper mapper;
    private final Counter addCounter;
    private static final Logger logger = LoggerFactory.getLogger(MangaServiceImpl.class);

    public MangaServiceImpl(MangaEntryRepository repository,
                            MangaMapper mapper,
                            MeterRegistry registry) {
        this.repository = repository;
        this.mapper = mapper;
        this.addCounter = registry.counter("manga_add_total");
    }

    @Override
    public MangaEntryDto addEntry(String name, int chapter) {
        MangaEntry entity = new MangaEntry();
        entity.setName(name);
        entity.setChapter(chapter);
        MangaEntry saved = repository.save(entity);
        addCounter.increment();
        logger.info("Saved MangaEntry id={} name={} chapter={}", saved.getId(), name, chapter);
        return mapper.toDto(saved);
    }

    @Override
    public List<MangaEntryDto> listEntries() {
        List<MangaEntry> all = repository.findAll();
        logger.info("Listing {} entries", all.size());
        return all.stream()
                  .map(mapper::toDto)
                  .collect(Collectors.toList());
    }
}