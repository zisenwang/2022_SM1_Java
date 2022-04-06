import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * contain three map: scenario characteristics counting, which includes all attributes in running scenario
 *                    saved characteristics counting
 *                    result percentage
 */

public class Statistic {
    private Map scenarioMap;
    private Map savedMap;
    private Map resultMap;

    public Statistic(){
        scenarioMap = new HashMap<String,Integer>();
        savedMap = new HashMap<String,Integer>();
        resultMap = new HashMap<String,String>();
    }

    public Map getScenarioMap() {
        return scenarioMap;
    }

    public Map getSavedMap() {
        return savedMap;
    }

    public Map getResultMap() {
        return resultMap;
    }

    public void setScenarioMap(Map scenarioMap) {
        this.scenarioMap = scenarioMap;
    }

    public void setSavedMap(Map savedMap) {
        this.savedMap = savedMap;
    }

    public void setResultMap(Map resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("""
                ======================================
                # Statistic
                ======================================
                - % SAVED AFTER <# of runs> RUNS
                """);
        for (Object key:resultMap.keySet()){
            s.append(String.format("%s: %d\n", key, (Integer)resultMap.get(key)));
        }
        s.append("--\n" + "average age: <average>");
        return s.toString();
    }

    public void parseScenario(Scenario scenario){
        parseCharacter(scenario,"passengers",scenarioMap);
        parseCharacter(scenario,"pedestrians",scenarioMap);
    }

    // TODO: finish
    public void parseSaved(){
        calculateResult();
    }

    private void addOneToValue(Map map, String key){
        if (map.containsKey(key)){
            map.put(key,(Integer)map.get(key) + 1);
        }else{
            map.put(key,1);
        }
    }

    private void calculateResult(){
        for (Object key:scenarioMap.keySet()){
            if (savedMap.containsKey(key)){
                Integer total = (Integer)scenarioMap.get(key);
                Integer saved = (Integer)savedMap.get(key);
                resultMap.put(key,String.format("%.2f",(double)saved/total));
            }else{
                resultMap.put(key,String.format("%.2f",0.00));
            }
        }
    }


    // TODO: may change to all possible map
    private void parseLight(Scenario scenario,Map map){
        if (scenario.isLegalCrossing()){
            addOneToValue(map,"green");
        }else{
            addOneToValue(map,"red");
        }
    }

    public void parseCharacter(Scenario scenario, String role, Map map){
        // Acquire role list
        ArrayList<Character> roleList = null;
        if (role.compareTo("passengers") == 0){
            roleList = scenario.getPassengers();
        }else{
            roleList = scenario.getPedestrians();
        }

        // Add characteristics to map
        for (Character character:roleList){

            // Parse Role
            addOneToValue(map,role);

            if (character instanceof Human human){
                // Parse Human characteristics
                addOneToValue(map,"human");

                // Parse AgeCategory
                addOneToValue(map,human.getAgeCategory().name().toLowerCase());

                // Parse Gender
                if (human.getGender() != Gender.UNKNOWN) {
                    addOneToValue(map,human.getGender().name().toLowerCase());
                }

                // Parse BodyType
                if (human.getBodyType() != BodyType.UNSPECIFIED) {
                    addOneToValue(map,human.getBodyType().name().toLowerCase());
                }

                // Parse Profession
                if (human.getProfession() != Profession.NONE) {
                    addOneToValue(map,human.getProfession().name().toLowerCase());
                }

                // Parse Pregnant
                if (human.isPregnant()){
                    addOneToValue(map,"pregnant");
                }

                // Parse You
                if (human.isYou()){
                    addOneToValue(map,"you");
                }
            }else{
                // Parse Animal characteristics
                addOneToValue(map,"animal");
                Animal animal = (Animal) character;

                // Parse Species
                addOneToValue(map, animal.getSpecies());

                // Parse Pets
                if (animal.isPet()){
                    addOneToValue(map,"pet");
                }
            }

            // Parse Legality
            parseLight(scenario,map);
        }
    }

}
