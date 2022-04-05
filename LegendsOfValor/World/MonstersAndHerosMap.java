package LegendsOfValor.World;

import LegendsOfValor.FancyPrint;
import LegendsOfValor.Players.Player;

public class MonstersAndHerosMap extends Randomness {
    private static final int ROWS = 8;
    private static final int COLUMNS = 8;
    private static final FancyPrint printer = new FancyPrint();
    private final Tile[][] boardContent = new Tile[ROWS][COLUMNS];
    private final int[] heroLocation = new int[2];
    // Player reference
    private final Player player;
    // The market object
    private final Market market;
    // Keep track where the hero was on
    private Tile heroStep;

    public MonstersAndHerosMap(Player player) {
        populateMap();
        this.player = player;
        market = new Market(player);
    }

    // 20% Non-playable cells 30% markets and 50% wild area
    private void populateMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                int randNum = getRandomNumber(1, 10);
                if (randNum < 2) {
                    boardContent[i][j] = new Tile("blocked");
                } else if (randNum < 5) {
                    boardContent[i][j] = new Tile("market");
                } else {
                    boardContent[i][j] = new Tile("wild");
                }
            }
        }
        // Place the hero
        int heroX = getRandomNumber(0, ROWS);
        int heroY = getRandomNumber(0, COLUMNS);
        heroStep = boardContent[heroX][heroY];
        heroLocation[0] = heroX;
        heroLocation[1] = heroY;
        boardContent[heroX][heroY] = new Tile("hero");
    }

    public void renderMap() {
        // First go by row
        int idx = 0;
        for (int i = 0; i < ROWS * 2 + 1; i++) {
            if (i % 2 == 0) {
                printDivider();
            } else {
                printContent(idx);
                idx += 1;
            }
        }
        printHints();
    }

    private void printDivider() {
        // Then print out content on each content row
        String repeated = new String(new char[COLUMNS * 6]).replace("\0", "=");
        printer.printWhite(repeated + "=\n");
    }

    private void printContent(int row) {
        printer.printWhite("||");
        for (int i = 0; i < COLUMNS; i++) {
            printer.printWhite(" ");
            switch (boardContent[row][i].getContent()) {
                // Use fancy emojis!
                case "M" -> printer.printWhite("\uD83C\uDFE0");
                case "H" -> printer.printWhite("\uD83E\uDDDD");
                case "X" -> printer.printWhite("\uD83D\uDEA7");
                case "W" -> printer.printWhite("\uD83C\uDF31");
            }
            printer.printWhite(" ||");
        }
        printer.printWhite("\n");
    }

    private void printHints() {
        printer.printYellow("Here are the inputs:\n");
        printer.printRed("W to move up ");
        printer.printGreen("A to move left ");
        printer.printBlue("S to move down ");
        printer.printCyan("D to move right \n");
        printer.printWhite("Q to ");
        printer.printRed("quit game ");
        printer.printWhite("I to ");
        printer.printGreen("show information\n");
    }

    public boolean moveUp() {
        if (checkBoundaries(heroLocation[0] - 1, heroLocation[1])) {
            return false;
        }
        // Store the new tile the player steps on
        Tile newHeroStep = boardContent[heroLocation[0] - 1][heroLocation[1]];
        // Set back the original tile
        boardContent[heroLocation[0]][heroLocation[1]] = heroStep;
        // Set new tile the hero steps on
        heroStep = newHeroStep;
        // Set the hero to the new tile
        boardContent[heroLocation[0] - 1][heroLocation[1]] = new Tile("hero");
        heroLocation[0] = heroLocation[0] - 1;
        checkMarket();
        checkWild();
        return true;
    }

    public boolean moveDown() {
        if (checkBoundaries(heroLocation[0] + 1, heroLocation[1])) {
            return false;
        }
        // Store the new tile the player steps on
        Tile newHeroStep = boardContent[heroLocation[0] + 1][heroLocation[1]];
        // Set back the original tile
        boardContent[heroLocation[0]][heroLocation[1]] = heroStep;
        // Set new tile the hero steps on
        heroStep = newHeroStep;
        // Set the hero to the new tile
        boardContent[heroLocation[0] + 1][heroLocation[1]] = new Tile("hero");
        heroLocation[0] = heroLocation[0] + 1;
        checkMarket();
        checkWild();
        return true;
    }

    public boolean moveRight() {
        if (checkBoundaries(heroLocation[0], heroLocation[1] + 1)) {
            return false;
        }
        // Store the new tile the player steps on
        Tile newHeroStep = boardContent[heroLocation[0]][heroLocation[1] + 1];
        // Set back the original tile
        boardContent[heroLocation[0]][heroLocation[1]] = heroStep;
        // Set new tile the hero steps on
        heroStep = newHeroStep;
        // Set the hero to the new tile
        boardContent[heroLocation[0]][heroLocation[1] + 1] = new Tile("hero");
        heroLocation[1] = heroLocation[1] + 1;
        checkMarket();
        checkWild();
        return true;
    }

    public boolean moveLeft() {
        if (checkBoundaries(heroLocation[0], heroLocation[1] - 1)) {
            return false;
        }
        // Store the new tile the player steps on
        Tile newHeroStep = boardContent[heroLocation[0]][heroLocation[1] - 1];
        // Set back the original tile
        boardContent[heroLocation[0]][heroLocation[1]] = heroStep;
        // Set new tile the hero steps on
        heroStep = newHeroStep;
        // Set the hero to the new tile
        boardContent[heroLocation[0]][heroLocation[1] - 1] = new Tile("hero");
        heroLocation[1] = heroLocation[1] - 1;
        checkMarket();
        checkWild();
        return true;
    }

    public boolean checkBoundaries(int x, int y) {
        boolean edgeCase = x < ROWS && x >= 0 && y < COLUMNS && y >= 0;
        if (!edgeCase) {
            return true;
        }
        return boardContent[x][y].getContent().equals("X");
    }

    public void checkMarket() {
        if (heroStep.getContent().equals("M")) {
            market.listOptions();
        }
    }

    public void checkWild() {
        if (heroStep.getContent().equals("W")) {
            if (Wild.fightOccurs()) {
                Combat combatScene = new Combat(player);
                combatScene.initiateCombat();
            }
        }
        return;
    }

}
