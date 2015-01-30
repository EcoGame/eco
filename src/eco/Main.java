package eco;

import java.util.Random;

import eco.Eco;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

//import phillip.renderengine.Render;
//http://www222.pair.com/sjohn/blueroom/demog.htm
// To run in Terminal: javac eco/Main.java && javac eco/Money.java && javac eco/Warrior.java && javac eco/Farmer.java && javac eco/Wheat.java

public class Main {
    
	public static Random random = new Random();
    public static final int fov = 90;
	public static final int height = 620;
	public static final int width = 854;
	public static int year = 1;
	public static int wheatPrice = 20;
    public static int oldtWheat = 0;
    public static int aggDemand;
    public static int oldaggDemand;
	public static int acres = 1000;
	public static int unemployedFarmers = 0;
    public static int employedFarmers = 0;
    public static float fBirthRate = 0.03f;
    public static float fDeathRate = 0.02f;
    public static float wBirthRate = 0.008f;
    public static float wDeathRate = 0.002f;
    
	public static void main(String[] args) {
        
		System.out.println("Welcome to EcoLand!");
        init();
        tick();
        
	}
    
    public static void tick(){
        
    	for(int i = 1; i < 2000; i++){
            year = i; //One tick is 1 year
        	int wPop = Warrior.wPop();
        	int fPop = Farmer.fPop();
            int tPop = wPop + fPop;
            
            Warrior.wHunger(wPop);
        	Wheat.tWheat(Farmer.fPop, acres, unemployedFarmers, employedFarmers);
            aggDemand = ((Farmer.fHunger * Farmer.fPop) + (Warrior.wHunger * Warrior.wPop));
        	wheatPrice = Market.wheatPrice(wheatPrice);
            int tMoney = Money.tMoney(Wheat.tWheat, Warrior.wHunger, Farmer.fHunger, wheatPrice);
        	
        	System.out.println("Year: " + year);
        	System.out.println("Wheat Produced this year: " + Wheat.tWheat);
            System.out.println("Price of wheat: " + wheatPrice);
        	System.out.println("Total number of Warriors: " + wPop);
            System.out.println("Total number of Farmers: " + fPop);
            System.out.println("        Employed Farmers: " + employedFarmers);
            System.out.println("        Unemployed Farmers: " + unemployedFarmers);
            System.out.println("Total Population: " + tPop);
            //System.out.println("Agg Demand: " + aggDemand);
            System.out.println("Money in the Treasury: " + tMoney);
        	System.out.println("\n");
            oldaggDemand = aggDemand;
            oldtWheat = Wheat.tWheat;
            
            //Render.drawString("yourmessage", 10, 10);
            tPop = Eco.tryToUpdatePop();
            acres= Eco.tick(year);
            
        }
    	Eco.simDone();
    }
	
    public static void init(){ //Initializes the simulation
    	Money.tMoney = 500;
		Wheat.tWheat = 10;
        Warrior.wPop();
        Warrior.wHunger(Warrior.wPop);
        Farmer.fPop();
        Farmer.fHunger(Farmer.fPop);
        
        Eco.init();
	
	}
    
	public static int randInt(int max){ //Returns a random number below max.
	
        return random.nextInt(max);
		
	}
    
    public static int randInt(int min, int max){ //Returns a random number between min and max.
        
        return min + random.nextInt((max + 1)- min);
		
	}
    
    public static void ioUpdate(){
        
		if (Keyboard.isKeyDown(Keyboard.KEY_R)){
			Render.rot -= 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)){
			Render.rot += 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)){
			Render.z += 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)){
			Render.x -= 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)){
			Render.z -= 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)){
			Render.x += 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Z)){
			Render.y += 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)){
			Render.y -= 0.1f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_O)){
			World.messages.add(new Message("dank messages", 10, 10, 300));
		}
		
	}


}
