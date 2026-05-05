package org.example.creational.factorymethod;

// Factory Method: Defines an interface for creating an object, letting subclasses decide the instantiation.
// Example: A toy factory producing different toys.

interface Toy {
    void play();
}

record Doll(String name) implements Toy {
    @Override
    public void play() {
        System.out.println(name + " doll says: Let's have a tea party!");
    }
}

record Car(String model) implements Toy {
    @Override
    public void play() {
        System.out.println(model + " car zooms: Vroom vroom!");
    }
}

abstract class ToyFactory {
    abstract Toy createToy(String type);

    public void produceToy(String type) {
        Toy toy = createToy(type);
        toy.play();
    }
}

class DollFactory extends ToyFactory {
    @Override
    Toy createToy(String type) {
        return new Doll(type);
    }
}

class CarFactory extends ToyFactory {
    @Override
    Toy createToy(String type) {
        return new Car(type);
    }
}

public class ToyFactoryDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Factory Method pattern:");
        ToyFactory dollFactory = new DollFactory();
        dollFactory.produceToy("Barbie");

        ToyFactory carFactory = new CarFactory();
        carFactory.produceToy("Ferrari");
    }
}
