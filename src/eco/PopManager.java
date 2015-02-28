package eco;

public class PopManager {

	public static int wPopulation = 0;
	public static int fPopulation = 0;
	public static int tPop = 0;
	public static int unusedAcres = 0;
	public static int unusedPops = 0;
	public static int uneatenWheat = 0;

	public static void initpops() {
		for (int w = 0; w < 10; w++)
        for(int i =0; i < Main.popArray[w].length; i++){
            Main.popArray[w][i] = new Pops();
        }

		Main.popArray[0][0].people = 5;
		Main.popArray[0][1].people = 5;
		Main.popArray[0][2].people = 5;
		Main.popArray[0][0].isFarmer = true;
		Main.popArray[0][1].isFarmer = false;
		Main.popArray[0][0].acres = 5;
		Main.popArray[0][1].acres = 5;
		Main.popArray[0][2].isWarrior = true;
		Main.popArray[0][0].isUsed = true;
		Main.popArray[0][1].isUsed = true;
		Main.popArray[0][2].isUsed = true;

	}

	public static void popController(int acres, int countrycode) {

		int x = 0;
		int y= 0;
        int r = 0;
        int q = 0;
        int w = 0;
        int k = 0;
        int m = 0;
        int e = 0;
        int newtPop = 0;
        int oldtPop = 0;
				 unusedAcres = 0;
				unusedPops = 0;
				uneatenWheat = 0;
        PopMethods.scanPops(countrycode);
        unusedAcres = acres;

        PopMethods.unusedAcresFarmersAssignment(countrycode);

        wPopulation = PopMethods.warriortotal(countrycode);
        fPopulation = PopMethods.farmertotal(countrycode);

        q = Warrior.wPop();
        r = Farmer.fPop();

        oldtPop = wPopulation + fPopulation;
        newtPop = q +r;
        PopManager.unusedPops = newtPop- oldtPop;
        PopMethods.popAssigner(countrycode);

        PopMethods.popBuilder(1, countrycode);
        wPopulation = q;
        fPopulation = r;
        tPop = wPopulation + fPopulation;

        PopMethods.farmerconsumecycle(countrycode);
        PopMethods.consumecyclewarrior(countrycode);

	}
}
