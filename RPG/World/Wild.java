package RPG.World;

// Wild class that contains logic on whether a fight should occur or not
public class Wild {

    public Wild() {

    }

    public static boolean fightOccurs() {
        // Randomly determine if a fight occurs
        return Math.random() < 0.5;
    }
}
