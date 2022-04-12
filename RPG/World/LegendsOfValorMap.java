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
    private static final int HERO_COUNT = 3;
    private static final int ENEMY_COUNT = 3;
    private static final Tile[][] gameContent = new Tile[8][8];
    private final CellType[][] map = new CellType[8][8];
    private final List<LocationTuple> playerPosition = new ArrayList<>();
    private final List<LocationTuple> enemyPosition = new ArrayList<>();


    public LegendsOfValorMap() {
        // Create the content map
        int heroIdx = 1;
        for (int i = 0; i < gameContent.length; i++) {
            for (int j = 0; j < gameContent[i].length; j++) {
                if (i == 0 && (j - 1) % 3 == 0) {
                    gameContent[i][j] = new Tile("enemy");
                    enemyPosition.add(new LocationTuple(i, j));
                } else if (i == gameContent.length - 1 && (j - 1) % 3 == 0) {
                    gameContent[i][j] = new Tile("H" + heroIdx);
                    playerPosition.add(new LocationTuple(i, j));
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

    private static void createOuterCell(CellType[][] map, List<StringBuilder> printableMap, int row, int col) {
        switch (map[row / 3][col]) {
            case NEXUS -> printableMap.get(row).append(getOuterCellStr('N'));
            case PLAIN -> printableMap.get(row).append(getOuterCellStr('P'));
            case KOULOU -> printableMap.get(row).append(getOuterCellStr('K'));
            case CAVE -> printableMap.get(row).append(getOuterCellStr('C'));
            case BUSH -> printableMap.get(row).append(getOuterCellStr('B'));
            case INACCESSIBLE -> printableMap.get(row).append(getOuterCellStr('I'));
        }
    }

    public static int getEnemyCount() {
        return ENEMY_COUNT;
    }

    public static int getHeroCount() {
        return HERO_COUNT;
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
                        createOuterCell(map, printableMap, row, col);
                    } else {
                        createInnerCell(map, printableMap, row, col);
                    }

                    if (col == size - 1)
                        printableMap.get(row).append("\n");
                }
            } else {
                for (int col = 0; col < size; col++) {
                    if (row % 2 == 1) {
                        createOuterCell(map, printableMap, row, col);
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

    private boolean checkMovable(LocationTuple destination) {
        for (LocationTuple location : playerPosition) {
            if (location.equals(destination)) {
                return false;
            }
        }
        for (LocationTuple location : enemyPosition) {
            if (location.equals(destination)) {
                return false;
            }
        }
        return true;
    }

    public boolean moveUp(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        Tile heroTile = gameContent[x][y];
        if (x - 1 >= 0) {
            LocationTuple destination = new LocationTuple(x - 1, y);
            if (checkMovable(destination)) {
                gameContent[x][y] = new Tile("wild");
                x -= 1;
                playerPosition.set(heroIdx, destination);
                gameContent[x][y] = heroTile;
                return true;
            }
        }
        return false;
    }

    public boolean moveDown(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        Tile heroTile = gameContent[x][y];
        if (x + 1 >= 0) {
            LocationTuple destination = new LocationTuple(x + 1, y);
            if (checkMovable(destination)) {
                gameContent[x][y] = new Tile("wild");
                x++;
                playerPosition.set(heroIdx, destination);
                gameContent[x][y] = heroTile;
                return true;
            }
        }
        return false;
    }

    public boolean moveLeft(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        Tile heroTile = gameContent[x][y];
        if ((y - 2) % 3 != 0) {
            LocationTuple destination = new LocationTuple(x, y - 1);
            if (checkMovable(destination)) {
                gameContent[x][y] = new Tile("wild");
                y -= 1;
                playerPosition.set(heroIdx, destination);
                gameContent[x][y] = heroTile;
                return true;
            }
        }
        return false;
    }

    public boolean moveRight(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        Tile heroTile = gameContent[x][y];
        if ((y + 2) % 3 != 0) {
            LocationTuple destination = new LocationTuple(x, y + 1);
            if (checkMovable(destination)) {
                gameContent[x][y] = new Tile("wild");
                y += 1;
                playerPosition.set(heroIdx, destination);
                gameContent[x][y] = heroTile;
                return true;
            }
        }
        return false;
    }

    public void backToNexus(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        Tile heroTile = gameContent[x][y];
        gameContent[x][y] = new Tile("wild");
        x = map.length - 1;
        playerPosition.set(heroIdx, new LocationTuple(x, y));
        gameContent[x][y] = heroTile;
    }

    public void teleport(int heroIdx, int targetHeroIdx) {
        Tile sourceHeroTile = gameContent[playerPosition.get(heroIdx).getX()][playerPosition.get(heroIdx).getY()];
        Tile targetHeroTile = gameContent[playerPosition.get(targetHeroIdx).getX()][playerPosition.get(targetHeroIdx).getY()];
        gameContent[playerPosition.get(heroIdx).getX()][playerPosition.get(heroIdx).getY()] = targetHeroTile;
        gameContent[playerPosition.get(targetHeroIdx).getX()][playerPosition.get(targetHeroIdx).getY()] = sourceHeroTile;

        int sourceX = playerPosition.get(heroIdx).getX();
        int sourceY = playerPosition.get(heroIdx).getY();
        int targetX = playerPosition.get(targetHeroIdx).getX();
        int targetY = playerPosition.get(targetHeroIdx).getY();

        playerPosition.set(heroIdx, new LocationTuple(targetX, targetY));
        playerPosition.set(targetHeroIdx, new LocationTuple(sourceX, sourceY));
    }

    public void moveMonster(int monsterIdx) {
        LocationTuple newMonsterLocation;

        int x = enemyPosition.get(monsterIdx).getX();
        int y = enemyPosition.get(monsterIdx).getY();
        Tile monsterTile = gameContent[x][y];
        x++;

        newMonsterLocation = new LocationTuple(x, y);

        if (checkMovable(newMonsterLocation)) {
            gameContent[x - 1][y] = new Tile("wild");
            enemyPosition.set(monsterIdx, newMonsterLocation);
            gameContent[x][y] = monsterTile;
        } else {

        }


    }

    public boolean checkMonsterReachedEnd() {
        for (int i = 0; i < enemyPosition.size(); i++) {
            if (enemyPosition.get(i).getX() == map.length - 1) {
                return true;
            }
        }
        return false;
    }

    public boolean checkHeroReachedEnd() {
        for (int i = 0; i < playerPosition.size(); i++) {
            if (playerPosition.get(i).getX() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean atNexus(int heroIdx) {
        int x = playerPosition.get(heroIdx).getX();
        int y = playerPosition.get(heroIdx).getY();
        return this.map[x][y] == CellType.NEXUS;
    }

    public List<Integer> enemyInRange(int heroIdx) {
        List<Integer> monstersInRange = new ArrayList<Integer>();
        int monsterIdx = 0;
        LocationTuple playerLocation = playerPosition.get(heroIdx);

        for (LocationTuple monsterLocation : this.enemyPosition) {
            if (playerLocation.isAdjacent(monsterLocation)) {
                monstersInRange.add(monsterIdx);
            }
            monsterIdx++;
        }

        return monstersInRange;

    }

    public int playerInRange(int monsterIdx) {
        for (int i = 0; i < this.playerPosition.size(); i++) {
            if (this.enemyPosition.get(monsterIdx).isAdjacent(this.playerPosition.get(i))) {
                return i;
            }
        }

        return -1;
    }

    public void removeMonster(int monsterIdx) {
        int x = enemyPosition.get(monsterIdx).getX();
        int y = enemyPosition.get(monsterIdx).getY();
        gameContent[x][y] = new Tile("wild");
        enemyPosition.remove(monsterIdx);

    }
}
