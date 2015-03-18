package eco;

/**
 * This class represents wheat and associated methods, including the wheat
 * stockpile
 * 
 * @author phil, nate, will, connor
 * 
 */

public class Wheat {

	private static int tWheat;
	private static int wheatPrice;

	public static int tWheat(int farmers) {
		tWheat += farmers * Farmer.getWheatProductionRate();
		return tWheat;
	}

	public static int eatWheat(int request) {
		if (request > tWheat) {
			int diff = request - tWheat;
			tWheat = 0;
			return diff;
		} else {
			tWheat -= request;
			return 0;
		}
	}

	public static int gettWheat() {
		return tWheat;
	}

	public static void settWheat(int tWheat) {
		Wheat.tWheat = tWheat;
	}

	public static int getWheatPrice() {
		return wheatPrice;
	}

	public static void setWheatPrice(int wheatPrice) {
		Wheat.wheatPrice = wheatPrice;
	}

}
