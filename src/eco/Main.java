package eco;

import java.util.Random;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Main {

	public static Random random = new Random();

	public static final int fov = 90;
	public static final int height = 620;
	public static final int width = 854;
	public static boolean attemptSaveLoad = false;
	public static final boolean isInEclipse = false;
	public static final boolean willsCode = false;
	public static boolean paused = false;
	public static boolean debug;
	public static boolean popDiags = false;
	public static boolean fullDebug = false;
	public static boolean skipFrame = false;

	public static float fBirthRate = 0.03f;
	public static volatile float fDeathRate = 0.02f;
	public static float fDefaultDeathRate = 0.02f;
	public static float wBirthRate = 0.008f;
	public static volatile float wDeathRate = 0.002f;
	public static float wDefaultDeathRate = 0.002f;

	public static volatile int year = 0;
	public static volatile int wheatPrice = 20;
	public static volatile int oldtWheat = 0;
	public static volatile int tAcres = 10000;


	public static int GDP;
	public static int taxRevenue;

	public static final int ticks = 2000;
	public static final String vn = "0.3";
	public static int framesPerTick = 4;
	public static int frame = 0;
	public static int unemployedFarmers = 0;
	public static int employedFarmers = 0;

	public static int aggDemand;
	public static int[][] unfilledpops = new int[10][1000000];
	public static Pops[][] popArray = new Pops[10][1000000];
	public static int[] unusedarray = new int [10];
	public static int popSize = 25;

	public static boolean shouldQuit = false;

	public static float desiredWarriorRatio = 0.15f;
	public static float desiredFarmerRatio = 0.85f;

	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		gameLoop();
		System.exit(0);

	}

	public static void gameLoop(){
		init();
		while (!shouldQuit){
			if (Display.isCloseRequested()){
				break;
			}
			frame++;
			if (frame >= framesPerTick && !paused && year < ticks){
				year++;
				if (!willsCode){
					tick();
				}
				else{
					willTick();
				}
				frame = 0;
			}
			UIManager.update();
			InputManager.update();
			tAcres = 0;
			if (!Main.paused){
				if (!skipFrame){
					Render.draw();
	       				OutputManager.newDebug();
				}
				else{
					skipFrame = false;
				}
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
      }
     			 else{
				Render.drawPaused();
				FPSCounter.tick();
				Display.update();
				Display.sync(60);
			}
		}

	}

	public static void tick(){

				Farmer.fPop -= World.displacedFarmers;
				Warrior.wPop -= World.displacedWarriors;
				Farmer.floatFPop -= World.displacedFarmers;
				Warrior.floatWPop -= World.displacedWarriors;
				if (World.displacedFarmers == 0){
					Farmer.fPop();
				}
				else{
					Farmer.oldFPop = Farmer.fPop; // Need to update this manually because it's done in fPop()
				}
				if (World.displacedWarriors == 0){
					Warrior.wPop();
				}
				else{
					Warrior.oldWPop = Warrior.wPop; // Need to update this manually because it's done in wPop()
				}
				wheatPrice = Market.wheatPrice(wheatPrice);
				taxRevenue = Tax.taxRevenue();
				World.displacedPeople += World.displacedFarmers + World.displacedWarriors;
				World.displacedFarmers = 0;
				World.displacedWarriors = 0;
        float newPopulation = Farmer.newPop() + Warrior.newPop();
				float newWarriors = newPopulation * desiredWarriorRatio;
				newPopulation -= newWarriors;
				Farmer.addPop(newPopulation);
				Warrior.addPop(newWarriors);
				Wheat.tWheat(Farmer.fPop);

        if(debug){
            OutputManager.printDebugInformation();
        }
        if(popDiags){
            OutputManager.popDiagnostics(0);
        }
        Graphs.draw(year, wheatPrice, getTotalPop(), taxRevenue);
        World.updateMap(Farmer.fPop, Warrior.wPop);
        World.freeAcres = World.calcAcres();

        if (Render.multithreading){
					ThreadManager.addJob(new MeshTask());
				}
				else{
					DisplayLists.mesh();
					skipFrame = true;
				}

	}

	public static void willTick(){

		PopManager.popController(World.freeAcres, 0);
		/*int farmPacks = Wheat.farmPacks(tAcres);
		int unemployedFarmers = Wheat.unemployedFarmers(farmPacks, PopManager.fPopulation);
		int employedFarmers = Wheat.employedFarmers(PopManager.fPopulation, unemployedFarmers);
		Warrior.wHunger(PopManager.wPopulation);
		Wheat.tWheat(employedFarmers);*/
		aggDemand = ((Farmer.fHunger * PopManager.fPopulation) + (Warrior.wHunger * PopManager.wPopulation));
		wheatPrice = Market.wheatPrice(wheatPrice);
		Money.tMoney(PopManager.uneatenWheat, wheatPrice);
		GDP = Money.GDP(Wheat.tWheat, wheatPrice);

		if(debug){
			OutputManager.printDebugInformation();
		}
		if(popDiags){
			OutputManager.popDiagnostics(0);
		}
    World.updateMap(PopManager.fPopulation, PopManager.wPopulation);
    World.freeAcres = World.calcAcres();
    if (Render.multithreading){
			ThreadManager.addJob(new MeshTask());
		}
		else{
			DisplayLists.mesh();
			skipFrame = true;
		}

}

public static float getTotalPopf(){
	return Warrior.floatWPop + Farmer.floatFPop;
}

public static int getTotalPop(){
	return Warrior.wPop + Farmer.fPop;
}

	public static void init() {

    Render.initDisplay();
    Render.init();
		if (willsCode){
			PopManager.initpops();
		}
    try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
      //  ThreadManager.addJob(new MeshTask());
		if (attemptSaveLoad){
			Util.readSave();
		}

		DisplayLists.mesh();

	}

}
