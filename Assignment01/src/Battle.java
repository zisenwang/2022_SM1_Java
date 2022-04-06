public class Battle {
    private Player player;
    private Monster monster;
    private boolean runningBattle;
    private boolean battleResult;

    public boolean getBattleResult(){
        return battleResult;
    }

    public Battle(Player player, Monster monster){
        this.player = player;
        this.monster = monster;
        this.runningBattle = true;
        System.out.println(player.getName() + " encountered a " + monster.getName()+ "!");
        while (runningBattle){
            this.battleLoop();
        }
    }
    private void battleLoop(){
        System.out.println();
        System.out.println(player.showShortInfo() + " | " + monster.showShortInfo());
        // Player attack monster.
        player.attack(monster);
        // Check if player defeats the monster.
        this.detectWinner();
        if (runningBattle){
            // Monster attack player.
            monster.attack(player);
            // Check if monster defeats the player.
            this.detectWinner();
        }
    }
    private void detectWinner(){
        if (player.getCurrentHealth() <= 0){
            System.out.println(monster.getName() + " wins!");
            runningBattle = false;
            battleResult = false;
        }
        if (monster.getCurrentHealth() <= 0){
            System.out.println(player.getName() + " wins!");
            runningBattle = false;
            battleResult = true;
        }
    }
}
