package eco;

/**
 * This class represents wheat and associated methods, including the wheat
 * stockpile
 * 
 * @author phil, nate, will, connor
 * 
 */

public class Wheat {

	private int tWheat;
	private static int wheatPrice;

	private int maxwheat = 5000;
	private int minwheat = 1000;

	public int tWheat(int farmers, Farmer farmer) {
		tWheat += farmers * farmer.getWheatProductionRate();
		return tWheat;
	}

	public int eatWheat(int request, Economy economy) {
		if (tWheat < minwheat) {
			int toBuy = minwheat - tWheat;
			tWheat += economy.buyWheat(toBuy);
		}
		if (request > tWheat) {
			int diff = request - tWheat;
			tWheat = 0;
			return diff;
		} else {
			tWheat -= request;
			return 0;
		}
	}

	public int gettWheat() {
		return tWheat;
	}

	public void settWheat(int tWheat) {
		this.tWheat = tWheat;
	}

	public int getWheatPrice() {
		return wheatPrice;
	}

	public void update(Economy economy) {
		if (tWheat > maxwheat) {
			economy.sellWheat(tWheat - maxwheat);
			tWheat = maxwheat;
		}
	}

	public void setWheatPrice(int wheatPrice) {
		Wheat.wheatPrice = wheatPrice;
	}

	public void resetWheat() {
		tWheat = 0;
	}

}
