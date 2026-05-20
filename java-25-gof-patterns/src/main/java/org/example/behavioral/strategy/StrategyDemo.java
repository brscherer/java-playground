package org.example.behavioral.strategy;

import java.util.Objects;

public class StrategyDemo {

    interface MovementStrategy {
        void move(String who);
    }

    static class WalkStrategy implements MovementStrategy {
        @Override
        public void move(String who) {
            System.out.println(who + " walks along the path, enjoying the scenery.");
        }
    }

    static class HorseStrategy implements MovementStrategy {
        @Override
        public void move(String who) {
            System.out.println(who + " rides a trusty steed at gallop speed.");
        }
    }

    static class FlyStrategy implements MovementStrategy {
        @Override
        public void move(String who) {
            System.out.println(who + " takes to the skies on enchanted wings.");
        }
    }

    static class TeleportStrategy implements MovementStrategy {
        @Override
        public void move(String who) {
            System.out.println(who + " teleports in a blink to the destination.");
        }
    }

    static class Adventurer {
        private final String name;
        private MovementStrategy movementStrategy;

        Adventurer(String name, MovementStrategy movementStrategy) {
            this.name = Objects.requireNonNull(name);
            this.movementStrategy = Objects.requireNonNull(movementStrategy);
        }

        public void setMovementStrategy(MovementStrategy strategy) {
            this.movementStrategy = Objects.requireNonNull(strategy);
        }

        public void travel() {
            movementStrategy.move(name);
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Strategy pattern (Movement strategies):");

        Adventurer elara = new Adventurer("Elara", new WalkStrategy());
        Adventurer borin = new Adventurer("Borin", new HorseStrategy());

        elara.travel();
        borin.travel();

        System.out.println("\nElara finds a cliff and upgrades strategy to Fly:");
        elara.setMovementStrategy(new FlyStrategy());
        elara.travel();

        System.out.println("\nBorin learns teleportation from a wizard:");
        borin.setMovementStrategy(new TeleportStrategy());
        borin.travel();
    }
}

