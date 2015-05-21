package eco.neural;

public class InputStream {

	public static int readinput(int currentnetwork, int id) {
		int k = 0;
		switch (id) {

		case 0:
			k = NeuralManager.Countries[currentnetwork].farmer.getfPop();

			break;

		case 1:
			k = NeuralManager.Countries[currentnetwork].warrior.getwPop();

			break;
		case 2:
			k = NeuralManager.Countries[currentnetwork].wheat.gettWheat();

			break;

		case 3:
			k = NeuralManager.Countries[currentnetwork].economy.getTreasury();
			break;

		case 4:
		}

		return k;
	}

}
