package org.example.creational.builder;

// Builder: Separates the construction of a complex object from its representation.
// Example: Building custom pizzas with different toppings.

record Pizza(String dough, String sauce, String toppings) {
    @Override
    public String toString() {
        return "Pizza with " + dough + " dough, " + sauce + " sauce, and " + toppings + " toppings.";
    }
}

interface PizzaBuilder {
    void setDough(String dough);
    void setSauce(String sauce);
    void setToppings(String toppings);
    Pizza getPizza();
}

class HawaiianPizzaBuilder implements PizzaBuilder {
    private String dough;
    private String sauce;
    private String toppings;

    @Override
    public void setDough(String dough) {
        this.dough = dough;
    }

    @Override
    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    @Override
    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    @Override
    public Pizza getPizza() {
        return new Pizza(dough, sauce, toppings);
    }
}

class MeatLoversPizzaBuilder implements PizzaBuilder {
    private String dough;
    private String sauce;
    private String toppings;

    @Override
    public void setDough(String dough) {
        this.dough = dough;
    }

    @Override
    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    @Override
    public void setToppings(String toppings) {
        this.toppings = toppings;
    }

    @Override
    public Pizza getPizza() {
        return new Pizza(dough, sauce, toppings);
    }
}

class PizzaDirector {
    public void constructPizza(PizzaBuilder builder) {
        builder.setDough("thin crust");
        builder.setSauce("tomato");
        builder.setToppings("cheese");
    }
}

public class PizzaBuilderDemo {
    public static void main(String[] args) {
        System.out.println("Demonstrating Builder pattern:");
        PizzaDirector director = new PizzaDirector();

        PizzaBuilder hawaiianBuilder = new HawaiianPizzaBuilder();
        director.constructPizza(hawaiianBuilder);
        hawaiianBuilder.setToppings("ham and pineapple");
        Pizza hawaiianPizza = hawaiianBuilder.getPizza();
        System.out.println(hawaiianPizza);

        PizzaBuilder meatLoversBuilder = new MeatLoversPizzaBuilder();
        director.constructPizza(meatLoversBuilder);
        meatLoversBuilder.setToppings("pepperoni, sausage, and bacon");
        Pizza meatLoversPizza = meatLoversBuilder.getPizza();
        System.out.println(meatLoversPizza);
    }
}
