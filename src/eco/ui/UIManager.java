package eco.ui;

import eco.game.Game;
import eco.game.Main;
import eco.game.PlayerCountry;
import eco.game.SaveUtil;
import eco.render.Render;
import eco.render.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class handles the rendering, updating, and passing of click events for
 * buttons
 *
 * @author phil, connor
 */

public class UIManager {

    public static String word = "";
    public static int cursorTick = 1;

    public static TextButton startSaveGame1 = new TextButton(
            (Main.width / 2) - 128, 256, 256, 32, 6, 2, 7, 2, Main.saveName1);
    public static TextButton startSaveGame2 = new TextButton(
            (Main.width / 2) - 128, 286, 256, 32, 6, 2, 7, 2, Main.saveName2);
    public static TextButton startSaveGame3 = new TextButton(
            (Main.width / 2) - 128, 316, 256, 32, 6, 2, 7, 2, Main.saveName3);
    public static TextButton startSaveGame4 = new TextButton(
            (Main.width / 2) - 128, 346, 256, 32, 6, 2, 7, 2, Main.saveName4);
    public static TextButton startSaveGame5 = new TextButton(
            (Main.width / 2) - 128, 376, 256, 32, 6, 2, 7, 2, Main.saveName5);

    private static LockButton delete1 = new LockButton(
            (Main.width / 2) + 128 + 8, 256, 24, 6, 3, 7, 3, false);
    private static LockButton delete2 = new LockButton(
            (Main.width / 2) + 128 + 8, 286 + 2, 24, 6, 3, 7, 3, false);
    private static LockButton delete3 = new LockButton(
            (Main.width / 2) + 128 + 8, 316 + 4, 24, 6, 3, 7, 3, false);
    private static LockButton delete4 = new LockButton(
            (Main.width / 2) + 128 + 8, 346 + 6, 24, 6, 3, 7, 3, false);
    private static LockButton delete5 = new LockButton(
            (Main.width / 2) + 128 + 8, 376 + 8, 24, 6, 3, 7, 3, false);

    private static ToggleTextButton generatorIsland = new ToggleTextButton(
            (Main.width / 2) + 256, 256, 256, 32, 6, 2, 7, 2, "Island", true);
    private static ToggleTextButton generatorArchipelago = new ToggleTextButton(
            (Main.width / 2) + 256, 320, 256, 32, 6, 2, 7, 2, "Archipelago",
            false);
    private static ToggleTextButton generatorMountains = new ToggleTextButton(
            (Main.width / 2) + 256, 384, 256, 32, 6, 2, 7, 2, "Mountains",
            false);

    private static TextButton exitToMenu = new TextButton(
            (Main.width / 2) - 128, Main.height / 2f, 256, 32, 6, 2, 7, 2,
            "Exit Game");

    private static TextButton exitToMenuGameOver = new TextButton(
            (Main.width / 2) - 128, (Main.height / 2f) + 42, 256, 32, 6, 2, 7,
            2, "Exit Game");
    private static TextButton loadLastSave = new TextButton(
            (Main.width / 2) - 128, (Main.height / 2f) + 82, 256, 32, 6, 2, 7,
            2, "Load Last Save");

    private static ArrayList<Button> buttons = new ArrayList<Button>();
    private static ArrayList<Button> menuButtons = new ArrayList<Button>();
    private static ArrayList<Button> pauseButtons = new ArrayList<Button>();
    private static ArrayList<Button> gameOverButtons = new ArrayList<Button>();

    private static final String emptyWorldName = "Untitled";

    /*
     * First define your button above, then register it below
     */

    static {

        menuButtons.add(startSaveGame1);
        menuButtons.add(startSaveGame2);
        menuButtons.add(startSaveGame3);
        menuButtons.add(startSaveGame4);
        menuButtons.add(startSaveGame5);
        menuButtons.add(generatorIsland);
        menuButtons.add(generatorArchipelago);
        menuButtons.add(generatorMountains);
        menuButtons.add(delete1);
        menuButtons.add(delete2);
        menuButtons.add(delete3);
        menuButtons.add(delete4);
        menuButtons.add(delete5);

        pauseButtons.add(exitToMenu);

        gameOverButtons.add(exitToMenuGameOver);
        gameOverButtons.add(loadLastSave);
    }

    /*
     * Write button logic in this method, it should be in the form: if
     * (myButton.checkForClick()){ myAction() }
     */

    public static void update() {
        MenuBar.update(PlayerCountry.playerCountry);
    }

    public static void updatePaused() {
        if (exitToMenu.checkForClick()) {
            Main.shouldBeInMenu = true;
            Main.shouldQuit = true;
        }
    }

    public static void updateGameOver() {
        if (exitToMenuGameOver.checkForClick()) {
            Main.shouldBeInMenu = true;
            Main.shouldQuit = true;
        }
        if (loadLastSave.checkForClick()) {
            Main.gameOver = false;
            SaveUtil.readSave();
        }
    }

    public static void updateMenu() {
        if (delete1.checkForClick()) {
            SaveUtil.deleteSave(1);
        }
        if (delete2.checkForClick()) {
            SaveUtil.deleteSave(2);
        }
        if (delete3.checkForClick()) {
            SaveUtil.deleteSave(3);
        }
        if (delete4.checkForClick()) {
            SaveUtil.deleteSave(4);
        }
        if (delete5.checkForClick()) {
            SaveUtil.deleteSave(5);
        }
        word = "";
        if (startSaveGame1.checkForClick()) {
            if (!SaveUtil.doesSaveExist(1)) {
                Main.saveName1 = "";
                while (!word.equals("@")) {
                    cursorTick++;
                    word = Typing.currentMenuName(word, cursorTick);
                    if (word.equals("@")) {
                        break;
                    }
                    if (word.equals("^")) {
                        Main.saveName1 = "Create a save";
                        startSaveGame1.setText("Create a save");
                        return;
                    }
                    Main.saveName1 = word;
                    startSaveGame1.setText(word);
                    RenderUtil.initOrtho();
                    renderMenu();
                    renderMenu2();
                    Render.drawMainMenu();
                    Display.update();
                    Display.sync(20);
                }
            }
            if (Main.saveName1.contains("|")) {
                Main.saveName1 = Main.saveName1.replace("|", "");
                startSaveGame1.setText(Main.saveName1);
            }
            if (Main.saveName1.length() == 0) {
                Main.saveName1 = emptyWorldName;
                startSaveGame1.setText(Main.saveName1);
            }
            Main.currentSave = 1;
            Main.shouldQuit = false;
            Game.gameLoop(new PlayerCountry());
        }

        if (startSaveGame2.checkForClick()) {
            if (!SaveUtil.doesSaveExist(2)) {
                Main.saveName2 = "";
                while (!word.equals("@")) {
                    cursorTick++;
                    word = Typing.currentMenuName(word, cursorTick);
                    if (word.equals("@")) {
                        break;
                    }
                    if (word.equals("^")) {
                        Main.saveName2 = "Create a save";
                        startSaveGame2.setText("Create a save");
                        return;
                    }
                    Main.saveName2 = word;
                    startSaveGame2.setText(word);
                    RenderUtil.initOrtho();
                    renderMenu();
                    renderMenu2();
                    Render.drawMainMenu();
                    Display.update();
                    Display.sync(20);
                }
            }
            if (Main.saveName2.contains("|")) {
                Main.saveName2 = Main.saveName2.replace("|", "");
                startSaveGame2.setText(Main.saveName2);
                startSaveGame2.setText(word);
            }
            if (Main.saveName2.length() == 0) {
                Main.saveName2 = emptyWorldName;
                startSaveGame2.setText(Main.saveName1);
            }
            Main.currentSave = 2;
            Main.shouldQuit = false;
            Game.gameLoop(new PlayerCountry());
        }

        if (startSaveGame3.checkForClick()) {
            if (!SaveUtil.doesSaveExist(3)) {
                Main.saveName3 = "";
                while (!word.equals("@")) {
                    cursorTick++;
                    word = Typing.currentMenuName(word, cursorTick);
                    if (word.equals("@")) {
                        break;
                    }
                    if (word.equals("^")) {
                        Main.saveName3 = "Create a save";
                        startSaveGame2.setText("Create a save");
                        return;
                    }
                    Main.saveName3 = word;
                    startSaveGame3.setText(word);
                    RenderUtil.initOrtho();
                    renderMenu();
                    renderMenu2();
                    Render.drawMainMenu();
                    Display.update();
                    Display.sync(20);
                }
            }
            if (Main.saveName3.contains("|")) {
                Main.saveName3 = Main.saveName3.replace("|", "");
                startSaveGame3.setText(Main.saveName3);
            }
            if (Main.saveName3.length() == 0) {
                Main.saveName3 = emptyWorldName;
                startSaveGame3.setText(Main.saveName1);
            }
            Main.currentSave = 3;
            Main.shouldQuit = false;
            Game.gameLoop(new PlayerCountry());

        }

        if (startSaveGame4.checkForClick()) {
            if (!SaveUtil.doesSaveExist(4)) {
                Main.saveName4 = "";
                while (!word.equals("@")) {
                    cursorTick++;
                    word = Typing.currentMenuName(word, cursorTick);
                    if (word.equals("@")) {
                        break;
                    }
                    if (word.equals("^")) {
                        Main.saveName4 = "Create a save";
                        startSaveGame4.setText("Create a save");
                        return;
                    }
                    Main.saveName4 = word;
                    startSaveGame4.setText(word);
                    RenderUtil.initOrtho();
                    renderMenu();
                    renderMenu2();
                    Render.drawMainMenu();
                    Display.update();
                    Display.sync(20);
                }
            }
            if (Main.saveName4.contains("|")) {
                Main.saveName4 = Main.saveName4.replace("|", "");
                startSaveGame4.setText(Main.saveName4);
            }
            if (Main.saveName4.length() == 0) {
                Main.saveName4 = emptyWorldName;
                startSaveGame4.setText(Main.saveName1);
            }
            Main.currentSave = 4;
            Main.shouldQuit = false;
            Game.gameLoop(new PlayerCountry());
        }

        if (startSaveGame5.checkForClick()) {
            if (!SaveUtil.doesSaveExist(5)) {
                Main.saveName5 = "";
                while (!word.equals("@")) {
                    cursorTick++;
                    word = Typing.currentMenuName(word, cursorTick);
                    if (word.equals("@")) {
                        break;
                    }
                    if (word.equals("^")) {
                        Main.saveName5 = "Create a save";
                        startSaveGame5.setText("Create a save");
                        return;
                    }
                    Main.saveName5 = word;
                    startSaveGame5.setText(word);
                    RenderUtil.initOrtho();
                    renderMenu();
                    renderMenu2();
                    Render.drawMainMenu();
                    Display.update();
                    Display.sync(20);
                }
            }
            if (Main.saveName5.contains("|")) {
                Main.saveName5 = Main.saveName5.replace("|", "");
                startSaveGame5.setText(Main.saveName5);
            }
            if (Main.saveName5.length() == 0) {
                Main.saveName5 = emptyWorldName;
                startSaveGame5.setText(Main.saveName1);
            }
            Main.currentSave = 5;
            Main.shouldQuit = false;
            Game.gameLoop(new PlayerCountry());

        }

        if (generatorIsland.checkForClick()) {
            PlayerCountry.generatorToUse = 0;
            generatorArchipelago.setToggle(false);
            generatorMountains.setToggle(false);
        }

        if (generatorArchipelago.checkForClick()) {
            PlayerCountry.generatorToUse = 1;
            generatorIsland.setToggle(false);
            generatorMountains.setToggle(false);
        }

        if (generatorMountains.checkForClick()) {
            PlayerCountry.generatorToUse = 2;
            generatorIsland.setToggle(false);
            generatorArchipelago.setToggle(false);
        }
    }

    public static void click(float x, float y) {
        for (Button b : buttons) {
            b.click(x, y);
        }
        MenuBar.click(x, y);
    }

    public static void clickMenu(float x, float y) {
        for (Button b : menuButtons) {
            b.click(x, y);
        }
    }

    public static void clickPause(float x, float y) {
        for (Button b : pauseButtons) {
            b.click(x, y);
        }
    }

    public static void clickGameOver(float x, float y) {
        for (Button b : gameOverButtons) {
            b.click(x, y);
        }
    }

    public static void renderMenu() {
        for (Button b : menuButtons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }
    }

    public static void renderPause() {
        for (Button b : pauseButtons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }
    }

    public static void renderGameOver() {
        for (Button b : gameOverButtons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }
    }

    public static void render() {
        MenuBar.render(Mouse.getX(), Main.height - Mouse.getY());
        for (Button b : buttons) {
            b.render(Mouse.getX(), Main.height - Mouse.getY());
        }
    }

    public static void renderMenu2() {
        for (Button b : menuButtons) {
            b.render2();
        }
    }

    public static void renderGameOver2() {
        for (Button b : gameOverButtons) {
            b.render2();
        }

        float centerX = Display.getWidth() / 2f;
        float centerY = Display.getHeight() / 2f;

        float textWidth = RenderUtil.font.getWidth("Game Over") / 2f;
        float textHeight = RenderUtil.font.getHeight("Game Over") / 2f;
        float textWidth2 = RenderUtil.font.getWidth(Main.reason) / 2f;

        glDisable(GL_DEPTH_TEST);
        RenderUtil.font.drawString(centerX - textWidth, centerY - textHeight,
                "Game Over");
        RenderUtil.font.drawString(centerX - textWidth2, centerY - textHeight + 30,
                Main.reason);
        glEnable(GL_DEPTH_TEST);
    }

    public static void renderPause2() {
        for (Button b : pauseButtons) {
            b.render2();
        }

        float centerX = Display.getWidth() / 2f;
        float centerY = 256 + 64;

        float textWidth = RenderUtil.font.getWidth("Paused") / 2f;
        float textHeight = RenderUtil.font.getHeight("Paused") / 2f;

        RenderUtil.font.drawString(centerX - textWidth, centerY - textHeight,
                "Paused");
    }

    public static void render2() {
        MenuBar.render2(Mouse.getX(), Main.height - Mouse.getY());
        for (Button b : buttons) {
            b.render2();
        }
    }

}
