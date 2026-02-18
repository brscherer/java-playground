package org.example.generics.basics;

/**
 * Simple generic holder that demonstrates basic use of type parameters.
 * @param <T> the type of the held value
 */
public class Box<T> {
    private T value;

    public Box() {
    }

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box{" + value + '}';
    }
}

