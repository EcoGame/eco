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

	// ===================//
	// Rendering Settings //
	// ===================//
	public static final int fov = 70;
	public static final int windowheight = 620;
	public static final int windowwidth = 854;
	public static final int height = 720;
	public static final int width = 1280;
	public static boolean skipFrame = false;

	public static final String vn = "Stable 0.7.1";

	public static int framesPerTick = 8;
	public static int frame = 0;

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

	public static boolean gameOver = false;
	public static String reason = "All of your citizens have perished!";
	public static boolean shouldBeInMenu = true;
	public static boolean shouldQuit = false;

	/* Main method */
	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		initDisplay();
		initMenu();
		mainMenu();
		System.exit(0);
	}
	
	/* Main menu loop */
	/* returns if the display was closed */
	public static void mainMenu() {
		while (shouldBeInMenu) {
			if (Display.isCloseRequested()) {
				shouldQuit = true;
				System.exit(0);
			}
			Render.drawMainMenu();
			InputManager.updateMenu();
			UIManager.updateMenu();
			FPSCounter.tick();
			Display.update();
			Display.sync(60);
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
		World.init(PlayerCountry.generatorToUse);
		paused = false;
		PlayerCountry.farmer.setfPop(5);
		PlayerCountry.warrior.setwPop(5);
		PlayerCountry.economy.setTreasury(0);
		PlayerCountry.wheat.resetWheat();
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
