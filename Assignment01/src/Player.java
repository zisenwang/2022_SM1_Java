import java.util.Scanner;

public class Player extends Unit{

    private int level;
    private int bonusDamage;
    private static final int HEALTH_OFFSET = 17;
    private static final int HEALTH_SCALE = 3;

    // Construct a player at default position with default level.
    public Player(String name){
        this.bonusDamage = 0;
        this.setName(name);
        this.level = 1;     // Initial level of a new player
        this.setMaxHealth();
        this.setDamage();
        this.setSymbol();
        this.heal();
        // Default position
        this.setXCoordinate(1);
        this.setYCoordinate(1);
    }

    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public void levelUp(){
        this.setLevel(this.level + 1);
    }
    public int getBonusDamage(){
        return bonusDamage;
    }
    public void setBonusDamage(int bonusDamage){
        this.bonusDamage = bonusDamage;
        this.setDamage();
    }
    // A special method for player to set damage.
    public void setDamage(){
        this.setDamage(1 + this.level + this.bonusDamage); // Damage = 1 + LV
    }

    // A special method for player to set maximum health.
    public void setMaxHealth(){
        this.setMaxHealth(HEALTH_OFFSET + this.getLevel() * HEALTH_SCALE); // HP = 17 + 3 * LV
    }

    // Set player's symbol as the capital first letter of its name.
    public void setSymbol(){
        this.setSymbol(Character.toString(this.getName().toUpperCase().charAt(0)));
    }

    // Show player's detail.
    public void printDetail(){
        System.out.printf("%s (Lv. %d)%n", this.getName(), this.getLevel());
        System.out.println("Damage: " + this.getDamage());
        System.out.printf("Health: %d/%d%n", this.getCurrentHealth(), this.getMaxHealth());
    }

    // Refresh the information of a player. Clear bonus damage.
    public void refresh(){
        this.setBonusDamage(0);
        this.setMaxHealth();
        this.setDamage();
    }
}
