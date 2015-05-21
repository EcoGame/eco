package eco.game;


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

	public static final String vn = "Beta 1.1.0";

	public static int framesPerTick = 8;
	public static int frame = 0;
	
	public static boolean renderPopMap = false;

	// =============//
	// IO Variables //
	// =============//
	public static final boolean isInEclipse = true;
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

	public static final int autoSaveInterval = 100;
	
	/* Main method */
	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		initDisplay();
		Menu.initMenu();
		Menu.mainMenu();
		System.exit(0);
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

}
