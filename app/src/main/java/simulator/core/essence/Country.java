package simulator.core.essence;
import simulator.core.essence.creature.*;
import java.util.ArrayList;

public class Country {
    // Fields
    private String countryName;
    private ArrayList<Human> humanPopulation = new ArrayList<>();
    private ArrayList<Animal> animalPopulation = new ArrayList<>();
    private Stats stats = new Stats();

    // Methods
    public Country(String name) {
        this.countryName = name;
    }

    public String getCountryName() {
        return this.countryName;
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

    public void setHumanPopulation(ArrayList<Human> humanPopulation) {
        this.humanPopulation = humanPopulation;
    }

    public ArrayList<Animal> getAnimalPopulation() {
        return animalPopulation;
    }

    public void setAnimalPopulation(ArrayList<Animal> animalPopulation) {
        this.animalPopulation = animalPopulation;
    }

    public Stats getStats() {
        return this.stats;
    }

    public void updateStats(Stats stats) {
        this.stats = stats;
    }
}
