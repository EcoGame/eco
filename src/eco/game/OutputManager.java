package eco.game;

/**
 * This class handles visual display of debug information
 *
 * @author phil, connor, nate, will
 *
 */

public class OutputManager {

    public static void newDebug() {

        RenderUtil.initOrtho();
        RenderUtil.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
        RenderUtil.drawString("Year: " + String.valueOf(PlayerCountry.year), 10, 30);
        if (Render.multithreading) {
            RenderUtil.drawString("Multithreading On",
                    Main.width
                            - (RenderUtil.font.getWidth("Multithreading On") + 5),
                    30);
            RenderUtil.drawString(
                    "Using Bufferobjects",
                    Main.width
                            - (RenderUtil.font.getWidth("Using Bufferobjects") + 5),
                    10);
        } else {
            RenderUtil.drawString("Using DisplayLists",
                    Main.width
                            - (RenderUtil.font.getWidth("Using DisplayLists") + 5),
                    10);
        }
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
           /* Render.drawString(
                    "Wheat Stored: " + String.valueOf(PlayerCountry.wheat.gettWheat())
                            + " (" + Util.getWheatRateForDisplay() + ")", 10,
                    190);
            Render.drawString(
                    "Farmer Wheat Quota: "OO
                            + String.valueOf(PlayerCountry.farmer.getTotalHunger()), 10,
                    210);
            Render.drawString(
                    "Warrior Wheat Quota: "
                            + String.valueOf(PlayerCountry.warrior.getTotalHunger()),
                    10, 230);
            Render.drawString(
                    "Land Wheat: "
                            + String.valueOf(PlayerCountry.land.getWheatRate()),
                    10, 250);*/

        }
    }

}
