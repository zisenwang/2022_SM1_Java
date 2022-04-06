import java.io.*;
import java.lang.Math;
import java.util.*;

/**
 * COMP90041, Sem2, 2021: Final Project
 * @author:
 * student id: 
 * student email: 
 */
public class EthicalEngine {

    public enum Decision {PASSENGERS, PEDESTRIANS};
    private static final HashSet<String> LOG_COMMAND = new HashSet<String>();
    private static final HashSet<String> HELP_COMMAND = new HashSet<String>();
    private static final HashSet<String> CONFIG_COMMAND = new HashSet<String>();

    /**
     * Initialize strings of commands
     */
    static {
        LOG_COMMAND.add("-l");
        LOG_COMMAND.add("--log");
        HELP_COMMAND.add("-h");
        HELP_COMMAND.add("--help");
        CONFIG_COMMAND.add("-c");
        CONFIG_COMMAND.add("--config");
    }

    /**
     * Decides whether to save the passengers or the pedestrians
     * @param scenario: the ethical dilemma
     * @return Decision: which group to save
     */
    public static Decision decide(Scenario scenario) {
        // a very simple decision engine
        // TODO: take into account at least 5 characteristics

        // 50/50
        if(Math.random() > 0.5) {
            return Decision.PEDESTRIANS;
        } else {
            return Decision.PASSENGERS;
        }
    }

    /**
     * Program entry
     */
    public static void main(String[] args) throws Exception{
        //Scenario scenario = new ScenarioBuilder().build();
        //System.out.println(scenario);
        //new EthicalEngine().help();
        ScenarioBuilder scenarioBuilder = new ScenarioBuilder();
        scenarioBuilder.build("test.csv");
        for (Scenario s:scenarioBuilder.getScenarioList()){
            System.out.println(s);
        }
    }

    private void command(String[] args){
        if (args.length == 0){

        }else {
            boolean promptHelp = true;
            for (int i = 0; i < args.length; i++) {
                if (LOG_COMMAND.contains(args[i])) {
                    // Check if the following string is a possible path
                    if ((i + 1 == args.length) || (LOG_COMMAND.contains(args[i + 1])) || (CONFIG_COMMAND.contains(args[i + 1])) ||
                            (HELP_COMMAND.contains(args[i + 1]))) {
                        promptHelp = false;
                        help();
                    } else {
                        // Build log
                        promptHelp = false;
                    }


                } else if (CONFIG_COMMAND.contains(args[i])) {
                    // Check if the following string is a possible path
                    if ((i + 1 == args.length) || (LOG_COMMAND.contains(args[i + 1])) || (CONFIG_COMMAND.contains(args[i + 1])) ||
                            (HELP_COMMAND.contains(args[i + 1]))) {
                        promptHelp = false;
                        help();
                    } else {
                        // Build scenario
                        promptHelp = false;
                        ScenarioBuilder builder = new ScenarioBuilder();
                        //Scenario scenario = builder.build(args[i + 1]);
                    }

                }
            }
            if (promptHelp) {
                help();
            }
        }
    }
    private void help(){
        System.out.println(Decision.PASSENGERS);
        System.out.println("EthicalEngine - COMP90041 - Final Project\n" +
                "\n" +
                "Usage: java EthicalEngine [arguments]\n" +
                "\n" +
                "Arguments:\n" +
                "    -c or --config        Optional: path to config file\n" +
                "    -h or --help          Optional: print Help (this message) and exit\n" +
                "    -l or --log           Optional: path to data log file");
    }


}