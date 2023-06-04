package simulator.core.essence.creature.virus;

public class Virus {
    // Fields
    private float infectiousness;
    private int lifetime = 0;

    // Methods
    public void setInfectiousness(float infectiousness) {
        this.infectiousness = infectiousness;
    }
    
    public float getInfectiousness() {
        return this.infectiousness;
    }

    public void setLifetime(int lifetime) {
        this.lifetime = lifetime;
    }

    public int getLifetime() {
        return this.lifetime;
    }
}
