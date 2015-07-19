package eco.game;

/**
 * This class represents the farmers in the world, and manages their needs and
 * outputs
 *
 * @author connor, will, nate, phil
 */

public class Farmer extends SocialClass {

    private int wheatPerFarmer = 14;
    public int lastHarvest;

    public Farmer(float birthrate, float deathrate, float ratio) {
        super(birthrate, deathrate, ratio);
        wheatPerFarmer = 14;
        setHunger(10);
    }

    @Override
    public void tick() {
        lastHarvest = wheatPerFarmer * getPop();
    }

    @Override
    public int onAddPop(int added) {
        return added;
    }

    @Override
    public void onRemovePop(int removed) {

    }

    public int getWheatRate() {
        return wheatPerFarmer;
    }


}
