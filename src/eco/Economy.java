package eco;

public class Economy {

	public static int treasury = 0;

	public static int wheatPrice = 10;
	public static int supplyShock = 0;

	public static float shockRatio = 2f;

	public static int foreignDemand = 1000;

	public static int buyWheat(int ammount) {
		return 0;
	}

	public static void sellWheat(int ammount) {
		while (ammount > 0 && foreignDemand > 0) {
			if (ammount >= 100) {
				int toSell = ammount - 100;
				treasury += toSell * getRealPrice();
				ammount -= toSell;
				supplyShock += shockRatio;
				foreignDemand -= toSell * getRealPrice();
			} else {
				int toSell = ammount;
				treasury += toSell * getRealPrice();
				ammount -= toSell;
				supplyShock += shockRatio;
				foreignDemand -= toSell * getRealPrice();
			}
		}
	}

	public static int getRealPrice() {
		return wheatPrice - supplyShock;
	}

	public static void updateMarket(int time) {
		float timeMul = (float) Math.sin(Math.toRadians(time / 8f));
		int deltaPrice = (int) (timeMul * 1f);
		wheatPrice += deltaPrice;
		foreignDemand -= (timeMul * 50);
	}

}
