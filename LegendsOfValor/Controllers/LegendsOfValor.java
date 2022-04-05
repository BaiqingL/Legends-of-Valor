package LegendsOfValor.Controllers;

import LegendsOfValor.World.LegendsOfValorMap;

public class LegendsOfValor implements Game {
    private static final FancyPrint printer = new FancyPrint();
    private LegendsOfValorMap gameMap;

    public LegendsOfValor() {
        gameMap = new LegendsOfValorMap();
    }

    @Override
    public void startGame() {
        gameMap.renderMap();
    }
}
