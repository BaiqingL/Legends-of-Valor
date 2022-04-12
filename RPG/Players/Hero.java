package RPG.Players;

import RPG.Controllers.Attack;
import RPG.Monsters.Monster;
import RPG.World.GameCharacter;
import RPG.World.LegendsOfValorMap;
import RPG.World.Randomness;

// Abstract class Hero that is extended by the playable classes
public abstract class Hero extends GameCharacter implements Attack {
    private final Inventory inventory = new Inventory();
    private final String name;
    private int level = 1;
    private int hp = 100;
    private int mana;
    private int strength;
    private int dexterity;
    private int agility;
    private int money;
    private int exp;
    private LegendsOfValorMap.CellType previousCell;

    public Hero(String name, int mana, int strength, int dexterity, int agility, int money, int exp) {
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

    public int getLevel() {
        return level;
    }

    public LegendsOfValorMap.CellType getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(LegendsOfValorMap.CellType previousCell) {
        this.previousCell = previousCell;
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
        if (this.exp >= this.level * 10) {
            this.level += 1;
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

    public int getDamageReduction(){
        int armorDamageReduction = 0;
        if (this.inventory.getArmors().size() > 0) {
            armorDamageReduction = this.inventory.getArmors().get(0).getDamageReduction();
        }

        return armorDamageReduction;
    }

    public boolean hitLands(){
        double chance = (double) this.agility * 0.002;
        if ((double) Randomness.getRandomNumber(0, 100) < chance) {
            return false;
        }

        return true;
    }
    @Override
    public int attack(GameCharacter character) {
        int damagePurposed = (int) (this.strength * 0.05);
        Monster monster = (Monster) character;

        if(monster.hitLands()){
            monster.decreaseHp(damagePurposed);
            return damagePurposed;
        } else{
            return -1;
        }
    }

    public String toString() {
        return "Name: " + name + " Mana: " + mana + " Strength: " + strength + " Dexterity: " + dexterity + " Agility: " + agility + " Level: " + level;
    }


    // Level up favors strength and agility
    public abstract void levelUpBoost();
}
