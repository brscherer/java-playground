package org.example.generics.wildcards;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WildcardUtilsTest {

    @Test
    void copyFromToShouldCopyIntegersToNumbers() {
        List<Integer> src = Arrays.asList(1, 2, 3);
        List<Number> dst = new ArrayList<>();

        WildcardUtils.copyFromTo(src, dst);

        assertEquals(3, dst.size());
        assertEquals(1, dst.get(0));
        assertEquals(2, dst.get(1));
        assertEquals(3, dst.get(2));
    }

    @Test
    void copyFromToShouldCopyStringsToObjects() {
        List<String> src = Arrays.asList("a", "b", "c");
        List<Object> dst = new ArrayList<>();

        WildcardUtils.copyFromTo(src, dst);

        assertEquals(3, dst.size());
        assertTrue(dst.contains("a"));
        assertTrue(dst.contains("b"));
        assertTrue(dst.contains("c"));
    }

    @Test
    void sumListShouldSumIntegers() {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        double sum = WildcardUtils.sumList(integers);
        assertEquals(10.0, sum, 1e-9);
    }

    @Test
    void sumListShouldSumDoubles() {
        List<Double> doubles = Arrays.asList(1.5, 2.5, 3.0);
        double sum = WildcardUtils.sumList(doubles);
        assertEquals(7.0, sum, 1e-9);
    }

    @Test
    void sumListShouldSumMixedNumbers() {
        List<Number> mixed = Arrays.asList(1, 2.5, 3L, 4.5);
        double sum = WildcardUtils.sumList(mixed);
        assertEquals(11.0, sum, 1e-9);
    }

    @Test
    void sumListOfEmptyShouldReturnZero() {
        List<Integer> empty = new ArrayList<>();
        double sum = WildcardUtils.sumList(empty);
        assertEquals(0.0, sum, 1e-9);
    }

    @Test
    void printListShouldAcceptAnyType() {
        List<String> strings = Arrays.asList("hello", "world");
        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Object> objects = new ArrayList<>();

        // Should compile and not throw
        assertDoesNotThrow(() -> WildcardUtils.printList(strings));
        assertDoesNotThrow(() -> WildcardUtils.printList(integers));
        assertDoesNotThrow(() -> WildcardUtils.printList(objects));
    }
}

