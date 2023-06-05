package simulator.core.essence;
import java.util.HashMap;

public class Stats {
    // Fields
    public int infectedHumans = 0;
    public int infectedAnimals = 0;
    public int startHumanPopulation = 0;
    public int startAnimalPopulation = 0;
    public int humanDeaths = 0;
    public int animalDeaths = 0;

    // Methods
    public HashMap<String, Integer> getHashMap() {
        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        
        hm.put("Start human Population", this.startHumanPopulation);
        hm.put("Start animal Population", this.startAnimalPopulation);
        hm.put("Infected humans", this.infectedHumans);
        hm.put("Infected animals", this.infectedAnimals);
        hm.put("Human deaths", this.humanDeaths);
        hm.put("Animal deaths", this.animalDeaths);

        return hm;
    }
}
