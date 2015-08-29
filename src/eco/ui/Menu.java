package eco.ui;

import eco.render.FPSCounter;
import eco.game.Main;
import eco.game.SaveUtil;
import eco.game.Util;
import eco.render.Render;
import org.lwjgl.opengl.Display;

import java.io.File;

public class Menu {

    /* Main menu loop */
    /* returns if the display was closed */
    public static void mainMenu() {
        initMenu();
        while (Main.shouldBeInMenu) {
            Command.update();
            if (Display.isCloseRequested()) {
                Main.shouldQuit = true;
                Util.quit(0);
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
        if (!SaveUtil.doesSaveExist(1)) {
            UIManager.startSaveGame1.setText("Create a save");
        } else {
            UIManager.startSaveGame1.setText("Play " + SaveUtil.loadSaveName(1));
        }
        if (!SaveUtil.doesSaveExist(2)) {
            UIManager.startSaveGame2.setText("Create a save");
        } else {
            UIManager.startSaveGame2.setText("Play " + SaveUtil.loadSaveName(2));
        }
        if (!SaveUtil.doesSaveExist(3)) {
            UIManager.startSaveGame3.setText("Create a save");
        } else {
            UIManager.startSaveGame3.setText("Play " + SaveUtil.loadSaveName(3));
        }
        if (!SaveUtil.doesSaveExist(4)) {
            UIManager.startSaveGame4.setText("Create a save");
        } else {
            UIManager.startSaveGame4.setText("Play " + SaveUtil.loadSaveName(4));
        }
        if (!SaveUtil.doesSaveExist(5)) {
            UIManager.startSaveGame5.setText("Create a save");
        } else {
            UIManager.startSaveGame5.setText("Play " + SaveUtil.loadSaveName(5));
        }
    }

}
