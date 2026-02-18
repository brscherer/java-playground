package org.example.generics.params;

/**
 * Generic container limited to subclasses of {@link Number}.
 * Demonstrates bounded type parameters.
 * @param <T> a Number subtype
 */
public class BoundedBox<T extends Number> {
    private T value;

    public BoundedBox() {
    }

    public BoundedBox(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    /**
     * Example method that uses the Number API available due to the bound.
     * @return the held value as a double, or NaN if null
     */
    public double getAsDouble() {
        return value == null ? Double.NaN : value.doubleValue();
    }

    @Override
    public String toString() {
        return "BoundedBox{" + value + '}';
    }
}

