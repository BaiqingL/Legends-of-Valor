package RPG.Players;

// Warrior class that the player can choose to play as
public class Warrior extends Hero {

    public Warrior(String name, int mana, int strength, int dexterity, int agility, int money, int exp) {
        super(name, mana, strength, dexterity, agility, money, exp);
    }

    // Level up favors strength and agility
    @Override
    public void levelUpBoost() {
        this.setStrength((int) (this.getStrength() * 1.20));
        this.setDexterity((int) (this.getDexterity() * 1.10));
        this.setAgility((int) (this.getAgility() * 1.20));
        this.setMana((int) (this.getMana() * 1.20));
        this.setHp(Math.max(this.getHp(), this.getLevel() * 100));
    }
}
