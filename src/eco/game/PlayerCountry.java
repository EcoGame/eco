package eco.game;
import eco.neural.GeneticMaster;
import org.lwjgl.opengl.Display;

/**
 *
 * Simulates the player country
 * @author phil, nate, will, connor
 *
 */

public class PlayerCountry extends Country{

    public static PlayerCountry playerCountry;

    public PlayerCountry(){
        super();
        eco.neural.Main.init();
        World.init(generatorToUse);
        start = Util.getRandomTile();
        claimInitialLand(start.getX(), start.getY());
        Main.paused = false;
        wheat = new Wheat();
        farmer = new Farmer();
        warrior = new Warrior();
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
    }

    /* Game tick */
    public void updateTick() {
        // ===========//
        // Map Update //
        // ===========//
       // World.updateMap(this, farmer.getfPop(), warrior.getwPop());
        //World.freeAcres = World.calcAcres();
       // farmer.addPop(-World.displacedFarmers);
       // warrior.addPop(-World.displacedWarriors);
       // World.displacedPeople += World.displacedFarmers
       //         + World.displacedWarriors;
       // World.displacedFarmers = 0;
       // World.displacedWarriors = 0;

        // ===============//
        // Render Updates //
        // ===============//
        if (Render.multithreading) {
            ThreadManager.addJob(new MeshTask());
        } else {
            DisplayLists.mesh();
            Main.skipFrame = true;
        }

        // =============//
        // Multicountry //
        // =============//
        /*for (NPCCountry NPCCountry : new ArrayList<NPCCountry>(countries)) {
            if (!NPCCountry.dead){
                NPCCountry.tick();
            }
        }*/

        // =========//
        // Autosave //
        // =========//
        if (year % Main.autoSaveInterval == 0){
            ThreadManager.addJob(new SaveTask());
        }

        // ===============//
        // Neural Network //
        // ===============//
       // for (int i = 0; i < countries.size(); i++) {
          //  NeuralManager.neuralTick(i);
        //}
    }


    /* Normal game loop */
    /* returns if the display was closed */
    public static void gameLoop(PlayerCountry playerCountry) {
        PlayerCountry.playerCountry = playerCountry;
        Main.shouldBeInMenu = true;
        while (!Main.shouldQuit) {
            if (Display.isCloseRequested()) {
                if (!Main.gameOver){
                    SaveUtil.createSave(playerCountry);
                }
                System.exit(0);
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
                    if (playerCountry.farmer.getfPop() <= 0
                            && playerCountry.warrior.getwPop() <= 0) {
                        Main.gameOver = true;
                    }
                    Main.frame = 0;
                }
                UIManager.update();
                InputManager.update();
                if (!Main.skipFrame) {
                    Render.draw();
                    OutputManager.newDebug();
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
        GeneticMaster.geneMaster();
        if (!Main.gameOver){
            SaveUtil.createSave(playerCountry);

        }
        Menu.mainMenu();
    }

}
