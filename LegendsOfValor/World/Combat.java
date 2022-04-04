package LegendsOfValor.World;

import LegendsOfValor.FancyPrint;
import LegendsOfValor.Items.Armor;
import LegendsOfValor.Items.Potion;
import LegendsOfValor.Items.Spell;
import LegendsOfValor.Items.Weapon;
import LegendsOfValor.Monsters.Monster;
import LegendsOfValor.Monsters.MonsterBuilder;
import LegendsOfValor.Players.Hero;
import LegendsOfValor.Players.Player;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Combat extends Randomness {
    private static final FancyPrint printer = new FancyPrint();
    private final Player player;
    private final MonsterBuilder monsterBuilder = new MonsterBuilder();
    private final List<Monster> monsters;
    private int playerHealth = 0;
    private int monsterHealth = 0;

    public Combat(Player player) {
        this.player = player;
        monsters = monsterBuilder.buildMonsters(player.getPartySize());
        for (Hero hero : player.getHeros()) {
            playerHealth += hero.getHp();
        }
        for (Monster monster : monsters) {
            monsterHealth += monster.getHp();
        }
    }

    public void initiateCombat() {
        boolean invalidMove = false;
        printer.clearScreen();
        while (true) {
            if (invalidMove) {
                printer.printRed("Invalid input");
                invalidMove = false;
            }
            printer.printYellow("Wild enemies appeared!\n");
            for (Monster monster : monsters) {
                monster.print();
                printer.printRed("Enemy HP: " + this.monsterHealth + "\n");
            }
            Scanner scanner = new Scanner(System.in);
            printer.printYellow("(r) Run away (a) Attack (s) Spells (i) Info (p) Potions\nInput: ");
            String choice = scanner.nextLine();
            switch (choice.toLowerCase(Locale.ROOT)) {
                case "r":
                    return;
                case "a":
                    if (attack()) {
                        return;
                    }
                    break;
                case "s":
                    useSpell();
                    break;
                case "i":
                    combatInfo();
                    break;
                case "p":
                    usePotion();
                    break;
                default:
                    invalidMove = true;
            }
        }
    }

    private void combatInfo() {
        printer.clearScreen();
        printer.printBlue("You have " + this.playerHealth + " HP left.\n");
        printer.printRed("The enemy have " + this.monsterHealth + " HP left.\n");
        printer.printYellow("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    private void useSpell() {
        while (true) {
            List<Spell> spells = player.getInventory().getSpells();
            if (spells.size() == 0) {
                printer.clearScreen();
                printer.printYellow("No spells...\nPress enter to continue");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return;
            }
            int idx = 1;
            for (Spell spell : spells) {
                printer.printBlue(idx + ": " + spell.getName() + "\n");
                idx++;
            }
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= spells.size() && spells.get(choice - 1).getManaCost() <= player.getMana()) {
                    Spell spell = spells.get(choice - 1);
                    player.getInventory().removeSpell(choice - 1);
                    switch (spell.getSpellType()) {
                        case ICE:
                            for (Monster monster : monsters) {
                                monster.decreaseDamage(spell.getDamage(player.getHeros()));
                            }
                            break;
                        case FIRE:
                            for (Monster monster : monsters) {
                                monster.decreaseDefense(spell.getDamage(player.getHeros()));
                            }
                            break;
                        case LIGHTNING:
                            for (Monster monster : monsters) {
                                monster.decreaseDodgeChance(spell.getDamage(player.getHeros()));
                            }
                            break;
                    }
                    return;
                }
            } catch (Exception ignored) {
                printer.printRed("Invalid choice\n");
            }
        }
    }

    private void usePotion() {
        while (true) {
            List<Potion> potions = player.getInventory().getPotions();
            if (potions.size() == 0) {
                printer.clearScreen();
                printer.printYellow("No potions...\nPress enter to continue");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return;
            }
            int idx = 1;
            for (Potion potion : potions) {
                printer.printBlue(idx + ": " + potion.getName() + "\n");
                idx++;
            }
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= potions.size()) {
                    Potion potion = potions.get(choice - 1);
                    player.getInventory().removePotion(choice - 1);
                    switch (potion.getName()) {
                        case "Healing Potion":
                            this.playerHealth += potion.getIncreaseAmount();
                            return;
                        case "Strength Potion":
                            this.player.getHeros().get(0).increaseStrength(potion.getIncreaseAmount());
                            return;
                        case "Magic Potion":
                            this.player.getHeros().get(0).increaseMana(potion.getIncreaseAmount());
                            return;
                        case "Luck Elixir":
                            this.player.getHeros().get(0).increaseAgility(potion.getIncreaseAmount());
                        case "Mermaid Tears":
                            this.player.getHeros().get(0).increaseAll(potion.getIncreaseAmount());
                            return;
                        case "Ambrosia":
                            this.player.getHeros().get(0).increaseAll(potion.getIncreaseAmount());
                            return;
                    }
                }
            } catch (Exception ignored) {
                printer.printRed("Invalid choice\n");
            }
        }
    }

    private boolean attack() {
        // (strength + weapon damage)*0.05
        int strengthTotal = 0;
        for (Hero hero : player.getHeros()) {
            strengthTotal += hero.getStrength();
        }
        for (Weapon weapon : player.getInventory().getWeapons()) {
            strengthTotal += weapon.getDamage();
        }
        double damagePurposed = (double) strengthTotal * 0.05;

        if (hitMonsterLands()) {
            if (enemyTakeDamage(damagePurposed)) {
                win();
                printer.printYellow("Press Enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return true;
            }
            logEnemyDamage(damagePurposed);
        } else {
            printer.printRed("Your attacked missed!\n");
        }

        double enemyDamage = 0;
        for (Monster monster : monsters) {
            enemyDamage += monster.getDamage();
        }
        enemyDamage *= 0.05;
        if (hitHeroLands()) {
            int damagePastDefense = calculateArmorDefense((int) enemyDamage);
            if (heroTakeDamage((damagePastDefense))) {
                lose();
                printer.printYellow("Press Enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return true;
            }
            logHeroDamage(damagePastDefense);
        } else {
            printer.printGreen("You dodged an attack!\n");
        }
        return false;
    }

    private boolean hitMonsterLands() {
        // Monster dodge dodge_chance *.01
        for (Monster monster : monsters) {
            int dodgeChance = monster.getDodgeChance();
            double chance = (double) dodgeChance * 0.01;
            if ((double) getRandomNumber(0, 100) < chance) {
                return false;
            }
        }
        return true;
    }

    private int calculateArmorDefense(int incomingDamage) {
        int armorDefense = 0;
        for (Armor armor : player.getInventory().getArmors()) {
            armorDefense += armor.getDamageReduction();
        }
        return incomingDamage - armorDefense;
    }

    private boolean hitHeroLands() {
        // Hero dodge_chance *.002
        for (Hero hero : player.getHeros()) {
            int dodgeChance = hero.getAgility();
            double chance = (double) dodgeChance * 0.002;
            if ((double) getRandomNumber(0, 100) < chance) {
                return false;
            }
        }
        return true;
    }

    // Returns true if enemy dies
    // False if game continues
    private boolean enemyTakeDamage(double damage) {
        this.monsterHealth -= (int) damage;
        return this.monsterHealth < 0;
    }

    private boolean heroTakeDamage(double damage) {
        this.playerHealth -= (int) damage;
        return this.playerHealth < 0;
    }

    private void logEnemyDamage(double damage) {
        printer.clearScreen();
        printer.printRed("Enemy took " + (int) damage + " damage!\n");
        printer.printYellow("They now have " + this.monsterHealth + " combined health.\n");
    }

    private void logHeroDamage(double damage) {
        printer.clearScreen();
        printer.printRed("You took " + (int) damage + " damage!\n");
        printer.printYellow("You now have " + this.playerHealth + " combined health.\n");
    }

    private void win() {
        printer.clearScreen();
        printer.printGreen("You win!\n");
        int moneyWon = 100 * this.monsters.get(0).getLevel();
        printer.printYellow("You gained " + moneyWon + " gold.\n");
        for (Hero hero : player.getHeros()) {
            hero.levelUpBoost();
        }
        printer.printYellow("All of your heroes have leveled up.\n");
    }

    private void lose() {
        printer.clearScreen();
        printer.printRed("You lose.\n");
        printer.printRed("All your heroes will recover at half health.\n");
        for (Hero hero : player.getHeros()) {
            hero.setHp(hero.getHp() / 2);
        }
    }
}
