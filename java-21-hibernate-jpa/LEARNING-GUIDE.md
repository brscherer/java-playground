# RPG Character Builder - Learning Guide

This guide explains the key concepts of **Hibernate**, **Spring Boot**, and **MapStruct** as demonstrated in the RPG Character Builder API project.

---

## Table of Contents

1. [Spring Boot Fundamentals](#spring-boot-fundamentals)
2. [Hibernate & JPA](#hibernate--jpa)
3. [MapStruct](#mapstruct)
4. [Architecture & Best Practices](#architecture--best-practices)
5. [Project Implementation Examples](#project-implementation-examples)

---

## Spring Boot Fundamentals

### What is Spring Boot?

Spring Boot is an opinionated framework built on top of the Spring Framework that simplifies creating production-ready applications by:
- Auto-configuring Spring and third-party libraries
- Providing embedded servers (Tomcat, Jetty)
- Offering convention over configuration approach
- Bundling commonly used dependencies

### Key Spring Boot Components in This Project

#### 1. **@SpringBootApplication**
```java
// RpgApplication.java
@SpringBootApplication
public class RpgApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpgApplication.class, args);
    }
}
```

**What it does:**
- Enables auto-configuration of the Spring application
- Activates component scanning for beans
- Enables Spring Boot's auto-configuration feature

#### 2. **Dependency Injection & Inversion of Control (IoC)**

Spring Boot uses dependency injection to manage object dependencies:

```java
// Example from CharacterService.java
@Service
@RequiredArgsConstructor  // Lombok generates constructor with final fields
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final SkillRepository skillRepository;
    
    // Dependencies are injected via constructor
}
```

**Why use DI?**
- Loose coupling between classes
- Easier testing (can inject mocks)
- Better maintainability

#### 3. **Spring Annotations**

| Annotation | Purpose |
|-----------|---------|
| `@Service` | Marks a class as a business logic layer component |
| `@Repository` | Marks a class as data access layer component |
| `@Controller` / `@RestController` | Marks a class as REST endpoint handler |
| `@Component` | Generic Spring-managed component |
| `@Bean` | Explicitly defines a Spring bean in configuration class |
| `@Autowired` | Marks a field/method for automatic dependency injection |
| `@Transactional` | Manages database transactions |

#### 4. **Spring Web (REST Controllers)**

```java
// CharacterController.java example
@RestController
@RequestMapping("/characters")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    
    @PostMapping
    public ResponseEntity<CharacterDto> createCharacter(
        @Valid @RequestBody CreateCharacterRequest request) {
        CharacterDto character = characterService.createCharacter(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(character);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CharacterDto> getCharacter(@PathVariable Long id) {
        CharacterDto character = characterService.getCharacterById(id);
        return ResponseEntity.ok(character);
    }
}
```

**REST Concepts:**
- `@PostMapping`: Handle HTTP POST requests
- `@GetMapping`: Handle HTTP GET requests
- `@PathVariable`: Extract variables from URL path
- `@RequestBody`: Bind request JSON to Java object
- `@Valid`: Trigger validation on request object

#### 5. **Validation**

Spring Boot integrates with Jakarta Validation (formerly Bean Validation):

```java
// CreateCharacterRequest.java
public record CreateCharacterRequest(
    @NotBlank(message = "Character name is required")
    String name,
    
    @NotNull(message = "Level is required")
    @Min(value = 1, message = "Level must be at least 1")
    Integer level,
    
    CreateCharacterStatsRequest stats
) {}
```

**Benefits:**
- Declarative validation rules
- Automatic error handling
- Consistent validation across application

---

## Hibernate & JPA

### What is Hibernate?

**Hibernate** is an Object-Relational Mapping (ORM) framework that:
- Maps Java objects to database tables
- Handles SQL generation automatically
- Provides query language (HQL/JPQL) for database queries
- Manages object lifecycle and relationships

### What is JPA?

**JPA** (Java Persistence API) is a Java standard specification for ORM. **Hibernate** is the most popular implementation.

### Key JPA/Hibernate Concepts Used in This Project

#### 1. **Entity Classes**

An entity represents a database table mapped to a Java class:

```java
// Character.java - Main entity
@Entity
@Table(name = "characters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false)
    private Integer level;
    
    @Version  // Optimistic locking
    private Long version;
}
```

**Key Annotations:**
- `@Entity`: Marks class as a JPA entity
- `@Table`: Specifies table name in database
- `@Id`: Marks primary key field
- `@GeneratedValue`: Auto-generates ID values
- `@Column`: Maps field to database column with constraints
- `@Version`: Enables optimistic locking for concurrency control

#### 2. **Entity Relationships**

JPA supports four types of relationships:

##### **One-to-One Relationship**

```java
// Character has one CharacterStats
@Entity
public class Character {
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "stats_id")
    private CharacterStats stats;
}

@Entity
public class CharacterStats {
    @OneToOne(mappedBy = "stats")
    private Character character;
}
```

**What happens:**
- When Character is deleted, CharacterStats is also deleted (cascade)
- Stats are loaded only when accessed (LAZY)
- `@JoinColumn` specifies foreign key column

##### **One-to-Many Relationship**

```java
// One Guild has many Character members
@Entity
public class Guild {
    @OneToMany(mappedBy = "guild", cascade = CascadeType.ALL)
    private Set<Character> members = new HashSet<>();
}

@Entity
public class Character {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guild_id")
    private Guild guild;
}
```

**What happens:**
- Parent (Guild) manages the relationship
- `mappedBy` tells JPA the field is managed by the child
- Children (Characters) are deleted if guild is deleted

##### **Many-to-Many Relationship**

```java
// Many Characters can have many Skills
@Entity
public class Character {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "character_skill",
        joinColumns = @JoinColumn(name = "character_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();
}

@Entity
public class Skill {
    @ManyToMany(mappedBy = "skills")
    private Set<Character> characters = new HashSet<>();
}
```

**What happens:**
- A junction table (`character_skill`) is created automatically
- Characters and Skills can be associated multiple times
- Changes are synced between entities

#### 3. **Fetch Strategies**

```java
// LAZY loading - loads related entities only when accessed
@ManyToOne(fetch = FetchType.LAZY)
private Guild guild;

// EAGER loading - loads immediately with parent
@OneToOne(fetch = FetchType.EAGER)
private CharacterStats stats;
```

**LAZY vs EAGER:**

| Feature | LAZY | EAGER |
|---------|------|-------|
| When loaded | On access | Immediately with parent |
| Performance | Better for large datasets | Can cause N+1 queries |
| Memory | Uses less memory | Uses more memory |
| Use case | Default choice | Use sparingly |

**Example of N+1 Query Problem:**
```java
// This causes N+1 queries (1 for characters + N for each character's guild)
List<Character> characters = characterRepository.findAll();
for (Character c : characters) {
    System.out.println(c.getGuild().getName()); // Query executed here!
}

// Solution: Use JOIN FETCH in custom query
@Query("SELECT c FROM Character c JOIN FETCH c.stats")
List<Character> findAllWithStats();
```

#### 4. **Cascade Types**

Cascade defines what happens to related entities when parent is modified:

```java
@OneToOne(cascade = CascadeType.ALL)  // All operations cascade
@OneToMany(cascade = CascadeType.REMOVE)  // Only delete cascades
```

| Cascade Type | Effect |
|-------------|--------|
| ALL | All operations cascade (persist, merge, remove, refresh, detach) |
| PERSIST | New child entities are saved when parent is saved |
| MERGE | Child entities are updated when parent is merged |
| REMOVE | Child entities are deleted when parent is deleted |
| REFRESH | Child entities are refreshed when parent is refreshed |
| DETACH | Child entities are detached when parent is detached |

#### 5. **Transactions**

```java
@Service
public class CharacterService {
    @Transactional  // Method-level transaction
    public CharacterDto createCharacter(CreateCharacterRequest request) {
        Character character = new Character();
        // Changes are automatically committed at method end
        return characterRepository.save(character);
    }
}
```

**Transaction Properties (ACID):**
- **Atomicity**: All or nothing - entire operation succeeds or fails
- **Consistency**: Database remains in valid state
- **Isolation**: Concurrent transactions don't interfere
- **Durability**: Committed data persists

#### 6. **Repository Pattern**

```java
// CharacterRepository.java - Spring Data JPA
@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    
    // Automatic implementation by Spring
    List<Character> findByLevel(Integer level);
    
    // Custom JPQL query with JOIN FETCH to avoid N+1
    @Query("SELECT c FROM Character c JOIN FETCH c.stats WHERE c.id = :id")
    Optional<Character> findByIdWithStats(@Param("id") Long id);
}
```

**Spring Data JPA Magic:**
- No need to write SQL
- Method names are parsed into queries
- Common operations provided by JpaRepository

#### 7. **Optimistic Locking**

```java
@Entity
public class Character {
    @Version
    private Long version;
}
```

**What it does:**
- Each update increments `version`
- If two threads try to update same record, second one fails
- No database-level locks (better performance)

```java
// If update fails due to version mismatch:
// org.hibernate.StaleObjectStateException is thrown
```

#### 8. **Pagination**

```java
@Transactional(readOnly = true)
public Page<CharacterDto> listCharacters(int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Character> characters = characterRepository.findAll(pageRequest);
    return characters.map(characterMapper::toDto);
}
```

**Page vs List:**
- `Page`: Contains metadata (total elements, total pages, current page)
- `List`: Just the data

---

## MapStruct

### What is MapStruct?

**MapStruct** is a code generator that creates type-safe bean mappers:
- Compile-time code generation (no reflection)
- Strongly typed
- High performance
- Can map nested objects and collections

### Why Use DTOs?

**DTO** (Data Transfer Object) separates internal entity structure from API response:

```java
// Entity - contains ALL fields including internal references
@Entity
public class Character {
    private Long id;
    private String name;
    private Long version;  // Internal, shouldn't expose to API
    private Set<Skill> skills;  // Complex relationships
}

// DTO - only expose necessary fields
public record CharacterDto(
    Long id,
    String name,
    Integer level,
    CharacterStatsDto stats,
    Set<SkillDto> skills
) {}
```

**Benefits:**
- Hide internal implementation
- Version API independently from database schema
- Validate input separately from entity
- Improve API documentation

### MapStruct in This Project

#### 1. **Basic Mapper Interface**

```java
@Mapper(componentModel = "spring")
public interface CharacterMapper {
    
    // Entity to DTO
    CharacterDto toDto(Character character);
    
    // DTO to Entity
    Character toEntity(CreateCharacterRequest request);
    
    // Nested mapping
    CharacterStatsDto toDto(CharacterStats stats);
    CharacterStats toEntity(CreateCharacterStatsRequest request);
}
```

**Annotations:**
- `@Mapper`: Marks interface as mapper
- `componentModel = "spring"`: Makes mapper a Spring bean (auto-injected)

#### 2. **Custom Mapping Rules**

```java
@Mapper(componentModel = "spring")
public interface InventoryMapper {
    
    // Default mapping for matching field names
    InventoryItemDto toDto(InventoryItem item);
    
    // Custom mapping when field names don't match
    @Mapping(source = "type.id", target = "typeId")
    @Mapping(source = "type.name", target = "typeName")
    InventoryItemDto toDtoCustom(InventoryItem item);
}
```

#### 3. **Collection Mapping**

```java
@Mapper(componentModel = "spring")
public interface CharacterMapper {
    
    // MapStruct automatically handles Set<T> -> Set<TDto> conversion
    @Mapping(source = "skills", target = "skills")
    CharacterDto toDto(Character character);
}
```

#### 4. **How MapStruct Works**

**At Compile Time:**
```
CharacterMapper.java (interface) 
    ↓ (Annotation Processor)
    → CharacterMapperImpl.java (generated class)
    → CharacterMapperImpl.class (compiled bytecode)
```

**Generated Code Example:**
```java
// Automatically generated by MapStruct
public class CharacterMapperImpl implements CharacterMapper {
    @Override
    public CharacterDto toDto(Character character) {
        if (character == null) {
            return null;
        }
        
        CharacterDto dto = new CharacterDto(
            character.getId(),
            character.getName(),
            character.getLevel(),
            statsToDto(character.getStats()),
            new HashSet<>(character.getSkills())
        );
        
        return dto;
    }
}
```

#### 5. **Using Mappers in Services**

```java
@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;  // Injected
    
    public CharacterDto createCharacter(CreateCharacterRequest request) {
        Character character = characterMapper.toEntity(request);
        Character saved = characterRepository.save(character);
        return characterMapper.toDto(saved);  // Convert back to DTO
    }
}
```

---

## Architecture & Best Practices

### Layered Architecture

This project uses a **three-layer architecture**:

```
┌─────────────────────────────────┐
│     Controller Layer            │ ← HTTP requests/responses
│  (REST endpoints)               │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│     Service Layer               │ ← Business logic
│  (Transactions, validation)     │
└──────────────┬──────────────────┘
               │
┌──────────────▼──────────────────┐
│     Repository Layer            │ ← Data access
│  (Database queries)             │
└─────────────────────────────────┘
```

### Layer Responsibilities

#### **Controller Layer**
- Receive HTTP requests
- Validate input (using `@Valid`)
- Convert DTOs to/from service
- Return HTTP responses

#### **Service Layer**
- Business logic and rules
- Transaction management (`@Transactional`)
- Orchestrate multiple repositories
- Convert entities to DTOs

#### **Repository Layer**
- Database queries
- Entity persistence
- Spring Data JPA handles most work

### Constructor Injection (Using Lombok)

```java
@Service
@RequiredArgsConstructor  // Generates constructor with final fields
public class CharacterService {
    private final CharacterRepository characterRepository;
    private final SkillRepository skillRepository;
    private final CharacterMapper characterMapper;
    
    // No need to write:
    // public CharacterService(CharacterRepository repo, ...) { ... }
}
```

**Benefits:**
- Cleaner code
- Final fields (immutable)
- Easy to test (pass mocks to constructor)

### Record Classes (Java 16+)

```java
// Immutable data carrier - automatically generates:
// constructor, equals, hashCode, toString
public record CreateCharacterRequest(
    String name,
    Integer level,
    CreateCharacterStatsRequest stats
) {}
```

**vs Traditional Class:**
```java
public class CreateCharacterRequest {
    private final String name;
    private final Integer level;
    
    public CreateCharacterRequest(String name, Integer level) {
        this.name = name;
        this.level = level;
    }
    
    public String getName() { return name; }
    public Integer getLevel() { return level; }
    
    @Override
    public boolean equals(Object o) { ... }
    
    @Override
    public int hashCode() { ... }
    
    @Override
    public String toString() { ... }
}
```

---

## Project Implementation Examples

### Example 1: Creating a Character with Stats and Skills

**Flow:**
1. Client sends POST request with CreateCharacterRequest
2. Controller receives and validates request
3. Service creates entities and establishes relationships
4. Mapper converts back to DTO for response

**Code Walkthrough:**

```java
// 1. Controller receives request
@PostMapping
public ResponseEntity<CharacterDto> createCharacter(
    @Valid @RequestBody CreateCharacterRequest request) {
    CharacterDto character = characterService.createCharacter(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(character);
}

// 2. Service handles business logic
@Transactional
public CharacterDto createCharacter(CreateCharacterRequest request) {
    // Map DTO to Entity
    Character character = characterMapper.toEntity(request);
    CharacterStats stats = characterMapper.toEntity(request.stats());
    
    // Establish relationship
    character.setStats(stats);
    
    // Save to database (cascade saves stats too)
    Character saved = characterRepository.save(character);
    
    // Map Entity back to DTO
    return characterMapper.toDto(saved);
}

// 3. Mapper converts types
Character toEntity(CreateCharacterRequest request) {
    // MapStruct generates:
    // new Character(request.name(), request.level(), ...)
}
```

**Database Sequence:**
```
1. INSERT INTO character_stats (strength, agility, intelligence) 
   VALUES (10, 8, 12)
2. INSERT INTO characters (name, level, stats_id) 
   VALUES ('Aragorn', 5, 1)
3. Return Character with populated stats
4. Mapper converts to CharacterDto
```

### Example 2: Adding a Skill (Many-to-Many)

```java
// Controller
@PostMapping("/{id}/skills")
public ResponseEntity<CharacterDto> addSkill(
    @PathVariable Long id,
    @Valid @RequestBody AddSkillRequest request) {
    CharacterDto character = characterService.addSkill(id, request.skillId());
    return ResponseEntity.ok(character);
}

// Service
@Transactional
public CharacterDto addSkill(Long characterId, Long skillId) {
    // Fetch entities
    Character character = characterRepository.findById(characterId)
        .orElseThrow(() -> new NotFoundException("Character not found"));
    Skill skill = skillRepository.findById(skillId)
        .orElseThrow(() -> new NotFoundException("Skill not found"));
    
    // Establish bidirectional relationship
    character.getSkills().add(skill);
    skill.getCharacters().add(character);
    
    // Hibernate tracks changes, updates junction table on commit
    Character saved = characterRepository.save(character);
    return characterMapper.toDto(saved);
}
```

**Database Result:**
```
INSERT INTO character_skill (character_id, skill_id) 
VALUES (1, 5)
```

### Example 3: Lazy Loading with JOIN FETCH

**Problem - N+1 Query Issue:**
```java
// Causes extra queries!
List<Character> characters = characterRepository.findAll();
for (Character c : characters) {
    System.out.println(c.getStats().getStrength()); // Extra query per character!
}
// Total queries: 1 (findAll) + N (getStats for each)
```

**Solution - JOIN FETCH:**
```java
// Custom query that loads stats in single query
@Query("SELECT c FROM Character c JOIN FETCH c.stats")
List<Character> findAllWithStats();

// Now this doesn't cause extra queries
List<Character> characters = characterRepository.findAllWithStats();
for (Character c : characters) {
    System.out.println(c.getStats().getStrength()); // No extra queries!
}
// Total queries: 1
```

---

## Key Takeaways

### Spring Boot
- ✅ Simplifies Spring application setup
- ✅ Provides auto-configuration
- ✅ Enables rapid development
- ✅ Built-in server (Tomcat)

### Hibernate/JPA
- ✅ Maps Java objects to database tables
- ✅ Handles SQL generation
- ✅ Manages object relationships (1-1, 1-N, N-N)
- ✅ Provides transaction management
- ✅ Offers multiple fetch strategies
- ⚠️ Watch out for N+1 query problems

### MapStruct
- ✅ Generates mapper code at compile time
- ✅ Type-safe (no reflection)
- ✅ High performance
- ✅ Separates API contracts from entities

### Best Practices Used
- ✅ Layered architecture (Controller → Service → Repository)
- ✅ Constructor injection with final fields
- ✅ DTOs for API contracts
- ✅ Transactions for data consistency
- ✅ Pagination for large datasets
- ✅ Validation at API boundaries
- ✅ Lazy loading with JOIN FETCH optimization

---

## Further Learning Resources

1. **Hibernate Official Docs**: https://hibernate.org/orm/documentation/
2. **Spring Data JPA Docs**: https://spring.io/projects/spring-data-jpa
3. **MapStruct User Guide**: https://mapstruct.org/documentation/stable/reference/html/
4. **Spring Boot Reference**: https://spring.io/projects/spring-boot
5. **Jakarta Persistence API**: https://jakarta.ee/specifications/persistence/

---

Happy learning! 🚀

