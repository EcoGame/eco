package eco;

public class OutputManager {

	public static void printDebugInformation(){
		int tPop = PopManager.fPopulation + PopManager.wPopulation;
		System.out.println("This Year: " + Main.year);
    	System.out.println("\n    Wheat Produced this year: " + Main.uneatenwheat);
        System.out.println("    Available Acres: " + Main.unusedacres);
    	System.out.println("    Price of wheat: " + Main.wheatPrice);
    	System.out.println("    Money that is sorta kinda in the Treasury: " + Money.tMoney);
        System.out.println("\n    Total number of Farmers: " + PopManager.fPopulation);
    	System.out.println("        Unemployed Farmers: " + Wheat.unemployedFarmers);
    	System.out.println("        Employed Farmers: " + Wheat.employedFarmers);
    	System.out.println("\n    Total Population: " + tPop);
        System.out.println("    Total number of Warriors: " + PopManager.wPopulation);
        System.out.println("    Unassigned people: " + Main.unusedpops);
    	System.out.println("\n\n");
	}

}
