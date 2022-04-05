package LegendsOfValor.Controllers;

import LegendsOfValor.Controllers.FancyPrint;
import LegendsOfValor.Controllers.Game;
import LegendsOfValor.Players.Hero;
import LegendsOfValor.Players.HeroSelector;
import LegendsOfValor.Players.Player;
import LegendsOfValor.World.MonstersAndHerosMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MonstersAndHeros implements Game {
    private static final FancyPrint printer = new FancyPrint();
    private int heroCount;
    private MonstersAndHerosMap gameMap;
    private Player player;

    public MonstersAndHeros() {

    }

    @Override
    public void startGame() {
        printer.clearScreen();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            printer.printYellow("Between 1 - 3, how many heros do you want to start with? ");
            String input = scanner.nextLine();
            try {
                heroCount = Integer.parseInt(input);
                if (heroCount > 0 && heroCount <= 3) {
                    break;
                }
            } catch (Exception ignored) {

            }
            printer.printYellow("Incorrect choice.\n");
        }
        List<Hero> heros = new ArrayList<>();
        for (int i = 0; i < heroCount; i++) {
            HeroSelector selector = new HeroSelector();
            selector.promptHero();
            heros.add(selector.getHero());
            printer.printGreen("You've chosen: " + selector.getHero().getName() + "\n");
        }

        // Make the player
        player = new Player(heros);

        printer.clearScreen();
        // Construct map object
        gameMap = new MonstersAndHerosMap(player);
        boolean improperMove = false;
        boolean printInfo = false;

        while (true) {
            printer.clearScreen();
            if (printInfo) {
                printInfo();
                printInfo = false;
            }
            gameMap.renderMap();

            if (improperMove) {
                printer.printRed("Improper move\n");
                improperMove = false;
            }
            String choice = getPlayerInput();

            switch (choice) {
                case "w" -> {
                    if (!gameMap.moveUp()) {
                        improperMove = true;
                    }
                }
                case "s" -> {
                    if (!gameMap.moveDown()) {
                        improperMove = true;
                    }
                }
                case "a" -> {
                    if (!gameMap.moveLeft()) {
                        improperMove = true;
                    }
                }
                case "d" -> {
                    if (!gameMap.moveRight()) {
                        improperMove = true;
                    }
                }
                case "q" -> {
                    return;
                }
                case "i" -> {
                    printInfo = true;
                }
                default -> {
                }
            }
        }
    }

    private void printInfo() {
        printer.clearScreen();
        for (Hero hero : player.getHeros()) {
            printer.printYellow(hero.toString() + "\n");
        }
        printer.printGreen("Money: " + player.getMoney() + "\n");
        player.getInventory().printInventory();
        Scanner scanner = new Scanner(System.in);
        printer.printYellow("Press enter to return...");
        scanner.nextLine();
        printer.clearScreen();
    }

    private String getPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printer.printYellow("Input: ");
            String choice = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (choice.equals("w") || choice.equals("a") || choice.equals("s") || choice.equals("d") || choice.equals("q") || choice.equals("i")) {
                return choice;
            } else {
                printer.clearScreen();
                gameMap.renderMap();
                printer.printRed("Incorrect choice\n");
            }
        }
    }
}
