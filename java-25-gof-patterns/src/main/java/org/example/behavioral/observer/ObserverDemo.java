package org.example.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObserverDemo {

    interface WeatherObserver {
        void update(WeatherType weather);
    }

    interface WeatherTower {
        void register(WeatherObserver observer);
        void unregister(WeatherObserver observer);
        void notifyObservers();
        void setWeather(WeatherType weather);
        WeatherType getWeather();
    }

    enum WeatherType {
        SUNNY, RAINY, WINDY, STORMY, SNOWY
    }

    static class SimpleWeatherTower implements WeatherTower {
        private final List<WeatherObserver> observers = new ArrayList<>();
        private WeatherType currentWeather = WeatherType.SUNNY;

        @Override
        public void register(WeatherObserver observer) {
            if (observer == null) return;
            if (!observers.contains(observer)) observers.add(observer);
        }

        @Override
        public void unregister(WeatherObserver observer) {
            observers.remove(observer);
        }

        @Override
        public void notifyObservers() {
            for (WeatherObserver o : new ArrayList<>(observers)) {
                o.update(currentWeather);
            }
        }

        @Override
        public void setWeather(WeatherType weather) {
            if (weather == null || weather == this.currentWeather) return;
            this.currentWeather = weather;
            System.out.println("\nWeatherTower: weather changed to " + weather);
            notifyObservers();
        }

        @Override
        public WeatherType getWeather() {
            return currentWeather;
        }
    }

    static class Mage implements WeatherObserver {
        private final String name;
        Mage(String name) { this.name = Objects.requireNonNull(name); }

        @Override
        public void update(WeatherType weather) {
            switch (weather) {
                case RAINY -> System.out.println(name + " the Mage: Conjuring umbrellas and damp-proofing wards.");
                case SUNNY -> System.out.println(name + " the Mage: Perfect light for harvesting solar mana.");
                case STORMY -> System.out.println(name + " the Mage: Sheltering apprentices — lightning interferes with spells!");
                case WINDY -> System.out.println(name + " the Mage: I'll anchor the floating runes.");
                case SNOWY -> System.out.println(name + " the Mage: Time to insulate the familiars.");
            }
        }
    }

    static class Dragon implements WeatherObserver {
        private final String name;
        Dragon(String name) { this.name = Objects.requireNonNull(name); }

        @Override
        public void update(WeatherType weather) {
            switch (weather) {
                case SUNNY, WINDY -> System.out.println(name + " the Dragon: Great day to sunbathe and crisp my scales.");
                case RAINY, SNOWY -> System.out.println(name + " the Dragon: I'll nap — wetter days make flames tricky.");
                case STORMY -> System.out.println(name + " the Dragon: Lightning! Time to fly low and avoid the peaks.");
            }
        }
    }

    static class Farmer implements WeatherObserver {
        private final String name;
        Farmer(String name) { this.name = Objects.requireNonNull(name); }

        @Override
        public void update(WeatherType weather) {
            switch (weather) {
                case RAINY -> System.out.println(name + " the Farmer: Excellent — crops will drink up.");
                case SUNNY -> System.out.println(name + " the Farmer: I'll irrigate less today.");
                case SNOWY -> System.out.println(name + " the Farmer: Protect the seedlings from frost!");
                case STORMY -> System.out.println(name + " the Farmer: Secure the barn doors and tether the goats.");
                case WINDY -> System.out.println(name + " the Farmer: Time to check fences and loose planks.");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Observer pattern (WeatherTower):");

        SimpleWeatherTower tower = new SimpleWeatherTower();

        Mage merlin = new Mage("Merlin");
        Dragon drogo = new Dragon("Drogo");
        Farmer ada = new Farmer("Ada");

        tower.register(merlin);
        tower.register(drogo);
        tower.register(ada);

        tower.setWeather(WeatherType.SUNNY);
        tower.setWeather(WeatherType.WINDY);
        tower.setWeather(WeatherType.RAINY);

        System.out.println("\nAda the Farmer is going to market (unregistering).");
        tower.unregister(ada);

        tower.setWeather(WeatherType.STORMY);
        tower.setWeather(WeatherType.SNOWY);
    }
}

