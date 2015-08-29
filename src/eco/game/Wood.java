package eco.game;

/**
 * This class represents a countries wood stockpile,
 * and handles adding, removing, buying, and rotting the wood
 *
 * @author phil
 */

public class Wood extends Resource{

    private static final int START_WOOD = 1000;
    private static final float START_ROT = 0.95f;

    public Wood() {
        super();
        add(START_WOOD);
        setRot(START_ROT);
    }


    @Override
    public void updateTick() {}
}
