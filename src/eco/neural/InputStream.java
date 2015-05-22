package eco.neural;

public class InputStream {

	public static int readInput(int currentnetwork, int id) {
		int k = 0;
		switch (id) {

		case 0:
			k = NeuralManager.Countries[currentnetwork].farmer.getfPop() * 100;

			break;

		case 1:
			k = NeuralManager.Countries[currentnetwork].warrior.getwPop() * 100;

			break;
		case 2:
			k = NeuralManager.Countries[currentnetwork].wheat.gettWheat() * 100;

			break;

		case 3:
			k = NeuralManager.Countries[currentnetwork].economy.getTreasury() * 100;
			break;

		case 4:
		}

		return k;
	}

}
