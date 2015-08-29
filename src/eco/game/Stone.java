package eco.game;

/**
 * This class represents a countries wood stockpile,
 * and handles adding, removing, buying, and rotting the stone
 *
 * @author phil
 */

public class Stone extends Resource{

    private static int price = 1;
    private static int globalAmmount = 0;
    private static float globalRotRate = 1.0f;

    private static final int START_STONE = 1000;

    public Stone() {
        super();
        add(START_STONE);
    }

    @Override
    public void updateTick() {}

}
