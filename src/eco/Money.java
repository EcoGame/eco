package eco;

public class Money {
    
    public static int tMoney;
    public static int GDP;
    
    public static int tMoney(int tWheat, int wheatPrice){
        
        tMoney = (int)(tMoney + ((tWheat - Main.aggDemand) * wheatPrice));
        return tMoney;
        
    }
    
    public static int GDP(int tWheat, int wheatPrice){
    	GDP = tWheat * wheatPrice;
    	return GDP;
    }
    
}
