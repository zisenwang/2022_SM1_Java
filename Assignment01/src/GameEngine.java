/**
 *
 * @author Zisen Wang, zisenw@student.unimelb.edu.au, 1098400
 *
 */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameEngine {

    private Player player;
    private Monster monster;
    public Monster[] monsters;
    public Item[] items;
    private boolean runningMainMenu;

    public boolean isRunningMainMenu() {
        return runningMainMenu;
    }


    public Player getPlayer() {
        return player;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setRunningMainMenu(boolean runningMainMenu) {
        this.runningMainMenu = runningMainMenu;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public GameEngine() {
        this.runningMainMenu = true;
        this.player = null;
        this.monsters = null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // TODO: Some starter code has been provided below.
        // Edit this method as you find appropriate.

        // Creates an instance of the game engine.
        GameEngine gameEngine = new GameEngine();

        // Runs the main game loop.
        gameEngine.runGameLoop(scanner);

    }


    /*
     *  Logic for running the main game loop.
     */
    private void runGameLoop(Scanner scanner) {
        String worldInfo = null; // A variable to receive advanced command.
        // Print out the title text.
        displayTitleText();

        // Main Menu receive commands.
        while (runningMainMenu) {
            System.out.print("> ");
            String s = scanner.nextLine();

            // Split the input content to find command.
            String[] commands = s.split(" ");
            String command = commands[0];
            if (commands.length > 1) {
                worldInfo = commands[1];
            }
            // Receive command
            switch (command) {
                case "help":
                    this.help();
                    break;
                case "commands":
                    this.commands();
                    break;
                case "player":
                    this.player(scanner);

                    break;
                case "monster":
                    this.monster(scanner);

                    break;
                case "start":
                    this.start(this.getPlayer(), this.getMonster(), worldInfo, scanner);
                    break;
                case "exit":
                    this.exit();
                    break;
            }
        }

    }


    /*
     *  Displays the title text.
     */
    private void displayTitleText() {

        String titleText = " ____                        \n" +
                "|  _ \\ ___   __ _ _   _  ___ \n" +
                "| |_) / _ \\ / _` | | | |/ _ \\\n" +
                "|  _ < (_) | (_| | |_| |  __/\n" +
                "|_| \\_\\___/ \\__, |\\__,_|\\___|\n" +
                "COMP90041   |___/ Assignment ";

        System.out.println(titleText);
        System.out.println();
        System.out.println("Player: " + (this.getPlayer() == null ? "[None]" : this.getPlayer().showShortInfo()) +
                "  | Monster: " + (this.getMonster() == null ? "[None]" : this.getMonster().showShortInfo()));
        System.out.println();
        System.out.println("Please enter a command to continue.");
        System.out.println("Type 'help' to learn how to get started.");
        System.out.println();
    }

    // help command method
    private void help() {
        System.out.println("Type 'commands' to list all available commands");
        System.out.println("Type 'start' to start a new game");
        System.out.println("Create a character, battle monsters, and find treasure!\n");
    }

    // commands method
    private void commands() {
        System.out.println("help\nplayer\nmonster\nstart\nexit\n");
    }

    // player command method
    private void player(Scanner scanner) {
        if (this.getPlayer() == null){
            // Create player
            Player player = this.createNewPlayer(scanner);
            this.setPlayer(player);
            this.promptToTitle(scanner);
        }else{
            this.player.printDetail();
            this.promptToTitle(scanner);
        }

    }

    // monster command method
    private void monster(Scanner scanner) {
        this.setMonster(Monster.createMonster(scanner));
        this.promptToTitle(scanner);
    }

    // start command method
    private void start(Player player, Monster monster, String worldInfo, Scanner scanner) {
        int countMonster = 0;
        int countItem = 0;
        ArrayList mm = new ArrayList();
        ArrayList ii = new ArrayList();
        if (player == null) {
            System.out.println("No player found, please create a player with 'player' first.");
            this.promptToTitle(scanner);
        }
        // Default play mode without loading map.
        if (worldInfo == null) {
            if (monster == null){
                System.out.println("No monster found, please create a monster with 'monster' first.");
                this.promptToTitle(scanner);
            }else {
                new World(player, monster, scanner);
                this.promptToTitle(scanner);
            }
        }else{
            FileReader fis = null;
            try {
                fis = new FileReader(worldInfo+".dat");
                char[] chars = new char[100];
                int countRead = 0;
                String loadedMap = "";
                while((countRead = fis.read(chars)) != -1){
                    loadedMap = loadedMap + new String(chars,0,countRead);
                }
                Scanner scan = new Scanner(loadedMap);
                String[] infoMap = scan.nextLine().split(" ");
                Map map = new Map(Integer.parseInt(infoMap[0]),Integer.parseInt(infoMap[1]));
                map.setMap(scan);
                while(scan.hasNextLine()) {
                    String[] s = scan.nextLine().split(" ");
                    if (s[0].compareTo("player") == 0){
                        player.setXCoordinate(Integer.parseInt(s[1]));
                        player.setYCoordinate(Integer.parseInt(s[2]));
                    }
                    if (s[0].compareTo("monster") == 0){
                        Monster m = new Monster(s[3],Integer.parseInt(s[4]),Integer.parseInt(s[5]));
                        m.setXCoordinate(Integer.parseInt(s[1]));
                        m.setYCoordinate(Integer.parseInt(s[2]));
                        countMonster++;
                        mm.add(m);
                    }
                    if (s[0].compareTo("item") == 0){
                        Item i = null;
                        if (s[3].compareTo("+") == 0){
                            i = new HealingItem(Integer.parseInt(s[1]),Integer.parseInt(s[2]));
                        }
                        if (s[3].compareTo("^") == 0){
                            i = new DamagePerk(Integer.parseInt(s[1]),Integer.parseInt(s[2]));
                        }
                        if (s[3].compareTo("@") == 0){
                            i = new WarpStone(Integer.parseInt(s[1]),Integer.parseInt(s[2]));
                        }
                        countItem++;
                        ii.add(i);
                    }
                    monsters = new Monster[countMonster];
                    int j = 0;
                    for (Object m:mm){
                        monsters[j] = (Monster) m;
                        j++;
                    }
                    items = new Item[countItem];
                    j = 0;
                    for (Object i:ii){
                        items[j]=(Item) i;
                        j++;
                    }
                }

            } catch (FileNotFoundException e) {
                System.out.println("Map not found.");
            } catch (IOException e) {
                System.out.println("An error occurred while loading the file.");
            } finally {
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // exit command method
    private void exit() {
        this.setRunningMainMenu(false);
        System.out.println("Thank you for playing Rogue!");
    }

    private void promptToTitle(Scanner scanner){
        System.out.println();
        System.out.println("(Press enter key to return to main menu)");
        scanner.nextLine();
        this.runGameLoop(scanner);
    }

    // Create a new player in Main Menu.
    private Player createNewPlayer(Scanner scanner){
        System.out.println("What is your character's name?");
        String name = scanner.nextLine();
        Player player = new Player(name);
        System.out.printf("Player '%s' created.%n",player.getName());
        return player;
    }
}