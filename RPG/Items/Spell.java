package RPG.Items;

import RPG.Players.Hero;

import java.util.List;

public class Spell extends Item {

    private final int manaRequired;
    private final SpellType spellType;

    public Spell(String name, int price, int minLevel, int manaRequired, SpellType spellType) {
        super(name, price, minLevel);
        this.manaRequired = manaRequired;
        this.spellType = spellType;
    }

    public SpellType getSpellType() {
        return spellType;
    }

    public int getManaCost() {
        return manaRequired;
    }

    public int getDamage(Hero hero) {
        int damage = 0;
        damage += hero.getDexterity() * 2;
        return damage;
    }

    public enum SpellType {
        ICE,
        FIRE,
        LIGHTNING
    }

}
