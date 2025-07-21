package org.example.manga.repository;
import org.example.manga.model.MangaEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MangaEntryRepository extends JpaRepository<MangaEntry, Long> {
}
