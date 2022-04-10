package RPG.Items;

public class Armor extends Item {

    private final int damageReduction;

    // Constructor for armor class
    public Armor(String name, int price, int minLevel, int damageReduction) {
        super(name, price, minLevel);
        this.damageReduction = damageReduction;
    }

    // Getter for damage reduction
    public int getDamageReduction() {
        return damageReduction;
    }
}
