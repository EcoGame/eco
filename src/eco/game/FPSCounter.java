package eco.game;

/**
 * A class that keeps track of how fast the program is running. Call
 * <i>tick()</i> once every frame to update correctly.
 *
 * @author phil
 */

public class FPSCounter {

    private static int totalFrames = 0;
    private static int FPS = 0;
    private static long delta = 0;

    public static void tick() {
        totalFrames++;
        if (System.currentTimeMillis() - delta >= 1000) {
            FPS = totalFrames;
            totalFrames = 0;
            delta = System.currentTimeMillis();
        }
    }

    public static int getFPS() {
        return FPS;
    }

}
