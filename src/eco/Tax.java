package eco; 

public class Tax {
	public static int taxRevenue;
	
    public static int taxRevenue(int countrycode){
         for(int i=0; i < Main.popArray[countrycode].length; i++){
         	taxRevenue = taxRevenue + Main.popArray[countrycode][i].groupmoney;
         	System.out.println("Tax: " + taxRevenue);
        }
        return taxRevenue;
	}
    

}
