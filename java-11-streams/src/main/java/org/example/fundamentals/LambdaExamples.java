package org.example.fundamentals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


public class LambdaExamples {

    // Functional Interface - Interface with single abstract method
    @FunctionalInterface
    interface Calculator {
        int calculate(int a, int b);
    }

    @FunctionalInterface
    interface Greeting {
        void greet(String name);
    }

    public static void main(String[] args) {
        Calculator add = (a, b) -> a + b;
        System.out.println("5 + 3 = " + add.calculate(5, 3));
        System.out.println();

        Calculator subtract = (a, b) -> a - b;
        System.out.println("10 - 4 = " + subtract.calculate(10, 4));
        System.out.println();

        Calculator multiply = (a, b) -> {
            int result = a * b;
            System.out.println("Calculating: " + a + " * " + b);
            return result;
        };
        System.out.println("Result: " + multiply.calculate(6, 7));
        System.out.println();

        Greeting greeting = name -> System.out.println("Hello, " + name + "!");
        greeting.greet("Astrid");
        greeting.greet("Bruno");
        System.out.println();

        // Predicate (Built-in Functional Interface)
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 4 even? " + isEven.test(4));
        System.out.println("Is 7 even? " + isEven.test(7));
        System.out.println();

        List<String> names = new ArrayList<>();
        names.add("Astrid");
        names.add("Bruno");

        System.out.println("Using lambda:");
        names.forEach(name -> System.out.println(name));
        System.out.println();

        System.out.println("Using method reference:");
        names.forEach(System.out::println);
        System.out.println();


        // Old way - Anonymous Class
        Calculator oldWay = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a / b;
            }
        };
        System.out.println("Old way (Anonymous): 20 / 4 = " + oldWay.calculate(20, 4));

        // New way - Lambda
        Calculator newWay = (a, b) -> a / b;
        System.out.println("New way (Lambda): 20 / 4 = " + newWay.calculate(20, 4));
    }
}

