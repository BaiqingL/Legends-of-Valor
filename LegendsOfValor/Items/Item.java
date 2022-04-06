package LegendsOfValor.Items;

import LegendsOfValor.Players.Hero;

abstract class Item implements Buyable {
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
    public void buy(Hero h) {
        if(h.getMoney() >= this.price){

        }
    }
}
