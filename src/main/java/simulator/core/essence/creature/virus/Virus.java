package simulator.core.essence.creature.virus;

public class Virus {
    // Fields
    private float infectiousness;
    private int timeToDeath = 5;

    // Methods
    public void setInfectiousness(float infectiousness) {
        this.infectiousness = infectiousness;
    }
    
    public float getInfectiousness() {
        return this.infectiousness;
    }

    public void setTimeToDeath(int timeToDeath) {
        this.timeToDeath = timeToDeath;
    }

    public int getTimeToDeath() {
        return this.timeToDeath;
    }
}
