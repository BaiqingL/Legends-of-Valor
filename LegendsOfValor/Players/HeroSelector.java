package LegendsOfValor.Players;

import LegendsOfValor.Controllers.FancyPrint;

import java.util.List;
import java.util.Scanner;

public class HeroSelector {
    private static final FancyPrint printer = new FancyPrint();
    private static final HeroBuilder heroBuilder = new HeroBuilder();
    private Hero hero;

    public HeroSelector() {

    }

    public void promptHero() {
        int choice = promptClass();
        switch (choice) {
            case 1 -> hero = promptHeroClass(heroBuilder.warriorBuilder());
            case 2 -> hero = promptHeroClass(heroBuilder.sorcererBuilder());
            case 3 -> hero = promptHeroClass(heroBuilder.paladinBuilder());
        }
    }

    private Hero promptHeroClass(List<Hero> heros) {
        printer.clearScreen();
        int choice;
        while (true) {
            try {
                printer.printYellow("Choose between the following characters:\n");
                int idx = 1;

                for (Hero warrior : heros) {
                    printer.printYellow(idx + ": " + warrior + "\n");
                    idx++;
                }
                Scanner scanner = new Scanner(System.in);
                printer.printYellow("Your choice: ");
                String input = scanner.next();
                choice = Integer.parseInt(input);
                if (choice <= heros.size() && choice > 0) {
                    break;
                }
            } catch (Exception e) {
                printer.printYellow("Wrong choice\n");
            }
        }
        Hero result = heros.get(choice - 1);
        printer.clearScreen();
        return result;
    }

    private int promptClass() {
        printer.printYellow("Which class would you like?\n");
        printer.printRed("1. Warrior\n");
        printer.printBlue("2. Sorcerer\n");
        printer.printGreen("3. Paladins\n");
        int choice;
        Scanner input = new Scanner(System.in);
        printer.printYellow("Your choice: ");
        while (true) {
            String output = input.next();
            try {
                choice = Integer.parseInt(output);
                if (choice > 0 && choice < 4) {
                    break;
                }
            } catch (Exception ignored) {

            }
            printer.clearScreen();
            printer.printYellow("Incorrect choice! Please choose between:\n");
            printer.printRed("1. Warrior\n");
            printer.printBlue("2. Sorcerer\n");
            printer.printGreen("3. Paladins\n");
            printer.printYellow("Your choice: ");

        }
        printer.clearScreen();

        switch (choice) {
            case 1 -> printer.printRed("You've chosen the Warrior class!\n");
            case 2 -> printer.printBlue("You've chosen the Sorcerer class!\n");
            case 3 -> printer.printGreen("You've chosen the Paladins class!\n");
        }

        return choice;
    }

    public Hero getHero() {
        return hero;
    }
}
