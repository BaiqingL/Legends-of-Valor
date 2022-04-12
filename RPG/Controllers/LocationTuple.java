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

    public boolean isAdjacent(LocationTuple otherLocation){


        if (otherLocation.equals(new LocationTuple(this.x-1, this.y)) ||
                otherLocation.equals(new LocationTuple(this.x-1, this.y-1)) ||
                otherLocation.equals(new LocationTuple(this.x-1, this.y+1)) ||
                otherLocation.equals(new LocationTuple(this.x+1, this.y)) ||
                otherLocation.equals(new LocationTuple(this.x+1, this.y-1)) ||
                otherLocation.equals(new LocationTuple(this.x+1, this.y+1)) ||
                otherLocation.equals(new LocationTuple(this.x, this.y-1)) ||
                otherLocation.equals(new LocationTuple(this.x, this.y+1))){

            return true;

        }
        return false;
    }

    public boolean equals(LocationTuple other) {
        return this.getX() == other.getX() && this.getY() == other.getY();
    }
}
