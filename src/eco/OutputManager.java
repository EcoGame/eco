package eco;

public class OutputManager {

	public static void printDebugInformation() {
		int tPop = PopManager.fPopulation + PopManager.wPopulation;
		System.out.println("This Year: " + Main.year);
    	System.out.println("\n    Wheat Produced this year: " + PopManager.uneatenWheat);
        System.out.println("    Available Acres: " + PopManager.unusedacres);
    	System.out.println("    Price of wheat: " + Main.wheatPrice);
    	System.out.println("    Money that is sorta kinda in the Treasury: " + Money.tMoney);
        System.out.println("\n    Total number of Farmers: " + PopManager.fPopulation);
    	System.out.println("        Unemployed Farmers: " + Wheat.unemployedFarmers);
    	System.out.println("        Employed Farmers: " + Wheat.employedFarmers);
    	System.out.println("\n    Total Population: " + tPop);
        System.out.println("    Total number of Warriors: " + PopManager.wPopulation);
        System.out.println("    Unassigned people: " + PopManager.unusedpops);
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

}
