package org.example.structural.bridge;

// Bridge: Decouples an abstraction from its implementation.
// Fun example: Heroes wielding different magical weapons.

interface WeaponImplementor {
    void wield();
    void attack();
}

class Sword implements WeaponImplementor {
    @Override
    public void wield() {
        System.out.println("Drawing a mighty sword!");
    }

    @Override
    public void attack() {
        System.out.println("Swinging sword for a slashing attack!");
    }
}

class Bow implements WeaponImplementor {
    @Override
    public void wield() {
        System.out.println("Nocking an arrow in the bow!");
    }

    @Override
    public void attack() {
        System.out.println("Releasing arrow for a piercing shot!");
    }
}

abstract class Hero {
    protected WeaponImplementor weapon;

    public Hero(WeaponImplementor weapon) {
        this.weapon = weapon;
    }

    public abstract void introduce();
    public void performAttack() {
        weapon.wield();
        weapon.attack();
    }
}

class Warrior extends Hero {
    public Warrior(WeaponImplementor weapon) {
        super(weapon);
    }

    @Override
    public void introduce() {
        System.out.println("I am a Warrior!");
    }
}

class Archer extends Hero {
    public Archer(WeaponImplementor weapon) {
        super(weapon);
    }

    @Override
    public void introduce() {
        System.out.println("I am an Archer!");
    }
}

public class BridgeDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Bridge pattern:");
        Hero warrior = new Warrior(new Sword());
        Hero archer = new Archer(new Bow());

        warrior.introduce();
        warrior.performAttack();

        archer.introduce();
        archer.performAttack();

        // Showing decoupling: Archer can use Sword if needed
        Hero versatileArcher = new Archer(new Sword());
        versatileArcher.introduce();
        versatileArcher.performAttack();
    }
}
