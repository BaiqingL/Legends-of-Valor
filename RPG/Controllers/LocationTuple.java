package RPG.Controllers;

public class LocationTuple {
    private final int x;
    private final int y;

    public LocationTuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(LocationTuple other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
