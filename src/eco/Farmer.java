package eco;

public class Farmer {
    
    public static int fPop = 5;
    public static int fHunger;
    
    
    public static int fHunger(int p){
        int x = 0;
        x = p*11;
        return x;
    }
    
    public static int fPop(float y) { //Returns the number of farmers in
        int k= 0;
        float x= 0;
        x = y;
        
        x = ((x) * (1 + Main.wBirthRate - Main.wDeathRate));
        System.out.println("Farmer pops " + x);
        
        k = (int)(Math.ceil(x));
        
        return k;
        
    }
    
}