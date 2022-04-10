package RPG.World;

import RPG.Controllers.LocationTuple;

import java.util.ArrayList;
import java.util.List;

enum CellType {
    NEXUS,
    PLAIN,
    KOULOU,
    CAVE,
    BUSH,
    INACCESSIBLE
}

public class LegendsOfValorMap extends Randomness implements Map {

    // Create 2d array of tiles that is 8 by 8
    private static final Tile[][] gameContent = new Tile[8][8];
    private final CellType[][] map = new CellType[8][8];
    private final LocationTuple[] playerPosition = new LocationTuple[3];

    public LegendsOfValorMap() {
        // Create the content map
        int heroIdx = 1;
        for (int i = 0; i < gameContent.length; i++) {
            for (int j = 0; j < gameContent[i].length; j++) {
                if (i == 0 && (j - 1) % 3 == 0) {
                    gameContent[i][j] = new Tile("enemy");
                } else if (i == gameContent.length - 1 && (j - 1) % 3 == 0) {
                    gameContent[i][j] = new Tile("H" + heroIdx);
                    playerPosition[heroIdx - 1] = new LocationTuple(i, j);
                    heroIdx++;
                } else {
                    gameContent[i][j] = new Tile("wild");
                }
            }
        }

        // Create the cell type map
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                // Nexus cells
                if ((i == 0 || i == map.length - 1) && (j + 1) % 3 != 0) {
                    map[i][j] = CellType.NEXUS;
                }
                // Other types of cells, 40% chance of being plain and 20% chance being every other type
                else if ((j + 1) % 3 != 0) {
                    int rand = getRandomNumber(0, 100);
                    if (rand < 40) {
                        map[i][j] = CellType.PLAIN;
                    } else if (rand < 60) {
                        map[i][j] = CellType.KOULOU;
                    } else if (rand < 80) {
                        map[i][j] = CellType.CAVE;
                    } else {
                        map[i][j] = CellType.BUSH;
                    }
                } else {
                    map[i][j] = CellType.INACCESSIBLE;
                }

            }
        }
    }

    private static String getOuterCellStr(char c) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            str.append(c).append(" - ");
        }
        str.append(c).append("   ");
        return str.toString();
    }

    private static String getInnerCellStr(String component) {
        return "| " + component + " |   ";
    }

    private static String getCellComponent(int row, int col) {
        switch (gameContent[row][col].getContent()) {
            case "W":
                return "     ";
            case "E":
                return "M    ";
            default:
                return gameContent[row][col].getContent() + "   ";
        }
    }

    private static void createInnerCell(CellType[][] map, List<StringBuilder> printableMap, int row, int col) {
        String component = getCellComponent(row / 3, col);
        if (map[row / 3][col] == CellType.INACCESSIBLE)
            component = "X X X";
        printableMap.get(row).append(getInnerCellStr(component));
    }

    private static void createOutterCell(CellType[][] map, List<StringBuilder> printableMap, int row, int col) {
        switch (map[row / 3][col]) {
            case NEXUS -> printableMap.get(row).append(getOuterCellStr('N'));
            case PLAIN -> printableMap.get(row).append(getOuterCellStr('P'));
            case KOULOU -> printableMap.get(row).append(getOuterCellStr('K'));
            case CAVE -> printableMap.get(row).append(getOuterCellStr('C'));
            case BUSH -> printableMap.get(row).append(getOuterCellStr('B'));
            case INACCESSIBLE -> printableMap.get(row).append(getOuterCellStr('I'));
        }
    }

    @Override
    public void renderMap() {

        List<StringBuilder> printableMap = new ArrayList<>();
        int size = 8;
        for (int row = 0; row < size * 3; row++) {
            printableMap.add(new StringBuilder());
            if ((row / 3) % 2 == 0) {
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 0) {
                        createOutterCell(map, printableMap, row, col);
                    } else {
                        createInnerCell(map, printableMap, row, col);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            } else {
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 1) {
                        createOutterCell(map, printableMap, row, col);
                    } else {
                        createInnerCell(map, printableMap, row, col);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            }

            if (row % 3 == 2)
                printableMap.get(row).append("\n");
        }

        for (int i = 0; i < size * 3; i++) {
            System.out.print(printableMap.get(i));
        }
    }

    @Override
    public boolean moveUp() {
        return true;
    }

    @Override
    public boolean moveDown() {
        return true;
    }

    @Override
    public boolean moveLeft() {
        return true;
    }

    @Override
    public boolean moveRight() {
        return true;
    }
}
