package eco.game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Main class
 *
 * @author phil, connor, nate, will
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

    public static final String vn = "Beta 1.2";

    public static int framesPerTick = 18;
    public static int frame = 0;

    public static boolean renderPopMap = false;

    // =============//
    // IO Variables //
    // =============//
    public static boolean isInEclipse = false;
    public static boolean paused = false;
    public static boolean fullDebug = false;

    public static int currentSave;
    public static String saveName1 = "save";
    public static String saveName2 = "save2";
    public static String saveName3 = "save3";
    public static String saveName4 = "save4";
    public static String saveName5 = "save5";

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
        if (args.length >= 1) {
            if (args[0].equals("-e")) {
                isInEclipse = true;
            }
        }
        // System.out.println("Welcome to EcoLand!");
        Log.title("Welcome to EcoLand!");
        Log.title("Version " + vn);
        initDisplay();
        Menu.initMenu();
        Log.info("Switching to menu");
        Menu.mainMenu();
        try {
            for (int p = 0; p < 10; p++) {
                Path filepath1 = Paths.get("../newtxt/connections" + p + ".ann");
                Path filepath2 = Paths.get("../neurons/connections" + p + ".ann");
                Files.move(filepath1, filepath2, REPLACE_EXISTING);
            }
            for (int q = 0; q < 10; q++) {
                Path filepath1 = Paths.get("../newtxt/neuron" + q + ".ann");
                Path filepath2 = Paths.get("../neurons/neuron" + q + ".ann");
                Files.move(filepath1, filepath2, REPLACE_EXISTING);
            }
            Path filepath1 = Paths.get("../newtxt/currentstate.ann");
            Path filepath2 = Paths.get("../neurons/currentstate.ann");
            Files.move(filepath1, filepath2, REPLACE_EXISTING);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        Util.quit(0);
    }

    /* Initiate Rendering */
    public static void initDisplay() {
        RenderUtil.initDisplay();
        Log.success("Created display");
        RenderUtil.init();
        Log.success("Initiated openGL");
        try {
            ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        Console.init();
    }

}
