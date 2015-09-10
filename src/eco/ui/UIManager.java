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
            (Main.width / 2) - 128, 256, 256, 32, 6, 2, 7, 2, ClickEvents.startGameOne, Main.saveName1);
    public static TextButton startSaveGame2 = new TextButton(
            (Main.width / 2) - 128, 286, 256, 32, 6, 2, 7, 2, ClickEvents.startGameTwo, Main.saveName2);
    public static TextButton startSaveGame3 = new TextButton(
            (Main.width / 2) - 128, 316, 256, 32, 6, 2, 7, 2, ClickEvents.startGameThree, Main.saveName3);
    public static TextButton startSaveGame4 = new TextButton(
            (Main.width / 2) - 128, 346, 256, 32, 6, 2, 7, 2, ClickEvents.startGameFour,  Main.saveName4);
    public static TextButton startSaveGame5 = new TextButton(
            (Main.width / 2) - 128, 376, 256, 32, 6, 2, 7, 2, ClickEvents.startGameFive, Main.saveName5);

    private static LockButton delete1 = new LockButton(
            (Main.width / 2) + 128 + 8, 256, 24, 6, 3, 7, 3, ClickEvents.deleteSave1, false);
    private static LockButton delete2 = new LockButton(
            (Main.width / 2) + 128 + 8, 286 + 2, 24, 6, 3, 7, 3, ClickEvents.deleteSave2, false);
    private static LockButton delete3 = new LockButton(
            (Main.width / 2) + 128 + 8, 316 + 4, 24, 6, 3, 7, 3, ClickEvents.deleteSave3,  false);
    private static LockButton delete4 = new LockButton(
            (Main.width / 2) + 128 + 8, 346 + 6, 24, 6, 3, 7, 3, ClickEvents.deleteSave4, false);
    private static LockButton delete5 = new LockButton(
            (Main.width / 2) + 128 + 8, 376 + 8, 24, 6, 3, 7, 3, ClickEvents.deleteSave5, false);

    protected static ToggleTextButton generatorIsland = new ToggleTextButton(
            (Main.width / 2) + 256, 256, 256, 32, 6, 2, 7, 2, ClickEvents.genIslands, "Island", true);
    protected static ToggleTextButton generatorArchipelago = new ToggleTextButton(
            (Main.width / 2) + 256, 320, 256, 32, 6, 2, 7, 2, ClickEvents.genArchipelago, "Archipelago",
            false);
    protected static ToggleTextButton generatorMountains = new ToggleTextButton(
            (Main.width / 2) + 256, 384, 256, 32, 6, 2, 7, 2, ClickEvents.genMountains, "Mountains",
            false);

    protected static TextButton exitToMenu = new TextButton(
            (Main.width / 2) - 128, Main.height / 2f, 256, 32, 6, 2, 7, 2,
            ClickEvents.exitToMenu, "Exit Game");

    protected static TextButton exitToMenuGameOver = new TextButton(
            (Main.width / 2) - 128, (Main.height / 2f) + 42, 256, 32, 6, 2, 7,
            2, ClickEvents.exitToMenu, "Exit Game");
    protected static TextButton loadLastSave = new TextButton(
            (Main.width / 2) - 128, (Main.height / 2f) + 82, 256, 32, 6, 2, 7,
            2, ClickEvents.loadLastSave, "Load Last Save");

    private static ArrayList<Button> buttons = new ArrayList<>();
    private static ArrayList<Button> menuButtons = new ArrayList<>();
    private static ArrayList<Button> pauseButtons = new ArrayList<>();
    private static ArrayList<Button> gameOverButtons = new ArrayList<>();

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

        }
    }

    public static void updateGameOver() {

    }

    public static void updateMenu() {

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

    protected static void startGame(int save, TextButton button){
        String word = "";
        if (!SaveUtil.doesSaveExist(save)) {
            setSaveName("", save);
            while (!word.equals("@")) {
                cursorTick++;
                word = Typing.currentMenuName(word, cursorTick);
                if (word.equals("@")) {
                    break;
                }
                if (word.equals("^")) {
                    setSaveName("Create a save", save);
                    button.setText("Create a save");
                    return;
                }
                setSaveName(word, save);
                button.setText(word);
                RenderUtil.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if (getSaveName(save).contains("|")) {
            setSaveName(getSaveName(save).replace("|", ""), save);
            button.setText(button.getText());
        }
        if (getSaveName(save).length() == 0) {
            setSaveName(emptyWorldName, save);
            button.setText(getSaveName(save));
        }
        Main.currentSave = save;
        Main.shouldQuit = false;
        Game.gameLoop(new PlayerCountry());
    }

    private static String getSaveName(int save){
        switch(save){
            case 1:
                return Main.saveName1;
            case 2:
                return Main.saveName2;
            case 3:
                return Main.saveName3;
            case 4:
                return Main.saveName4;
            case 5:
                return Main.saveName5;
            default:
                Log.severe("Trying to get name of invalid save '"+save+"'!");
                return "";
        }
    }

    private static void setSaveName(String name, int save){
        switch(save){
            case 1:
                Main.saveName1 = name;
                break;
            case 2:
                Main.saveName2 = name;
                break;
            case 3:
                Main.saveName3 = name;
                break;
            case 4:
                Main.saveName4 = name;
                break;
            case 5:
                Main.saveName5 = name;
                break;
            default:
                Log.severe("Trying to set name of invalid save '"+save+"'!");
        }
    }

}
