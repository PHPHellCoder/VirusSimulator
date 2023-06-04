package simulator.core;
import simulator.core.essence.*;
import simulator.core.essence.creature.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.lang.Math;

public class IterationHandler {
    // Fields
    private ArrayList<Country> countries = new ArrayList<>();
    private int currentIteration = 0;
    private int HtoHcontacts;
    private int AtoAcontacts;
    private int HtoAcontacts;
    private float minInfectiousness;
    private float maxInfectiousness;

    // Methods
    public IterationHandler(int HtoHcontacts, int AtoAcontacts, int HtoAcontacts, float minInfectiousness, float maxInfectiousness) {
        this.HtoHcontacts = HtoHcontacts;
        this.AtoAcontacts = AtoAcontacts;
        this.HtoAcontacts = HtoAcontacts;
        this.minInfectiousness = minInfectiousness;
        this.maxInfectiousness = maxInfectiousness;
    }

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

    public int getIteration() {
        return this.currentIteration;
    }

    public void startSimulation(String countryName) {
        for(Country country : this.countries) {
            if (country.getCountryName() == countryName) {
                Stats stats = country.getStats();
                ArrayList<Animal> animals = country.getAnimals();
                Animal animal = animals.get(0);
                animal.setInfectiousness(1);
                animals.set(0, animal);
                country.setAnimals(animals);
                stats.infectedAnimals++;
                return;
            }
        }
    }

    public void nextIteration() {
        this.currentIteration++;
        for(int i = 0; i < this.countries.size(); i++) {
            Country country = this.countries.get(i);
            ArrayList<Human> humans = country.getHumans();
            ArrayList<Animal> animals = country.getAnimals();
            killCreatures(humans);
            killCreatures(animals);
            updateTimeToDeath(humans);
            updateTimeToDeath(animals);
            this.contactHtoH(humans);
            this.contactAtoA(animals);
            country.updateStats(this.updateStats(country.getStats(), humans, animals));
        }
    }

    public HashMap<String, Stats> getAllStats() {
        HashMap<String, Stats> allStats = new HashMap<String, Stats>();
        for(Country country : this.countries) {
            allStats.put(country.getCountryName(), country.getStats());
        }
        return allStats;
    }

    private void contactHtoH(ArrayList<Human> humans) {
        if(humans.size() < 2)
            return;
        for(int i = 0; i < humans.size(); i++) {
            if (humans.get(i).isInfected()) {
                Human infectedHuman = humans.get(i);
                float infectiousness = infectedHuman.getInfectiousness();
                if (infectedHuman.hasMask)
                    infectiousness /= 2;
                for(int j = 0; j < this.HtoHcontacts - 1; j++) {
                    int n = (int)(Math.random() * humans.size());
                    if (n != i) {
                        Human healthyHuman = humans.get(n);
                        float proc = (float)Math.random();
                        if (healthyHuman.hasMask) {
                            if (proc < (infectiousness / 1.5)) {
                                float newInfectiousness = (float)(Math.random()*(this.maxInfectiousness - this.minInfectiousness) + this.minInfectiousness);
                                healthyHuman.setInfectiousness(newInfectiousness);
                                humans.set(n, healthyHuman);
                            }
                        } else {
                            if (proc < (infectiousness)) {
                                float newInfectiousness = (float)(Math.random()*(this.maxInfectiousness - this.minInfectiousness) + this.minInfectiousness);
                                healthyHuman.setInfectiousness(newInfectiousness);
                                humans.set(n, healthyHuman);
                            }
                        }
                    } else {
                        j--;
                    }
                }
            }
        }
    }

    private void contactAtoA(ArrayList<Animal> animals) {
        if(animals.size() < 2)
            return;
        for(int i = 0; i < animals.size(); i++) {
            if (animals.get(i).isInfected()) {
                Animal infectedAnimal = animals.get(i);
                float infectiousness = infectedAnimal.getInfectiousness();
                for(int j = 0; j < this.AtoAcontacts - 1; j++) {
                    int n = (int)(Math.random() * animals.size());
                    if (n != i) {
                        if (!animals.get(n).isVaccinated() && !animals.get(n).isInfected()) {
                            Animal healthyAnimal = animals.get(n);
                            float proc = (float)Math.random();
                            if (proc < (infectiousness)) {
                                float newInfectiousness = (float)(Math.random()*(this.maxInfectiousness - this.minInfectiousness) + this.minInfectiousness);
                                healthyAnimal.setInfectiousness(newInfectiousness);
                                animals.set(n, healthyAnimal);
                            }
                        }
                    } else {
                        j--;
                    }
                }
            }
        }
    }

    private void contactHtoA(ArrayList<Human> humans, ArrayList<Animal> animals) {

    }

    private void updateTimeToDeath(ArrayList<? extends Creature> creatures) {
        for(Creature creature : creatures) {
            creature.updateTimeToDeath();
        }
    }

    private void killCreatures(ArrayList<? extends Creature> creatures) {
        for(int i = 0; i < creatures.size(); i++) {
            if(creatures.get(i).getTimeToDeath() == 0) {
                creatures.remove(i);
                i--;
            }
        }
    }

    private Stats updateStats(Stats stats, ArrayList<Human> humans, ArrayList<Animal> animals) {
        int countInfHumans = 0;
        int countInfAnimals = 0;
        for(Human human : humans) {
            if (human.isInfected())
                countInfHumans++;
        }
        for(Animal animal : animals) {
            if (animal.isInfected())
                countInfAnimals++;
        }

        int humanDeaths = stats.startHumanPopulation - humans.size();
        int animalDeaths  = stats.startAnimalPopulation - animals.size();

        stats.infectedHumans = countInfHumans;
        stats.infectedAnimals = countInfAnimals;
        stats.humanDeaths = humanDeaths;
        stats.animalDeaths = animalDeaths;
        return stats;
    }
}
