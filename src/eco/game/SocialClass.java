package eco.game;

/**
 * Abstract base class that represents a social class
 *
 * @author phil
 */
public abstract class SocialClass implements Comparable<SocialClass> {

    private float population;
    private float birthRate;
    private float deathRate;

    private int hunger;
    private float starvationRatio = 1.0f;

    private float desiredRatio;

    private int bias = 0;

    public SocialClass(float birthRate, float deathRate, float ratio) {
        this.birthRate = birthRate;
        this.deathRate = deathRate;
        this.desiredRatio = ratio;
        population = 5;
    }

    public float updatePop() {
        float rate = 1f + birthRate - deathRate;
        population *= rate;
        population = Math.max(population, 0);
        return population;
    }

    public int updateHunger(int wheat) {
        int needed = Math.round(population * hunger);
        if (needed >= wheat) {
            if (needed != wheat) {
                int diff = (needed - wheat) / hunger;
                int death = Math.round(diff * starvationRatio);
                population -= death;
            }
            return 0;
        }
        return wheat - needed;
    }

    public int getTotalHunger() {
        return Math.round(population * hunger);
    }

    public abstract void tick();

    public float rebalance(float total) {
        population = total * desiredRatio;
        return total - population;
    }

    public void rebalanceLast(float total) {
        population = total;
    }

    public abstract int onAddPop(int added);

    public abstract void onRemovePop(int removed);

    public int getPop() {
        return (int) population;
    }

    public float getPopFloat() {
        return population;
    }

    public void setBias(int bias) {
        this.bias = bias;
    }

    public int getBias() {
        return bias;
    }

    @Override
    public int compareTo(SocialClass o) {
        if (o == null) {
            return 0;
        }
        if (o.bias < bias) {
            return -1;
        } else if (o.bias == bias) {
            return 0;
        }
        return 1;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public int getHunger() {
        return hunger;
    }

    public void setPop(int pop) {
        population = pop;
    }

    public void setStarvationRatio(float f) {
        starvationRatio = f;
    }

}
