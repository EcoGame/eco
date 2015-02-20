package eco;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.SharedDrawable;

public class RenderLink {

	public static void init() {
        Render.initDisplay();
        Render.init();
		World.updatePop(PopManager.tPop);
        World.updateFarms(PopManager.fPopulation);
        World.updateWarriors(PopManager.wPopulation);
        try {
			ThreadManager.drawable = new SharedDrawable(Display.getDrawable());
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static int tick(int year) {

        PopManager.fPopulation -= World.updateFarms(PopManager.fPopulation);
        PopManager.wPopulation -= World.updateWarriors(PopManager.wPopulation);

        Render.draw();
        Render.drawString("Year: "+String.valueOf(year), 10, 10);
    	if (Render.multithreading){
    		Render.drawString("Multithreading On", Main.width - (Render.font.getWidth("Multithreading On") + 5), 30);
    		Render.drawString("Using Bufferobjects", Main.width - (Render.font.getWidth("Using Bufferobjects") + 5), 10);
    	}
    	else{
    		Render.drawString("Using Immediate Mode :(", Main.width - (Render.font.getWidth("Using Immediate Mode :(") + 5), 10);
    	}
        if (Main.fullDebug){
   		 	int tPop = PopManager.fPopulation + PopManager.wPopulation;
        	Render.drawString("Wheat Produced: "+String.valueOf(Main.uneatenwheat), 10, 30);
            Render.drawString("Unused Acres: "+String.valueOf(Main.unusedacres), 10, 50);
            Render.drawString("Wheat Price: "+String.valueOf(Main.wheatPrice), 10, 70);
            Render.drawString("Money in Treasury: "+String.valueOf(Money.tMoney), 10, 90);
            Render.drawString("Farmers: "+String.valueOf(PopManager.fPopulation), 10, 110);
            Render.drawString("Employed Farmers: "+String.valueOf(Wheat.employedFarmers), 10, 130);
            Render.drawString("Unemployed Farmers: "+String.valueOf(Wheat.unemployedFarmers), 10, 150);
            if (Wheat.employedFarmers + Wheat.unemployedFarmers != PopManager.fPopulation){
            	Render.drawString("Warning! Population is mis-matched!", 100, 110);
            }
            Render.drawString("Total Population: "+String.valueOf(tPop), 10, 170);
            Render.drawString("Warriors: "+String.valueOf(PopManager.wPopulation), 10, 190);
            Render.drawString("Unassigned: "+String.valueOf(Main.unusedpops), 10, 210);
        }
        Display.update();
        Display.sync(60);

        ThreadManager.addJob(new MeshTask());
        
        return World.calcAcres();

	}

	public static int tryToUpdatePop() {
			return World.updatePop(PopManager.fPopulation + PopManager.wPopulation);
	}

	public static void simDone() {

		while (!Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !Display.isCloseRequested()) {
			Render.draw();
			InputManager.update();
			Display.update();
			Display.sync(60);
		}
	}

}
