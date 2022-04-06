package LegendsOfValor.Items;

public class Weapon extends Item {

    private final int damage;
    private final int handsRequired;

    public Weapon(String name, int price, int minLevel, int damage, int handsRequired) {
        super(name, price, minLevel);
        this.damage = damage;
        this.handsRequired = handsRequired;
    }

    public int getDamage() {
        return damage;
    }

    public int getHandsRequired() {
        return handsRequired;
    }

}
