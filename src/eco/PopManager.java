package eco;

public class PopManager {

	public static int wPopulation = 0;
	public static int fPopulation = 0;
	public static int tPop = 0;
	public static int unusedAcres = 0;
	public static int unusedPops = 0;
	public static int uneatenWheat = 0;

	public static void initPops() {
        
		for (int w = 0; w < 10; w++)
        for(int i =0; i < Main.popArray[w].length; i++) {
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

	public static void popController(int acres, int countryCode) {

        int r = 0;
        int q = 0;
        int newTPop = 0;
        int oldTPop = 0;
        unusedAcres = 0;
        unusedPops = 0;
        uneatenWheat = 0;
        PopMethods.scanPops(countryCode);
        unusedAcres = acres;

        PopMethods.unusedAcresFarmersAssignment(countryCode);

        wPopulation = PopMethods.warriortotal(countryCode);
        fPopulation = PopMethods.farmertotal(countryCode);

        q = Warrior.wPop(wPopulation);
        r = Farmer.fPop(fPopulation);

        oldtPop = wPopulation + fPopulation;
        newtPop = q +r;
        PopManager.unusedPops = newTPop- oldTPop;
        PopMethods.popAssigner(countryCode);

        PopMethods.popBuilder(1, countryCode);
        wPopulation = q;
        fPopulation = r;
        tPop = wPopulation + fPopulation;

        PopMethods.farmerconsumecycle(countryCode);
        PopMethods.consumecyclewarrior(countryCode);

	}
}
