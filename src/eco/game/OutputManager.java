package eco.game;

/**
 * This class handles visual display of debug information
 *
 * @author phil, connor, nate, will
 */

public class OutputManager {

    public static void newDebug() {

        RenderUtil.initOrtho();
        RenderUtil.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
        RenderUtil.drawString("Year: " + String.valueOf(PlayerCountry.year), 10, 30);
        if (Main.fullDebug) {
            RenderUtil.drawString(
                    "Population: " + String.valueOf(MathUtil.getTotalPop(PlayerCountry.playerCountry)), 10, 70);
            RenderUtil.drawString(
                    "Farmers: " + String.valueOf(World.oldFarmers), 10, 90);
            RenderUtil.drawString(
                    "Warriors: " + String.valueOf(World.oldWarriors), 10,
                    110);
            RenderUtil.drawString(
                    "Total Displaced: " + String.valueOf(World.displacedPeople),
                    10, 130);
            RenderUtil.drawString(
                    "Displaced Farmers: "
                            + String.valueOf(World.displacedFarmers), 40, 150);
            RenderUtil.drawString(
                    "Displaced Warriors: "
                            + String.valueOf(World.displacedWarriors), 40, 170);
        }
    }

}
