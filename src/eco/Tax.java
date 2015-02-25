package eco; 

public class Tax {
	public static int taxRevenue;
	public static int[] tax = new int[75];
	public static int taxRate = 20;
	
    public static int taxRevenue(int countrycode){
        for(int i=0; i < Main.popArray[countrycode].length; i++){
         	taxRevenue = taxRevenue + Main.popArray[countrycode][i].groupmoney * taxRate;
         	int x = Main.popArray[countrycode][i].groupmoney;
         	Main.popArray[countrycode][i].groupmoney = (x - (x * taxRate));
        }
        int y = 620;
        int x = 854;
        int shift = 100;
        
    	if(Main.debug){
    		System.out.println("Tax: " + taxRevenue);
    	}
        tax[74] = taxRevenue;
        
        
    	for(int i=0; i<tax.length; i++){
    		World.messages.add(new Message("\u25A0 .", (x - 160 - shift) + i, (y - shift) - (tax[i]), 1));
    	}


    	for(int i=0; i<tax.length - 1; i++){
        	tax[i] = tax[i + 1];
    	}
    	
    	World.messages.add(new Message("Tax", (x - 160 - shift), y - 60, 1));
    	return taxRevenue;
	}	
    
}

