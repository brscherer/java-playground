package org.example.reflections;

import org.example.models.CoffeeShop;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectionsRead {

    public static void inspectCoffeeShop() {
        CoffeeShop shop = new CoffeeShop("Brew Haven", 4);
        Class<?> clazz = shop.getClass();

        System.out.println("Inspecting: " + clazz.getSimpleName());
        System.out.println("Full class name: " + clazz.getName());
        System.out.println();

        System.out.println("All Declared Fields:");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.println("  - " + field.getType().getSimpleName() + " " + field.getName());
        }
        System.out.println();

        System.out.println("All Public Methods:");
        for (Method method : clazz.getMethods()) {
            if (!method.getDeclaringClass().equals(Object.class)) {
                System.out.println("  - " + method.getName() + "()");
            }
        }
        System.out.println();

        System.out.println("All Declared Methods (including private):");
        for (Method method : clazz.getDeclaredMethods()) {
            String modifier = java.lang.reflect.Modifier.isPrivate(method.getModifiers()) ? "private " : "public ";
            System.out.println("  - " + modifier + method.getName() + "()");
        }
        System.out.println();

        System.out.println("Reading Field Values via Reflection:");
        try {
            Field nameField = clazz.getDeclaredField("name");
            nameField.setAccessible(true);
            Object nameValue = nameField.get(shop);
            System.out.println("  - name: " + nameValue);

            Field ratingField = clazz.getDeclaredField("ratingScore");
            ratingField.setAccessible(true);
            Object ratingValue = ratingField.get(shop);
            System.out.println("  - ratingScore: " + ratingValue);

            Field isOpenField = clazz.getDeclaredField("isOpen");
            isOpenField.setAccessible(true);
            Object openValue = isOpenField.get(shop);
            System.out.println("  - isOpen: " + openValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        inspectCoffeeShop();
    }
}

