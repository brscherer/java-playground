package org.example.reflections;

import org.example.models.CoffeeShop;
import java.lang.reflect.Field;

public class ReflectionsModify {

    public static void modifyFieldsViaReflection() {
        CoffeeShop shop = new CoffeeShop("Cozy Brew", 3);

        try {
            System.out.println("Initial state:");
            System.out.println("  " + shop);
            System.out.println();

            System.out.println("Modifying public properties:\n");

            Class<?> clazz = shop.getClass();

            Field nameField = clazz.getDeclaredField("name");
            nameField.setAccessible(true);
            nameField.set(shop, "The Daily Grind");
            System.out.println("  Changed name -> " + shop.getName());

            Field ratingField = clazz.getDeclaredField("ratingScore");
            ratingField.setAccessible(true);
            ratingField.set(shop, 5);
            System.out.println("  Changed rating -> " + shop.getRatingScore());
            System.out.println();

            System.out.println("Modifying private properties:\n");

            Field isOpenField = clazz.getDeclaredField("isOpen");
            isOpenField.setAccessible(true);
            isOpenField.set(shop, false);
            System.out.println("  Changed isOpen -> " + shop.isOpen());
            System.out.println();

            System.out.println("Final state:");
            System.out.println("  " + shop);
            System.out.println("  " + shop.describeCoffee());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        modifyFieldsViaReflection();
    }
}

