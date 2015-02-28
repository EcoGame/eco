package eco;

import java.util.Random;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.input.Mouse;

//http://www222.pair.com/sjohn/blueroom/demog.htm

public class Main {

    public static Random random = new Random();
    public static final int fov = 90;
    public static final int height = 620;
    public static final int width = 854;
    public static volatile int year = 1;
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

	public static void main(String[] args) {
	System.out.println("Welcome to EcoLand!");
        init();
        tick();
        System.exit(0);
	}

    public static void tick(){

        for(int i = 1; i < 2000; i++) {
            year = i; //One tick is 1 year
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
            UIManager.update();
            oldtWheat = Wheat.tWheat;
            InputManager.update();
            PopManager.tPop = RenderLink.tryToUpdatePop();
            tAcres= RenderLink.tick(year);

        }
        RenderLink.simDone();
    }


    public static void init() {

        RenderLink.init();
        if (attemptSaveLoad){
        	Util.readSave();
        }
        PopManager.initPops();

	}

}
