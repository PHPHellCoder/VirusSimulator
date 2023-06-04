package simulator.core.essence.creature;
import simulator.core.essence.creature.virus.Virus;

public class Animal extends Creature{
    // Fields
    public static int count = 0;
    private boolean isVaccinated = false;

    // Methods
    public void Animal() {
        count++;
    }

    public float getInfectiousness(){
        if (this.isInfected()) {
            return this.virus.getInfectiousness();
        } else {
            return 0;
        }
    }

    public void setInfectiousness(float infectiousness) {
        if (this.isInfected()) {
            this.virus.setInfectiousness(infectiousness);
        } else {
            this.virus = new Virus();
            this.virus.setInfectiousness(infectiousness);
        }
    }

    public void vaccinate() {
        this.isVaccinated = true;
    }

    public boolean isVaccinated() {
        if (this.isVaccinated) {
            return true;
        } else {
            return false;
        }
    }
}
