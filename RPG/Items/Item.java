package RPG.Items;

import RPG.Players.Hero;

// Class: Item
public abstract class Item implements Buyable, Sellable {
    private final String name;
    private final int price;
    private final int minLevel;

    // Constructor: Item
    public Item(String name, int price, int minLevel) {
        this.name = name;
        this.price = price;
        this.minLevel = minLevel;
    }

    // Method: getName
    public String getName() {
        return this.name;
    }

    // Method: getPrice
    public int getPrice() {
        return this.price;
    }

    // Method: getMinLevel
    public int getMinLevel() {
        return this.minLevel;
    }

    // Buys the item for the hero
    @Override
    public boolean buy(Hero hero) {
        if (hero.getMoney() >= this.price && hero.getLevel() >= this.minLevel) {
            hero.getInventory().addItem(this);
            hero.setMoney(hero.getMoney() - this.price);
            return true;
        }
        return false;
    }

    // Sells the item for the hero
    @Override
    public boolean sell(Hero hero) {
        if (hero.getInventory().contains(this)) {
            hero.getInventory().removeItem(this);
            hero.setMoney(hero.getMoney() + this.price);
            return true;
        }

        return false;
    }
}
