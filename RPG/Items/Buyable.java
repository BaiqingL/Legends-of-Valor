package RPG.Items;

import RPG.Players.Hero;

public interface Buyable {

    public int getPrice();
    public boolean buy(Hero hero);
}
