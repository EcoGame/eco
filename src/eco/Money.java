package eco;

public class Money {

    public static int tMoney;
    
    public static int tMoney(int tWheat, int wHunger, int fHunger, int wheatPrice) {
    
        tMoney = (int)(tMoney + ((tWheat - Main.aggDemand) * wheatPrice));
        return tMoney;
        
    }

}
