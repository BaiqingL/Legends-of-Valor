package RPG.Items;

public class Potion extends Item {
    private final int increaseAmount;
    private final String statToIncrease;

    // Constructor for potion class
    public Potion(String name, int price, int minLevel, int increaseAmount, String statToIncrease) {
        super(name, price, minLevel);
        this.increaseAmount = increaseAmount;
        this.statToIncrease = statToIncrease;
    }

    // Get the amount to increase the stat by
    public int getIncreaseAmount() {
        return increaseAmount;
    }

    public String getStatToIncrease() {
        return statToIncrease;
    }
}
