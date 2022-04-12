package RPG.Players;

import java.util.List;

// Party class that contains all playable information and data
public class Party {
    private final List<Hero> heros;
    private final int level = 1;
    private int money;
    private int exp = 0;
    private int mana;

    public Party(List<Hero> heros) {
        this.heros = heros;
        for (Hero hero : heros) {
            money += hero.getMoney();
            exp += hero.getExp();
        }
    }

    public List<Hero> getHeros() {
        return heros;
    }

    public int getMana() {
        int result = 0;
        for (Hero hero : getHeros()) {
            result += hero.getMana();
        }
        return result;
    }

    public int getExp() {
        return exp;
    }

    public int getPartySize() {
        return heros.size();
    }

    public int getMoney() {
        return money;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        for (Hero hero : heros) {
            hero.levelUpBoost();
        }
    }

    public boolean spendMoney(int money) {
        if (this.money - money > 0) {
            this.money -= money;
            return true;
        }
        return false;
    }

    public void recieveMoney(int money) {
        this.money += money;
    }

    public boolean allHerosDead(){
        for (Hero hero : this.heros) {
            if (hero.getHp() > 0){
                return false;
            }
        }

        return true;
    }

}
