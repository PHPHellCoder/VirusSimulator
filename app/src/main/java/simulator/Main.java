package simulator;
import simulator.core.IterationHandler;
import simulator.core.essence.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        IterationHandler simulator = new IterationHandler();

        simulator.addCountry("Poland", 5, 10);
        simulator.startSimulation("Poland");

        HashMap<String, Stats> allStats = simulator.getAllStats();
        for(HashMap.Entry<String, Stats> stats : allStats.entrySet()) {
            System.out.println(stats.getKey());
            HashMap<String, Integer> HMstats = stats.getValue().getHashMap();
            for(HashMap.Entry<String, Integer> stat : HMstats.entrySet()) {
                System.out.println("\t"+stat.getKey()+" "+stat.getValue());
            }
        }

        // Country poland = new Country("Poland");
        // for(int i = 0; i < 20; i++) {
        //     poland.addHuman();
        //     poland.addAnimal();
        // }
        // Stats stats = poland.getStats();
        // System.out.println(stats.startHumanPopulation);
        // System.out.println(stats.startAnimalPopulation);
    }
}