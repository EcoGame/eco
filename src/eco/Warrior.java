package eco;


public class Warrior {

    public static int wPop = 5;
    public static float floatWPop = wPop;
    public static int wHunger;
    
	public static int wPop() {
        
		float rate = (1 + Main.wBirthRate - Main.wDeathRate);
		floatWPop = (floatWPop * rate);
		wPop = (int)(floatWPop);
		return wPop;

	}

    public static int wHunger(int p){

        int x = 0;
        x = p*11;
        return x;

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
