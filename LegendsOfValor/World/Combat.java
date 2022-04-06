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
                printer.printYellow(heros.get(partyIdx).getName() + "'s Turn\n");
                printer.printYellow("(r) Run away (a) Attack (s) Spells (i) Info (p) Potions\nInput: ");
                String choice = scanner.nextLine();
                switch (choice.toLowerCase(Locale.ROOT)) {
                    case "r":
                        return;
                    case "a":
                        if (monsters.get(partyIdx).getHp() <= 0) {
                            break;
                        }
                        if ( attack(heros.get(partyIdx), monsters.get(partyIdx) )) {
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
                        usePotion(heros.get(partyIdx));
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
                    hero.getInventory().removeSpell(spells.get(choice - 1));
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
        strengthTotal += hero.getStrength();
        if(hero.getInventory().getWeapons().size()>0) {
            strengthTotal += hero.getInventory().getWeapons().get(0).getDamage();
        }
        int damagePurposed = (int)(strengthTotal * 0.05);

        if (hitMonsterLands()) {
            if (enemyTakeDamage(target, damagePurposed)) {
                printer.printGreen(target.getName() + " was killed!");
                printer.printYellow("Press Enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();

                return true;
            }
            logEnemyDamage(target, damagePurposed);
        } else {
            printer.printRed("Your attacked missed!\n");
        }

        double enemyDamage = 0;
        for (Monster monster : monsters) {
            enemyDamage += monster.getDamage();
        }
        enemyDamage *= 0.05;
        if (hitHeroLands()) {
            int armorDamageReduction = 0;
            if (hero.getInventory().getArmors().size() > 0) {
                armorDamageReduction = hero.getInventory().getArmors().get(0).getDamageReduction();
            }
            int damageDone = (int) enemyDamage - armorDamageReduction;
            if (heroTakeDamage(hero, damageDone)) {
                printer.printYellow("Press Enter to continue...");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                return true;
            }
            logHeroDamage(hero, damageDone);
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

//    private int calculateArmorDefense(int incomingDamage) {
//        int armorDefense = 0;
//        for (Armor armor : party.getInventory().getArmors()) {
//            armorDefense += armor.getDamageReduction();
//        }
//        return incomingDamage - armorDefense;
//    }

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
    private boolean enemyTakeDamage(Monster monster, int damage) {
        monster.decreaseHp( damage );
        return monster.getHp() <= 0;
    }

    private boolean heroTakeDamage(Hero hero, int damage) {
        hero.setHp( hero.getHp() -  damage );
        return hero.getHp() <= 0;
    }

    private void logEnemyDamage(Monster monster, int damage) {
        printer.clearScreen();
        printer.printRed(monster.getName() + " took " + damage + " damage!\n");
        printer.printYellow("They now have " + monster.getHp() + " health.\n");
    }

    private void logHeroDamage(Hero hero , double damage) {
        printer.clearScreen();
        printer.printRed(hero.getName() + " took " + (int) damage + " damage!\n");
        printer.printYellow("They now have " + hero.getHp() + " health.\n");
    }

    private boolean allHerosDead() {
        for (Hero hero : this.heros) {
            if (hero.getHp() > 0){
                return false;
            }
        }
        return true;
    }

    private boolean allMonstersDead() {
        for (Monster monster : this.monsters) {
            if (monster.getHp() > 0){
                return false;
            }
        }
        return true;
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
