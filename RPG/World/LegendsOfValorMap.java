package RPG.World;

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
        if (row == 7 && col == 1) {
            return "H1   ";
        } else if (row == 1 && col == 3) {
            return "H2   ";
        } else if (row == 3 && col == 1) {
            return "   M1";
        } else if (row == 1 && col == 4) {
            return "   M2";
        } else if (row == 3 && col == 6) {
            return "H3 M3";
        } else {
            return "     ";
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
        CellType[][] map = {
                {CellType.NEXUS, CellType.NEXUS, CellType.INACCESSIBLE, CellType.NEXUS, CellType.NEXUS, CellType.INACCESSIBLE, CellType.NEXUS, CellType.NEXUS},
                {CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.CAVE, CellType.PLAIN, CellType.INACCESSIBLE, CellType.BUSH, CellType.BUSH},
                {CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.PLAIN},
                {CellType.CAVE, CellType.BUSH, CellType.INACCESSIBLE, CellType.BUSH, CellType.KOULOU, CellType.INACCESSIBLE, CellType.KOULOU, CellType.PLAIN},
                {CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.BUSH, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.BUSH},
                {CellType.KOULOU, CellType.KOULOU, CellType.INACCESSIBLE, CellType.KOULOU, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.PLAIN},
                {CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.PLAIN, CellType.INACCESSIBLE, CellType.PLAIN, CellType.PLAIN},
                {CellType.NEXUS, CellType.NEXUS, CellType.INACCESSIBLE, CellType.NEXUS, CellType.NEXUS, CellType.INACCESSIBLE, CellType.NEXUS, CellType.NEXUS}
        };

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
