package eco;

public class popManger {
    
	public static int wPopulation = 0;
	public static int fPopulation = 0;
	public static int tPop = 0;

	public static void initpops() {
        for(int i =0; i < Main.popArray.length; i++){
            Main.popArray[i] = new Pops();
        }
        
		Main.popArray[0].people = 5;
		Main.popArray[1].people = 5;
		Main.popArray[0].isFarmer = true;
		Main.popArray[0].acres = 5;
		Main.popArray[1].isWarrior = true;
		Main.popArray[0].isUsed = true;
		Main.popArray[1].isUsed = true;
        
	}

	public static void popController() {
        
		int x = 0;
		int y= 0;
        int r = 0;
        int q = 0;
        int w = 0;
        int k = 0;
        int m = 0;
        int e = 0;
        int newtPop = 0;
        int oldtPop;
        
        popMethods.scanPops();
		
		System.out.println(popMethods.usedacres());
        Main.unusedacres = Main.tAcres - popMethods.usedacres();
	
        popMethods.unusedAcresFarmersAssignment();

        wPopulation = popMethods.warriortotal();
        fPopulation = popMethods.farmertotal();

        q = Warrior.wPop(wPopulation);
        r = Farmer.fPop(fPopulation);

        oldtPop = wPopulation + fPopulation;
        newtPop = q +r;
        Main.unusedpops = newtPop- oldtPop;
        System.out.println(Main.unusedpops);
        popMethods.popAssigner();

        popMethods.popBuilder(1);
        wPopulation = q;
        fPopulation = r;
        tPop = wPopulation + fPopulation;

        popMethods.farmerconsumecycle();
        popMethods.consumecyclewarrior();

	}
}
