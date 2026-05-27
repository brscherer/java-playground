package org.example.behavioral.command;

public class CommandDemo {

    interface Command {
        void execute();
        void undo();
    }

    static class NoOpCommand implements Command {
        @Override public void execute() {}
        @Override public void undo() {}
    }

    // Receiver
    static class SpellCaster {
        private String lastAction = "none";

        void castLight() {
            lastAction = "light";
            System.out.println("SpellCaster: A warm glow fills the room.");
        }

        void extinguishLight() {
            System.out.println("SpellCaster: The light fades away.");
        }

        void castTeleport(String destination) {
            lastAction = "teleport to " + destination;
            System.out.println("SpellCaster: Whoosh! Teleported to " + destination + ".");
        }

        void undoTeleport() {
            System.out.println("SpellCaster: Teleport reversed (returning to previous location)." );
        }

        void castShield() {
            lastAction = "shield";
            System.out.println("SpellCaster: A shimmering shield surrounds you.");
        }

        void removeShield() {
            System.out.println("SpellCaster: The shield dissipates.");
        }

        String getLastAction() { return lastAction; }
    }

    // Concrete commands
    static class LightCommand implements Command {
        private final SpellCaster caster;
        LightCommand(SpellCaster caster) { this.caster = caster; }
        @Override public void execute() { caster.castLight(); }
        @Override public void undo() { caster.extinguishLight(); }
    }

    static class TeleportCommand implements Command {
        private final SpellCaster caster;
        private final String destination;
        TeleportCommand(SpellCaster caster, String destination) { this.caster = caster; this.destination = destination; }
        @Override public void execute() { caster.castTeleport(destination); }
        @Override public void undo() { caster.undoTeleport(); }
    }

    static class ShieldCommand implements Command {
        private final SpellCaster caster;
        ShieldCommand(SpellCaster caster) { this.caster = caster; }
        @Override public void execute() { caster.castShield(); }
        @Override public void undo() { caster.removeShield(); }
    }

    // Invoker
    static class SpellInvoker {
        private Command[] slots;
        private Command lastCommand = new NoOpCommand();

        SpellInvoker(int slotsCount) {
            slots = new Command[slotsCount];
            for (int i = 0; i < slotsCount; i++) slots[i] = new NoOpCommand();
        }

        void setCommand(int slot, Command command) {
            if (slot < 0 || slot >= slots.length) throw new IllegalArgumentException("invalid slot");
            slots[slot] = command;
        }

        void press(int slot) {
            if (slot < 0 || slot >= slots.length) throw new IllegalArgumentException("invalid slot");
            System.out.println("Invoker: pressing slot " + slot + "...");
            slots[slot].execute();
            lastCommand = slots[slot];
        }

        void pressUndo() {
            System.out.println("Invoker: undoing last command...");
            lastCommand.undo();
            lastCommand = new NoOpCommand();
        }
    }

    public static void main(String[] args) {
        System.out.println("Demonstrating Command pattern (Spell invoker):");

        SpellCaster caster = new SpellCaster();
        SpellInvoker invoker = new SpellInvoker(3);

        invoker.setCommand(0, new LightCommand(caster));
        invoker.setCommand(1, new TeleportCommand(caster, "Crystal Tower"));
        invoker.setCommand(2, new ShieldCommand(caster));

        invoker.press(0);
        invoker.pressUndo();

        System.out.println();
        invoker.press(1);
        invoker.pressUndo();

        System.out.println();
        invoker.press(2);
        invoker.pressUndo();
    }
}

