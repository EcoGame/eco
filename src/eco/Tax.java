package eco;

/**
 * This class does some random stuff for taxes
 * 
 * @author nate
 * 
 */

public class Tax {

	private static int taxRevenue = 500;
	@SuppressWarnings("unused")
	private static int taxRate = 20;

	public static int taxRevenue() {
		taxRevenue = taxRevenue - Util.randInt(-2, 6);
		return taxRevenue;
	}

}
