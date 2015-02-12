package eco;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import eco.Farmer;
import eco.Warrior;

public class Eco {
	
	public static void init() {
        Render.initDisplay();
        Render.init();
		World.updatePop(popManger.tPop);
        World.updateFarms(popManger.fPopulation);
        World.updateWarriors(popManger.wPopulation);
	}
	
	public static int tick(int year) {
	
        Main.ioUpdate();
         
        popManger.fPopulation -= World.updateFarms(popManger.fPopulation);
        popManger.wPopulation -= World.updateWarriors(popManger.wPopulation);
          
        Render.draw();
        Render.drawString(String.valueOf(year), 10, 10);
        Render.drawString(String.valueOf(Main.wheatPrice), 10, 50);
        Display.update();
        Display.sync(60);
        
        return World.calcAcres();
		
	}
	
	public static int tryToUpdatePop() {
			return World.updatePop(popManger.fPopulation + popManger.wPopulation);
	}
	
	public static void simDone() {
		
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !Display.isCloseRequested()) {
			Render.draw();
			Main.ioUpdate();
			Display.update();
			Display.sync(60);
		}
	}

}
