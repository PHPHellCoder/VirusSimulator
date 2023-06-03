package simulator.core.essence.creature;

public class Human extends Creature{
    // Fields
    public static int count = 0;
    public boolean hasMask;

    // Methods
    public void Human() {
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
        }
    }

    public void wearMask() {
        this.hasMask = true;
    }

    public void takeOffMask() {
        this.hasMask = false;
    }

    public boolean hasMask() {
        if (this.hasMask) {
            return true;
        } else {
            return false;
        }
    }
}
