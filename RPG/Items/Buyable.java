package RPG.Items;

import LegendsOfValor.Players.Hero;

public interface Buyable {

    public int getPrice();
    public boolean buy(Hero hero);

}
