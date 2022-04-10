package RPG.Items;

import RPG.Players.Hero;

// Represents a buyable item
public interface Buyable {

    int getPrice();

    boolean buy(Hero hero);
}
