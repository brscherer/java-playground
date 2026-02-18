package org.example.generics.basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {

    @Test
    void setAndGetShouldReturnSameValue() {
        Box<String> box = new Box<>();
        box.set("hello");
        assertEquals("hello", box.get());
    }

    @Test
    void constructorShouldInitializeValue() {
        Box<Integer> box = new Box<>(42);
        assertEquals(Integer.valueOf(42), box.get());
    }

    @Test
    void boxClassShouldHaveOneTypeParameter() {
        assertEquals(1, Box.class.getTypeParameters().length);
        assertEquals("T", Box.class.getTypeParameters()[0].getName());
    }
}

