package eco;

public class Money {
    
    public static int tMoney;
    
    public static int tMoney(int tWheat, int wheatPrice){
        
        tMoney = (int)(tMoney + ((tWheat - Main.aggDemand) * wheatPrice));
        return tMoney;
        
    }
    
}