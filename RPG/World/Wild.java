package RPG.World;

public class Wild {

    public Wild() {

    }

    public static boolean fightOccurs() {
        // Randomly determine if a fight occurs
        return Math.random() < 0.5;
    }
}
