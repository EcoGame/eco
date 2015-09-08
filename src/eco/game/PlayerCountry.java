package eco.game;

import eco.render.MeshTask;
import eco.render.RenderUtil;
import eco.ui.MenuBar;

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
        color = RenderUtil.getColor(24, 218, 234);
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


    public static void testGame() {
        Main.currentSave = -1;
        Main.shouldQuit = false;
        Game.gameLoop(new PlayerCountry());
    }

}
