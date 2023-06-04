package simulator;
import simulator.core.IterationHandler;
import simulator.core.essence.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        IterationHandler simulator = new IterationHandler(2, 2, 2, (float)1, (float)1);

        simulator.addCountry("Poland", 5, 10);
        simulator.startSimulation("Poland");

        for(int i = 0; i < 10; i++) {
            HashMap<String, Stats> allStats = simulator.getAllStats();
            for(HashMap.Entry<String, Stats> stats : allStats.entrySet()) {
                System.out.println(stats.getKey());
                HashMap<String, Integer> HMstats = stats.getValue().getHashMap();
                for(HashMap.Entry<String, Integer> stat : HMstats.entrySet()) {
                    System.out.println("\t"+stat.getKey()+" "+stat.getValue());
                }
            }
            System.out.println("***********************************************************************");
            simulator.nextIteration();
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