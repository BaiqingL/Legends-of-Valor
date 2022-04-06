package LegendsOfValor.World;

import LegendsOfValor.Controllers.FancyPrint;
import LegendsOfValor.Items.Armor;
import LegendsOfValor.Items.Potion;
import LegendsOfValor.Items.Spell;
import LegendsOfValor.Items.Weapon;
import LegendsOfValor.Monsters.Monster;
import LegendsOfValor.Monsters.MonsterBuilder;
import LegendsOfValor.Players.Hero;
import LegendsOfValor.Players.Party;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Combat extends Randomness {
    private static final FancyPrint printer = new FancyPrint();
    private final Party party;
    private final List<Hero> heros;
    private final MonsterBuilder monsterBuilder = new MonsterBuilder();
    private final List<Monster> monsters;

    public Combat(Party party) {
        this.party = party;
        this.heros = this.party.getHeros();
        monsters = monsterBuilder.buildMonsters(party.getPartySize());
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
                printer.printRed("Enemy HP: " + monster.getHp() + "\n");
            }
            Scanner scanner = new Scanner(System.in);
            for (int partyIdx = 0; partyIdx < heros.size(); partyIdx++) {
                printer.printYellow(heros.get(partyIdx).getName() + "'s Turn");
                printer.printYellow("(r) Run away (a) Attack (s) Spells (i) Info (p) Potions\nInput: ");
                String choice = scanner.nextLine();
                switch (choice.toLowerCase(Locale.ROOT)) {
                    case "r":
                        return;
                    case "a":
                        if (monsters.get(partyIdx).getHp() <= 0 || attack(heros.get(partyIdx), monsters.get(partyIdx) )) {
                            return;
                        }
                        break;
                    case "s":
                        useSpell(heros.get(partyIdx), monsters.get(partyIdx));
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
    }

    private void combatInfo() {
        printer.clearScreen();

        for (Hero hero : this.party.getHeros()) {
            printer.printBlue(hero.getClass() + " " + hero.getName() + " has " + hero.getHp() + " HP left.\n");
        }
        System.out.println();
        for (Monster monster : this.monsters) {
            printer.printRed(monster.getClass() + " " + monster.getName() + " has " + monster.getHp() + " HP left.\n");
        }

        printer.printYellow("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    private void useSpell(Hero hero, Monster target) {
        while (true) {
            List<Spell> spells = hero.getInventory().getSpells();
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
                if (choice > 0 && choice <= spells.size() && spells.get(choice - 1).getManaCost() <= party.getMana()) {
                    Spell spell = spells.get(choice - 1);
                    hero.getInventory().removeSpell(choice - 1);
                    switch (spell.getSpellType()) {
                        case ICE:
                            target.decreaseDamage(spell.getDamage(hero));
                            break;
                        case FIRE:
                            target.decreaseDefense(spell.getDamage(hero));
                            break;
                        case LIGHTNING:
                            target.decreaseDodgeChance(spell.getDamage(hero));
                            break;
                    }
                    return;
                }
            } catch (Exception ignored) {
                printer.printRed("Invalid choice\n");
            }
        }
    }

    private void usePotion(Hero hero) {
        while (true) {
            List<Potion> potions = hero.getInventory().getPotions();
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
                    hero.getInventory().removePotion(choice - 1);
                    switch (potion.getName()) {
                        case "Healing Potion":
                            hero.setHp( hero.getHp() + potion.getIncreaseAmount() );
                            return;
                        case "Strength Potion":
                            hero.increaseStrength(potion.getIncreaseAmount());
                            return;
                        case "Magic Potion":
                            hero.increaseMana(potion.getIncreaseAmount());
                            return;
                        case "Luck Elixir":
                            hero.increaseAgility(potion.getIncreaseAmount());
                        case "Mermaid Tears":
                            hero.increaseAll(potion.getIncreaseAmount());
                            return;
                        case "Ambrosia":
                            hero.increaseAll(potion.getIncreaseAmount());
                            return;
                    }
                }
            } catch (Exception ignored) {
                printer.printRed("Invalid choice\n");
            }
        }
    }

    private boolean attack(Hero hero, Monster target) {
        // (strength + weapon damage)*0.05
        int strengthTotal = 0;
        for (Hero hero : party.getHeros()) {
            strengthTotal += hero.getStrength();
        }
        for (Weapon weapon : hero.getInventory().getWeapons()) {
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
        for (Armor armor : party.getInventory().getArmors()) {
            armorDefense += armor.getDamageReduction();
        }
        return incomingDamage - armorDefense;
    }

    private boolean hitHeroLands() {
        // Hero dodge_chance *.002
        for (Hero hero : party.getHeros()) {
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
        return this.monsterHealth <= 0;
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
        for (Hero hero : party.getHeros()) {
            hero.levelUpBoost();
        }
        printer.printYellow("All of your heroes have leveled up.\n");
    }

    private void lose() {
        printer.clearScreen();
        printer.printRed("You lose.\n");
        printer.printRed("All your heroes will recover at half health.\n");
        for (Hero hero : party.getHeros()) {
            hero.setHp(hero.getHp() / 2);
        }
    }
}
