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
        printer.printYellow("q: quit\n");
    }

    @Override
    public void startGame() {
        printer.clearScreen();
        while (true) {
            List<Hero> heros = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                HeroSelector selector = new HeroSelector();
                selector.promptHero();
                heros.add(selector.getHero());
                printer.printGreen("You've chosen: " + selector.getHero().getName() + "\n");
            }

            party = new Party(heros);
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
                printGameDetails();
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
                    case "i" -> printInfo = true;
                    default -> {
                    }
                }
            }
        }
    }

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
