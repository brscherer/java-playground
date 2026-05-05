package org.example.creational.abstractfactory;

// Abstract Factory: Provides an interface for creating families of related objects without specifying concrete classes.
// Example: A furniture factory creating themed sets of chairs and tables.

interface Chair {
    void sitOn();
}

interface Table {
    void placeOn();
}

record VictorianChair() implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Sitting elegantly on a Victorian chair.");
    }
}

record VictorianTable() implements Table {
    @Override
    public void placeOn() {
        System.out.println("Placing items on an ornate Victorian table.");
    }
}

record ModernChair() implements Chair {
    @Override
    public void sitOn() {
        System.out.println("Lounging on a sleek modern chair.");
    }
}

record ModernTable() implements Table {
    @Override
    public void placeOn() {
        System.out.println("Setting up on a minimalist modern table.");
    }
}

interface FurnitureFactory {
    Chair createChair();
    Table createTable();
}

class VictorianFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new VictorianChair();
    }

    @Override
    public Table createTable() {
        return new VictorianTable();
    }
}

class ModernFurnitureFactory implements FurnitureFactory {
    @Override
    public Chair createChair() {
        return new ModernChair();
    }

    @Override
    public Table createTable() {
        return new ModernTable();
    }
}

public class FurnitureFactoryDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Abstract Factory pattern:");
        FurnitureFactory victorianFactory = new VictorianFurnitureFactory();
        Chair victorianChair = victorianFactory.createChair();
        Table victorianTable = victorianFactory.createTable();
        victorianChair.sitOn();
        victorianTable.placeOn();

        FurnitureFactory modernFactory = new ModernFurnitureFactory();
        Chair modernChair = modernFactory.createChair();
        Table modernTable = modernFactory.createTable();
        modernChair.sitOn();
        modernTable.placeOn();
    }
}
