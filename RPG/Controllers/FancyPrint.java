package RPG.Controllers;

public class FancyPrint {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    public FancyPrint() {

    }

    public void printBlack(String input) {
        System.out.print(ANSI_BLACK + input + ANSI_RESET);
    }

    public void printRed(String input) {
        System.out.print(ANSI_RED + input + ANSI_RESET);
    }

    public void printGreen(String input) {
        System.out.print(ANSI_GREEN + input + ANSI_RESET);
    }

    public void printYellow(String input) {
        System.out.print(ANSI_YELLOW + input + ANSI_RESET);
    }

    public void printBlue(String input) {
        System.out.print(ANSI_BLUE + input + ANSI_RESET);
    }

    public void printPurple(String input) {
        System.out.print(ANSI_PURPLE + input + ANSI_RESET);
    }

    public void printCyan(String input) {
        System.out.print(ANSI_CYAN + input + ANSI_RESET);
    }

    public void printWhite(String input) {
        System.out.print(ANSI_WHITE + input + ANSI_RESET);
    }

    public void clearScreen() {
        // ANSI codes for clear screen and return
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
