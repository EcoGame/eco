package eco.neural;

/**
 * A class that handles Axons
 * 
 * @author will
 * 
 */

public class Axon {

	int currentNetwork;
	int id;
	int pairedNeuronId;
	int[][] connections = new int[5][2];
	int typeBondedTo = 0;
	int fired;
	int currentvalue = 0;
	int transferValue = 0;

	boolean isUsed = false;

	public void pullFires(int currentnetwork) {

		switch (typeBondedTo) {
		case 0:
			int q = 0;
			for (int r = 0; r < Main.axonArraytofill[typeBondedTo].length; r++) {
				q = connections[r][0];
				transferValue = transferValue
						+ (connections[r][1] * Main.inputNeuralArray[currentnetwork][q].fired);
			}
			break;

		case 1:
			int e = 0;
			for (int r = 0; r < Main.axonArraytofill[typeBondedTo].length; r++) {
				e = connections[r][0];
                
				transferValue = transferValue
						+ (connections[r][1] * Main.axonArray[currentnetwork][e].fired);
			}
			break;

		case 2:
			int k = 0;
			for (int r = 0; r < Main.axonArraytofill[typeBondedTo].length; r++) {
				k = connections[r][0];
                System.out.println(k+ "k");
				transferValue = transferValue
						+ (connections[r][1] * Main.axonArray[currentnetwork][k].fired);
			}
			break;
		}

	}

	public void neuronCheck(int currentnetwork) {

		int k = 0;
		switch (typeBondedTo) {
		case 0:
			k = pairedNeuronId - Main.INPUT_ID_OFFSET;
			fired = Main.inputNeuralArray[currentnetwork][k].fired;
			break;

		case 1:
			k = pairedNeuronId - Main.OUTPUT_ID_OFFSET;
			fired = Main.outputNeuralArray[currentnetwork][k].fired;
			break;

		case 2:
			k = pairedNeuronId - Main.RELAY_ID_OFFSET;
			fired = Main.neuralArray[currentnetwork][k].fired;
			break;
		}

	}

	public void reset() {

		transferValue = 0;
		currentvalue = 0;
		fired = 0;
	}
}
