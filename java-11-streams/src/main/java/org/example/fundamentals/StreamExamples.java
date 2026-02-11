package org.example.fundamentals;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class StreamExamples {

    static class Student {
        private String name;
        private int age;
        private double gpa;
        private String major;

        public Student(String name, int age, double gpa, String major) {
            this.name = name;
            this.age = age;
            this.gpa = gpa;
            this.major = major;
        }

        public String getName() { return name; }
        public int getAge() { return age; }
        public double getGpa() { return gpa; }
        public String getMajor() { return major; }

        @Override
        public String toString() {
            return name + " (Age: " + age + ", GPA: " + String.format("%.2f", gpa) + ", Major: " + major + ")";
        }
    }

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        List<Student> students = Arrays.asList(
            new Student("Astrid", 20, 3.8, "Computer Science"),
            new Student("Bruno", 22, 3.5, "Mathematics"),
            new Student("Paola", 21, 3.9, "Computer Science"),
            new Student("Wanda", 20, 3.6, "Physics")
        );

        System.out.println("Filter");
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .forEach(System.out::print);
        System.out.println("\n");

        System.out.println("Map");
        numbers.stream()
               .map(n -> n * n)
               .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Filter + Map");
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .map(n -> n * 2)
               .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Distinct Numbers");
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 2, 3, 3, 3, 4, 4, 4, 4);
        numbersWithDuplicates.stream()
                             .distinct()
                             .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Sorted Reverse");
        numbers.stream()
               .sorted(Comparator.reverseOrder())
               .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Limit");
        numbers.stream()
               .limit(5)
               .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Skip");
        numbers.stream()
               .skip(3)
               .forEach(n -> System.out.print(n + " "));
        System.out.println("\n");

        System.out.println("Reduce");
        int sum = numbers.stream()
                         .reduce(0, Integer::sum);
        System.out.println("Sum: " + sum);
        System.out.println("\n");

        System.out.println("Filter + Collect to List");
        List<Integer> evenNumbers = numbers.stream()
                                           .filter(n -> n % 2 == 0)
                                           .collect(Collectors.toList());
        System.out.println("Even numbers: " + evenNumbers);
        System.out.println("\n");

        System.out.println("Filter + Count");
        long count = students.stream()
                            .filter(s -> s.getGpa() > 3.6)
                            .count();
        System.out.println("Count: " + count);
        System.out.println("\n");

        System.out.println("Filter + Find First");
        Optional<Student> firstCS = students.stream()
                                            .filter(s -> "Computer Science".equals(s.getMajor()))
                                            .findFirst();
        firstCS.ifPresent(System.out::println);
        System.out.println("\n");

        System.out.println("Match operations");
        boolean hasHighGPA = students.stream()
                                    .anyMatch(s -> s.getGpa() >= 3.9);
        System.out.println("Any student with GPA >= 3.9? " + hasHighGPA);

        boolean allAdults = students.stream()
                                   .allMatch(s -> s.getAge() >= 18);
        System.out.println("All students 18+? " + allAdults);

        boolean noneBelow20 = students.stream()
                                     .noneMatch(s -> s.getAge() < 20);
        System.out.println("No students below 20? " + noneBelow20);
        System.out.println("\n");

        System.out.println("Collect groupingBy");
        Map<String, List<Student>> byMajor = students.stream()
                                                     .collect(Collectors.groupingBy(Student::getMajor));
        byMajor.forEach((major, studentList) -> {
            System.out.println(major + ":");
            studentList.forEach(s -> System.out.println("  - " + s.getName()));
        });
        System.out.println("\n");

        System.out.println("stream pipeline - Filter + Sort + Map");
        students.stream()
                .filter(s -> "Computer Science".equals(s.getMajor()))
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .map(Student::getName)
                .forEach(System.out::println);
        System.out.println("\n");

        System.out.println("IntStream - Sum using range:");
        int rangeSum = IntStream.rangeClosed(1, 10)
                               .sum();
        System.out.println("Sum 1-10: " + rangeSum);
        System.out.println("\n");

        System.out.println("Peek - Debug Stream (Filter even, multiply by 2):");
        numbers.stream()
               .filter(n -> n % 2 == 0)
               .peek(n -> System.out.println("  Filtered: " + n))
               .map(n -> n * 2)
               .peek(n -> System.out.println("  Mapped: " + n))
               .collect(Collectors.toList());
    }
}

