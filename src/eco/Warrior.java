package eco;

/**
 * This class represents the warriors in the world, and manages their needs and
 * outputs
 * 
 * @author connor, will, nate, phil
 * 
 */

public class Warrior {

	private static int wPop = 5;
	private static int oldWPop = wPop;
	private static float floatWPop = wPop;
	private static int wHunger;

	private static int normalHunger = 10;
	private static int totalHunger;

	public static int wPop() {
		float rate = (1 + Main.wBirthRate - Main.wDeathRate);
		floatWPop = (floatWPop * rate);
		oldWPop = wPop;
		wPop = (int) (floatWPop);
		return wPop;
	}

	public static int newPop() {
		return wPop - oldWPop;
	}

	public static void addPop(float newPop) {
		floatWPop += newPop;
		wPop = (int) floatWPop;
	}

	public static int wHunger() {
		wHunger = 0;
		wHunger = normalHunger + (Util.randInt(0, 3));
		return wHunger;
	}

	public static int getwPop() {
		return wPop;
	}

	public static void setwPop(int wPop) {
		Warrior.wPop = wPop;
	}

	public static int getOldWPop() {
		return oldWPop;
	}

	public static void setOldWPop(int oldWPop) {
		Warrior.oldWPop = oldWPop;
	}

	public static float getFloatWPop() {
		return floatWPop;
	}

	public static void setFloatWPop(float floatWPop) {
		Warrior.floatWPop = floatWPop;
	}

	public static int getwHunger() {
		return wHunger;
	}

	public static void setwHunger(int wHunger) {
		Warrior.wHunger = wHunger;
	}

	public static int getNormalHunger() {
		return normalHunger;
	}

	public static void setNormalHunger(int normalHunger) {
		Warrior.normalHunger = normalHunger;
	}

	public static int getTotalHunger() {
		return totalHunger;
	}

	public static void setTotalHunger(int totalHunger) {
		Warrior.totalHunger = totalHunger;
	}

	public static void addPop(int tooAdd) {
		wPop += tooAdd;
		floatWPop += tooAdd;
	}

}
