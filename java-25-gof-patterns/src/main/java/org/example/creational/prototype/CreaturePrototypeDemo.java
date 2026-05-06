package org.example.creational.prototype;

// Prototype: Creates new objects by copying an existing object.
// Example: Cloning magical creatures for an army.

interface CreaturePrototype extends Cloneable {
    CreaturePrototype clone();
}

class Dragon implements CreaturePrototype {
    private String name;
    private int firePower;

    public Dragon(String name, int firePower) {
        this.name = name;
        this.firePower = firePower;
    }

    @Override
    public Dragon clone() {
        try {
            return (Dragon) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return name + " the Dragon with fire power " + firePower;
    }
}

class Unicorn implements CreaturePrototype {
    private String name;
    private int magicPower;

    public Unicorn(String name, int magicPower) {
        this.name = name;
        this.magicPower = magicPower;
    }

    @Override
    public Unicorn clone() {
        try {
            return (Unicorn) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return name + " the Unicorn with magic power " + magicPower;
    }
}

public class CreaturePrototypeDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Prototype pattern:");
        Dragon originalDragon = new Dragon("Smaug", 100);
        Dragon clonedDragon = originalDragon.clone();
        System.out.println("Original: " + originalDragon);
        System.out.println("Cloned: " + clonedDragon);
        System.out.println("Are they the same instance? " + (originalDragon == clonedDragon));

        Unicorn originalUnicorn = new Unicorn("Sparkle", 80);
        Unicorn clonedUnicorn = originalUnicorn.clone();
        System.out.println("Original: " + originalUnicorn);
        System.out.println("Cloned: " + clonedUnicorn);
        System.out.println("Are they the same instance? " + (originalUnicorn == clonedUnicorn));
    }
}
