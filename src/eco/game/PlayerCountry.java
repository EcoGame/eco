package eco.game;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class PlayerCountry {

	/**
	 * 
	 * Just a class to keep Main less cluttered
	 * Simulates the player country
	 * @author phil, nate, will, connor
	 * 
	 */
	// =====================//
	// Simulation Variables //
	// =====================//
	public static float fBirthRate = 0.03f;
	public static volatile float fDeathRate = 0.02f;
	public static float wBirthRate = 0.008f;
	public static float wDeathRate = 0.002f;

	public static int year = 0;

	public static float farmerDeathRatio = 0.75f;
	public static float warriorDeathRatio = 0.75f;
	public static float desiredWarriorRatio = 0.15f;
	public static float desiredFarmerRatio = 1f - desiredWarriorRatio;

	public static boolean favorFarmers = true;
	public static boolean displacedEat = true;

	public static final int ticks = 2000;

	public static int generatorToUse = 0;
	
	public static float wheatRot = 0.9f;

	public static ArrayList<Country> countries = new ArrayList<Country>();
	
	public static boolean forceConscription = true;

	// ======================//
	// Newly Instanced Stuff //
	// ======================//
	public static final Wheat wheat = new Wheat();
	public static final Farmer farmer = new Farmer();
	public static final Warrior warrior = new Warrior();
	public static final Economy economy = new Economy();
	
	public static String name = "Canada";

	/* Game tick */
	public static void tick() {


		// ==================//
		// Population growth //
		// ==================//
		farmer.addPop(-World.displacedFarmers);
		warrior.addPop(-World.displacedWarriors);

		if (World.displacedFarmers != 0) {
			farmer.setOldFPop(farmer.getfPop());
		}

		if (World.displacedWarriors != 0) {
			warrior.setOldWPop(warrior.getwPop());
		}

		World.displacedPeople += World.displacedFarmers
				+ World.displacedWarriors;
		World.displacedFarmers = 0;
		World.displacedWarriors = 0;

		if (World.displacedFarmers == 0 && World.displacedWarriors == 0){
			float newPopulation = farmer.fPop(fBirthRate, fDeathRate) + warrior.wPop(fBirthRate, fDeathRate);
			float newWarriors = newPopulation * desiredWarriorRatio;
			newPopulation -= newWarriors;
			farmer.addPop(newPopulation);
			warrior.addPop(newWarriors);
			warrior.setOldWPop(warrior.getwPop());
			farmer.setOldFPop(farmer.getfPop());
		}
		
		if (forceConscription){
			float totalpop = farmer.getFloatFPop() + warrior.getFloatWPop();
			float warriors = totalpop * desiredWarriorRatio;
			float farmers = totalpop - warriors;
			warrior.setwPop((int) warriors);
			warrior.setFloatWPop(warriors);
			farmer.setfHunger((int) farmers);
			farmer.setFloatFPop(farmers);
		}

		// =================//
		// Wheat Production //
		// =================//
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
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			displacedHunger = wheat.eatWheat(displacedHunger, economy);
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) farmer.getfHunger() / 2f) * 1f);
				World.displacedPeople -= displacedDeath;
			}
		} else {
			int displacedHungerConst = farmer.getfHunger() / 2;
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) farmer.getfHunger() / 2f) * 1f);
				World.displacedPeople -= displacedDeath;
			}
		}

		wheat.update(economy);
		wheat.rot(wheatRot);
		Wheat.globalRot(wheatRot);

		// ===========//
		// Map Update //
		// ===========//
		World.updateMap(farmer.getfPop(), warrior.getwPop());
		World.freeAcres = World.calcAcres();

		// ===============//
		// Render Updates //
		// ===============//
		if (Render.multithreading) {
			ThreadManager.addJob(new MeshTask());
		} else {
			DisplayLists.mesh();
			Main.skipFrame = true;
		}

		// =============//
		// Multicountry //
		// =============//
		for (Country country : countries) {
			if (!country.dead){
				country.tick();
			}
		}
	}
	

	/* Normal game loop */
	/* returns if the display was closed */
	public static void gameLoop() {
		Main.shouldBeInMenu = true;
		while (!Main.shouldQuit) {
			if (Display.isCloseRequested()) {
				Util.createSave();
				System.exit(0);
			}
			if (Main.gameOver) {
				Render.drawGameOver(Main.reason);
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
			} else if (!Main.paused) {
				Main.frame++;
				if (Main.frame >= Main.framesPerTick && !Main.paused
						&& PlayerCountry.year < PlayerCountry.ticks) {
					PlayerCountry.year++;
					PlayerCountry.tick();
					if (PlayerCountry.farmer.getfPop() <= 0
							&& PlayerCountry.warrior.getwPop() <= 0) {
						Main.gameOver = true;
					}
					Main.frame = 0;
				}
				UIManager.update();
				InputManager.update();
				if (!Main.skipFrame) {
					Render.draw();
					OutputManager.newDebug();
					Graphs.draw(PlayerCountry.year,
							PlayerCountry.farmer.getfPop()
									+ PlayerCountry.warrior.getwPop(),
									Wheat.globalWheat,
							PlayerCountry.economy.getTreasury());
				} else {
					Main.skipFrame = false;
				}
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
			} else {
				Render.drawPaused();
				InputManager.updatePause();

				UIManager.renderPause();
				UIManager.renderPause2();
				UIManager.updatePaused();
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
			}
		}
		Util.createSave();
		Menu.mainMenu();
	}


	/* Starts a game */
	public static void initGame() {
		World.init(generatorToUse);
		Main.paused = false;
		farmer.setfPop(5);
		warrior.setwPop(5);
		economy.setTreasury(0);
		wheat.resetWheat();
		if (Util.doesSaveExist(Main.currentSave)) {
			Util.readSave();
		} else {
		}
		DisplayLists.mesh();
		countries = new ArrayList<Country>();
		int countriesToGenerate = 10;
		for (int i = 0; i <= countriesToGenerate; i++) {
			countries.add(new Country(true, true, 0.15f, 0.85f));
		}
	}


}
