package eco;

/**
 * This class represents the warriors in the world, and manages their needs and
 * outputs
 * 
 * @author connor, will, nate, phil
 * 
 */

public class Warrior {

	private int wPop = 5;
	private int oldWPop = wPop;
	private float floatWPop = wPop;
	private int wHunger;

	private int normalHunger = 10;
	private int totalHunger;

	public int wPop() {
		float rate = (1 + Main.wBirthRate - Main.wDeathRate);
		floatWPop = (floatWPop * rate);
		oldWPop = wPop;
		wPop = (int) (floatWPop);
		return wPop;
	}

	public int newPop() {
		return wPop - oldWPop;
	}

	public void addPop(float newPop) {
		floatWPop += newPop;
		wPop = (int) floatWPop;
	}

	public int wHunger() {
		wHunger = 0;
		wHunger = normalHunger + (Util.randInt(0, 3));
		return wHunger;
	}

	public int getwPop() {
		return wPop;
	}

	public void setwPop(int wPop) {
		this.wPop = wPop;
	}

	public int getOldWPop() {
		return oldWPop;
	}

	public void setOldWPop(int oldWPop) {
		this.oldWPop = oldWPop;
	}

	public float getFloatWPop() {
		return floatWPop;
	}

	public void setFloatWPop(float floatWPop) {
		this.floatWPop = floatWPop;
	}

	public int getwHunger() {
		return wHunger;
	}

	public void setwHunger(int wHunger) {
		this.wHunger = wHunger;
	}

	public int getNormalHunger() {
		return normalHunger;
	}

	public void setNormalHunger(int normalHunger) {
		this.normalHunger = normalHunger;
	}

	public int getTotalHunger() {
		return totalHunger;
	}

	public void setTotalHunger(int totalHunger) {
		this.totalHunger = totalHunger;
	}

	public void addPop(int tooAdd) {
		wPop += tooAdd;
		floatWPop += tooAdd;
	}

}
