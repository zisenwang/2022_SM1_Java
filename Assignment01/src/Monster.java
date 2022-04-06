import java.util.Scanner;

public class Monster extends Unit{
    private static final int DEFAULT_MONSTER_X_COORDINATE = 4;
    private static final int DEFAULT_MONSTER_Y_COORDINATE = 2;

    // Construct a monster at default position.
    public Monster(String name, int maxHealth, int damage){
        this.setName(name);
        this.setDamage(damage);
        this.setMaxHealth(maxHealth);
        this.heal();
        this.setSymbol();
        this.setXCoordinate(DEFAULT_MONSTER_X_COORDINATE);
        this.setYCoordinate(DEFAULT_MONSTER_Y_COORDINATE);
    }

    // A special method for monster to set symbol on map.
    public void setSymbol(){
        this.setSymbol(Character.toString(this.getName().toLowerCase().charAt(0)));
    }

    // Creat a new monster with prompt.
    public static Monster createMonster(Scanner scanner){
        System.out.print("Monster name: ");
        String name = scanner.nextLine();
        System.out.print("Monster health: ");
        String maxHP = scanner.nextLine();
        System.out.print("Monster damage: ");
        String damage = scanner.nextLine();
        Monster monster = new Monster(name,Integer.parseInt(maxHP),Integer.parseInt(damage));
        System.out.printf("Monster '%s' created.%n",monster.getName());
        return monster;
    }

}
