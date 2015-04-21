package eco;

public class Country {
	public static String[] names = new String[] {
		"Tumblr",
		"Yahoo",
		"PiedPiper",
		"Baidu",
		"Xiaomi"	
	};
	
	public String name;
	
	 //To be randomized
	public float fBirthRate = 0.03f;
	public volatile float fDeathRate = 0.02f;
	public float fDefaultDeathRate = 0.02f;
	public float wBirthRate = 0.008f;
	public volatile float wDeathRate = 0.002f;
	public float wDefaultDeathRate = 0.002f;
	public float farmerDeathRatio = 0.75f;
	public float warriorDeathRatio = 0.75f;
	public int landSize = 0; //idk, randomize
	// To be randomized ^^^^
	
	public boolean favorFarmers = true;
	public boolean displacedEat = true;
	public float desiredWarriorRatio = 0.15f;
	public float desiredFarmerRatio = 0.85f;
	
	public int tWheat;
	public int maxwheat;
	
	public int unemployedFarmers = 0;
	public int employedFarmers = 0;
	public int treasury = 0;
	
	
	public int fPop = 5;
	public int oldFPop = fPop;
	public int fHunger;
	public float floatFPop = fPop;
	public int wheatPerFarmer = 14;
	public int totalHarvest;
	public int totalHunger;
	public int normalHunger = 10;
	
	
	public int wPop = 5;
	public int oldWPop = wPop;
	public float floatWPop = wPop;
	public int wHunger;
	public int wNormalHunger = 10;
	public int wTotalHunger;
	
	
	public Country(boolean favorFarmers, boolean displacedEat, 
		float desiredWarriorRatio, float desiredFarmerRatio){
		name = names[Util.randInt(0, names.length - 1)];
		
		//randomize the variables below
		fBirthRate = 0.03f;
		fDeathRate = 0.02f;
		fDefaultDeathRate = 0.02f;
		wBirthRate = 0.008f;
		wDeathRate = 0.002f;
		wDefaultDeathRate = 0.002f;
		farmerDeathRatio = 0.75f;
		warriorDeathRatio = 0.75f;
		landSize = 0; //idk, randomize
		
		
	}
	
	public void buy(Country partner, int wheat) {
		partner.tWheat = partner.tWheat - wheat;
		partner.treasury = partner.treasury + (wheat * Economy.getPrice());
		tWheat = tWheat + wheat;
		treasury = treasury - (wheat * Economy.getPrice()); 
	
	}
	
	public void sell(Country partner, int wheat){
		partner.tWheat = partner.tWheat + wheat;
		partner.treasury = partner.treasury - (wheat * Economy.getPrice());
		tWheat = tWheat - wheat;
		treasury = treasury + (wheat * Economy.getPrice()); 
	}
	
	public int fHunger() {

		fHunger = 0;
		fHunger = normalHunger + (Util.randInt(0, 3));
		return fHunger;

	}

	public int fPop() {

		float rate = (1 + Main.fBirthRate - Main.fDeathRate);
		floatFPop = (floatFPop * rate);
		oldFPop = fPop;
		fPop = (int) (floatFPop);

		return fPop;

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
		fPop = fPop;
	}

	public int getOldFPop() {
		return oldFPop;
	}

	public void setOldFPop(int oldFPop) {
		oldFPop = oldFPop;
	}

	public int getfHunger() {
		return fHunger;
	}

	public void setfHunger(int fHunger) {
		fHunger = fHunger;
	}

	public float getFloatFPop() {
		return floatFPop;
	}

	public void setFloatFPop(float floatFPop) {
		floatFPop = floatFPop;
	}

	public int getWheatProductionRate() {
		return wheatPerFarmer;
	}

	public void setWheatPerFarmer(int wheatPerFarmer) {
		wheatPerFarmer = wheatPerFarmer;
	}

	public int getTotalHarvest() {
		return totalHarvest;
	}

	public void setTotalHarvest(int totalHarvest) {
		totalHarvest = totalHarvest;
	}

	public int getTotalHunger() {
		return totalHunger;
	}

	public void setTotalHunger(int totalHunger) {
		totalHunger = totalHunger;
	}

	public int getNormalHunger() {
		return normalHunger;
	}

	public void setNormalHunger(int normalHunger) {
		normalHunger = normalHunger;
	}

	public void addPop(int tooAdd) {
		fPop += tooAdd;
		floatFPop += tooAdd;
	}
	
	//
	//
	//Warrior
	//
	//
	
		public int wPop() {
		float rate = (1 + Main.wBirthRate - Main.wDeathRate);
		floatWPop = (floatWPop * rate);
		oldWPop = wPop;
		wPop = (int) (floatWPop);
		return wPop;
	}

	public int newWPop() {
		return wPop - oldWPop;
	}

	public void addWPop(float newPop) {
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
		wPop = wPop;
	}

	public int getOldWPop() {
		return oldWPop;
	}

	public void setOldWPop(int oldWPop) {
		oldWPop = oldWPop;
	}

	public float getFloatWPop() {
		return floatWPop;
	}

	public void setFloatWPop(float floatWPop) {
		floatWPop = floatWPop;
	}

	public int getwHunger() {
		return wHunger;
	}

	public void setwHunger(int wHunger) {
		wHunger = wHunger;
	}

	public int getWNormalHunger() {
		return wNormalHunger;
	}

	public void setWNormalHunger(int wNormalHunger) {
		normalHunger = wNormalHunger;
	}

	public int getWTotalHunger() {
		return wTotalHunger;
	}

	public void setWTotalHunger(int wTotalHunger) {
		totalHunger = wTotalHunger;
	}

	public void wAddPop(int tooAdd) {
		wPop += tooAdd;
		floatWPop += tooAdd;
	}
	
	public void update(){
		if (tWheat > maxwheat){
			Economy.sellWheat(tWheat - maxwheat);
			tWheat = maxwheat;	
		}
	}	

	
	public void tick(){
			fPop();
			setOldFPop(getfPop()); // Need to update this manually
													// because it's done in
													// fPop()
			wPop();
		float newPopulation = newPop() + newWPop();
		float newWarriors = newPopulation * desiredWarriorRatio;
		newPopulation -= newWarriors;
		addPop(newPopulation);
		addWPop(newWarriors);
		//Wheat.tWheat(getfPop());
		setTotalHunger(fHunger() * fPop());
		setWTotalHunger(wHunger() * getwPop());
		int warriorWheat = getTotalHunger();
		int farmerWheat = getWTotalHunger();
		if (favorFarmers) {
			farmerWheat = Wheat.eatWheat(farmerWheat);
			warriorWheat = Wheat.eatWheat(warriorWheat);
		} else {
			warriorWheat = Wheat.eatWheat(warriorWheat);
			farmerWheat = Wheat.eatWheat(farmerWheat);
		}
		if (farmerWheat != 0) {
			int fDeath = (int) Math.round(((float) farmerWheat / (float) getfHunger()) * farmerDeathRatio);
			addPop(-fDeath);
		}
		if (warriorWheat != 0) {
			int wDeath = (int) Math
					.round(((float) warriorWheat / (float) getwHunger())
							* warriorDeathRatio);
			wAddPop(-wDeath);
		}
		if (displacedEat) {
			int displacedHungerConst = getfHunger() / 2;
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			displacedHunger = Wheat.eatWheat(displacedHunger);
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) getfHunger() / 2f) * 1f);
			}
		} else {
			int displacedHungerConst = getfHunger() / 2;
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) getfHunger() / 2f) * 1f);
			}
		}
		update();		
		if (Render.multithreading) {
			ThreadManager.addJob(new MeshTask());
		} else {
			DisplayLists.mesh();
			//skipFrame = true;
		}
	
	}
	
}
