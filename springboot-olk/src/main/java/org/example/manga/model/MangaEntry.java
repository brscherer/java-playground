package org.example.model;

@Entity
@Table(name = "manga_entries")
public class MangaEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int chapter;
    // constructors, getters, setters
}