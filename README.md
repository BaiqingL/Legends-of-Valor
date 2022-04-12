# CS611 Legends of Valor

## Baiqing Lyu
---------------------------------------------------------------------------
baiqing@bu.edu

U03215838


## Files
---------------------------------------------------------------------------

- Main
  - Main entry class for the game

### Package `Controllers`
- FancyPrint
  - Prints in color, serves as main printing class of the game
- Game
  - Main game interface
- GameLauncher
  - Main game launcher
- LegendsOfValor
  - Main game class for legends of valor
- MonstersAndHeros
  - Main game class for monsters and heros
- LocationTuple
  - Tuple class for location
### Package `Items`
- Item
  - Base class for items
- ItemBuilder
  - Builder class for items
- Buyable
  - Base interface for buyable items
- Consumable
  - Base interface for consumable items
- Sellable
  - Base interface for sellable items
- Armor
  - Armor class
- Weapon
  - Weapon class
- Potion
  - Potion class
- Spell
  - Spell class
### Package `Monsters`
- Monster
  - Base class for monsters
- MonsterBuilder
  - Builder class for monsters
- Dragon
  - Dragon class
- Exoskeleton
  - Exoskeleton class
- Spirit
  - Spirit class
### Package `Players`
- Hero
  - Abstract for heroes, contains some shared methods
- HeroBuilder
  - Builder class for heroes
- HeroSelector
  - Selector class for heroes, prompts users to select a hero
- Inventory
  - Inventory class
- Party
  - Party class that contains all the heros for the player
- Warrior
  - Warrior class
- Sorcerer
  - Sorcerer class
- Paladin
  - Paladin class
### Package `World`
- Combat
  - Combat class that contains all the combat logic
- LegendsOfValorMap
  - Map class for legends of valor, uses provided map from Piazza and modified
- MonstersAndHerosMap
  - Map class for monsters and heros
- Market
  - Market class that contains all the market logic and items
- Randomness
  - Randomness class that contains all the randomness logic to generate random numbers
- Tile
  - Tile class that contains tile information
- Wild
  - Wilderness class that checks if combat occurs for MonstersAndHeros

## How to run
---------------------------------------------------------------------------
1. Navigate to the main Project Directory after downloading the files
2. Run the following instructions on command line:
	javac RPG/*.java && java RPG.Main