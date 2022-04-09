package RPG.World;

import RPG.Players.Hero;
import RPG.Controllers.FancyPrint;
import RPG.Items.*;
import RPG.Players.Party;


import java.util.List;
import java.util.Scanner;

public class Market {
    private static final ItemBuilder ITEM_BUILDER = new ItemBuilder();
    private static final FancyPrint printer = new FancyPrint();
    private static final List<Potion> potions = ITEM_BUILDER.buildPotions();
    private static final List<Weapon> weapons = ITEM_BUILDER.buildWeapons();
    private static final List<Armor> armors = ITEM_BUILDER.buildArmor();
    private static final List<Spell> spells = ITEM_BUILDER.buildSpell();
    private final Party party;
    private Hero hero;

    public Market(Party party) {
        this.party = party;

    }

    public void listOptions() {
        printer.clearScreen();
        Scanner scanner = new Scanner(System.in);

        boolean selected = false;
        while (!selected){
            printer.printYellow("Choose a hero to buy for\n");
            for (int i = 0; i< this.party.getHeros().size(); i++) {
                printer.printYellow((i+1) +". "+ this.party.getHeros().get(i).getName()+"\n");
            }

            try {
                int choice = scanner.nextInt();
                if (choice > 0 && choice <= 6) {
                    switch (choice) {
                        case 1:
                            this.hero = this.party.getHeros().get(0);
                            selected = true;
                            break;
                        case 2:
                            if(this.party.getPartySize() < 2){
                                printer.clearScreen();
                                printer.printRed("Invalid choice.");
                                break;
                            }
                            this.hero = this.party.getHeros().get(1);
                            selected = true;
                            break;
                        case 3:
                            if(this.party.getPartySize() < 3 ){
                                printer.clearScreen();
                                printer.printRed("Invalid choice.");
                                break;
                            }
                            this.hero = this.party.getHeros().get(2);
                            selected = true;
                            break;
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

        selected = false;
        while (!selected) {

            printer.printYellow("\nWelcome to the Market! You can only select one type of item to buy per visit\n");

            printer.printGreen("You have " + hero.getMoney() + " gold.\n");
            printer.printBlue("1. Buy potions\n");
            printer.printRed("2. Buy weapons\n");
            printer.printGreen("3. Buy armor\n");
            printer.printCyan("4. Buy spell\n");
            printer.printYellow("5. Leave the market\n");
            printer.printYellow("6. Quit the game\n");
            try {
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
                if (choice > 0 && choice <= spells.size()) {
                    Spell spell = spells.get(choice - 1);
                    if (spell.buy(hero)) {
                        break;
                    } else {
                        printer.printRed("You can't buy this item.\n");
                        return;
                    }
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
                    if (purchase(potion.buy(hero), potion)) return;
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
                    if (purchase(weapon.buy(hero), weapon)) return;
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
                    if (purchase(armor.buy(hero), armor)) return;
                } else {
                    printer.printRed("Invalid choice.\n");
                }
            } catch (Exception ignored) {
                printer.printRed("Wrong input.\n");
            }
        }

    }

    private boolean purchase( boolean buy, Item item) {
        Scanner scanner = new Scanner(System.in);
        if (buy) {
            printer.printGreen("You bought " + item.getName() + " for " + item.getPrice() + " gold.\n");
            printer.printYellow("Do you want to keep buying? (y/n): ");
            String input = scanner.next().toLowerCase();
            if (input.equals("n")) {
                return true;
            }
        } else {
            printer.printRed("You can't buy this item.\n");
            printer.printYellow("Would you like to quit? (y/n): ");
            String input = scanner.next().toLowerCase();
            if (input.equals("y")) {
                return true;
            }
        }
        return false;
    }


}
