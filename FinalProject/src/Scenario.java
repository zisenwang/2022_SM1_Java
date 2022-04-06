import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a scenario to decide on
 * COMP90041, Sem2, 2021: Final Project
 * @author:
 * student id:
 * student email:
 */
public class Scenario {
    private boolean legalCrossing;
    private ArrayList<Character> passengers;
    private ArrayList<Character> pedestrians;

    public Scenario(){
        legalCrossing = false;
        passengers = new ArrayList<Character>();
        pedestrians = new ArrayList<Character>();
    }

    public boolean isLegalCrossing() {
        return legalCrossing;
    }

    public void setLegalCrossing(boolean legalCrossing) {
        this.legalCrossing = legalCrossing;
    }

    public ArrayList<Character> getPassengers(){
        return passengers;
    }

    public ArrayList<Character> getPedestrians() {
        return pedestrians;
    }

    public void addPassenger(Character character){
        passengers.add(character);
    }

    public void addPedestrian(Character character){
        pedestrians.add(character);
    }

    @Override
    public String toString() {
        String res = "======================================\n" +
                "# Scenario \n" +
                "======================================\n" +
                "Legal Crossing: ";
        if (isLegalCrossing()){
            res += "yes\n";
        }else{
            res += "no\n";
        }
        res += String.format("Passengers (%d)\n",passengers.size());
        for (Character c:passengers){
            res += "- " + c.toString() + "\n";

        }
        res += String.format("Pedestrians (%d)\n",pedestrians.size());
        for (Character c:pedestrians){
            res += "- " + c.toString() + "\n";

        }
        return res;
    }



    //TODO: may move this method for structure
    public void choose(Scanner scanner){
        System.out.println("Who should be saved? (passenger(s) [1] or pedestrian(s) [2])");
        String s = scanner.nextLine();
    }

    //TODO: a test code, may delete later
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        Human human = new Human();
        Animal animal = new Animal();
        Scenario scenario = new Scenario();
        animal.setSpecies("dog");
        human.setAge(20);
        human.setProfession(Profession.CRIMINAL);
        scenario.addPassenger(human);
        scenario.addPassenger(animal);
        System.out.println(scenario);
        //scenario.choose(s);
        System.out.println(BodyType.valueOf("Unspecified"));
    }
}

