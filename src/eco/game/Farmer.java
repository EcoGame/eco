package eco.game;

/**
 * This class represents the farmers in the world, and manages their needs and
 * outputs
 * 
 * @author connor, will, nate, phil
 * 
 */

public class Farmer {

	private int fPop = 5;
	private int oldFPop = fPop;
	private int fHunger;

	private float floatFPop = fPop;

	private int wheatPerFarmer = 14;

	private int totalHarvest;

	private int totalHunger;

	private int normalHunger = 10;

	public Farmer(){
		 fPop = 5;
		 oldFPop = fPop;

		floatFPop = fPop;

		 wheatPerFarmer = 14;

		 normalHunger = 10;
	}
	
	public int fHunger() {

		fHunger = 0;
		fHunger = normalHunger + (Util.randInt(0, 2));
		return fHunger;

	}

	public float fPop(float fBirthRate, float fDeathRate) {
		
		if (fPop < 0){
			fPop = 0;
		}
		if (floatFPop < 0){
			floatFPop = 0;
		}
		
		float rate = (1 + fBirthRate - fDeathRate);
		float floatfPop = (this.floatFPop * rate);
		int fpop = (int) (floatfPop);


		return fpop - oldFPop;

	}

	public void addPop(float newPop) {
		floatFPop += newPop;
		fPop = (int) floatFPop;
	}

	public int newPop() {
		return fPop - oldFPop;

	}

	public void harvest() {
		totalHarvest = wheatPerFarmer * fPop;
	}

	public int getfPop() {
		return fPop;
	}

	public void setfPop(int fPop) {
		this.fPop = fPop;
	}

	public int getOldFPop() {
		return oldFPop;
	}

	public void setOldFPop(int oldFPop) {
		this.oldFPop = oldFPop;
	}

	public int getfHunger() {
		return fHunger;
	}

	public void setfHunger(int fHunger) {
		this.fHunger = fHunger;
	}

	public float getFloatFPop() {
		return floatFPop;
	}

	public void setFloatFPop(float floatFPop) {
		this.floatFPop = floatFPop;
	}

	public int getWheatProductionRate() {
		return wheatPerFarmer;
	}

	public void setWheatPerFarmer(int wheatPerFarmer) {
		this.wheatPerFarmer = wheatPerFarmer;
	}

	public int getTotalHarvest() {
		return totalHarvest;
	}

	public void setTotalHarvest(int totalHarvest) {
		this.totalHarvest = totalHarvest;
	}

	public int getTotalHunger() {
		return totalHunger;
	}

	public void setTotalHunger(int totalHunger) {
		this.totalHunger = totalHunger;
	}

	public int getNormalHunger() {
		return normalHunger;
	}

	public void setNormalHunger(int normalHunger) {
		this.normalHunger = normalHunger;
	}

	public void addPop(int tooAdd) {
		fPop += tooAdd;
		floatFPop += tooAdd;
	}

}
