import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is for building several scenarios, containing two methods of building manually or randomly.
 * @<code>buildByFile</code> is to build scenarios with given csv
 * @<code>buildRandomly</code> is to build random scenarios
 * These two methods has been merged as <code>build</code> method
 * @author Zisen Wang
 */
public class ScenarioBuilder {

    // An identifier to check if "you human" has been produced
    boolean repeated = false;
    private ArrayList<Scenario> scenarioList;

    public ScenarioBuilder(){
        scenarioList = new ArrayList<Scenario>();
    }

    public ArrayList<Scenario> getScenarioList() {
        return scenarioList;
    }

    public void addScenario(Scenario scenario) {
        this.scenarioList.add(scenario);
    }

    /**
     * The entry for randomly building a scenario
     * @return <code>Scenario</code>
     */
    public void build(int i){
        while (i>0) {
            buildRandomly();
            i--;
        }
    }

    /**
     * The entry for using given csv to build a scenario
     * @param fileDir the direction of csv file
     */
    public void build(String fileDir){
        buildByFile(fileDir);
    }

    /**
     *
     *
     */
    private void buildByFile(String fileDir){
        File file = new File(fileDir);
        Scanner scanner;

        // A line counter to record warning
        int line = 2;

        // Load the file
        try {
            scanner = new Scanner(file);

            // Skip the first line in the csv file
            scanner.nextLine();

            // Process the file
            csvProcessor(scanner,line);

        } catch (FileNotFoundException e) {
            System.out.println("ERROR: could not find config file.");
        }



    }

    /**
     * The main method to generate a random scenario
     * @return <code>Scenario</code>
     */
    private void buildRandomly(){
        Random r = new Random();
        Scenario scenario = new Scenario();

        // Randomly determine the light
        scenario.setLegalCrossing(r.nextBoolean());

        // Pick random numbers for passengers and pedestrians
        int passengerNumber = r.nextInt(60) + 1;
        int pedestrianNumber = r.nextInt(60) + 1;

        // Generate passenger set
        while (passengerNumber > 0) {
            // Create a random character
            Character passenger = makeRandomCharacter();
            scenario.addPassenger(passenger);
            passengerNumber --;
        }
        // Generate pedestrian set
        while (pedestrianNumber > 0) {
            // Create a random character
            Character pedestrian = makeRandomCharacter();
            scenario.addPedestrian(pedestrian);
            pedestrianNumber --;
        }
        scenarioList.add(scenario);

    }

    private Character makeRandomCharacter(){
        Random r = new Random();

        // A set of animal species
        String[] animals = new String[]{"dog","cat","bird","turtle","MonkeyKing"};

        // Randomly generate gender and body type
        Gender gender = Gender.values()[r.nextInt(Gender.values().length)];
        BodyType bodyType = BodyType.values()[r.nextInt(BodyType.values().length)];

        // Randomly decide to produce a human or animal. 80% to generate a human.
        if (r.nextInt(100) < 80) {
            Human passenger = new Human();
            passenger.setBodyType(bodyType);
            passenger.setGender(gender);
            passenger.setAge(r.nextInt(100));    // Human age is 0-99
            passenger.setProfession(Profession.values()[r.nextInt(Profession.values().length)]);
            if (r.nextBoolean()) {
                passenger.setYou(true, repeated);
                repeated = true;
            }
            passenger.setPregnant(r.nextBoolean());
            return passenger;
        } else {
            Animal passenger = new Animal();
            passenger.setSpecies(animals[r.nextInt(animals.length)]);
            passenger.setAge(r.nextInt(20));    // Animal age is 0-20
            passenger.setBodyType(bodyType);
            passenger.setGender(gender);
            passenger.setPet(true);
            return passenger;
        }
    }

    private void csvProcessor(Scanner scanner, int line){
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] blocks = s.split("[,:]");

            // Create new scenario
            if (blocks[0].compareTo("scenario") == 0) {
                Scenario scenario = new Scenario();
                addScenario(scenario);
                scenario.setLegalCrossing(blocks[1].compareTo("green") == 0);
                line++;
            }else {
                try {
                    // Set attributes for scenario
                    lineProcessor(scenarioList.get(scenarioList.size()-1), s, line);
                    line++;
                } catch (InvalidCharacteristicException e) {
                    System.out.println("WARNING: invalid characteristic in config file in line " + line + "\n");
                } catch (InvalidDataFormatException e) {
                    System.out.println("WARNING: invalid data format in config file in line " + line + "\n");
                }
            }
        }
    }

    private void lineProcessor(Scenario scenario, String s, int line) throws InvalidDataFormatException,
            InvalidCharacteristicException{
        String[] blocks = s.split("[,:]");

        // Scenario setting
        if (blocks[0].compareTo("scenario") == 0){
            scenario.setLegalCrossing(blocks[1].compareTo("green") == 0);
        }else{

            // Handle exceptions
            if (blocks.length != 10){
                throw new InvalidDataFormatException();
            }

            // For human
            if (blocks[0].compareTo("human") == 0){
                Human human = new Human();
                for (int i = 1; i<10 ; i++){
                    try {
                        sharedAttributeSetter(blocks[i],human,i,scenario);
                        humanAttributeSetter(blocks[i],human,i);
                    } catch (NumberFormatException e) {
                        System.out.println("WARNING: invalid number format in config file in line " + line + "\n");
                    } catch (InvalidCharacteristicException e){
                        System.out.println("WARNING: invalid characteristic in config file in line " + line + "\n");
                    }

                }

            // For animal
            }else if (blocks[0].compareTo("animal") == 0){
                Animal animal = new Animal();
                for (int i = 1; i<10 ; i++){
                    try {
                        sharedAttributeSetter(blocks[i],animal,i,scenario);
                        animalAttributeSetter(blocks[i],animal,i);
                    } catch (NumberFormatException e) {
                        System.out.println("WARNING: invalid number format in config file in line " + line + "\n");
                    } catch (InvalidCharacteristicException e){
                        System.out.println("WARNING: invalid characteristic in config file in line " + line + "\n");
                    }

                }
            }else{
                // A third non-exist class appears
                throw new InvalidCharacteristicException();
            }

        }

    }

    private void sharedAttributeSetter(String s, Character character, int i, Scenario scenario)
            throws InvalidCharacteristicException, NumberFormatException {
        if (i == 1){
            setGender(character,s);
        }
        if (i == 2){
            setAge(character,s);
        }
        if (i == 3){
            setBodyType(character,s);
        }
        if (i == 9){
            setRole(character,s,scenario);
        }
    }

    private void humanAttributeSetter(String s, Human human, int i) throws InvalidCharacteristicException {
        if (i == 4){
            setHumanProfession(human,s);
        }
        if (i == 5){
            setHumanPregnant(human,s);
        }
        if (i == 6){
            setYou(human,s);
        }
    }

    private void animalAttributeSetter(String s, Animal animal, int i) throws InvalidCharacteristicException {
        if (i == 7){
            setSpecies(animal,s);
        }
        if (i == 8){
            setPet(animal,s);
        }
    }
    private void setGender(Character character, String s) throws InvalidCharacteristicException {
        try {
            character.setGender(Gender.valueOf(s.toUpperCase()));
        }catch (Exception e){
            throw new InvalidCharacteristicException();
        }
    }

    private void setAge(Character character, String s) throws NumberFormatException {
        int age;
        try {
            age = Integer.parseInt(s);
        }catch (Exception e){
            throw new NumberFormatException();
        }
        character.setAge(age);
    }

    private void setBodyType(Character character, String s) throws InvalidCharacteristicException {
        try {
            character.setBodyType(BodyType.valueOf(s.toUpperCase()));
        }catch (Exception e){
            throw new InvalidCharacteristicException();
        }
    }

    private void setHumanProfession(Human human, String s) throws InvalidCharacteristicException {
        try {
            human.setProfession(Profession.valueOf(s.toUpperCase()));
        }catch (Exception e){
            throw new InvalidCharacteristicException();
        }
    }

    private void setHumanPregnant(Human human, String s) throws InvalidCharacteristicException {
        if (s.compareTo("true") == 0){
            human.setPregnant(true);
        }else if (s.compareTo("false") == 0){
            human.setPregnant(false);
        }else{
            throw new InvalidCharacteristicException();
        }
    }

    private void setYou(Human human, String s) throws InvalidCharacteristicException {
        if (s.compareTo("true") == 0){
            human.setYou(true, repeated);
        }else if (s.compareTo("false") == 0){
            human.setYou(false, repeated);
        }else{
            throw new InvalidCharacteristicException();
        }
        repeated = true;
    }

    private void setSpecies(Animal animal, String s) {
        animal.setSpecies(s);
    }

    private void setPet(Animal animal, String s) throws InvalidCharacteristicException{
        if (s.compareTo("true") == 0){
            animal.setPet(true);
        }else if (s.compareTo("false") == 0){
            animal.setPet(false);
        }else{
            throw new InvalidCharacteristicException();
        }
    }

    private void setRole(Character character, String s, Scenario scenario)
    throws InvalidCharacteristicException{
        if (s.compareTo("passenger") == 0){
            scenario.addPassenger(character);
        }else if (s.compareTo("pedestrian") == 0){
            scenario.addPedestrian(character);
        }else{
            throw new InvalidCharacteristicException();
        }
    }
}
