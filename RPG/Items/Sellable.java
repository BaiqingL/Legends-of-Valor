package RPG.Items;

import RPG.Players.Hero;

public interface Sellable {
    public int getPrice();
    public boolean sell(Hero hero);
}
