package eco;

public class Farmer {

	public static int fPop = 5;
	public static int oldFPop = fPop;
	public static int fHunger;

	public static float floatFPop = fPop;

	public static int wheatPerFarmer = 15;

	public static int totalHarvest;

	public static int normalHunger = 30;

 /**/	public static int fHunger(int popGroupNumber){

	 /**/	int fHunger = 0;
	 /**/	fHunger = popGroupNumber*(Util.randInt(9, 10));
	 /**/	return fHunger;

	 /**/}

        public static int fHunger(){

		int fHunger = 0;
	   	fHunger = 30 + (Util.randInt(5, 10));
	 	return fHunger;

	 }

	public static int fPop() {

		float rate = (1 + Main.fBirthRate - Main.fDeathRate);
		floatFPop = (floatFPop * rate);
		oldFPop = fPop;
		fPop = (int)(floatFPop);

		return fPop;

	}

	public static void addPop(float newPop){
		floatFPop += newPop;
		fPop = (int) floatFPop;
	}

	public static int newPop() {
		return fPop - oldFPop;

	}

	public static void harvest(){
		totalHarvest = wheatPerFarmer * fPop;
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
