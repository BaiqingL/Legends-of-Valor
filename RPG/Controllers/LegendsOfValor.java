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
        printer.printYellow("w: Move up\n");
        printer.printYellow("s: Move down\n");
        printer.printYellow("a: Move left\n");
        printer.printYellow("d: Move right\n");
        printer.printYellow("i: Show info\n");
        printer.printYellow("b: Back to Nexus\n");
        printer.printYellow("t: Teleport/Swap with another hero\n");
        printer.printYellow("q: Quit\n");
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
                        case "t" -> {
                            int targetHeroIdx = getTeleportTarget(heroIdx);
                            gameMap.teleport(heroIdx, targetHeroIdx);
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

                    // Check for win loss condition
                    if (gameMap.checkHeroReachedEnd()) {
                        printer.clearScreen();
                        printer.printGreen("You've reached the end!\n");
                        printer.printGreen("You win!\n");
                        System.exit(0);
                    }
                }

                // Heros finished, now move monsters
                gameMap.moveMonsters();
                if (gameMap.checkMonsterReachedEnd()) {
                    printer.clearScreen();
                    // Render map to show monsters that reached the end
                    gameMap.renderMap();
                    printer.printRed("You've been defeated!\n");
                    System.exit(0);
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

    private int getTeleportTarget(int sourceHeroIdx) {
        printer.clearScreen();
        Scanner scanner = new Scanner(System.in);
        int targetHeroIdx = -1;
        while (targetHeroIdx == -1) {
            printer.printYellow("Currently choosing target for Hero " + (sourceHeroIdx + 1) + ": " + party.getHeros().get(sourceHeroIdx).getName() + "\n");
            printer.printYellow("Choose a hero to teleport to: \n");
            for (int i = 0; i < party.getHeros().size(); i++) {
                if (i != sourceHeroIdx) {
                    printer.printGreen(i + 1 + ": " + party.getHeros().get(i).getName() + "\n");
                }
            }
            try {
                targetHeroIdx = Integer.parseInt(scanner.nextLine());
                if (targetHeroIdx < 0 || targetHeroIdx > party.getHeros().size() || targetHeroIdx == sourceHeroIdx + 1) {
                    // Reset to keep looping
                    targetHeroIdx = -1;
                    throw new Exception("Invalid target");
                }
            } catch (Exception e) {
                printer.printRed("Improper input\n");
            }
        }
        // Offset by 1 to account for array indexing
        return targetHeroIdx - 1;
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
                    choice.equals("t") ||
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
