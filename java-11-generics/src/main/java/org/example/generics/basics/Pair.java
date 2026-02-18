package org.example.generics.basics;

/**
 * Simple generic Pair with two type parameters and a generic swap utility.
 * @param <A> type of the first element
 * @param <B> type of the second element
 */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair() {
    }

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

    public A getFirst() {
        return first;
    }

    public void setFirst(A first) {
        this.first = first;
    }

    public B getSecond() {
        return second;
    }

    public void setSecond(B second) {
        this.second = second;
    }

    /**
     * Generic method that returns a new Pair with elements swapped.
     */
    public static <A, B> Pair<B, A> swap(Pair<A, B> pair) {
        return new Pair<>(pair.getSecond(), pair.getFirst());
    }

    @Override
    public String toString() {
        return "Pair{" + first + ", " + second + '}';
    }
}

