package RPG.Players;

import RPG.Controllers.FancyPrint;
import RPG.Items.*;

import java.util.ArrayList;
import java.util.List;

// Inventory class that contains the players inventory
public class Inventory {
    private static final FancyPrint printer = new FancyPrint();
    private final List<Potion> potions = new ArrayList<Potion>();
    private final List<Weapon> weapons = new ArrayList<Weapon>();
    private final List<Armor> armors = new ArrayList<Armor>();
    private final List<Spell> spells = new ArrayList<Spell>();

    public Inventory() {
    }

    public List<Spell> getSpells() {
        return spells;
    }

    public List<Potion> getPotions() {
        return potions;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Armor> getArmors() {
        return armors;
    }

    public void removePotion(int choice) {
        potions.remove(choice);
    }

    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    public void removeSpell(Spell spell) {
        spells.remove(spell);
    }

    public void addArmor(Armor armor) {
        armors.add(armor);
    }

    public void removeArmor(Armor armor) {
        armors.remove(armor);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void removeWeapon(Weapon weapon) {
        weapons.remove(weapon);
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
    }

    public void removePotion(Potion potion) {
        potions.remove(potion);
    }

    public void addItem(Item item) {
        if (item instanceof Spell) {
            addSpell((Spell) item);
        } else if (item instanceof Armor) {
            addArmor((Armor) item);
        } else if (item instanceof Weapon) {
            addWeapon((Weapon) item);
        } else if (item instanceof Potion) {
            addPotion((Potion) item);
        }
    }

    public void removeItem(Item item) {
        if (item instanceof Spell) {
            removeSpell((Spell) item);
        } else if (item instanceof Armor) {
            removeArmor((Armor) item);
        } else if (item instanceof Weapon) {
            removeWeapon((Weapon) item);
        } else if (item instanceof Potion) {
            removePotion((Potion) item);
        }
    }

    public boolean contains(Item item) {
        if (item instanceof Spell) {
            return this.spells.contains((Spell) item);
        } else if (item instanceof Armor) {
            return this.armors.contains((Armor) item);
        } else if (item instanceof Weapon) {
            return this.weapons.contains((Weapon) item);
        } else if (item instanceof Potion) {
            return this.potions.contains((Potion) item);
        }

        return false;
    }

    public void printInventory() {
        System.out.println("Inventory:");
        printer.printBlue("Potions: \n");
        for (Potion potion : potions) {
            printer.printBlue(potion.getName() + "\n");
        }
        printer.printRed("Weapons: \n");
        for (Weapon weapon : weapons) {
            printer.printRed(weapon.getName() + "\n");
        }
        printer.printGreen("Armor: \n");
        for (Armor armor : armors) {
            printer.printGreen(armor.getName() + "\n");
        }
        printer.printCyan("Spells: \n");
        for (Spell spell : spells) {
            printer.printGreen(spell.getName() + "\n");
        }
    }
}
