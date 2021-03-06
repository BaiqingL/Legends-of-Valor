package RPG.Players;

// Paladins class for the game
public class Paladin extends Hero {

    public Paladin(String name, int mana, int strength, int dexterity, int agility, int money, int exp) {
        super(name, mana, strength, dexterity, agility, money, exp);
    }

    // Level up favors strength and agility
    @Override
    public void levelUpBoost() {
        this.setStrength((int) (this.getStrength() * 1.20));
        this.setDexterity((int) (this.getDexterity() * 1.20));
        this.setAgility((int) (this.getAgility() * 1.10));
        this.setMana((int) (this.getMana() * 1.20));
        this.setHp(Math.max(this.getHp(), this.getLevel() * 100));
    }
}
