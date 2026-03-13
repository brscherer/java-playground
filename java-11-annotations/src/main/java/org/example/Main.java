package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MagicSpell {
    String value();
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    @MagicSpell("Abracadabra!")
    public void castSpell() {
        System.out.println("Spell cast!");
    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = Main.class;
            Method method = clazz.getMethod("castSpell");
            MagicSpell annotation = method.getAnnotation(MagicSpell.class);
            if (annotation != null) {
                System.out.println("Reading annotation: " + annotation.value());
            }
            Main instance = new Main();
            method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
