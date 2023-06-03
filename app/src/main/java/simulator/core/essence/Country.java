package simulator.core.essence;
import simulator.core.essence.creature.*;

public class Country {
    // Fields
    private String countryName;
    private Human[] humanPopulation;
    private Animal[] animalPopulation;
    public Stats stats;

    // Methods
    public void addHuman(int count) {

    }
    public void addAnimal(int count) {

    }

    public Human[] getHumanPopulation() {
        return humanPopulation;
    }
    public Animal[] getAnimalPopulation() {
        return animalPopulation;
    }
}
