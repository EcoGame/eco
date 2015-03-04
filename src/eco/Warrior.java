package eco;


public class Warrior {

    public static int wPop = 5;
    public static int oldWPop = wPop;
    public static float floatWPop = wPop;
    public static int wHunger;

	public static int wPop() {

		float rate = (1 + Main.wBirthRate - Main.wDeathRate);
		floatWPop = (floatWPop * rate);
		oldWPop = wPop;
    wPop = (int)(floatWPop);
		return wPop;

	}

  public static int newPop(){
    return wPop - oldWPop;
  }

  public static void addPop(float newPop){
		floatWPop += newPop;
    wPop = (int) floatWPop;
	}

    public static int wHunger(int wPop){

        int wHunger = 0;
        wHunger = wPop * (Util.randInt(9, 11));
        return wHunger;

    }

    public static int checkStarvation(int hunger, int wheat ){

        int deadpeople;
        int q;
        int e;
        double k;
        double o;
        if(hunger > wheat){
            o = wheat/11;
            k = hunger/11;
            e = (int) Math.floor(k);// floors hungirness
            q = (int) Math.floor(o);// floors total wheat
            deadpeople = e-q;
            return deadpeople;
        }
        return 0;
    }

}
