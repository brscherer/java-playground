package org.example.reflections;

import org.example.models.CoffeeShop;
import java.lang.reflect.Method;

public class ReflectionsInvoke {

    public static void invokeMethodsOnCoffeeShop() {
        CoffeeShop shop = new CoffeeShop("Java Junction", 5);

        try {
            System.out.println("Invoking public methods:\n");

            Method describeCoffeeMethod = shop.getClass().getMethod("describeCoffee");
            Object result = describeCoffeeMethod.invoke(shop);
            System.out.println("  Result: " + result);
            System.out.println();

            Method serveCoffeeMethod = shop.getClass().getMethod("serveCoffee", String.class, String.class);
            serveCoffeeMethod.invoke(shop, "Alice", "Cappuccino");
            System.out.println();

            System.out.println("Invoking private method:\n");

            Method secretRecipeMethod = shop.getClass().getDeclaredMethod("getSecretRecipe");
            secretRecipeMethod.setAccessible(true);
            Object secretResult = secretRecipeMethod.invoke(shop);
            System.out.println("  Secret Recipe: " + secretResult);
            System.out.println();

            System.out.println("Invoking setter methods:\n");

            Method setRatingMethod = shop.getClass().getMethod("setRatingScore", int.class);
            setRatingMethod.invoke(shop, 5);
            System.out.println("  Updated rating to 5");

            Method getRatingMethod = shop.getClass().getMethod("getRatingScore");
            Object newRating = getRatingMethod.invoke(shop);
            System.out.println("  Current rating: " + newRating);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        invokeMethodsOnCoffeeShop();
    }
}

