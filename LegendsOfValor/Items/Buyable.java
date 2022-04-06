package LegendsOfValor.Items;

import LegendsOfValor.Players.Hero;

public interface Buyable {

    public int getPrice();
    public void buy(Hero h);

}
