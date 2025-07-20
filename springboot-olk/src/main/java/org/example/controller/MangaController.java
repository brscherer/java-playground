package org.example.controller;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.example.model.MangaEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/manga")
public class MangaController {
    private static final Logger logger = LoggerFactory.getLogger(MangaController.class);
    private final List<MangaEntry> store = new CopyOnWriteArrayList<>();
    private final Counter addCounter;

    public MangaController(MeterRegistry registry) {
        addCounter = registry.counter("manga_add_total");
    }

    @PostMapping("/add")
    public MangaEntry add(@RequestBody MangaEntry entry) {
        store.add(entry);
        addCounter.increment();
        logger.info("Added manga entry: {}", entry);
        return entry;
    }

    @GetMapping("/list")
    public List<MangaEntry> list() {
        logger.info("Listing all manga entries, count={}", store.size());
        return store;
    }
}
