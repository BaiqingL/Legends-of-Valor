package hw3.LegendsAndHeros.src.Monsters;

import hw3.LegendsAndHeros.src.Monsters.Monster.MonsterType;
import hw3.LegendsAndHeros.src.World.Randomness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonsterBuilder extends Randomness {
    private final List<String> dragonNames = Arrays.asList("Desghidorrah", "Chrysophylax", "BunsenBurner", "Natsunomeryu");
    private final List<String> exoskeletonNames = Arrays.asList("Cyrrollalee", "Brandobaris", "BigBad-Wolf", "WickedWitch");
    private final List<String> spiritNames = Arrays.asList("Andrealphus", "Blinky", "Andromalius", "Chiang-shih");

    public MonsterBuilder() {

    }

    public List<Monster> buildMonsters(int numMonsters) {
        List<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < numMonsters; i++) {
            // Randomly choose between buildDragon, buildExoskeleton, and buildSpirit
            int random = getRandomNumber(0, 3);
            switch (random) {
                case 0 -> monsters.add(buildDragon());
                case 1 -> monsters.add(buildExoskeleton());
                case 2 -> monsters.add(buildSpirit());
            }
        }
        return monsters;
    }

    // Build a dragon
    private Monster buildDragon() {
        // Chose a random name from dragonNames
        String name = dragonNames.get(getRandomNumber(0, dragonNames.size()));
        // Chose a random level from 3 to 10
        int level = getRandomNumber(3, 10);
        // Chose a random damage from 100 to 1000
        int damage = getRandomNumber(100, 1000);
        // Chose a random defense from 400 to 600
        int defense = getRandomNumber(400, 600);
        // Chose a random dodge chance from 30 to 50
        int dodgeChance = getRandomNumber(30, 50);
        // Create a dragon monster from above parameters
        return new Monster(name, level, damage, defense, dodgeChance, MonsterType.DRAGON);
    }

    // Build an exoskeleton
    private Monster buildExoskeleton() {
        // Chose a random name from exoskeletonNames
        String name = exoskeletonNames.get(getRandomNumber(0, exoskeletonNames.size()));
        // Chose a random level from 3 to 10
        int level = getRandomNumber(3, 10);
        // Chose a random damage from 100 to 200
        int damage = getRandomNumber(100, 200);
        // Chose a random defense from 500 to 1000
        int defense = getRandomNumber(500, 1000);
        // Chose a random dodge chance from 30 to 50
        int dodgeChance = getRandomNumber(30, 50);
        // Create an exoskeleton monster from above parameters
        return new Monster(name, level, damage, defense, dodgeChance, MonsterType.EXOSKELETON);
    }

    // Build a spirit
    private Monster buildSpirit() {
        // Chose a random name from spiritNames
        String name = spiritNames.get(getRandomNumber(0, spiritNames.size()));
        // Chose a random level from 3 to 10
        int level = getRandomNumber(3, 10);
        // Chose a random damage from 100 to 200
        int damage = getRandomNumber(100, 200);
        // Chose a random defense from 10 to 20
        int defense = getRandomNumber(10, 20);
        // Chose a random dodge chance from 49 to 99
        int dodgeChance = getRandomNumber(49, 99);
        // Create a spirit monster from above parameters
        return new Monster(name, level, damage, defense, dodgeChance, MonsterType.SPIRIT);
    }

}
