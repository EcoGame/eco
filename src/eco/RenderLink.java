package eco;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class RenderLink {

	public static int tick(int year) {
        
		
		if (!Main.paused){
            
			Render.draw();
	        Render.initOrtho();
	        Render.drawString("FPS: "+String.valueOf(FPSCounter.getFPS()), 10, 10);
	        Render.drawString("Year: "+String.valueOf(year), 10, 30);
	    	if (Render.multithreading){
	    		Render.drawString("Multithreading On", Main.width - (Render.font.getWidth("Multithreading On") + 5), 30);
	    		Render.drawString("Using Bufferobjects", Main.width - (Render.font.getWidth("Using Bufferobjects") + 5), 10);
	    	}
	    	else{
	    		Render.drawString("Using Immediate Mode :(", Main.width - (Render.font.getWidth("Using Immediate Mode :(") + 5), 10);
	    	}
	        if (Main.fullDebug){
	   		 	int tPop = PopManager.fPopulation + PopManager.wPopulation;
	        	Render.drawString("Wheat Produced: "+String.valueOf(PopManager.uneatenWheat), 10, 70);
	            Render.drawString("Unused Acres: "+String.valueOf(PopManager.unusedAcres), 10, 90);
	            Render.drawString("Wheat Price: "+String.valueOf(Main.wheatPrice), 10, 110);
	            Render.drawString("Money in Treasury: "+String.valueOf(Money.tMoney), 10, 130);
	            Render.drawString("Farmers: "+String.valueOf(Farmer.fPop), 10, 150);
	            Render.drawString("Employed Farmers: "+String.valueOf(Wheat.employedFarmers), 10, 170);
	            Render.drawString("Unemployed Farmers: "+String.valueOf(Wheat.unemployedFarmers), 10, 190);
	            if (Wheat.employedFarmers + Wheat.unemployedFarmers != PopManager.fPopulation){
	            	Render.drawString("Warning! Population is mis-matched!", 100, 210);
	            }
	            Render.drawString("Total Population: "+String.valueOf(tPop), 10, 230);
	            Render.drawString("Warriors: "+String.valueOf(PopManager.wPopulation), 10, 250);
	            Render.drawString("Unassigned: "+String.valueOf(PopManager.unusedPops), 10, 270);
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
        return 0;

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
