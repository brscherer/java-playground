# POC Java - Streams & Lambdas

#### `LambdaExamples.java`
- Simple lambda expressions
- Lambda with multiple statements
- Functional interfaces (@FunctionalInterface)
- Predicate functional interface
- Method references
- Comparison: Lambda vs Anonymous Classes

---

#### `StreamExamples.java`

**Intermediate Operations (Lazy):**
- `filter()` - Keep matching elements
- `map()` - Transform elements
- `distinct()` - Remove duplicates
- `sorted()` - Order elements
- `limit()` - Take first N
- `skip()` - Skip first N
- `flatMap()` - Flatten nested structures
- `peek()` - Debug intermediate values

**Terminal Operations (Eager):**
- `forEach()` - Perform action on each
- `collect()` - Convert to collection
- `reduce()` - Combine into single value
- `count()` - Count elements
- `findFirst()` / `findAny()` - Get element
- `anyMatch()` / `allMatch()` / `noneMatch()` - Conditional checks
- `min()` / `max()` - Find extremes
- `groupingBy()` - Group by criteria
- `IntStream` - Primitive value streams

---

## 💡 Key Takeaways

### Lambdas
- Concise syntax for functional interfaces
- Enables functional programming style
- More readable than anonymous classes
- Modern Java standard since 8

### Streams
- Declarative data processing
- Lazy evaluation for efficiency
- Chainable operations for readability
- Powerful aggregation and transformation
