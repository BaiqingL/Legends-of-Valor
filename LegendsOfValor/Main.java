package LegendsOfValor;

import LegendsOfValor.Controllers.Game;
import LegendsOfValor.Controllers.GameLauncher;

public class Main {
    public static void main(String[] args) {
        Game game = new GameLauncher().selectGame();
        game.startGame();
    }
}
