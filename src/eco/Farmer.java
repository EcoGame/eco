package eco;

/**
 * This class represents the farmers in the world, and manages their needs and
 * outputs
 * 
 * @author connor, will, nate, phil
 * 
 */

public class Farmer {

	private static int fPop = 5;
	private static int oldFPop = fPop;
	private static int fHunger;

	private static float floatFPop = fPop;

	private static int wheatPerFarmer = 14;

	private static int totalHarvest;

	private static int totalHunger;

	private static int normalHunger = 10;

	public static int fHunger() {

		fHunger = 0;
		fHunger = normalHunger + (Util.randInt(0, 3));
		return fHunger;

	}

	public static int fPop() {

		float rate = (1 + Main.fBirthRate - Main.fDeathRate);
		floatFPop = (floatFPop * rate);
		oldFPop = fPop;
		fPop = (int) (floatFPop);

		return fPop;

	}

	public static void addPop(float newPop) {
		floatFPop += newPop;
		fPop = (int) floatFPop;
	}

	public static int newPop() {
		return fPop - oldFPop;

	}

	public static void harvest() {
		totalHarvest = wheatPerFarmer * fPop;
	}

	public static int getfPop() {
		return fPop;
	}

	public static void setfPop(int fPop) {
		Farmer.fPop = fPop;
	}

	public static int getOldFPop() {
		return oldFPop;
	}

	public static void setOldFPop(int oldFPop) {
		Farmer.oldFPop = oldFPop;
	}

	public static int getfHunger() {
		return fHunger;
	}

	public static void setfHunger(int fHunger) {
		Farmer.fHunger = fHunger;
	}

	public static float getFloatFPop() {
		return floatFPop;
	}

	public static void setFloatFPop(float floatFPop) {
		Farmer.floatFPop = floatFPop;
	}

	public static int getWheatProductionRate() {
		return wheatPerFarmer;
	}

	public static void setWheatPerFarmer(int wheatPerFarmer) {
		Farmer.wheatPerFarmer = wheatPerFarmer;
	}

	public static int getTotalHarvest() {
		return totalHarvest;
	}

	public static void setTotalHarvest(int totalHarvest) {
		Farmer.totalHarvest = totalHarvest;
	}

	public static int getTotalHunger() {
		return totalHunger;
	}

	public static void setTotalHunger(int totalHunger) {
		Farmer.totalHunger = totalHunger;
	}

	public static int getNormalHunger() {
		return normalHunger;
	}

	public static void setNormalHunger(int normalHunger) {
		Farmer.normalHunger = normalHunger;
	}

	public static void addPop(int tooAdd) {
		fPop += tooAdd;
		floatFPop += tooAdd;
	}

}
