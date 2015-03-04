package eco;

public class PopManager {

	public static int wPopulation = 0;
	public static int fPopulation = 0;
	public static int tPop = 0;
	public static int unusedAcres = 0;
	public static int unusedPops = 0;
	public static int uneatenWheat = 0;
	public static double warriorRatio = 0;
	public static double farmerRatio = 0;
	public static int currentTPop = 0;


	public static void initpops() {
		for (int w = 0; w < 10; w++)
        for(int i =0; i < Main.popArray[w].length; i++){
            Main.popArray[w][i] = new Pops();
        }
		//System.out.println("Kill yourself");
		Main.popArray[0][0].people = 5;
	//	Main.popArray[0][1].people = 5;
		Main.popArray[0][1].people = 5;
		Main.popArray[0][0].isFarmer = true;
	//	Main.popArray[0][1].isFarmer = true;
		Main.popArray[0][0].acres = 5;
//		Main.popArray[0][1].acres = 5;
		Main.popArray[0][1].isWarrior = true;
		Main.popArray[0][0].isUsed = true;
		Main.popArray[0][1].isUsed = true;
		//Main.popArray[0][].isUsed = true;

	}

	public static void popController(int acres, int countrycode) {
        Main.unemployedFarmers =0;
        Main.employedFarmers = 0;

        int r = 0;
        int q = 0;

			 warriorRatio = 0;
			 farmerRatio = 0;
        int newtPop = 0;
         currentTPop = 0;
				 unusedAcres = 0;
				unusedPops = 0;
				uneatenWheat = 0;
				Main.unemployedFarmers =0;
				Main.employedFarmers = 0;
        PopMethods.scanPops(countrycode);
        unusedAcres = acres;

        PopMethods.unusedAcresFarmersAssignment(countrycode);

        wPopulation = PopMethods.warriortotal(countrycode);
        fPopulation = PopMethods.farmertotal(countrycode);


        q = Warrior.wPop();
        r = Farmer.fPop();

        currentTPop = wPopulation + fPopulation;
				warriorRatio = PopMethods.currentratiowarriors(countrycode);
				farmerRatio = PopMethods.currentratiofarmers(countrycode);
				//System.out.println("Popmanager old +" +currentTPop);
        newtPop = q +r;
				//System.out.println("Popmanager new +" +newtPop);
        PopManager.unusedPops = newtPop- currentTPop;
        PopMethods.popAssigner( countrycode);

        PopMethods.popBuilder(countrycode);
        wPopulation = q;
        fPopulation = r;
        tPop = wPopulation + fPopulation;
			//	System.out.println(" stats" + Main.popArray[0][1].isFarmer + Main.popArray[0][1].isFarmer);
        PopMethods.farmerconsumecycle(countrycode);
        PopMethods.consumecyclewarrior(countrycode);
				warriorRatio = PopMethods.currentratiowarriors(countrycode);
				farmerRatio = PopMethods.currentratiofarmers(countrycode);
			//	System.out.println("Warrior R"+warriorRatio);
			//	System.out.println("farmer R"+farmerRatio);

				//wPopulation = PopMethods.warriortotal(countrycode);
				//fPopulation = PopMethods.farmertotal(countrycode);

	}
}
