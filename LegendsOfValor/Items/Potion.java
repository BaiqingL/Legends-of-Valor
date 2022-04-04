package LegendsOfValor.Items;

public class Potion extends Buyable {
    private final int increaseAmount;
    private final String statToIncrease;

    public Potion(String name, int price, int minLevel, int increaseAmount, String statToIncrease) {
        super(name, price, minLevel);
        this.increaseAmount = increaseAmount;
        this.statToIncrease = statToIncrease;
    }

    public int getIncreaseAmount() {
        return increaseAmount;
    }

    public String getStatToIncrease() {
        return statToIncrease;
    }
}
