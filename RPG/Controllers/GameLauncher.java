package RPG.Controllers;

import java.util.Scanner;

// Game launcher selects, confirms and launches the game
public class GameLauncher {
    private final FancyPrint printer = new FancyPrint();

    public GameLauncher() {
    }

    public Game selectGame() {
        while (true) {
            printer.printGreen("Welcome to the RPG selection menu!\n");
            printer.printGreen("Please select a game to play:\n");
            printer.printGreen("1. Legends of Valor\n");
            printer.printGreen("2. Monsters and Heros\n");
            printer.printYellow("Your choice: ");

            Scanner scanner = new Scanner(System.in);
            try {
                int gameChoice = scanner.nextInt();
                switch (gameChoice) {
                    case 1:
                        return new LegendsOfValor();
                    case 2:
                        return new MonstersAndHeros();
                    default:
                        printer.clearScreen();
                        printer.printRed("Invalid choice! Please try again.\n");
                }
            } catch (Exception ignored) {
                printer.clearScreen();
                printer.printRed("Invalid input. Please try again.\n");
            }
        }
    }
}
