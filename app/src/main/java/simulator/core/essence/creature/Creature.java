package simulator.core.essence.creature;
import simulator.core.essence.creature.virus.*;

public abstract class Creature {
    // Fields
    private int id;
    private Virus virus = null;

    // Methods
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public boolean isInfected(){
        if (this.virus != null)
            return true;
        else
            return false;
    }
    public abstract float getInfectiousness();
    public abstract void setInfectiousness();
    public int getVirusLifetime() {
        return 0;
    }

}
