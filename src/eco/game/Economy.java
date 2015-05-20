package eco.game;

/**
 * This class describes the international market for wheat, it manages wheat
 * reserve goals and sells or buys to meet it.
 * 
 * @Author phil
 */

public class Economy {

	private int treasury = 0;

	private static int wheatPrice = 10;

	public int buyWheat(int ammount) {
		int neededMoney = wheatPrice * ammount;
		if (neededMoney <= treasury) {
			treasury -= neededMoney;
			return ammount;
		} else {
			int canBuy = treasury / wheatPrice;
			treasury = 0;
			return canBuy;
		}
	}

	public int sellWheat(int ammount) {
		treasury += wheatPrice * ammount;
		return ammount;
	}

	public int takeMoney(int request){
		if (request > treasury){
			request = treasury;
			treasury = 0;
			return request;
		}
		treasury -= request;
		return request;
	}
	
	public void updateMarket(int time) {
		wheatPrice += Util.randInt(-5, 5);
	}

	public int getTreasury() {
		return treasury;
	}

	public void setTreasury(int ammount) {
		treasury = ammount;
	}

	public int getPrice() {
		return wheatPrice;
	}

}
