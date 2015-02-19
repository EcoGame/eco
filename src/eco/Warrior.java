package eco;

import java.util.Random;

public class Warrior {
    
    //  public static int wPop = 5;
    public static int wHunger;
    // public static float rate = wPop;
    
    
    public static int wPop(float y) {
        float x;
        int k;
        x = y;
        
        x = ((x) * (1 + Main.wBirthRate - Main.wDeathRate));
       // System.out.println("Warrior pops " + x);
        
        k = (int)(Math.ceil(x));
        
        return k;
        
        
    }
    
    public static int wHunger(int p){
        
        int x = 0;
        x = p*11;
        return x;
        
    }
    
}
