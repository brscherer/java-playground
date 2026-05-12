package org.example.structural.adapter;

// Adapter: Allows incompatible interfaces to work together.
// Fun example: Adapting a wild turkey to behave like a duck in a magical feast.

interface Duck {
    void quack();
    void fly();
}

class MallardDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("Quack!");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying!");
    }
}

interface Turkey {
    void gobble();
    void fly();
}

class WildTurkey implements Turkey {
    @Override
    public void gobble() {
        System.out.println("Gobble gobble!");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying a short distance!");
    }
}

class TurkeyAdapter implements Duck {
    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) {
        this.turkey = turkey;
    }

    @Override
    public void quack() {
        turkey.gobble();
    }

    @Override
    public void fly() {
        turkey.fly();
    }
}

public class AdapterDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Adapter pattern:");
        Duck duck = new MallardDuck();
        Turkey turkey = new WildTurkey();
        Duck turkeyAdapter = new TurkeyAdapter(turkey);

        System.out.println("Duck says:");
        duck.quack();
        duck.fly();

        System.out.println("Turkey says:");
        turkey.gobble();
        turkey.fly();

        System.out.println("TurkeyAdapter (acting as Duck) says:");
        turkeyAdapter.quack();
        turkeyAdapter.fly();
    }
}
