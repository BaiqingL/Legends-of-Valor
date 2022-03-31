package hw3.LegendsAndHeros.src.World;

public abstract class Randomness {
    public Randomness() {

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
