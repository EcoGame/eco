package eco; 

public class Market {
	static int[] prices = new int[75];
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
                //World.messages.add(new Message("Price is one!", 100, 100, 300));
            }
        }
        //int num = 100000000;
        int y = 620;
        int x = 854;
        int shift = 100;
        prices[74] = newPrice;
        for(int i=0; i<prices.length; i++){
            	World.messages.add(new Message(".", (x - shift) + i, (y - shift) - prices[i], 1));
         }

    	for(int i=0; i<prices.length - 1; i++){
            prices[i] = prices[i + 1];
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
