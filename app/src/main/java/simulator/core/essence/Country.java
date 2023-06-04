package simulator.core.essence;
import simulator.core.essence.creature.*;
import java.util.ArrayList;

public class Country {
    // Fields
    private String countryName;
    private ArrayList<Human> humanPopulation = new ArrayList<>();
    private ArrayList<Animal> animalPopulation = new ArrayList<>();
    private Stats stats;

    // Methods
    public Country(String name) {
        this.countryName = name;
    }

    public void addHuman() {
        this.stats.startHumanPopulation++;
        humanPopulation.add(new Human());
    }

    public void addAnimal() {
        this.stats.startAnimalPopulation++;
        animalPopulation.add(new Animal());
    }

    public ArrayList<Human> getHumanPopulation() {
        return humanPopulation;
    }

    public ArrayList<Animal> getAnimalPopulation() {
        return animalPopulation;
    }

    public Stats getStats() {
        return this.stats;
    }

    public void updateStats(Stats stats) {
        this.stats = stats;
    }
}
