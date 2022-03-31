package hw3.LegendsAndHeros.src.Items;

public class Armor extends Buyable {

    private final int damageReduction;

    public Armor(String name, int price, int minLevel, int damageReduction) {
        super(name, price, minLevel);
        this.damageReduction = damageReduction;
    }

    public int getDamageReduction() {
        return damageReduction;
    }
}
