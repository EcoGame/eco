package eco;

public class OutputManager {

	public static void printDebugInformation() {

		int tPop = Farmer.fPop + Warrior.wPop;
    	System.out.println("\n    Wheat Produced this year: " + PopManager.uneatenWheat);
        System.out.println("    Available Acres: " + PopManager.unusedAcres);
    	System.out.println("    Price of wheat: " + Main.wheatPrice);
    	System.out.println("    Money that is sorta kinda in the Treasury: " + Money.tMoney);
        System.out.println("\n    Total number of Farmers: " + Farmer.fPop);
    	System.out.println("        Unemployed Farmers: " + Main.unemployedFarmers);
    	System.out.println("        Employed Farmers: " + Main.employedFarmers);
    	System.out.println("\n    Total Population: " + tPop);
        System.out.println("    Total number of Warriors: " + Warrior.wPop);
        System.out.println("    Unassigned people: " + PopManager.unusedPops);
    	System.out.println("\n\n");
	}

	public static void popDiagnostics(int countrycode) {

		int x =0;
		while(Main.popArray[countrycode][x].isUsed == true) {
			System.out.println("Stats for PopArray: " + x);
            System.out.println("CountryCode: " + countrycode);
			System.out.println("People: " + Main.popArray[countrycode][x].people);
			System.out.println("Acres: " + Main.popArray[countrycode][x].acres);
			System.out.println("Famer: " + Main.popArray[countrycode][x].isFarmer);
			System.out.println("Warrior: " + Main.popArray[countrycode][x].isWarrior);
			System.out.println("\n\n");
			x++;
		}
		System.out.println("Diagnostics completed on used pop Arrays");
	}

    public static void newDebug(){

        int tPop = Farmer.fPop + Warrior.wPop;
        Render.initOrtho();
        Render.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
        Render.drawString("Year: " + String.valueOf(Main.year), 10, 30);
        if (Render.multithreading) {
                Render.drawString("Multithreading On", Main.width - (Render.font.getWidth("Multithreading On") + 5), 30);
                Render.drawString("Using Bufferobjects", Main.width - (Render.font.getWidth("Using Bufferobjects") + 5), 10);
        }
        else{
                Render.drawString("Using DisplayLists", Main.width - (Render.font.getWidth("Using DisplayLists") + 5), 10);
        }
        if (Main.fullDebug){
            Render.drawString("Population: " + String.valueOf(Main.getTotalPop()), 10, 70);
            Render.drawString("Farmers: " + String.valueOf(Farmer.fPop), 10, 90);
            Render.drawString("Warriors: " + String.valueOf(Warrior.wPop), 10, 110);
            Render.drawString("Total Displaced: " + String.valueOf(World.displacedPeople), 10, 130);
            Render.drawString("Displaced Farmers: " + String.valueOf(World.displacedFarmers), 40, 150);
            Render.drawString("Displaced Warriors: " + String.valueOf(World.displacedWarriors), 40, 170);
						Render.drawString("Wheat Stored: " + String.valueOf(Wheat.tWheat)+" ("+Util.getWheatRateForDisplay()+")", 10, 190);
						Render.drawString("Farmer Wheat Quota: " + String.valueOf(Farmer.totalHunger), 10, 210);
						Render.drawString("Warrior Wheat Quota: " + String.valueOf(Warrior.totalHunger), 10, 230);

        }
    }

}
