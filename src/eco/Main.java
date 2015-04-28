package eco;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;

/**
 * Main class
 * 
 * @author phil, connor, nate, will
 * 
 */

public class Main {

	// ====================//
	// Rendering Settings //
	// ====================//
	public static final int fov = 70;
	public static final int windowheight = 620;
	public static final int windowwidth = 854;
	public static final int height = 720;
	public static final int width = 1280;
	public static boolean skipFrame = false;

    public static final String vn = "Stable 0.7.0";

	public static int framesPerTick = 8;
	public static int frame = 0;

	// ======================//
	// Simulation Variables //
	// ======================//
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

	public static ArrayList<Country> countries = new ArrayList<Country>();

	// =============//
	// IO Variables //
	// =============//
	public static final boolean isInEclipse = false;
	public static boolean paused = false;
	public static boolean debug;
	public static boolean fullDebug = false;

	public static String saveName1 = "save";
	public static String saveName2 = "save2";
	public static String saveName3 = "save3";
	public static String saveName4 = "save4";
	public static String saveName5 = "save5";
	public static int currentSave;

	// ================//
	// Other Variables //
	// ================//
	public static boolean shouldQuit = false;

	public static boolean gameOver = false;
	public static String reason = "All of your citizens have perished!";
	public static boolean shouldBeInMenu = true;

	public static boolean displayCloseReq = false;

	// ======================//
	// Newly Instanced Stuff //
	// ======================//
	public static Wheat wheat = new Wheat();
	public static Farmer farmer = new Farmer();
	public static Warrior warrior = new Warrior();
	public static Economy economy = new Economy();

	/* Main method */
	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		initDisplay();
        initMenu();
        mainMenu();
		System.exit(0);
	}

	/* Normal game loop */
	/* returns if the display was closed */
	public static boolean gameLoop() {
		shouldBeInMenu = true;
		while (!shouldQuit) {
			if (Display.isCloseRequested()) {
				Util.createSave();
                return true;
			}
			if (gameOver) {
				Render.drawGameOver(reason);
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
			} else if (!Main.paused) {
				frame++;
				if (frame >= framesPerTick && !paused && year < ticks) {
					year++;
					tick();
					if (farmer.getfPop() == 0 && warrior.getwPop() == 0
							&& World.displacedPeople == 0) {
						gameOver = true;
					}
					frame = 0;
				}
				UIManager.update();
				InputManager.update();
				if (!skipFrame) {
					Render.draw();
					OutputManager.newDebug();
					Graphs.draw(year, economy.getPrice(), farmer.getfPop()
							+ warrior.getwPop(), economy.getTreasury());
				} else {
					skipFrame = false;
				}
				// Graphs.draw(year, wheatPrice, getTotalPop(), taxRevenue);
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
        mainMenu();
		return false;
	}

	/* Main menu loop */
	/* returns if the display was closed */
	public static boolean mainMenu() {
		while (shouldBeInMenu) {
			if (Display.isCloseRequested()) {
				shouldQuit = true;
				return true;
			}
			Render.drawMainMenu();
			InputManager.updateMenu();
			UIManager.updateMenu();
			FPSCounter.tick();
			Display.update();
			Display.sync(60);
		}
		return false;
	}

	/* Game tick */
	public static void tick() {

		farmer.addPop(-World.displacedFarmers);
		warrior.addPop(-World.displacedWarriors);
		if (World.displacedFarmers == 0) {
			farmer.fPop();
		} else {
			farmer.setOldFPop(farmer.getfPop()); // Need to update this manually
			// because it's done in
			// fPop()
		}
		if (World.displacedWarriors == 0) {
			warrior.wPop();
		} else {
			warrior.setOldWPop(warrior.getwPop()); // Need to update this
			// manually because it's
			// done in wPop()
		}
		// wheatPrice = Market.wheatPrice(wheatPrice);
		World.displacedPeople += World.displacedFarmers
				+ World.displacedWarriors;
		World.displacedFarmers = 0;
		World.displacedWarriors = 0;
		float newPopulation = farmer.newPop() + warrior.newPop();
		float newWarriors = newPopulation * desiredWarriorRatio;
		newPopulation -= newWarriors;
		farmer.addPop(newPopulation);
		warrior.addPop(newWarriors);
		wheat.tWheat(farmer.getfPop(), farmer);
		farmer.setTotalHunger(farmer.fHunger() * farmer.fPop());
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
		World.updateMap(farmer.getfPop(), warrior.getwPop());
		World.freeAcres = World.calcAcres();
		if (Render.multithreading) {
			ThreadManager.addJob(new MeshTask());
		} else {
			DisplayLists.mesh();
			skipFrame = true;
		}

		for (Country country : countries) {
			country.tick();
		}
	}

	/* Initiate Rendering */
	public static void initDisplay() {
		Render.initDisplay();
		Render.init();
		try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		DisplayLists.mesh();

	}

	/* Starts a game */
	public static void initGame() {
		World.init(generatorToUse);
		farmer.setfPop(5);
		warrior.setwPop(5);
		economy.setTreasury(0);
		wheat.resetWheat();
		if (Util.doesSaveExist(currentSave)) {
			Util.readSave();
		} else {
		}
		DisplayLists.mesh();
		ArrayList<Country> countries = new ArrayList<Country>();
		int countriesToGenerate = 600;
		for (int i = 0; i <= countriesToGenerate; i++) {
			countries.add(new Country(true, true, 0.15f, 0.85f));
		}
	}

	/* Starts the menu */
	public static void initMenu() {
		paused = false;
		if (isInEclipse) {
			File saves = new File("saves");
			saves.mkdirs();
		} else {
			File saves = new File("../saves");
			saves.mkdirs();
		}
		if (!Util.doesSaveExist(1)) {
			UIManager.startSaveGame1.setText("Create a save");
		} else {
			UIManager.startSaveGame1.setText("Play " + Util.loadSaveName(1));
		}
		if (!Util.doesSaveExist(2)) {
			UIManager.startSaveGame2.setText("Create a save");
		} else {
			UIManager.startSaveGame2.setText("Play " + Util.loadSaveName(2));
		}
		if (!Util.doesSaveExist(3)) {
			UIManager.startSaveGame3.setText("Create a save");
		} else {
			UIManager.startSaveGame3.setText("Play " + Util.loadSaveName(3));
		}
		if (!Util.doesSaveExist(4)) {
			UIManager.startSaveGame4.setText("Create a save");
		} else {
			UIManager.startSaveGame4.setText("Play " + Util.loadSaveName(4));
		}
		if (!Util.doesSaveExist(5)) {
			UIManager.startSaveGame5.setText("Create a save");
		} else {
			UIManager.startSaveGame5.setText("Play " + Util.loadSaveName(5));
		}
	}

}
