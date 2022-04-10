package RPG;

import RPG.Controllers.Game;
import RPG.Controllers.GameLauncher;

public class Main {
    public static void main(String[] args) {
        // Create a new game by calling the GameLauncher
        Game game = new GameLauncher().selectGame();
        // Start the game
        game.startGame();
    }
}
