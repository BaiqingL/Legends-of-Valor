package RPG.Items;

import RPG.Players.Hero;

public abstract class Item implements Buyable, Sellable {
    private final String name;
    private final int price;
    private final int minLevel;

    public Item(String name, int price, int minLevel) {
        this.name = name;
        this.price = price;
        this.minLevel = minLevel;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getMinLevel() {
        return this.minLevel;
    }

    @Override
    public boolean buy(Hero hero) {
        if(hero.getMoney() >= this.price && hero.getLevel() >= this.minLevel){
            hero.getInventory().addItem(this);
            hero.setMoney( hero.getMoney() - this.price );
            return true;
        }
        return false;
    }

    @Override
    public boolean sell(Hero hero) {
        if(hero.getInventory().contains(this)){
            hero.getInventory().removeItem(this);
            hero.setMoney( hero.getMoney() + this.price);
            return true;
        }

        return false;
    }
}
