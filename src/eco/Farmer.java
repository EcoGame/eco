package eco;

public class Farmer {

	public static int fPop = 5;
	public static int fHunger;

	public static float realFPop = fPop;
	
	public static int wheatPerFarmer = 15;
	
	public static int totalHarvest;

	public static int fHunger(int popGroupNumber){

		int fHunger = 0;
		fHunger = popGroupNumber*(Util.randInt(9, 10));
		return fHunger;
	}

	public static int fPop() {
		float rate = (1 + Main.fBirthRate - Main.fDeathRate);
		realFPop = (realFPop * rate);
		fPop = (int)(realFPop);
		return fPop;

	}
	
	public static void harvest(){
		totalHarvest = wheatPerFarmer * fPop;
	}

	public static int fPop(float screwYouWill){
		return -1;
	}

	public static int checkStarvation(int hungriness, int wheat ){
		int deadpeople;
		int q;
		int e;
		double k;
		double o;
		if(hungriness > wheat){
			o = wheat/11;
			k = hungriness/11;
			e = (int) Math.floor(k);// floors hungirness
			q = (int) Math.floor(o);// floors total wheat
			deadpeople = e-q;
			return deadpeople;
		}
		return 0;
	}

}
