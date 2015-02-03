package eco;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import eco.Farmer;
import eco.Warrior;

public class Eco {
	
	public static void init(){
        Render.initDisplay();
        Render.init();
		World.updatePop(Farmer.fPop() + Warrior.wPop());
        World.updateFarms(Farmer.fPop());
        World.updateWarriors(Warrior.wPop());
	}
	
	public static int tick(int year){
        Main.ioUpdate();
         
        Farmer.fPop -= World.updateFarms(Farmer.fPop);
        Warrior.wPop -= World.updateWarriors(Warrior.wPop);
          
        Render.draw();
        Render.drawString(String.valueOf(year), 10, 10);
        Render.drawString(String.valueOf(Main.wheatPrice), 20, 10)
        Display.update();
        Display.sync(60);
          
  		
        return World.calcAcres();
		
	}
	
	public static int tryToUpdatePop(){
    	return World.updatePop(Farmer.fPop + Warrior.wPop);
	}
	
	public static void simDone(){
		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) 
				   && !Display.isCloseRequested()){
		
			Render.draw();
			Main.ioUpdate();
			Display.update();
			Display.sync(60);
		}
	}

}
