package eco;

public class Wheat{

    public static int tWheat;
    public static int wheatPrice;
    
    public static int tWheat(int fPop, int tAcres, int unemployedFarmers, int employedFarmers) {
        
        int farmPacks = tAcres/5;
        unemployedFarmers = fPop - farmPacks;
        
        if(unemployedFarmers < 0) {
            unemployedFarmers = 0;
        }
        
        employedFarmers = (fPop - unemployedFarmers);
        tWheat = employedFarmers * 40;
        return tWheat;
        
    }
	 
}
