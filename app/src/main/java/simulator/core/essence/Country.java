package simulator.core.essence;
import simulator.core.essence.creature.*;
import java.util.ArrayList;

public class Country {
    // Fields
    private String countryName;
    private ArrayList<Human> humans = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
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
        humans.add(new Human());
    }

    public void addAnimal() {
        this.stats.startAnimalPopulation++;
        animals.add(new Animal());
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    public Stats getStats() {
        return this.stats;
    }

    public void updateStats(Stats stats) {
        this.stats = stats;
    }
}
