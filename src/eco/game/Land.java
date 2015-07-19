package eco.game;

public class Land {

    /**
     * This class represents lands that countries win
     * through war or diplomacy
     *
     * @author phil
     */

    private int land;
    private int pop;

    private static final int popPerLand = 1000;
    private static final int wheatPerLand = 10;

    public Land() {
        land = 0;
        pop = 0;
    }

    public void addLand(int toAdd) {
        land += toAdd;
    }

    public int addPop(int newPop) {
        int max = land * popPerLand;
        int oldPop = pop;
        pop = Math.min(max, pop + newPop);
        return newPop - (newPop - oldPop);
    }

    public void updateWheat(Wheat wheat) {
        wheat.addWheat(wheatPerLand * land);
    }

    public int getLand() {
        return land;
    }

    public void setLand(int land) {
        this.land = land;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public int getWheatRate() {
        return land * wheatPerLand;
    }
}
