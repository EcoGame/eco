package eco; 

public class Market {
	static int[] prices = new int[75];
	static int[] pop = new int[75];
	static int[] year = new int[75];
    public static int wheatPrice(int pastPrice){
        int newPrice = 1;
        int chance = (Util.randInt(0,100));
        if(chance > 50){
            newPrice = pastPrice + Util.randInt(1,5);
        }
        else{
            newPrice = pastPrice - Util.randInt(1,5);
            if(newPrice < 1){
                newPrice = 1;
                //World.messages.add(new Message("Price is one!", 100, 100, 300));
            }
        }
        //int num = 100000000;
        int y = 620;
        int x = 854;
        int shift = 100;
        //Render.awtFont = Render.awtFont.deriveFont(24f); // set font size
		//Render.font = new Render.TrueTypeFont(Render.awtFont, false);
        
        prices[74] = newPrice;
        //if(Main.year % 35 == 0){
        //	year[74] = Main.year;
        //}
        
        for(int i=0; i<prices.length; i++){
            	World.messages.add(new Message("\u25A0 .", (x - shift) + i, (y - shift) - (prices[i]), 1));
            	//if(year[i] != 0)
            	//	World.messages.add(new Message("" + year[i], (x - shift) + i, (y - shift), 1));
         }
         

    	for(int i=0; i<prices.length - 1; i++){
            prices[i] = prices[i + 1];
        }
        
        //for(int i=0; i<year.length - 1; i++){
          //  year[i] = year[i + 1];
        //}
        
        
        //World.messages.add(new Message("" + year, (x - shift), (y - shift - 40), 1));
        

        pop[74] = PopManager.wPopulation + PopManager.fPopulation;
        for(int i=0; i<pop.length; i++){
            	World.messages.add(new Message("\u25A0 .", (x - 80 - shift) + i, (y - shift) - (pop[i]), 1));
         }


    	for(int i=0; i<pop.length - 1; i++){
            pop[i] = pop[i + 1];
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
