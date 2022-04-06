
import java.util.Scanner;

public class World {
    private Player player;
    private Monster[] monsters;
    private Item[] items;
    private Map map;
    private boolean runningGameWorld;

    public World(Player player, Monster monster, Scanner scanner){
        this.map = new Map();
        this.player = player;
        this.monsters = new Monster[]{monster};
        this.runningGameWorld = true;
        this.runWorldLoop(scanner);
    }

    public World(Player player, Monster monster, int[] PlayerPo, int[] MonsterPo){
        this.runningGameWorld = true;
        this.player = player;

    }

    public boolean isRunningGameWorld() {
        return runningGameWorld;
    }

    public void setRunningGameWorld(boolean runningGameWorld) {
        this.runningGameWorld = runningGameWorld;
    }

    // Player move command execution
    private void move(Unit unit, String dir){
        int x = unit.getXCoordinate();
        int y = unit.getYCoordinate();
        switch (dir){
            case "w":
                if ((y - 1 >= 0) && (map.traversable(x, y-1))) {
                    unit.setYCoordinate(y-1);
                }
                break;
            case "a":
                if ((x - 1 >= 0) && (map.traversable(x-1, y))){
                    unit.setXCoordinate(x-1);
                }
                break;
            case "s":
                if ((y + 1 < map.getHeight()) && (map.traversable(x, y+1))){
                    unit.setYCoordinate(y+1);
                }
                break;
            case "d":
                if ((x + 1 < map.getWidth()) && (map.traversable(x+1, y))){
                    unit.setXCoordinate(x+1);
                }
                break;
        }
    }

    // Monster move decision maker
    private void monsterMove(Monster monster){
        int px = player.getXCoordinate();
        int py = player.getYCoordinate();
        int mx = monster.getXCoordinate();
        int my = monster.getYCoordinate();
        // Check if player is in 3*3 of monster
        if ((Math.abs(px-mx)<3) && (Math.abs(py-my)<3)){
            // Decide move right or left and check if it's possible
            if ((px < mx) && (map.traversable(mx-1,my))){
                move(monster, "a");
            }else if ((px > mx) && (map.traversable(mx+1,my))){
                move(monster, "d");
            // Decide move northward or southward
            }else if ((py < my) && (map.traversable(mx,my-1))){
                move(monster, "s");
            }else if (map.traversable(mx,my+1)){
                move(monster, "w");
            // There is obstacle in the way, so try another direction.
            }else if (map.traversable(mx-1,my)){
                move(monster, "a");
            }else if (map.traversable(mx+1,my)){
                move(monster, "d");
            }else if (map.traversable(mx,my-1)){
                move(monster, "s");
            }
        }
    }

    // Print out rendered world as text.
    public void renderWorld(){
        for (int y = 0; y < map.getHeight(); y++){
            for (int x = 0; x < map.getWidth(); x++){
                // Initialize a tile
                Entity temp = new Entity(x,y);
                String renderedTile = map.getMap()[y][x];
                // Try render all items
                if (items != null){
                    for (Item item:items){
                        if (Utility.comparePosition(item,temp)) {
                            renderedTile = item.getSymbol();
                        }
                    }
                }

                // Try render all monsters
                if (monsters != null){
                    for (Monster monster:monsters){
                        if (Utility.comparePosition(monster,temp)) {
                            renderedTile = monster.getSymbol();
                        }
                    }
                }

                // Try render player
                if (Utility.comparePosition(player,temp)) {
                    renderedTile = player.getSymbol();
                }
                System.out.print(renderedTile);
            }
            System.out.println();
        }
        System.out.println();
    }

    // Update monster array after beating it.
    private void updateMonsters(Monster monster){
        Monster[] temp = new Monster[monsters.length-1];
        int i = 0;
        for (Monster m:monsters){
            if (m != monster){
                temp[i] = m;
                i++;
            }
        }
        monsters = temp;
    }

    // Update item array after using it.
    private void updateItems(Item item){
        Item[] temp = new Item[items.length-1];
        int i = 0;
        for (Item m:items){
            if (m != item){
                temp[i] = m;
                i++;
            }
        }
        items = temp;
    }
    // Console of World and Battle
    public void runWorldLoop(Scanner scanner){
        boolean battleResult = true;  // A record for all kinds of battles.
        player.heal();
        for (Monster monster:monsters){
            monster.heal();
        }
        this.renderWorld();
        while (this.isRunningGameWorld()) {
            System.out.print("> ");
            String command = scanner.nextLine();
            if (command.equals("home")) {
                System.out.println("Returning home...");
                this.setRunningGameWorld(false);
            } else {
                // Check all monsters move and begin fight if monster meets player
                for (Monster monster:monsters){
                    monsterMove(monster);
                    if (Utility.comparePosition(monster,player)){
                        Battle battle = new Battle(player,monster);
                        battleResult = battle.getBattleResult();
                        // Break the checking loop if lose
                        if (battleResult == false) break;
                        // Clear the dead monster from array
                        updateMonsters(monster);
                    }
                }
                if (battleResult == false) break;
                // Player's turn to move
                this.move(player,command);
                // Check if player meets any monster
                if (monsters != null){
                    for (Monster monster:monsters){
                        if (Utility.comparePosition(monster,player)){
                            Battle battle = new Battle(player,monster);
                            battleResult = battle.getBattleResult();
                            if (battleResult == false) break;
                            updateMonsters(monster);
                        }
                    }
                }
                if (battleResult == false) break;
                //Check if player picks up any item
                if (items != null){
                    for (Item item:items){
                        if (Utility.comparePosition(item,player)){
                            item.usedBy(player);
                            // Check if the item is the magic stone
                            if (item.getName().compareTo("WarpingStone") == 0){
                                this.setRunningGameWorld(false);
                            }
                            updateItems(item);
                        }
                    }
                }
                if (isRunningGameWorld()){
                    this.renderWorld();
                }
            }
        }
    }
}
