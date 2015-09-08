package eco.game;

import eco.render.FPSCounter;
import eco.render.Render;
import eco.ui.*;
import org.lwjgl.opengl.Display;

/**
 * A class that contains various methods to run the game
 *
 * @author phil
 */
public final class Game {

    public static void gameLoop(PlayerCountry playerCountry) {
        init(playerCountry);

        while (!Main.shouldQuit) {
            Command.update();
            if (Display.isCloseRequested()) {
                if (!Main.gameOver) {
                    SaveUtil.createSave(playerCountry);
                }
                Util.quit(0);
            }
            if (Main.gameOver) {
                gameOverLoop();
            } else if (!Main.paused) {
                gameUpdate(playerCountry);
                FPSCounter.tick();
                Display.update();
                Display.sync(60);
            } else {
               pausedLoop();
            }
        }

        cleanup(playerCountry);
        Menu.mainMenu();
    }

    private static void init(PlayerCountry playerCountry){
        PlayerCountry.playerCountry = playerCountry;
        Country.init();
        Main.shouldBeInMenu = false;
    }

    private static void cleanup(PlayerCountry playerCountry){
        if (!Main.gameOver) {
            SaveUtil.createSave(playerCountry);

        }
        PlayerCountry.playerCountry = null;
    }

    private static void gameUpdate(PlayerCountry playerCountry){
        Main.frame++;
        if (Main.frame >= Main.framesPerTick && !Main.paused
                && PlayerCountry.year < PlayerCountry.ticks) {
            PlayerCountry.year++;
            Country.globalTick();
            if (playerCountry.farmer.getPop() <= 0
                    && playerCountry.warrior.getPop() <= 0) {
                Main.gameOver = true;
            }
            Main.frame = 0;
        }

        UIManager.update();
        InputManager.gameInput.update();
        Typer.type("", InputManager.gameInput);

        if (!Main.skipFrame) {
            Render.draw();
        } else {
            Main.skipFrame = false;
        }
    }

    private static void pausedLoop(){
        Render.drawPaused();
        InputManager.pausedInput.update();
        Typer.type("", InputManager.pausedInput);

        UIManager.renderPause();
        UIManager.renderPause2();
        UIManager.updatePaused();
        FPSCounter.tick();
        Display.update();
        Display.sync(60);
    }

    private static void gameOverLoop(){
        Render.drawGameOver();
        FPSCounter.tick();

        InputManager.gameOverInput.update();
        Typer.type("", InputManager.gameOverInput);
        UIManager.updateGameOver();
        UIManager.renderGameOver();
        UIManager.renderGameOver2();

        Display.update();
        Display.sync(60);
    }

}
