package org.example;

import org.example.reflections.ReflectionsRead;
import org.example.reflections.ReflectionsInvoke;
import org.example.reflections.ReflectionsModify;

public class Main {
    public static void main(String[] args) {
        ReflectionsRead.inspectCoffeeShop();
        ReflectionsInvoke.invokeMethodsOnCoffeeShop();
        ReflectionsModify.modifyFieldsViaReflection();
    }
}