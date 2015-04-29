package eco;

public class Country {

	/**
	 * 
	 * This class represents and simulates another country
	 * 
	 * @author phil, nate
	 */

	// ================//
	// Main Variables //
	// ===============//
	public Farmer farmer = new Farmer();
	public Warrior warrior = new Warrior();
	public Economy economy = new Economy();
	public Wheat wheat = new Wheat();

	// ==================//
	// Country Variables //
	// ==================//
	public String name;

	// ====================//
	// Simulation Settings //
	// ====================//
	public boolean favorFarmers = true;
	public boolean displacedEat = true;
	public float desiredWarriorRatio = 0.15f;
	public float desiredFarmerRatio = 0.85f;
	public float fBirthRate = 0.03f;
	public float fDeathRate = 0.02f;
	public float fDefaultDeathRate = 0.02f;
	public float wBirthRate = 0.008f;
	public float wDeathRate = 0.002f;
	public float wDefaultDeathRate = 0.002f;
	public float farmerDeathRatio = 0.75f;
	public float warriorDeathRatio = 0.75f;

	// ================//
	// Other Variables //
	// ================//
	public int maxpop = 10000;
	public int displaced = 0;
	public static String[] names = new String[] { "Tumblr", "Yahoo",
			"PiedPiper", "Baidu", "Xiaomi" };

	public Country(boolean favorFarmers, boolean displacedEat,
			float desiredWarriorRatio, float desiredFarmerRatio) {
		name = names[Util.randInt(0, names.length - 1)];

		fBirthRate = Util.randFloat(0.01f, 0.07f);
		fDeathRate = 0.02f;
		fDefaultDeathRate = 0.02f;
		wBirthRate = Util.randFloat(0.001f, 0.01f);
		wDeathRate = 0.002f;
		wDefaultDeathRate = 0.002f;
		farmerDeathRatio = Util.randFloat(0.00f, 1f);
		warriorDeathRatio = Util.randFloat(0.00f, 1f);
	}

	public void tick() {
		// ==================//
		// Population growth //
		// ==================//
		farmer.fPop();
		warrior.wPop();
		if (farmer.getfPop() + warrior.getwPop() > maxpop) {
			int overflow = farmer.getfPop() + warrior.getwPop() - maxpop;
			int kfarmer = (int) (overflow * desiredFarmerRatio);
			int kwarrior = (int) (overflow * desiredWarriorRatio);
			farmer.addPop(-kfarmer);
			warrior.addPop(-kwarrior);
			displaced += overflow;
		}

		// ==============//
		// Conscription //
		// =============//
		float newPopulation = farmer.newPop() + warrior.newPop();
		float newWarriors = newPopulation * desiredWarriorRatio;
		newPopulation -= newWarriors;
		farmer.addPop(newPopulation);
		warrior.addPop(newWarriors);

		// ====================//
		// Hunger Calculations //
		// ====================//

		wheat.tWheat(farmer.getfPop(), farmer);

		farmer.setTotalHunger(farmer.fHunger() * farmer.getfPop());
		warrior.setTotalHunger(warrior.wHunger() * warrior.getwPop());
		int warriorWheat = warrior.getTotalHunger();
		int farmerWheat = farmer.getTotalHunger();

		if (favorFarmers) {
			farmerWheat = wheat.eatWheat(farmerWheat, economy);
			warriorWheat = wheat.eatWheat(warriorWheat, economy);
		} else {
			warriorWheat = wheat.eatWheat(warriorWheat, economy);
			farmerWheat = wheat.eatWheat(farmerWheat, economy);
		}

		if (farmerWheat != 0) {
			int fDeath = (int) Math.round(((float) farmerWheat / (float) farmer
					.getfHunger()) * farmerDeathRatio);
			farmer.addPop(-fDeath);
		}

		if (warriorWheat != 0) {
			int wDeath = (int) Math
					.round(((float) warriorWheat / (float) warrior.getwHunger())
							* warriorDeathRatio);
			warrior.addPop(-wDeath);
		}

		if (displacedEat) {
			int displacedHungerConst = farmer.getfHunger() / 2;
			int displacedHunger = displaced * displacedHungerConst;
			displacedHunger = wheat.eatWheat(displacedHunger, economy);
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) farmer.getfHunger() / 2f) * 1f);
				displaced -= displacedDeath;
			}
		} else {
			int displacedHungerConst = farmer.getfHunger() / 2;
			int displacedHunger = displaced * displacedHungerConst;
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) farmer.getfHunger() / 2f) * 1f);
				displaced -= displacedDeath;
			}
		}

		// =================//
		// Economic Updates //
		// =================//
		wheat.update(economy);
	}

}
