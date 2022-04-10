package RPG.Items;

import RPG.Players.Hero;

public class Potion extends Item implements Consumable{
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

    @Override
    public boolean consume(Hero hero) {
        for (String attr : this.statToIncrease.split(" ")) {
            switch (attr){
                case "Health":
                    hero.setHp(hero.getHp() + this.increaseAmount);
                    break;
                case "Strength":
                    hero.increaseStrength(this.increaseAmount);
                    break;
                case "Mana":
                    hero.increaseMana(this.increaseAmount);
                    break;
                case "Agility":
                    hero.increaseAgility(this.increaseAmount);
                    break;
                case "All":
                    hero.increaseAll(this.increaseAmount);
                    break;
            }
            return true;
        }
        return false;
    }
}
