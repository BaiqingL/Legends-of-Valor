package LegendsOfValor.Monsters;

import LegendsOfValor.FancyPrint;
import LegendsOfValor.World.Randomness;

public abstract class Monster extends Randomness {
    private final String name;
    private final FancyPrint printer = new FancyPrint();
    private final int hp;
    private final int level;
    private int damage;
    private int defense;
    private int dodgeChance;

    public Monster(String name, int level, int damage, int defense, int dodgeChance) {
        this.name = name;
        this.level = level;
        this.hp = 100 * this.level;
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
    }

    public void decreaseDamage(int amount) {
        this.damage -= amount;
        if (this.damage < 0) {
            damage = 0;
        }
    }

    public void decreaseDefense(int amount) {
        this.defense -= amount;
        if (this.defense < 0) {
            defense = 0;
        }
    }

    public void decreaseDodgeChance(int amount) {
        this.dodgeChance -= amount;
        if (this.dodgeChance < 0) {
            dodgeChance = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getDamage() {
        return damage;
    }

    public int getDefense() {
        return defense;
    }

    public int getDodgeChance() {
        return dodgeChance;
    }
    
    public int getHp() {
        return hp;
    }

    public boolean hitLands() {
        return getRandomNumber(0, 100) > dodgeChance;
    }

    public void print() {
        printer.printYellow(this.name + " ");
        printer.printWhite("Level: " + this.level + " ");
        printer.printPurple("Attack " + this.damage + " ");
        printer.printBlue("Defense " + this.defense + " \n");
    }

}
