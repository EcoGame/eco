package eco;

/**
 * This class handles visual display of debug information
 * 
 * @author phil, connor, nate, will
 * 
 */

public class OutputManager {

	public static void newDebug() {

		Render.initOrtho();
		Render.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
		Render.drawString("Year: " + String.valueOf(Main.year), 10, 30);
		if (Render.multithreading) {
			Render.drawString("Multithreading On",
					Main.width
							- (Render.font.getWidth("Multithreading On") + 5),
					30);
			Render.drawString(
					"Using Bufferobjects",
					Main.width
							- (Render.font.getWidth("Using Bufferobjects") + 5),
					10);
		} else {
			Render.drawString("Using DisplayLists",
					Main.width
							- (Render.font.getWidth("Using DisplayLists") + 5),
					10);
		}
		if (Main.fullDebug) {
			Render.drawString(
					"Population: " + String.valueOf(Util.getTotalPop()), 10, 70);
			Render.drawString(
					"Farmers: " + String.valueOf(Main.farmer.getfPop()), 10, 90);
			Render.drawString(
					"Warriors: " + String.valueOf(Main.warrior.getwPop()), 10,
					110);
			Render.drawString(
					"Total Displaced: " + String.valueOf(World.displacedPeople),
					10, 130);
			Render.drawString(
					"Displaced Farmers: "
							+ String.valueOf(World.displacedFarmers), 40, 150);
			Render.drawString(
					"Displaced Warriors: "
							+ String.valueOf(World.displacedWarriors), 40, 170);
			Render.drawString(
					"Wheat Stored: " + String.valueOf(Main.wheat.gettWheat())
							+ " (" + Util.getWheatRateForDisplay() + ")", 10,
					190);
			Render.drawString(
					"Farmer Wheat Quota: "
							+ String.valueOf(Main.farmer.getTotalHunger()), 10,
					210);
			Render.drawString(
					"Warrior Wheat Quota: "
							+ String.valueOf(Main.warrior.getTotalHunger()),
					10, 230);

		}
	}

}
