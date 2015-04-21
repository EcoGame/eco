package eco;

import java.io.File;
import java.util.Random;
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

	public static Random random = new Random();

	public static final int fov = 70;
	public static final int windowheight = 620;
	public static final int windowwidth = 854;
	public static final int height = 720;
	public static final int width = 1280;

	public static boolean attemptSaveLoad = false;
	public static final boolean isInEclipse = false;
	public static boolean paused = false;
	public static boolean debug;
	public static boolean fullDebug = false;

	public static boolean skipFrame = false;

	public static String saveName1 = "save";
	public static String saveName2 = "save2";
	public static String saveName3 = "save3";
	public static String saveName4 = "save4";
	public static String saveName5 = "save5";
	public static int currentSave;

	public static float fBirthRate = 0.03f;
	public static volatile float fDeathRate = 0.02f;
	public static float fDefaultDeathRate = 0.02f;
	public static float wBirthRate = 0.008f;
	public static volatile float wDeathRate = 0.002f;
	public static float wDefaultDeathRate = 0.002f;

	public static volatile int year = 0;

	public static float farmerDeathRatio = 0.75f;
	public static float warriorDeathRatio = 0.75f;

	public static boolean favorFarmers = true;

	public static boolean displacedEat = true;

	public static final int ticks = 2000;

	public static final String vn = "Stable 0.6.1";

	public static int framesPerTick = 8;
	public static int frame = 0;
	public static int unemployedFarmers = 0;
	public static int employedFarmers = 0;

	public static boolean shouldQuit = false;

	public static float desiredWarriorRatio = 0.15f;
	public static float desiredFarmerRatio = 0.85f;

	public static boolean gameOver = false;
	public static String reason = "All of your citizens have perished!";

	public static boolean shouldBeInMenu = true;

	public static int generatorToUse = 0;

	public static int aggDemand;

	public static boolean displayCloseReq = false;
	public static ArrayList<Country> countries = new ArrayList<Country>();


	/* Main method */
	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		init();
		while (!displayCloseReq) {
			shouldBeInMenu = true;
			paused = false;
			menuInit();
			displayCloseReq = mainMenu();
		}
		System.exit(0);
	}

	/* Normal game loop */
	/* returns if the display was closed */
	public static boolean gameLoop() {
		shouldBeInMenu = true;
		while (!shouldQuit) {
			if (Display.isCloseRequested()) {
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
					if (Farmer.getfPop() == 0 && Warrior.getwPop() == 0
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
					Graphs.draw(year, Economy.getPrice(), Farmer.getfPop() + Warrior.getwPop(), Economy.getTreasury());
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

		Farmer.addPop(-World.displacedFarmers);
		Warrior.addPop(-World.displacedWarriors);
		if (World.displacedFarmers == 0) {
			Farmer.fPop();
		} else {
			Farmer.setOldFPop(Farmer.getfPop()); // Need to update this manually
													// because it's done in
													// fPop()
		}
		if (World.displacedWarriors == 0) {
			Warrior.wPop();
		} else {
			Warrior.setOldWPop(Warrior.getwPop()); // Need to update this
													// manually because it's
													// done in wPop()
		}
		//wheatPrice = Market.wheatPrice(wheatPrice);
		World.displacedPeople += World.displacedFarmers
				+ World.displacedWarriors;
		World.displacedFarmers = 0;
		World.displacedWarriors = 0;
		float newPopulation = Farmer.newPop() + Warrior.newPop();
		float newWarriors = newPopulation * desiredWarriorRatio;
		newPopulation -= newWarriors;
		Farmer.addPop(newPopulation);
		Warrior.addPop(newWarriors);
		Wheat.tWheat(Farmer.getfPop());
		Farmer.setTotalHunger(Farmer.fHunger() * Farmer.fPop());
		Warrior.setTotalHunger(Warrior.wHunger() * Warrior.getwPop());
		int warriorWheat = Warrior.getTotalHunger();
		int farmerWheat = Farmer.getTotalHunger();
		if (favorFarmers) {
			farmerWheat = Wheat.eatWheat(farmerWheat);
			warriorWheat = Wheat.eatWheat(warriorWheat);
		} else {
			warriorWheat = Wheat.eatWheat(warriorWheat);
			farmerWheat = Wheat.eatWheat(farmerWheat);
		}
		if (farmerWheat != 0) {
			int fDeath = (int) Math.round(((float) farmerWheat / (float) Farmer
					.getfHunger()) * farmerDeathRatio);
			Farmer.addPop(-fDeath);
		}
		if (warriorWheat != 0) {
			int wDeath = (int) Math
					.round(((float) warriorWheat / (float) Warrior.getwHunger())
							* warriorDeathRatio);
			Warrior.addPop(-wDeath);
		}
		if (displacedEat) {
			int displacedHungerConst = Farmer.getfHunger() / 2;
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			displacedHunger = Wheat.eatWheat(displacedHunger);
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) Farmer.getfHunger() / 2f) * 1f);
				World.displacedPeople -= displacedDeath;
			}
		} else {
			int displacedHungerConst = Farmer.getfHunger() / 2;
			int displacedHunger = World.displacedPeople * displacedHungerConst;
			if (displacedHunger != 0) {
				int displacedDeath = (int) Math.round(((float) displacedHunger
						/ (float) Farmer.getfHunger() / 2f) * 1f);
				World.displacedPeople -= displacedDeath;
			}
		}
		Wheat.update();		
		World.updateMap(Farmer.getfPop(), Warrior.getwPop());
		World.freeAcres = World.calcAcres();
		if (Render.multithreading) {
			ThreadManager.addJob(new MeshTask());
		} else {
			DisplayLists.mesh();
			skipFrame = true;
		}
	}

	/* Initiate stuff */
	public static void init() {
		Render.initDisplay();
		Render.init();
		try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		if (attemptSaveLoad) {
			Util.readSave();
		}
		DisplayLists.mesh();

	}

	public static void initGame() {
		World.init(generatorToUse);
		Farmer.setfPop(5);
		Warrior.setwPop(5);
		Economy.setTreasury(0);
		Wheat.resetWheat();		
		if (Util.doesSaveExist(currentSave)) {
			Util.readSave();
		}
		else{
		}
		DisplayLists.mesh();
		ArrayList<Country> countries = new ArrayList<Country>();
		int countriesToGenerate = 600;
		for(int i = 0; i <= 600; i++){
			countries.add(new Country(true, true, 0.15f, 0.85f));
		}
	}

	public static void menuInit() {
		paused = false;
		if (isInEclipse) {
			File saves = new File("saves");
			saves.mkdirs();
		} else {
			File saves = new File("../saves");
			saves.mkdirs();
		}
		if (!Util.doesSaveExist(1)) {
			UIManager.startSaveGame1.setText("Create " + saveName1);
		}
		else{
			UIManager.startSaveGame1.setText("Play "+Util.loadSaveName(1));	
		}
		if (!Util.doesSaveExist(2)) {
			UIManager.startSaveGame2.setText("Create " + saveName2);
		}
		else{
			UIManager.startSaveGame2.setText("Play "+Util.loadSaveName(2));	
		}
		if (!Util.doesSaveExist(3)) {
			UIManager.startSaveGame3.setText("Create " + saveName3);
		}
		else{
			UIManager.startSaveGame3.setText("Play "+Util.loadSaveName(3));	
		}
		if (!Util.doesSaveExist(4)) {
			UIManager.startSaveGame4.setText("Create " + saveName4);
		}
		else{
			UIManager.startSaveGame4.setText("Play "+Util.loadSaveName(4));	
		}
		if (!Util.doesSaveExist(5)) {
			UIManager.startSaveGame5.setText("Create " + saveName5);
		}
		else{
			UIManager.startSaveGame5.setText("Play "+Util.loadSaveName(5));	
		}
	}

}
