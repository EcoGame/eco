package eco.game;

/**
 * This class represents the warriors in the world, and manages their needs and
 * outputs
 *
 * @author connor, will, nate, phil
 */

public class Warrior extends SocialClass {


    public Warrior(float birthrate, float deathrate, float ratio) {
        super(birthrate, deathrate, ratio);
        setHunger(10);
    }

    @Override
    public void tick() {

    }

    @Override
    public int onAddPop(int added) {
        return 0;
    }

    @Override
    public void onRemovePop(int removed) {

    }

    public int getMilitaryMight() {
        return getPop();
    }

}
