package eco.game;

import org.lwjgl.opengl.Display;

/**
 * Simulates the player country
 *
 * @author phil, nate, will, connor
 */

public class PlayerCountry extends Country {

    public static PlayerCountry playerCountry;

    public PlayerCountry() {
        super();
        eco.neural.Main.init();
        World.init(generatorToUse);
        claimInitialLand(Util.getRandomUnclaimedTile());
        Main.paused = false;
        wheat = new Wheat();
        economy = new Economy();
        stone = new Stone();
        land = new Land();
        wood = new Wood();
        aggression = new AggressionScore();
        year = 0;
        farmerDeathRatio = 0.75f;
        warriorDeathRatio = 0.75f;
        desiredWarriorRatio = 0.15f;
        desiredFarmerRatio = 1f - desiredWarriorRatio;
        Main.gameOver = false;
        MenuBar.reset();
        if (SaveUtil.doesSaveExist(Main.currentSave)) {
            SaveUtil.readSave();
        }
        color = RenderUtil.getColor(24,218,234);
    }

    /* Game tick */
    public void updateTick() {
        // ===============//
        // Render Updates //
        // ===============//
        ThreadManager.addJob(new MeshTask());

        // =========//
        // Autosave //
        // =========//
        if (year % Main.autoSaveInterval == 0) {
            ThreadManager.addJob(new SaveTask());
        }
    }


    /* Normal game loop */
    public static void gameLoop(PlayerCountry playerCountry) {
        PlayerCountry.playerCountry = playerCountry;
        Country.init();
        Main.shouldBeInMenu = true;
        while (!Main.shouldQuit) {
            Command.update();
            if (Display.isCloseRequested()) {
                if (!Main.gameOver) {
                    SaveUtil.createSave(playerCountry);
                }
                Util.quit(0);
            }
            if (Main.gameOver) {
                Render.drawGameOver();
                FPSCounter.tick();

                InputManager.updateGameOver();
                UIManager.updateGameOver();
                UIManager.renderGameOver();
                UIManager.renderGameOver2();

                Display.update();
                Display.sync(60);
            } else if (!Main.paused) {
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
                InputManager.update();
                if (!Main.skipFrame) {
                    Render.draw();
                } else {
                    Main.skipFrame = false;
                }
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
        //GeneticMaster.geneMaster();
        if (!Main.gameOver) {
            SaveUtil.createSave(playerCountry);

        }
        PlayerCountry.playerCountry = null;
        Menu.mainMenu();
    }

    public static void testGame() {
        Main.currentSave = -1;
        Main.shouldQuit = false;
        gameLoop(new PlayerCountry());
    }

}
