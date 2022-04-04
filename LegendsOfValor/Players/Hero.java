package LegendsOfValor.Players;

public abstract class Hero {
    protected String name;
    protected int level = 1;
    protected int hp = 100;
    protected int mana;
    protected int strength;
    protected int dexterity;
    protected int agility;
    protected int money;
    protected int exp;

    public String getName() {
        return name;
    }

    public int getMana() {
        return mana;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getAgility() {
        return agility;
    }

    public int getMoney() {
        return money;
    }

    public int getExp() {
        return exp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    int getLevel() {
        return level;
    }

    public void increaseStrength(int amount) {
        this.strength += amount;
    }

    public void increaseMana(int amount) {
        this.mana += mana;
    }

    public void increaseAgility(int amount) {
        this.agility += amount;
    }

    public void increaseAll(int amount) {
        this.strength += amount;
        this.mana += amount;
        this.dexterity += amount;
        this.agility += amount;
    }

    public String toString() {
        return "Name: " + name + " Mana: " + mana + " Strength: " + strength + " Dexterity: " + dexterity + " Agility: " + agility + " Level: " + level;
    }

    // Level up favors strength and agility
    public abstract void levelUpBoost();
}
