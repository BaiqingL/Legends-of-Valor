package RPG.Controllers;

import RPG.Players.Hero;
import RPG.Players.HeroSelector;
import RPG.Players.Party;
import RPG.World.LegendsOfValorMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LegendsOfValor implements Game {
    private static final FancyPrint printer = new FancyPrint();
    private final LegendsOfValorMap gameMap;
    private Party party;

    public LegendsOfValor() {
        gameMap = new LegendsOfValorMap();
    }

    private void printGameDetails() {
        printer.printYellow("\nControls:\n");
        printer.printYellow("w: move up\n");
        printer.printYellow("s: move down\n");
        printer.printYellow("a: move left\n");
        printer.printYellow("d: move right\n");
        printer.printYellow("i: show info\n");
        printer.printYellow("b: Back to Nexus\n");
        printer.printYellow("q: quit\n");
    }

    @Override
    public void startGame() {
        printer.clearScreen();
        // Start game loop
        while (true) {
            List<Hero> heros = new ArrayList<>();
            // We know that there will always be 3 heros in the game, so default select three heros
            for (int i = 0; i < 3; i++) {
                HeroSelector selector = new HeroSelector();
                selector.promptHero();
                heros.add(selector.getHero());
                printer.printGreen("You've chosen: " + selector.getHero().getName() + "\n");
            }

            // Create the party and start the movement loop
            party = new Party(heros);
            boolean improperMove = false;
            boolean printInfo = false;
            while (true) {
                printer.clearScreen();
                if (printInfo) {
                    printInfo();
                    printInfo = false;
                }

                // Turn based movement, check which hero should move and have moved
                int heroIdx = 0;
                boolean[] heroMoved = new boolean[heros.size()];
                for (int i = 0; i < heroMoved.length; i++) {
                    heroMoved[i] = false;
                }
                // Makes sure that each hero has taken their turn
                while (heroRemainsUnmoved(heroMoved)) {
                    gameMap.renderMap();
                    printGameDetails();
                    if (improperMove) {
                        printer.printRed("Improper move\n");
                        improperMove = false;
                    }
                    printer.printYellow("Currently choosing action for Hero " + (heroIdx + 1) + ": " + party.getHeros().get(heroIdx).getName() + "\n");
                    String choice = getPlayerInput();

                    switch (choice) {
                        case "w" -> {
                            if (!gameMap.moveUp(heroIdx)) {
                                improperMove = true;
                            } else {
                                heroMoved[heroIdx] = true;
                                heroIdx++;
                            }
                        }
                        case "s" -> {
                            if (!gameMap.moveDown(heroIdx)) {
                                improperMove = true;
                            } else {
                                heroMoved[heroIdx] = true;
                                heroIdx++;
                            }
                        }
                        case "a" -> {
                            if (!gameMap.moveLeft(heroIdx)) {
                                improperMove = true;
                            } else {
                                heroMoved[heroIdx] = true;
                                heroIdx++;
                            }
                        }
                        case "d" -> {
                            if (!gameMap.moveRight(heroIdx)) {
                                improperMove = true;
                            } else {
                                heroMoved[heroIdx] = true;
                                heroIdx++;
                            }
                        }
                        case "b" -> {
                            gameMap.backToNexus(heroIdx);
                            heroMoved[heroIdx] = true;
                            heroIdx++;
                        }
                        case "q" -> {
                            return;
                        }
                        case "i" -> printInfo = true;
                        default -> {
                        }
                    }
                }
            }
        }
    }

    // Checks if any hero has not moved
    private boolean heroRemainsUnmoved(boolean[] heroMoved) {
        for (boolean moved : heroMoved) {
            if (!moved) {
                return true;
            }
        }
        return false;
    }

    // Prints the game details
    private void printInfo() {
        printer.clearScreen();
        printer.printYellow("\nMap cell meanings:\n");
        printer.printGreen("N: Nexus, acts as market place for items\n");
        printer.printGreen("B: Bush, boost dexterity by 10%\n");
        printer.printGreen("K: Koulou, boost strength by 10%\n");
        printer.printGreen("C: Cave, boost agility by 10%\n");
        printer.printRed("I: Inaccessible, borders that can not be accessed\n");

        for (Hero hero : party.getHeros()) {
            printer.printYellow(hero.toString() + "\n");
            printer.printGreen("Money: " + party.getMoney() + "\n");
            hero.getInventory().printInventory();
        }
        Scanner scanner = new Scanner(System.in);
        printer.printYellow("Press enter to return...");
        scanner.nextLine();
        printer.clearScreen();
    }


    // Get the player input and sanitize it
    private String getPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printer.printYellow("Input: ");
            String choice = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (choice.equals("w") ||
                    choice.equals("a") ||
                    choice.equals("s") ||
                    choice.equals("d") ||
                    choice.equals("b") ||
                    choice.equals("q") ||
                    choice.equals("i")) {
                return choice;
            } else {
                printer.clearScreen();
                gameMap.renderMap();
                printGameDetails();
                printer.printRed("Incorrect choice\n");
            }
        }
    }
}
