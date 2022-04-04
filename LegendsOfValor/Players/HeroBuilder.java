package LegendsOfValor.Players;

import java.util.ArrayList;
import java.util.List;

public class HeroBuilder {

    public HeroBuilder() {

    }

    public List<Hero> warriorBuilder() {
        List<Hero> warriors = new ArrayList<>();
        warriors.add(new Warrior("Gaerdal Ironhand", 100, 700, 500, 600, 1354, 7));
        warriors.add(new Warrior("Sehanine Monnbow", 600, 700, 800, 500, 2500, 8));
        warriors.add(new Warrior("Muamman Duathall", 300, 900, 500, 750, 2546, 6));
        return warriors;
    }

    public List<Hero> sorcererBuilder() {
        List<Hero> sorceres = new ArrayList<>();
        sorceres.add(new Sorcerer("Rillifane Rallathil", 1300, 750, 450, 500, 2500, 9));
        sorceres.add(new Sorcerer("Segojan Earthcaller", 900, 800, 500, 650, 2500, 5));
        sorceres.add(new Sorcerer("Reign Havoc", 800, 800, 800, 800, 2500, 8));
        return sorceres;
    }

    public List<Hero> paladinBuilder() {
        List<Hero> paladins = new ArrayList<>();
        paladins.add(new Paladin("Parzival", 300, 750, 650, 700, 2500, 7));
        paladins.add(new Paladin("Sehanine Moonbow", 300, 750, 700, 700, 2500, 7));
        paladins.add(new Paladin("Skoraeus Stonebones", 250, 650, 600, 350, 2500, 4));
        return paladins;
    }
}
