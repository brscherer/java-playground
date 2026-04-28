package org.example.creational.singleton;

// Singleton: Ensures only one instance exists.
// Fun example: A wizard's hat that can't be duplicated.
public final class WizardsHat {
    private static WizardsHat instance;

    private WizardsHat() {} // Private constructor prevents external instantiation.

    public static WizardsHat getInstance() {
        if (instance == null) {
            instance = new WizardsHat();
        }
        return instance;
    }

    public void castSpell(String spell) {
        System.out.println("Casting " + spell + " from the one true wizard's hat!");
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Singleton pattern:");
        WizardsHat hat1 = WizardsHat.getInstance();
        WizardsHat hat2 = WizardsHat.getInstance();
        hat1.castSpell("Abracadabra");
        System.out.println("Same instance? " + (hat1 == hat2)); // Should print true
    }
}
