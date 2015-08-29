package eco.game;

/**
 * This class represents wheat and associated methods, including the wheat
 * stockpile
 *
 * @author phil, nate, will, connor
 */

public class Wheat extends Resource{

    private static final float START_ROT = 0.95f;

    public Wheat(){
        super();
        setRot(START_ROT);
    }

    @Override
    public void updateTick() {}
}
