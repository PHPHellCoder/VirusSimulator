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

    public int getTimeToDeath() {
        if (this.virus != null) {
            return this.virus.getTimeToDeath();
        } else {
            return -1;
        }
    }

    public void updateTimeToDeath() {
        if (this.virus != null) {
            int lifetime = this.getTimeToDeath() - 1;
            this.virus.setTimeToDeath(lifetime);
        }
    }
}
