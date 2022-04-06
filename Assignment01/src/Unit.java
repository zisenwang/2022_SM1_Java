/**
 *  A class to
 */
public class Unit extends Entity{
    private int damage;
    private int currentHealth;
    private int maxHealth;

    public int getDamage() {
        return damage;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // General method for attacking a unit.
    public void attack(Unit enemy){
        enemy.setCurrentHealth(enemy.getCurrentHealth() - this.getDamage());
        System.out.printf("%s attacks %s for %d damage.\n", this.getName(), enemy.getName(),
                this.getDamage());
    }
    // General method to heal a unit to full health.
    public void heal(){
        this.setCurrentHealth(this.getMaxHealth());
    }

    // General method to show short description of a unit.
    public String showShortInfo(){
        return (this.getName() + " " + this.getCurrentHealth() + "/" + this.getMaxHealth());
    }
}
