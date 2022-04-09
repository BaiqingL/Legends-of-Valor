package RPG;

import RPG.Controllers.Game;
import RPG.Controllers.GameLauncher;

public class Main {
    public static void main(String[] args) {
        Game game = new GameLauncher().selectGame();
        game.startGame();
    }
}
