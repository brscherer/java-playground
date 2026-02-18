package org.example.generics.wildcards;

import java.util.List;

/**
 * Utility methods demonstrating wildcard use cases.
 * Wildcards (?) are used when the exact type is unknown or irrelevant,
 * and to enforce read-only (? extends T) or write-only (? super T) semantics.
 */
public class WildcardUtils {

    /**
     * Copy elements from source list to destination list.
     * Source is a producer (read from it) — use ? extends T.
     * Destination is a consumer (write to it) — use ? super T.
     *
     * @param <T> the element type
     * @param src source list (producer)
     * @param dst destination list (consumer)
     */
    public static <T> void copyFromTo(List<? extends T> src, List<? super T> dst) {
        for (T item : src) {
            dst.add(item);
        }
    }

    /**
     * Sum elements from a list of Numbers.
     * Since Number types (Integer, Double, etc.) are subclasses of Number,
     * use ? extends Number to accept any numeric type.
     *
     * @param numbers list of numeric values
     * @return sum of all elements, or 0.0 if list is empty
     */
    public static double sumList(List<? extends Number> numbers) {
        double sum = 0.0;
        for (Number num : numbers) {
            sum += num.doubleValue();
        }
        return sum;
    }

    /**
     * Print items from a list of unknown type.
     * Use wildcard (?) when type is irrelevant.
     *
     * @param items list of any type
     */
    public static void printList(List<?> items) {
        for (Object item : items) {
            System.out.println(item);
        }
    }
}

