package eco;

import java.util.Random;

public class Warrior {
    
    public static int wPop = 5;
    public static int wHunger;
    public static float rate = wPop;
    
    public static int wHunger(int wPop) {
        
        //wHunger = (Main.randInt(10,12));
        wHunger = 11;
        return wHunger;
        
    }
    
    public static int wPop() {
        
        if(Wheat.tWheat < (wHunger * wPop)) {
            
            wPop = Wheat.tWheat/wHunger;
            Wheat.tWheat = Wheat.tWheat - (wHunger * wPop);
            if(Wheat.tWheat < 0)
                Wheat.tWheat = 0;
            
        }
        
        rate = (rate * (1 + Main.wBirthRate - Main.wDeathRate));
		wPop = (int)(rate);
	    return wPop;
        
        
    }
	
}
