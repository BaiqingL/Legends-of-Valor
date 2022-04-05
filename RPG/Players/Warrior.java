package RPG.Players;

public class Warrior extends Hero {

    public Warrior(String name, int mana, int strength, int dexterity, int agility, int money, int exp) {
        this.name = name;
        this.mana = mana;
        this.strength = strength;
        this.dexterity = dexterity;
        this.agility = agility;
        this.money = money;
        this.exp = exp;
    }

    // Level up favors strength and agility
    @Override
    public void levelUpBoost() {
        this.strength *= 1.20;
        this.dexterity *= 10;
        this.agility *= 1.20;
        this.mana *= 1.10;
        this.level++;
        this.hp = Math.max(this.hp, this.level * 100);
    }
}
