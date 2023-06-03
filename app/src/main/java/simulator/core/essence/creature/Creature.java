package simulator.core.essence.creature;
import simulator.core.essence.creature.virus.*;

public abstract class Creature {
    // Fields
    protected Virus virus = null;

    // Methods
    public boolean isInfected(){
        if (this.virus != null)
            return true;
        else
            return false;
    }

    public abstract float getInfectiousness();
    public abstract void setInfectiousness(float infectiousness);

    public int getVirusLifetime() {
        if (this.virus != null) {
            return this.virus.getLifetime();
        } else {
            return 0;
        }
    }

    public void updateVirusLifetime() {
        if (this.virus != null) {
            int lifetime = this.getVirusLifetime() + 1;
            this.virus.setLifetime(lifetime);
        }
    }
}
