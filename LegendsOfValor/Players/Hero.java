package LegendsOfValor.Players;

public abstract class Hero {
    private String name;
    private final Inventory inventory = new Inventory();
    private int level = 1;
    private int hp = 100;
    private int mana;
    private int strength;
    private int dexterity;
    private int agility;
    private int money;
    private int exp;

    public Hero(String name, int mana, int strength, int dexterity, int agility, int money, int exp){
        this.name = name;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.money = money;
        this.exp = exp;
    }

    public String getName() {
        return name;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
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

    public void increaseExp(int amount) {
        this.exp += amount;
        if(this.exp >= this.level * 10){
            this.level+=1;
        }
        this.exp = 0;
    }

    public void increaseAll(int amount) {
        this.strength += amount;
        this.mana += amount;
        this.dexterity += amount;
        this.agility += amount;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String toString() {
        return "Name: " + name + " Mana: " + mana + " Strength: " + strength + " Dexterity: " + dexterity + " Agility: " + agility + " Level: " + level;
    }


    // Level up favors strength and agility
    public abstract void levelUpBoost();
}
