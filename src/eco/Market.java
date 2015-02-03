package eco; 

public class Market {
    public static int wheatPrice(int pastPrice){
        int newPrice = 1;
        int chance = (Main.randInt(0,100));
        if(chance > 50){
            newPrice = pastPrice + Main.randInt(1,5);
        }
        else{
            newPrice = pastPrice - Main.randInt(1,5);
            if(newPrice < 1){
                newPrice = 1;
                Render.drawString("Price is one!", 30, 10);
            }
        }
        return newPrice;
        
    /*
    // Old Market
	public static double wheatPrice(int oldtWheat, int tWheat, int oldaggDemand, int aggDemand,double pastPrice){
        double newPrice = 1;
        
        if(oldtWheat != tWheat || oldaggDemand != aggDemand){
            double changeFactor;
            changeFactor = (tWheat)/(aggDemand);
            
            if(changeFactor ==  1){
                newPrice = pastPrice;
            }
            
            else if(changeFactor > 1){
                newPrice = (pastPrice*(1/changeFactor));
            }
            
            else if(changeFactor < 1){
                newPrice = (pastPrice + (changeFactor));
            }
            
            return newPrice;
        }
        newPrice = pastPrice;
        return newPrice;
    }
    */
        
    }

}
