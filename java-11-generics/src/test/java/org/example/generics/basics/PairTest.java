package org.example.generics.basics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairTest {

    @Test
    void setAndGetShouldReturnSameValues() {
        Pair<String, Integer> p = new Pair<>("a", 1);
        assertEquals("a", p.getFirst());
        assertEquals(Integer.valueOf(1), p.getSecond());
    }

    @Test
    void swapShouldReturnSwappedPair() {
        Pair<String, Integer> p = new Pair<>("a", 1);
        Pair<Integer, String> swapped = Pair.swap(p);
        assertEquals(Integer.valueOf(1), swapped.getFirst());
        assertEquals("a", swapped.getSecond());
    }

    @Test
    void pairClassShouldHaveTwoTypeParameters() {
        assertEquals(2, Pair.class.getTypeParameters().length);
    }
}

