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
				Render.draw();
				Render.initOrtho();
		    Render.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
		    Render.drawString("Year: " + String.valueOf(year), 10, 30);
    		if (Render.multithreading) {
						Render.drawString("Multithreading On", Main.width - (Render.font.getWidth("Multithreading On") + 5), 30);
		    		Render.drawString("Using Bufferobjects", Main.width - (Render.font.getWidth("Using Bufferobjects") + 5), 10);
		    }
		   	else{
		    		Render.drawString("Using Immediate Mode :(", Main.width - (Render.font.getWidth("Using Immediate Mode :(") + 5), 10);
		    }
		    if (Main.fullDebug){
					int tPop = PopManager.fPopulation + PopManager.wPopulation;
		     	Render.drawString("Wheat Produced: " + String.valueOf(PopManager.uneatenWheat), 10, 70);
         	Render.drawString("Unused Acres: " + String.valueOf(PopManager.unusedAcres), 10, 90);
		      Render.drawString("Wheat Price: " + String.valueOf(Main.wheatPrice), 10, 110);
	        Render.drawString("Money in Treasury: " + String.valueOf(Money.tMoney), 10, 130);
	        Render.drawString("Farmers: " + String.valueOf(Farmer.fPop), 10, 150);
	        Render.drawString("Employed Farmers: " + String.valueOf(Wheat.employedFarmers), 10, 170);
	        Render.drawString("Unemployed Farmers: " + String.valueOf(Wheat.unemployedFarmers), 10, 190);
		      if (Wheat.employedFarmers + Wheat.unemployedFarmers != PopManager.fPopulation){
		     		Render.drawString("Warning! Population is mis-matched!", 100, 210);
		      }
		      Render.drawString("Total Population: " + String.valueOf(tPop), 10, 230);
		      Render.drawString("Warriors: " + String.valueOf(PopManager.wPopulation), 10, 250);
		      Render.drawString("Unassigned: " + String.valueOf(PopManager.unusedPops), 10, 270);
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
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !Display.isCloseRequested()) {
			Render.draw();
			InputManager.update();
			Display.update();
			Display.sync(60);
		}
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
        World.updateMap(Farmer.fPop, Warrior.wPop);
        World.freeAcres = World.calcAcres();
        ThreadManager.addJob(new MeshTask());

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
        World.updateMap(Farmer.fPop, Warrior.wPop);
        World.freeAcres = World.calcAcres();
        ThreadManager.addJob(new MeshTask());

}


	public static void init() {

        Render.initDisplay();
        Render.init();
        try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
        ThreadManager.addJob(new MeshTask());
		if (attemptSaveLoad){
			Util.readSave();
		}
		//PopManager.initpops();

	}

}
