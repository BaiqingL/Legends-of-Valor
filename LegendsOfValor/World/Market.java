package LegendsOfValor.World;

import LegendsOfValor.Controllers.FancyPrint;
import LegendsOfValor.Items.*;
import LegendsOfValor.Players.Party;

import java.util.List;
import java.util.Scanner;

public class Market {
    private static final ItemBuilder ITEM_BUILDER = new ItemBuilder();
    private static final FancyPrint printer = new FancyPrint();
    private static final List<Potion> potions = ITEM_BUILDER.buildPotions();
    private static final List<Weapon> weapons = ITEM_BUILDER.buildWeapons();
    private static final List<Armor> armors = ITEM_BUILDER.buildArmor();
    private final Party party;

    public Market(Party party) {
        this.party = party;

    }

    public void listOptions() {
        printer.clearScreen();

        boolean selected = false;
        while (!selected) {
            printer.printYellow("\nWelcome to the Market! You can only select one type of item to buy per visit\n");
            printer.printGreen("You have " + party.getMoney() + " gold.\n");
            printer.printBlue("1. Buy potions\n");
            printer.printRed("2. Buy weapons\n");
            printer.printGreen("3. Buy armor\n");
            printer.printCyan("4. Buy spell\n");
            printer.printYellow("5. Leave the market\n");
            printer.printYellow("6. Quit the game\n");
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= 6) {
                    switch (choice) {
                        case 1:
                            listPotions();
                            selected = true;
                            break;
                        case 2:
                            listWeapons();
                            selected = true;
                            break;
                        case 3:
                            listArmor();
                            selected = true;
                            break;
                        case 4:
                            listSpells();
                            selected = true;
                            break;
                        case 5:
                            return;
                        case 6:
                            System.exit(0);
                    }
                } else {
                    printer.clearScreen();
                    printer.printRed("Invalid choice.");
                }
            } catch (Exception ignored) {
                printer.clearScreen();
                printer.printRed("Wrong input");
            }
        }
    }

    private void listSpells() {
        printer.printCyan("\nSpells:\n");
        printer.printBlue("1. Ice 100 Gold\n");
        printer.printRed("2. Fire 100 Gold\n");
        printer.printGreen("3. Lightning 100 Gold\n");
        printer.printWhite("\n");

        while (true) {
            try {
                printer.printYellow("Enter the number of the spell you want to buy: ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= 3) {
                    switch (choice) {
                        case 1:
                            if (party.getMoney() >= 100) {
                                party.spendMoney(100);
                                party.getInventory().addSpell(new Spell("Ice", 100, 1, 50, Spell.SpellType.ICE));
                            } else {
                                printer.printRed("You don't have enough money!\n");
                                return;
                            }
                            break;
                        case 2:
                            if (party.getMoney() >= 100) {
                                party.spendMoney(100);
                                party.getInventory().addSpell(new Spell("Fire", 100, 1, 50, Spell.SpellType.FIRE));
                            } else {
                                printer.printRed("You don't have enough money!\n");
                                return;
                            }
                            break;
                        case 3:
                            if (party.getMoney() >= 100) {
                                party.spendMoney(100);
                                party.getInventory().addSpell(new Spell("Lightning", 100, 1, 50, Spell.SpellType.LIGHTNING));
                            } else {
                                printer.printRed("You don't have enough money!\n");
                                return;
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

    private void listPotions() {
        printer.printBlue("\nPotions:\n");
        for (int i = 0; i < potions.size(); i++) {
            Potion potion = potions.get(i);
            printer.printBlue(i + 1 + ". " + potion.getName() + " - " + potion.getPrice() + " gold\n");
        }
        printer.printBlue("\n");

        while (true) {
            printer.printYellow("Choose a potion to buy: ");
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= potions.size()) {
                    Potion potion = potions.get(choice - 1);
                    if (potion.getPrice() <= party.getMoney() && potion.getMinLevel() <= party.getLevel()) {
                        party.spendMoney(potion.getPrice());
                        // Add potion to inventory;
                        printer.printGreen("You bought " + potion.getName() + " for " + potion.getPrice() + " gold.\n");
                        party.getInventory().addPotion(potion);
                        printer.printYellow("Do you want to keep buying? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("n")) {
                            return;
                        }
                    } else {
                        printer.printRed("You can't buy this item.\n");
                        printer.printYellow("Would you like to quit? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("y")) {
                            return;
                        }
                    }
                } else {
                    printer.printRed("Invalid choice.\n");
                }
            } catch (Exception e) {
                System.out.println(e);
                printer.printRed("Wrong input.\n");
            }
        }
    }

    private void listWeapons() {
        printer.printRed("\nWeapons:\n");
        for (int i = 0; i < weapons.size(); i++) {
            Weapon weapon = weapons.get(i);
            printer.printRed(i + 1 + ". " + weapon.getName() + " - " + weapon.getPrice() + " gold\n");
        }
        printer.printRed("\n");

        while (true) {
            printer.printYellow("Choose a weapon to buy: ");
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= weapons.size()) {
                    Weapon weapon = weapons.get(choice - 1);
                    if (weapon.getPrice() <= party.getMoney() && weapon.getMinLevel() <= party.getLevel()) {
                        party.spendMoney(weapon.getPrice());
                        // Add weapon to inventory;
                        party.getInventory().addWeapon(weapon);
                        printer.printGreen("You bought " + weapon.getName() + " for " + weapon.getPrice() + " gold.\n");
                        printer.printYellow("Do you want to keep buying? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("n")) {
                            return;
                        }
                    } else {
                        printer.printRed("You can't buy this item.\n");
                        printer.printYellow("Would you like to quit? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("y")) {
                            return;
                        }
                    }
                } else {
                    printer.printRed("Invalid choice.\n");
                }
            } catch (Exception ignored) {
                printer.printRed("Wrong input.\n");
            }
        }

    }

    private void listArmor() {
        printer.printGreen("\nArmor:\n");
        for (int i = 0; i < armors.size(); i++) {
            Armor armor = armors.get(i);
            printer.printGreen(i + 1 + ". " + armor.getName() + " - " + armor.getPrice() + " gold\n");
        }
        printer.printGreen("\n");

        while (true) {
            printer.printYellow("Choose an armor to buy: ");
            try {
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= armors.size()) {
                    Armor armor = armors.get(choice - 1);
                    if (armor.getPrice() <= party.getMoney() && armor.getMinLevel() <= party.getLevel()) {
                        party.spendMoney(armor.getPrice());
                        // Add armor to inventory;
                        party.getInventory().addArmor(armor);
                        printer.printGreen("You bought " + armor.getName() + " for " + armor.getPrice() + " gold.\n");
                        printer.printYellow("Do you want to keep buying? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("n")) {
                            return;
                        }
                    } else {
                        printer.printRed("You can't buy this item.\n");
                        printer.printYellow("Would you like to quit? (y/n): ");
                        String input = scanner.next().toLowerCase();
                        if (input.equals("y")) {
                            return;
                        }
                    }
                } else {
                    printer.printRed("Invalid choice.\n");
                }
            } catch (Exception ignored) {
                printer.printRed("Wrong input.\n");
            }
        }

    }


}
