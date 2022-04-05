package RPG.Players;

import RPG.Controllers.FancyPrint;
import RPG.Items.Armor;
import RPG.Items.Potion;
import RPG.Items.Spell;
import RPG.Items.Weapon;

import java.util.ArrayList;
import java.util.List;

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

    public void removeSpell(int choice) {
        spells.remove(choice);
    }

    public void addArmor(Armor armor) {
        armors.add(armor);
    }

    public void addWeapon(Weapon weapon) {
        weapons.add(weapon);
    }

    public void addPotion(Potion potion) {
        potions.add(potion);
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
    }
}
