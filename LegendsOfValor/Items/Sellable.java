package LegendsOfValor.Items;

import LegendsOfValor.Players.Hero;

public interface Sellable {
    public int getPrice();
    public boolean sell(Hero hero);
}
