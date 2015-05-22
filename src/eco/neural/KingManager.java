package eco.neural;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that manages the network
 * 
 * @author will
 * 
 */

public class KingManager {

	public static int currentnetwork = 0;

	public static void topManager() {

	}

	public static void gameManager() {

		statusReader();
		boolean gamecomplete = false;
		boolean iscomplete0 = false;
		boolean iscomplete1 = false;

		for (int x = 0; (x < Main.workingnetworks.length)
				&& (iscomplete1 == false); x++) {
			if (Main.workingnetworks[x] == true) {
				periodicInit(x);
			}

		}

		iscomplete1 = false;

		for (int x = 0; (x < Main.workingnetworks.length)
				&& (iscomplete0 == false); x++) {
			if (Main.workingnetworks[x] == true) {
				currentnetwork = x;
				Main.workingnetworks[x] = false;
				iscomplete0 = true;
			}
		}

		iscomplete0 = false;
		while (gamecomplete == false) {

		}
	}

	@SuppressWarnings("unused")
	public static void statusReader() {

		char code;
		int x;
		int generation;
		int success;
		int filler;

		try {
			File file = new File("./eco/txt/currentstates.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;

			while ((line = bufferedReader.readLine()) != null) {
				code = line.charAt(0);
				String[] tokenSpace = line.split(" ");

				switch (code) {
				case 'g':
					generation = Integer.parseInt(tokenSpace[1]);
					break;
				case 'y':
					x = Integer.parseInt(tokenSpace[1]);
					Main.workingnetworks[x] = true;
					break;
				case 'n':
					x = Integer.parseInt(tokenSpace[1]);
					Main.workingnetworks[x] = false;
					break;

				case 'q':
					break;
				}

			}

			fileReader.close();

		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void periodicInit(int currentnetwork) {

		NeuronReader.connectionReader(currentnetwork);
		NeuronReader.attributeReader(currentnetwork);
		NeuralManager.axonPairing(currentnetwork);
		NeuralManager.neuronMaker(currentnetwork);

		for (int k = 0; Main.axonArray.length > k; k++) {
			ConnectionHandler.connectionMaker(k,
					Main.axonArray[currentnetwork][k].typeBondedTo,
					currentnetwork);
		}
	}
}
