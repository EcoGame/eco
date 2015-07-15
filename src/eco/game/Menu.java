package eco.game;

import java.io.File;

import org.lwjgl.opengl.Display;

public class Menu {

    /* Main menu loop */
    /* returns if the display was closed */
    public static void mainMenu() {
        initMenu();
        while (Main.shouldBeInMenu) {
            if (Display.isCloseRequested()) {
                Main.shouldQuit = true;
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

    /* Starts the menu */
    public static void initMenu() {
        Main.paused = false;
        Main.gameOver = false;
        SplashText.newSplash();
        if (Main.isInEclipse) {
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
