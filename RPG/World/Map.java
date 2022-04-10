package RPG.World;

// Map interface that includes the methods to be implemented by the Map class for each game
public interface Map {
    void renderMap();
    boolean moveUp();
    boolean moveDown();
    boolean moveLeft();
    boolean moveRight();
}
