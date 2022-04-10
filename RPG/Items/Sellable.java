package RPG.Items;

import RPG.Players.Hero;

// Sellable interface
public interface Sellable {
    int getPrice();

    boolean sell(Hero hero);
}
