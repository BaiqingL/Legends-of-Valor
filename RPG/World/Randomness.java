package RPG.World;

// Abstract class to generate random numbers
public class Randomness {
    public Randomness() {

    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
