package RPG.Items;

import java.util.ArrayList;
import java.util.List;

// Creates builder for item
public class ItemBuilder {
    public ItemBuilder() {
    }

    // Creates list of all weapons
    public List<Weapon> buildWeapons() {
        List<Weapon> weapons = new ArrayList<>();
        weapons.add(new Weapon("Sword", 500, 1, 800, 1));
        weapons.add(new Weapon("Bow", 300, 2, 500, 2));
        weapons.add(new Weapon("Scythe", 1000, 6, 1100, 2));
        weapons.add(new Weapon("Axe", 550, 5, 850, 1));
        weapons.add(new Weapon("TSwords", 1400, 8, 1600, 2));
        weapons.add(new Weapon("Dagger", 200, 1, 250, 1));
        return weapons;
    }

    // Creates list of all potions
    public List<Potion> buildPotions() {
        List<Potion> potions = new ArrayList<>();
        potions.add(new Potion("Healing Potion", 250, 1, 100, "Health"));
        potions.add(new Potion("Strength Potion", 200, 1, 75, "Strength"));
        potions.add(new Potion("Magic Potion", 350, 2, 100, "Mana"));
        potions.add(new Potion("Luck Elixir", 500, 4, 65, "Agility"));
        potions.add(new Potion("Mermaid Tears", 850, 5, 100, "Health Mana Strength Agility"));
        potions.add(new Potion("Ambrosia", 1000, 8, 150, "All"));
        return potions;
    }

    // Creates list of all armors
    public List<Armor> buildArmor() {
        List<Armor> armors = new ArrayList<>();
        armors.add(new Armor("Platinum Shield", 150, 1, 200));
        armors.add(new Armor("Breastplate", 350, 3, 600));
        armors.add(new Armor("Full Body Armor", 1000, 8, 1100));
        armors.add(new Armor("Wizard Shield", 1200, 10, 1500));
        armors.add(new Armor("Guardian Angle", 1000, 10, 1000));
        return armors;
    }

    // Creates list of all spells
    public List<Spell> buildSpell() {
        List<Spell> spells = new ArrayList<>();
        spells.add(new Spell("Ice", 100, 1, 50, Spell.SpellType.ICE));
        spells.add(new Spell("Fire", 100, 1, 50, Spell.SpellType.FIRE));
        spells.add(new Spell("Lightning", 100, 1, 50, Spell.SpellType.LIGHTNING));
        return spells;
    }
}
