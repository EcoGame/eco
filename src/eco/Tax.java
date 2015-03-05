package eco; 

public class Tax {
	public static int taxRevenue = 500;
	public static int taxRate = 20;

    public static int taxRevenue(){
		taxRevenue = taxRevenue - Util.randInt(-2,6);
    	return taxRevenue;
	}

}
