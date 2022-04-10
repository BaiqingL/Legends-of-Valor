package RPG.World;

// Abstract class to generate random numbers
public abstract class Randomness {
    public Randomness() {

    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
