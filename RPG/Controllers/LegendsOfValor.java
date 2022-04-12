package RPG.Controllers;

import RPG.Items.Potion;
import RPG.Monsters.Monster;
import RPG.Monsters.MonsterBuilder;
import RPG.Players.Hero;
import RPG.Players.HeroSelector;
import RPG.Players.Party;
import RPG.World.LegendsOfValorMap;
import RPG.World.Market;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class LegendsOfValor implements Game {
    private static final FancyPrint printer = new FancyPrint();
    private static final int NEW_MONSTER_SPAWN_TURN = 8;
    private final LegendsOfValorMap gameMap;
    private final MonsterBuilder monsterBuilder = new MonsterBuilder();
    private final List<Monster> monsters;
    int heroIdx;
    Hero currentHero;
    private Party party;
    // The market object
    private Market market;

    public LegendsOfValor() {
        gameMap = new LegendsOfValorMap();
        monsters = monsterBuilder.buildMonsters(gameMap.getEnemyCount());
    }

    private void printGameDetails() {
        printer.printYellow("\nControls:\n");
        printer.printYellow("w: Move up\n");
        printer.printYellow("s: Move down\n");
        printer.printYellow("a: Move left\n");
        printer.printYellow("d: Move right\n");
        printer.printYellow("r: Attack\n");
        printer.printYellow("p: Drink potion\n");
        printer.printYellow("i: Show info\n");
        if (gameMap.atNexus(this.heroIdx)) {
            printer.printYellow("b: Enter Nexus Market\n");
        } else {
            printer.printYellow("b: Back to Nexus\n");
        }
        printer.printYellow("t: Teleport/Swap with another hero\n");
        printer.printYellow("q: Quit\n");
    }

    @Override
    public void startGame() {
        printer.clearScreen();
        // Start game loop
        while (true) {
            List<Hero> heros = new ArrayList<>();
            // We know that there will always be 3 heros in the game, so default select three heros
            printer.printYellow("Select your three champions\n");
            for (int i = 0; i < 3; i++) {
                HeroSelector selector = new HeroSelector();
                selector.promptHero();
                heros.add(selector.getHero());
                printer.printGreen("You've chosen: " + selector.getHero().getName() + "\n");
            }

            // Create the party and start the movement loop
            this.heroIdx = 0;
            party = new Party(heros);
            boolean improperMove = false;
            int turn = 1;
            while (true) {
                printer.clearScreen();

                // Turn based movement, check which hero should move and have moved
                boolean[] heroMoved = new boolean[heros.size()];
                for (int i = 0; i < heroMoved.length; i++) {
                    heroMoved[i] = false;
                }
                // Makes sure that each hero has taken their turn
                while (heroRemainsUnmoved(heroMoved)) {
                    gameMap.renderMap();
                    printGameDetails();
                    this.currentHero =  party.getHeros().get(heroIdx);

                    if (improperMove) {
                        printer.printRed("Improper move\n");
                        improperMove = false;
                    }
                    printer.printGreen("\nTurn " + turn + "\n");
                    printer.printYellow("Currently choosing action for Hero " + (this.heroIdx + 1) + ": " + party.getHeros().get(this.heroIdx).getName() + "\n");
                    String choice = getPlayerInput();

                    switch (choice) {
                        case "w" -> {
                            if (!gameMap.moveUp(this.heroIdx)) {
                                improperMove = true;
                            } else {
                                setBuff(this.heroIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            }
                        }
                        case "s" -> {
                            if (!gameMap.moveDown(this.heroIdx)) {
                                improperMove = true;
                            } else {
                                setBuff(this.heroIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            }
                        }
                        case "a" -> {
                            if (!gameMap.moveLeft(this.heroIdx)) {
                                improperMove = true;
                            } else {
                                setBuff(this.heroIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            }
                        }
                        case "d" -> {
                            if (!gameMap.moveRight(this.heroIdx)) {
                                improperMove = true;
                            } else {
                                setBuff(this.heroIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            }
                        }
                        case "r" -> {
                            List<Integer> monstersInRange = gameMap.enemyInRange(this.heroIdx);
                            if (monstersInRange.size() > 0) {
                                int monsterIdx = getAttackTarget(monstersInRange);
                                enemyAttacked(monsterIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            } else {
                                improperMove = true;
                            }


                        }
                        case "p" -> {
                            Potion potionToDrink = getPotion();
                            drinkPotion(potionToDrink);
                            heroMoved[this.heroIdx] = true;
                            this.heroIdx++;
                        }
                        case "b" -> {
                            if (gameMap.atNexus(this.heroIdx)) {
                                this.market = new Market(party.getHeros().get(this.heroIdx));
                                market.listOptions();
                            } else {
                                gameMap.backToNexus(this.heroIdx);
                                setBuff(this.heroIdx);
                                heroMoved[this.heroIdx] = true;
                                this.heroIdx++;
                            }
                        }
                        case "t" -> {
                            int targetHeroIdx = getTeleportTarget();
                            gameMap.teleport(this.heroIdx, targetHeroIdx);
                            setBuff(this.heroIdx);
                            setBuff(targetHeroIdx);
                            heroMoved[this.heroIdx] = true;
                            this.heroIdx++;
                        }
                        case "q" -> {
                            return;
                        }
                        case "i" -> printInfo();
                        default -> {
                        }
                    }

                    // Check for win loss condition
                    if (gameMap.checkHeroReachedEnd()) {
                        printer.clearScreen();
                        printer.printGreen("You've reached the end!\n");
                        printer.printGreen("You win!\n");
                        System.exit(0);
                    }
                }

                // Heros finished, now move monsters

                printer.printYellow("\nENEMY TURN:\n");
                for (int monsterIdx = 0; monsterIdx < this.monsters.size(); monsterIdx++) {
                    int heroToAttack = gameMap.playerInRange(monsterIdx);
                    if (heroToAttack != -1) {
                        playerAttacked(monsterIdx, heroToAttack);
                    } else {
                        gameMap.moveMonster(monsterIdx);
                    }
                }
                // Reset hero index
                heroIdx = 0;
                if (gameMap.checkMonsterReachedEnd() || this.party.allHerosDead()) {
                    printer.clearScreen();
                    // Render map to show monsters that reached the end
                    gameMap.renderMap();
                    printer.printRed("You've been defeated!\n");
                    System.exit(0);
                }
                // Increment turn
                turn++;

                if (turn % NEW_MONSTER_SPAWN_TURN == 0) {
                    spawnMonsters();
                    printer.printRed("New monsters have spawned!\n");
                }
            }
        }
    }

    // Checks if any hero has not moved
    private boolean heroRemainsUnmoved(boolean[] heroMoved) {
        for (boolean moved : heroMoved) {
            if (!moved) {
                return true;
            }
        }
        return false;
    }

    private void spawnMonsters() {
        monsters.addAll(monsterBuilder.buildMonsters(3));
        gameMap.addMonsters();
    }

    private int getAttackTarget(List<Integer> targets) {
        printer.clearScreen();
        Scanner scanner = new Scanner(System.in);
        int monsterIdx;

        while (true) {

            printer.printYellow("Choose an Enemy to attack:\n");
            monsterIdx = 1;
            for (int target : targets) {
                Monster monster = this.monsters.get(target);
                printer.printYellow((monsterIdx) + ". " + monster.getName() + " the " + monster.getClass().getSimpleName() + "\n");
                monsterIdx++;
            }

            try {
                monsterIdx = Integer.parseInt(scanner.nextLine());
                if (monsterIdx <= 0 || monsterIdx > targets.size()) {
                    throw new Exception("Invalid Target");
                }
                return targets.get(monsterIdx - 1);
            } catch (Exception e) {
                printer.printRed("Improper input\n");
            }
        }

    }

    private int getTeleportTarget() {
        printer.clearScreen();
        Scanner scanner = new Scanner(System.in);
        int targetHeroIdx = -1;
        while (targetHeroIdx == -1) {
            printer.printYellow("Currently choosing target for Hero " + (this.heroIdx + 1) + ": " + party.getHeros().get(this.heroIdx).getName() + "\n");
            printer.printYellow("Choose a hero to teleport to: \n");
            for (int i = 0; i < party.getHeros().size(); i++) {
                if (i != this.heroIdx) {
                    printer.printGreen(i + 1 + ": " + party.getHeros().get(i).getName() + "\n");
                }
            }
            try {
                targetHeroIdx = Integer.parseInt(scanner.nextLine());
                if (targetHeroIdx < 0 || targetHeroIdx > party.getHeros().size() || targetHeroIdx == this.heroIdx + 1) {
                    // Reset to keep looping
                    targetHeroIdx = -1;
                    throw new Exception("Invalid target");
                }
            } catch (Exception e) {
                printer.printRed("Improper input\n");
            }
        }
        // Offset by 1 to account for array indexing
        return targetHeroIdx - 1;
    }


    private Potion getPotion() {
        printer.clearScreen();
        Scanner scanner = new Scanner(System.in);
        List<Potion> potions = this.currentHero.getInventory().getPotions();
        int potionIdx;

        if (potions.size() < 1) {
            printer.printRed("No Potions in Inventory\n");
            printer.printYellow("Press Enter to continue...\n");
            scanner.nextLine();
            return null;

        }

        while (true) {
            printer.printYellow("Choose a Potion to Drink:\n");
            potionIdx = 1;

            for (Potion potion : potions) {
                printer.printYellow((potionIdx) + ". " + potion.getName() + "\n");
                potionIdx++;
            }
            try {
                potionIdx = Integer.parseInt(scanner.nextLine());
                if (potionIdx <= 0 || potionIdx > potions.size()) {
                    throw new Exception("Invalid Target");
                }
                return potions.get(potionIdx - 1);
            } catch (Exception e) {
                printer.printRed("Improper input\n");
            }
        }


    }

    private void enemyAttacked(int monsterIdx) {
        Monster monster = this.monsters.get(monsterIdx);
        Hero hero = party.getHeros().get(this.heroIdx);
        int damageDone = hero.attack(monster);

        if (damageDone == -1) {
            printer.printRed(monster.getName() + " Dodged!\n");
        } else if (monster.getHp() > 0) {
            printer.clearScreen();
            printer.printGreen(monster.getName() + " took " + damageDone + " damage!\n");
            printer.printYellow("They now have " + monster.getHp() + " health.\n");
        } else {
            printer.printGreen(monster.getName() + " was killed!\n");
            this.gameMap.removeMonster(monsterIdx);
            this.monsters.remove(monster);
        }

        printer.printYellow("Press Enter to continue...\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();


    }

    private void playerAttacked(int monsterIdx, int targetHeroIdx) {
        Monster monster = this.monsters.get(monsterIdx);
        Hero hero = party.getHeros().get(targetHeroIdx);
        int damageDone = monster.attack(hero);

        printer.printYellow(monster.getName() + " Attacked " + hero.getName() + ":\n");

        if (damageDone == -1) {
            printer.printRed(hero.getName() + " Dodged!\n");
        } else if (hero.getHp() > 0) {
            printer.clearScreen();
            printer.printRed(hero.getName() + " took " + damageDone + " damage.\n");
            printer.printYellow("They now have " + hero.getHp() + " health.\n");
        } else {
            printer.printRed(hero.getName() + " was killed.\n");
            printer.printGreen(hero.getName() + "is spawned back at the Nexus.\n");
            this.gameMap.backToNexus(targetHeroIdx);
            this.party.healHero(targetHeroIdx);
        }

        printer.printYellow("Press Enter to continue...\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

    }

    public void drinkPotion(Potion potion) {
        if (potion != null) {
            potion.consume(this.currentHero);
            printer.printGreen(potion.getName() + " increased to following stats for " + this.currentHero.getName() + " by " + potion.getIncreaseAmount() + ":\n"
                    + potion.getStatToIncrease().replace(' ', '\n') + "\n");
            printer.printYellow("Press Enter to continue...\n");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        }
    }

    public void setBuff(int targetIdx){
        LegendsOfValorMap.CellType cell = this.gameMap.getHeroCell(targetIdx);
        Hero hero = this.party.getHeros().get(targetIdx);

        switch (cell){
            case BUSH -> {
                this.currentHero.setDexterity( (int)(this.currentHero.getDexterity() * 1.1) );
            }
            case CAVE -> {
                this.currentHero.setAgility( (int)(this.currentHero.getAgility() * 1.1) );
            }
            case KOULOU -> {
                this.currentHero.setStrength( (int)(this.currentHero.getStrength() * 1.1) );
            }
            default -> {

            }
        }

        if (hero.getPreviousCell() != null) {
            switch (this.currentHero.getPreviousCell()) {
                case BUSH -> {
                    this.currentHero.setDexterity((int) (this.currentHero.getDexterity() / 1.1));
                }
                case CAVE -> {
                    this.currentHero.setAgility((int) (this.currentHero.getAgility() / 1.1));
                }
                case KOULOU -> {
                    this.currentHero.setStrength((int) (this.currentHero.getStrength() / 1.1));
                }
                default -> {

                }
            }
        }

        this.currentHero.setPreviousCell(cell);
    }



    // Prints the game details
    private void printInfo() {
        printer.clearScreen();
        printer.printYellow("\nMap cell meanings:\n");
        printer.printGreen("N: Nexus, acts as market place for items\n");
        printer.printGreen("B: Bush, boost dexterity by 10%\n");
        printer.printGreen("K: Koulou, boost strength by 10%\n");
        printer.printGreen("C: Cave, boost agility by 10%\n");
        printer.printRed("I: Inaccessible, borders that can not be accessed\n");

        for (Hero hero : party.getHeros()) {
            printer.printYellow(hero.toString() + "\n");
            printer.printGreen("Money: " + hero.getMoney() + "\n");
            hero.getInventory().printInventory();
        }
        Scanner scanner = new Scanner(System.in);
        printer.printYellow("Press enter to return...");
        scanner.nextLine();
        printer.clearScreen();
    }


    // Get the player input and sanitize it
    @Override
    public String getPlayerInput() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printer.printYellow("Input: ");
            String choice = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (choice.equals("w") ||
                    choice.equals("a") ||
                    choice.equals("s") ||
                    choice.equals("d") ||
                    choice.equals("r") ||
                    choice.equals("p") ||
                    choice.equals("b") ||
                    choice.equals("t") ||
                    choice.equals("q") ||
                    choice.equals("i")) {
                return choice;
            } else {
                printer.clearScreen();
                gameMap.renderMap();
                printGameDetails();
                printer.printRed("Incorrect choice\n");
            }
        }
    }

}
