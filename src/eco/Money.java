package eco;

/**
 * This class does some random economy stuff
 * 
 * @author nate
 * 
 */

public class Money {

	private static int tMoney;
	private static int GDP;

	public static int tMoney(int tWheat, int wheatPrice) {

		tMoney = (int) (tMoney + ((tWheat - Main.aggDemand) * wheatPrice));
		return tMoney;

	}

	public static int GDP(int tWheat, int wheatPrice) {
		GDP = tWheat * wheatPrice;
		return GDP;
	}

	public static int gettMoney() {
		return tMoney;
	}

	public static void settMoney(int tMoney) {
		Money.tMoney = tMoney;
	}

	public static int getGDP() {
		return GDP;
	}

	public static void setGDP(int gDP) {
		GDP = gDP;
	}

}
