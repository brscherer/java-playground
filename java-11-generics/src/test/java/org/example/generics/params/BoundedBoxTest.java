package org.example.generics.params;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoundedBoxTest {

    @Test
    void getAsDoubleForInteger() {
        BoundedBox<Integer> b = new BoundedBox<>(5);
        assertEquals(5.0, b.getAsDouble(), 1e-9);
    }

    @Test
    void getAsDoubleForDouble() {
        BoundedBox<Double> b = new BoundedBox<>(3.14);
        assertEquals(3.14, b.getAsDouble(), 1e-9);
    }

    @Test
    void boundedBoxShouldHaveOneTypeParameterNamedT() {
        assertEquals(1, BoundedBox.class.getTypeParameters().length);
        assertEquals("T", BoundedBox.class.getTypeParameters()[0].getName());
    }
}

