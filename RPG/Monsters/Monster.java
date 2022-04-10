package RPG.Monsters;

import RPG.Controllers.FancyPrint;
import RPG.World.Randomness;

// Monster class and extend randomness for damage generation
public abstract class Monster extends Randomness {
    private final String name;
    private final FancyPrint printer = new FancyPrint();
    private final int level;
    private int hp;
    private int damage;
    private int defense;
    private int dodgeChance;

    // Constructor
    public Monster(String name, int level, int damage, int defense, int dodgeChance) {
        this.name = name;
        this.level = level;
        this.hp = 100 * this.level;
        this.damage = damage;
        this.defense = defense;
        this.dodgeChance = dodgeChance;
    }

    // Getters
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

    public void decreaseHp(int amount) {
        this.hp -= amount;
    }

    public boolean hitLands() {
        return getRandomNumber(0, 100) > dodgeChance;
    }

    // Prints the monster's stats
    public void print() {
        printer.printYellow(this.name + " ");
        String monsterType = this.getClass().getSimpleName();
        switch (monsterType) {
            case "Dragon":
                printer.printRed("Dragon ");
                break;
            case "Exoskeleton":
                printer.printWhite("Exoskeleton ");
                break;
            case "Spirit":
                printer.printGreen("Spirit ");
                break;
        }
        printer.printWhite("Level: " + this.level + " ");
        printer.printPurple("Attack " + this.damage + " ");
        printer.printBlue("Defense " + this.defense + " \n");
    }

}
