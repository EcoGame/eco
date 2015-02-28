package eco;

import java.util.Random;
import org.lwjgl.opengl.Display;

public class Main {

	public static Random random = new Random();
	public static final int fov = 90;
	public static final int height = 620;
	public static final int width = 854;
	public static volatile int year = 0;
	public static volatile int wheatPrice = 20;
	public static volatile int oldtWheat = 0;
	public static volatile int tAcres = 10000;
	public static volatile float fBirthRate = 0.03f;
	public static volatile float fDeathRate = 0.02f;
	public static volatile float wBirthRate = 0.008f;
	public static volatile float wDeathRate = 0.002f;
	public static int[][] unfilledpops = new int[10][10000];
	public static Pops[][] popArray = new Pops[10][10000];
	public static int[] unusedarray = new int [10];
	public static int popSize = 25;
	public static int aggDemand;
	public static boolean debug;
	public static boolean popDiags = false;
	public static boolean fullDebug = false;
	public static int GDP;
	public static int taxRevenue;
	public static boolean attemptSaveLoad = false;
	public static final boolean isInEclipse = false;
	public static final boolean willsCode = false;
	public static boolean paused = false;
	public static final int ticks = 2000;
	public static final String vn = "0.2";
	public static int framesPerTick = 1;
	public static int frame = 0;

	public static void main(String[] args) {
		System.out.println("Welcome to EcoLand!");
		gameLoop();
		System.exit(0);
		
	}
	
	public static void gameLoop(){
		init();
		while (year < ticks && !Display.isCloseRequested()){
			frame++;
			if (frame >= framesPerTick && !paused){
				year++;//One tick is 1 year
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
			tAcres = RenderLink.tick(year);
		}
		RenderLink.simDone();
		//****************//
		//     WARNING    //
		//                //
		//ALL STRINGS MUST//
		//BE DRAWN AFTER  //
		//THIS POINT      //
		//****************//
	}

	public static void tick(){
			Farmer.fPop = Farmer.fPop();
			Warrior.wPop = Warrior.wPop();
			// Warrior.wHunger(PopManager.wPopulation);
			// Wheat.tWheat(employedFarmers);
			//aggDemand = ((Farmer.fHunger * PopManager.fPopulation) + (Warrior.wHunger * PopManager.wPopulation));
			//wheatPrice = Market.wheatPrice(wheatPrice);
			//Money.tMoney(PopManager.uneatenwheat, wheatPrice);
			//GDP = Money.GDP(Wheat.tWheat, wheatPrice);
			//taxRevenue = Tax.taxRevenue(0);

			if(debug){
				OutputManager.printDebugInformation();
			}
			if(popDiags){
				OutputManager.popDiagnostics(0);
			}
			// PopManager.tPop = RenderLink.tryToUpdatePop();
			RenderLink.update();

	}
	
	public static void willTick(){
        
		PopManager.popController(tAcres, 0);
		int farmPacks = Wheat.farmPacks(tAcres);
		int unemployedFarmers = Wheat.unemployedFarmers(farmPacks, PopManager.fPopulation);
		int employedFarmers = Wheat.employedFarmers(PopManager.fPopulation, unemployedFarmers);
		Warrior.wHunger(PopManager.wPopulation);
		Wheat.tWheat(employedFarmers);
		aggDemand = ((Farmer.fHunger * PopManager.fPopulation) + (Warrior.wHunger * PopManager.wPopulation));
		wheatPrice = Market.wheatPrice(wheatPrice);
		Money.tMoney(PopManager.uneatenWheat, wheatPrice);
		GDP = Money.GDP(Wheat.tWheat, wheatPrice);
		taxRevenue = Tax.taxRevenue(0);

		if(debug){
			OutputManager.printDebugInformation();
		}
		if(popDiags){
			OutputManager.popDiagnostics(0);
		}
		// PopManager.tPop = RenderLink.tryToUpdatePop();
		RenderLink.update();

}


	public static void init() {

		RenderLink.init();
		if (attemptSaveLoad){
			Util.readSave();
		}
		//PopManager.initpops();

	}

}
