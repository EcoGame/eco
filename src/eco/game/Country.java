package eco.game;


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
	public Land land = new Land();
	public Wood wood = new Wood();
	public Stone stone = new Stone();

	// ==================//
	// Country Variables //
	// ==================//
	public String name;

	// ====================//
	// Simulation Settings //
	// ====================//
	public Score score = new Score();
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

	public static float wheatRot = PlayerCountry.wheatRot;

	public int landsize;
	
	public boolean cutForests = false;
	
	public boolean forceConscription = false;

	public boolean dead = false;

    public AggressionScore aggression = new AggressionScore();

	// ================//
	// Other Variables //
	// ================//
	public int maxpop = 10000;
	public int displaced = 0;

	public Country(boolean favorFarmers, boolean displacedEat,
			float desiredWarriorRatio, float desiredFarmerRatio) {

		name = NameGen.generateRandom();

		fBirthRate = Util.randFloat(0.01f, 0.07f);
		fDeathRate = 0.02f;
		fDefaultDeathRate = 0.02f;
		wBirthRate = Util.randFloat(0.001f, 0.01f);
		wDeathRate = 0.002f;
		wDefaultDeathRate = 0.002f;
		farmerDeathRatio = Util.randFloat(0.00f, 1f);
		warriorDeathRatio = Util.randFloat(0.00f, 1f);

		landsize = World.random.nextInt(4000) + 100;
		maxpop = World.random.nextInt(4000) + 100;

	}

	public void tick() {
		// ==================//
		// Population growth //
		// ==================//

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
		float newPopulation = farmer.fPop(fBirthRate, fDeathRate) + warrior.wPop(wBirthRate, wDeathRate);
		float newWarriors = newPopulation * desiredWarriorRatio;
		newPopulation -= newWarriors;
		farmer.addPop(newPopulation);
		warrior.addPop(newWarriors);
		warrior.setOldWPop(warrior.getwPop());
		farmer.setOldFPop(farmer.getfPop());
		
		if (forceConscription){
			float totalpop = farmer.getFloatFPop() + warrior.getFloatWPop();
			float warriors = totalpop * desiredWarriorRatio;
			float farmers = totalpop - warriors;
			warrior.setwPop((int) warriors);
			warrior.setFloatWPop(warriors);
			farmer.setfHunger((int) farmers);
			farmer.setFloatFPop(farmers);
		}

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
			farmer.addPop(fDeath);
			fDeathRate = Math.min(1f, fDeathRate + 0.001f);
		}
		else{
			fDeathRate = Math.max(0f, fDeathRate - 0.001f);
		}

		if (warriorWheat != 0) {
			int wDeath = (int) Math
					.round(((float) warriorWheat / (float) warrior.getwHunger())
							* warriorDeathRatio);
			warrior.addPop(-wDeath);
			wDeathRate = Math.min(1f, wDeathRate + 0.001f);
		}
		else{
			wDeathRate = Math.max(0f, wDeathRate - 0.001f);
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

		if (farmer.getfPop() <= 0 && warrior.getwPop() <= 0){
			dead = true;
			farmer.setfPop(0);
			warrior.setwPop(0);
			wheat.settWheat(0);
			economy.setTreasury(0);
		} else if (landsize <= 0){
			dead = true;
			farmer.setfPop(0);
			warrior.setwPop(0);
			wheat.settWheat(0);
			economy.setTreasury(0);
		}

		// =================//
		// Economic Updates //
		// =================//
		wheat.update(economy);
		wheat.rot(wheatRot);

		// ======//
		// Score //
		// ======//
		int tick = PlayerCountry.year;
		score.calculateTickScore(tick, farmer.getfPop(), warrior.getwPop(), wheat.gettWheat(), economy.getTreasury());
		score.calculateAvgScore(tick);
		score.calculateTickGrowth(tick);
		score.calculateAvgGrowth(tick);
		score.calculatePeakScore(tick);
		score.calculateTotalScore(tick);
		aggression.calculateAggressionScore(warrior.getwPop());

		// ====//
		// War //
		// ====//
		int diff = (Math.abs(aggression.aggressionScore - PlayerCountry.aggression.aggressionScore)) + 1;
		int warMul = 100;
		if (PlayerCountry.year < 250){
			warMul = 1000;
		}
		if (World.random.nextInt(diff * warMul) == 0){
			War.attackPlayer(this);
		}
        
        
        // ================//
		//  Neural Network //
		// ================//
        //eco.neural.NeuralManager.neuralTick(PlayerCountry.countries.indexOf(this));

	}

	public int getScore(){
		try{
			return score.scoreAt(PlayerCountry.year - 1);
		}
		catch(Exception e){
			return 0;
		}
	}

	public void takeLand(int toTake){
		if (toTake >= land.getLand()){
			toTake -= land.getLand();
			land.setLand(0);
			landsize -= toTake;
		} else{
			land.setLand(land.getLand() - toTake);
		}
	}

}
