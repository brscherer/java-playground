package org.example.models;

public class CoffeeShop {
    private String name;
    private int ratingScore;
    private boolean isOpen;

    public CoffeeShop(String name, int ratingScore) {
        this.name = name;
        this.ratingScore = ratingScore;
        this.isOpen = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(int ratingScore) {
        this.ratingScore = ratingScore;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String describeCoffee() {
        return "Welcome to " + name + "! Rating: " + ratingScore + "/5";
    }

    public void serveCoffee(String customerName, String coffeeType) {
        System.out.println("Serving " + coffeeType + " to " + customerName);
    }

    private String getSecretRecipe() {
        return "Espresso + Love + A pinch of magic";
    }

    @Override
    public String toString() {
        return "CoffeeShop{" +
                "name='" + name + '\'' +
                ", ratingScore=" + ratingScore +
                ", isOpen=" + isOpen +
                '}';
    }
}

