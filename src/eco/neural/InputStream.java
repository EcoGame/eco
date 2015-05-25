package eco.neural;

public class InputStream {

	public static int readInput(int currentnetwork, int id) {
		int k = 0;
		switch (id) {

		case 0:
               // System.out.println(currentnetwork +"fasd");
			k = NeuralManager.Countries[currentnetwork].farmer.getfPop();
          //  System.out.println(NeuralManager.Countries[currentnetwork].farmer.//getfPop()+" af");
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
       // System.out.println("k"+ k);
		return k;
	}

}
