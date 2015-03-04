package eco;

public class OutputManager {

	public static void printDebugInformation() {
		int tPop = PopManager.fPopulation + PopManager.wPopulation;
		System.out.println("This Year: " + Main.year);
    	System.out.println("\n    Wheat Produced this year: " + PopManager.uneatenWheat);
        System.out.println("    Available Acres: " + PopManager.unusedAcres);
    	System.out.println("    Price of wheat: " + Main.wheatPrice);
    	System.out.println("    Money that is sorta kinda in the Treasury: " + Money.tMoney);
        System.out.println("\n    Total number of Farmers: " + PopManager.fPopulation);
    	System.out.println("        Unemployed Farmers: " + Main.unemployedFarmers);
    	System.out.println("        Employed Farmers: " + Main.employedFarmers);
    	System.out.println("\n    Total Population: " + tPop);
        System.out.println("    Total number of Warriors: " + PopManager.wPopulation);
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

    public static void debug() {

        Render.initOrtho();
        Render.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
        Render.drawString("Year: " + String.valueOf(Main.year), 10, 30);
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
            Render.drawString("Employed Farmers: " + String.valueOf(Main.employedFarmers), 10, 170);
            Render.drawString("Unemployed Farmers: " + String.valueOf(Main.unemployedFarmers), 10, 190);
            if (Main.employedFarmers + Main.unemployedFarmers != PopManager.fPopulation){
                Render.drawString("Warning! Population is mis-matched!", 100, 210);
            }
            Render.drawString("Total Population: " + String.valueOf(tPop), 10, 230);
            Render.drawString("Warriors: " + String.valueOf(PopManager.wPopulation), 10, 250);
            Render.drawString("Unassigned: " + String.valueOf(PopManager.unusedPops), 10, 270);
        }
    }

		public static void newDebug(){
			Render.initOrtho();
			Render.drawString("FPS: " + String.valueOf(FPSCounter.getFPS()), 10, 10);
			Render.drawString("Year: " + String.valueOf(Main.year), 10, 30);
			if (Render.multithreading) {
					Render.drawString("Multithreading On", Main.width - (Render.font.getWidth("Multithreading On") + 5), 30);
					Render.drawString("Using Bufferobjects", Main.width - (Render.font.getWidth("Using Bufferobjects") + 5), 10);
			}
			else{
					Render.drawString("Using Immediate Mode :(", Main.width - (Render.font.getWidth("Using Immediate Mode :(") + 5), 10);
			}
			if (Main.fullDebug){
				Render.drawString("Population: " + String.valueOf(Main.getTotalPop()), 10, 70);
				Render.drawString("Farmers: " + String.valueOf(Farmer.fPop), 10, 90);
				Render.drawString("Warriors: " + String.valueOf(Warrior.wPop), 10, 110);
				Render.drawString("Displaced: " + String.valueOf(World.displacedPeople), 10, 130);
				Render.drawString("	Farmers: " + String.valueOf(World.displacedFarmers), 10, 150);
				Render.drawString("	Warriors: " + String.valueOf(World.displacedWarriors), 10, 170);
			}
		}

}
