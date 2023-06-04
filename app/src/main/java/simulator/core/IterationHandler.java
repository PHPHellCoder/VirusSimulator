package simulator.core;
import simulator.core.essence.*;
import simulator.core.essence.creature.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IterationHandler {
    // Fields
    private ArrayList<Country> countries = new ArrayList<>();
    private int currentIteration;

    // Methods
    public void addCountry(String name, int humanCount, int animalCount) {
        Country country = new Country(name);
        for(int i = 0; i < humanCount; i++) {
            country.addHuman();
        }
        for(int i = 0; i < animalCount; i++) {
            country.addAnimal();
        }
        countries.add(country);
    }

    public int getCurrentIteration() {
        return this.currentIteration;
    }

    public void startSimulation(String countryName) {
        for(Country country : this.countries) {
            if (country.getCountryName() == countryName) {
                Stats stats = country.getStats();
                ArrayList<Animal> animals = country.getAnimalPopulation();
                Animal animal = animals.get(0);
                animal.setInfectiousness(1);
                animals.set(0, animal);
                country.setAnimalPopulation(animals);
                stats.infectedAnimals++;
                return;
            }
        }
    }

    public void nextIteration() {
        this.currentIteration++;
    }

    public HashMap<String, Stats> getAllStats() {
        HashMap<String, Stats> allStats = new HashMap<String, Stats>();
        for(Country country : this.countries) {
            allStats.put(country.getCountryName(), country.getStats());
        }
        return allStats;
    }
}
