package eco.neural;


/**
 * A class that manages the neural network
 *
 * @author will
 * @param parentOne
 *            the chromosome of the first parent
 * @param parentTwo
 *            the chromosome of the second parent
 * @return a new semirandom child chromosome
 *
 */
public class NeuralManager {
	public static eco.game.Country Countries[] = new eco.game.Country[10];

	public static void axonPairing(int currentnetwork) {

		boolean isComplete = false;
		int type = 0;
		int idToTrans = 0;
		int id = 0;
		while (isComplete == false) {

			switch (type) {
			case 0:
				for (int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
					idToTrans = Main.inputNeuralArray[currentnetwork][i].id;
					Main.inputNeuralArray[currentnetwork][i].pairedAxon = id;
					Main.axonArray[currentnetwork][id].pairedNeuronId = idToTrans
							+ Main.INPUT_ID_OFFSET;
					Main.axonArray[currentnetwork][id].typeBondedTo = 0;
					Main.axonArray[currentnetwork][id].id = id;
					id++;

				}
				type++;
				break;
			case 1:
				for (int i = 0; i < Main.outputNeuralArray[currentnetwork].length; i++) {
					idToTrans = Main.outputNeuralArray[currentnetwork][i].id;
					Main.outputNeuralArray[currentnetwork][i].pairedAxon = id;
					Main.axonArray[currentnetwork][id].pairedNeuronId = idToTrans
							+ Main.OUTPUT_ID_OFFSET;
					Main.axonArray[currentnetwork][id].typeBondedTo = 1;
					Main.axonArray[currentnetwork][id].id = id;
					id++;

				}
				type++;
				break;
			case 2:
				for (int i = 0; i < Main.neuralArray[currentnetwork].length; i++) {
					Main.neuralArray[currentnetwork][i].pairedAxon = id;
					idToTrans = Main.neuralArray[currentnetwork][i].id;
					Main.axonArray[currentnetwork][id].pairedNeuronId = idToTrans
							+ Main.RELAY_ID_OFFSET;
					Main.axonArray[currentnetwork][id].typeBondedTo = 2;
					Main.axonArray[currentnetwork][id].id = id;
					id++;
				
				}
				type++;
				break;
			default:
				isComplete = true;
				break;
			}

		}
	}

	public static void neuronMaker(int currentnetwork) {
		boolean isComplete = false;
		int type = 0;
		while (isComplete == false) {

			switch (type) {
			case 0:
				for (int x = 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++) {
					Main.inputNeuralArray[currentnetwork][x].fireValue = Main.neuronArrayfill[currentnetwork][type][x][0];
					Main.inputNeuralArray[currentnetwork][x].stream = Main.neuronArrayfill[currentnetwork][type][x][1];
                    //System.out.println(Main.neuronArrayfill[currentnetwork][type][x][1]);
				}
				type++;
				break;
			case 1:
				for (int x = 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++) {
					Main.outputNeuralArray[currentnetwork][x].fireValue = Main.neuronArrayfill[currentnetwork][type][x][0];
					Main.outputNeuralArray[currentnetwork][x].action = Main.neuronArrayfill[currentnetwork][type][x][1];
				}
				type++;
				break;
			case 2:
				for (int x = 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++) {
					Main.neuralArray[currentnetwork][x].fireValue = Main.neuronArrayfill[currentnetwork][type][x][0];
				}
				type++;
				break;
			default:
				isComplete = true;
				break;
			}
		}
	}

	public static void resetAllNeurons(int currentnetwork) {

		boolean isComplete = false;
		int eur;
		int type = 0;
		while (isComplete == false) {
			switch (type) {
			case 0:
				for (int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
					Main.inputNeuralArray[currentnetwork][i].reset();
					eur = Main.inputNeuralArray[currentnetwork][i].pairedAxon;
					Main.axonArray[currentnetwork][eur].reset();
				}
				type++;
				break;
			case 1:
				for (int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++) {
					Main.outputNeuralArray[currentnetwork][k].reset();
					eur = Main.outputNeuralArray[currentnetwork][k].pairedAxon;
					Main.axonArray[currentnetwork][eur].reset();
				}
				type++;
				break;
			case 2:
				for (int e = 0; e < Main.neuralArray.length; e++) {
					Main.neuralArray[currentnetwork][e].reset();
					eur = Main.neuralArray[currentnetwork][e].pairedAxon;
					Main.axonArray[currentnetwork][eur].reset();
				}
				type++;
				isComplete = true;
				break;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void neuronAddCheck(int currentnetwork) {

		boolean isComplete = false;
		int propagation = 0;
		int type = 0;
		int eur = 0;

		while (isComplete == false) {

			switch (type) {
			case 0:
				for (int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
					Main.inputNeuralArray[currentnetwork][i]
							.checkInput(currentnetwork);
					eur = Main.inputNeuralArray[currentnetwork][i].pairedAxon;
					Main.axonArray[currentnetwork][eur].fired = Main.inputNeuralArray[currentnetwork][i].fired;
				}
				type++;
				break;
			case 1:
				for (int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++) {
					eur = Main.outputNeuralArray[currentnetwork][k].pairedAxon;
                   // System.out.println("f" + eur);
					Main.axonArray[currentnetwork][eur]
							.pullFires(currentnetwork);
					Main.outputNeuralArray[currentnetwork][k].currentValue = Main.axonArray[currentnetwork][eur].transferValue;
					// Main.outputneuralArray[k].firecheck();
					 //Main.axonArray[eur].neuroncheck();
				}
				type++;
				break;
			case 2:
				for (int e = 0; e < Main.neuralArray[currentnetwork].length; e++) {
					eur = Main.neuralArray[currentnetwork][e].pairedAxon;
                 //  System.out.println("a" + eur);
					Main.axonArray[currentnetwork][eur]
							.pullFires(currentnetwork);
					Main.neuralArray[currentnetwork][e].currentValue = Main.axonArray[currentnetwork][eur].transferValue;
					 //Main.outputneuralArray[e].firecheck();
					// Main.axonArray[eur].neuroncheck();
				}
				isComplete = true;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void fireAllNeurons(int currentnetwork) {

		boolean iscomplete = false;
		int noFires = 0;
		int iteration = 0;
		int propagation = 0;
		int type = 0;
		int eur = 0;


		while (iscomplete == false) {
			switch (type) {
			case 0:
				for (int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
					if (Main.inputNeuralArray[currentnetwork][i].alreadyFired == false) {
						Main.inputNeuralArray[currentnetwork][i]
								.checkInput(currentnetwork);
						propagation = Main.inputNeuralArray[currentnetwork][i].fired;
						eur = Main.inputNeuralArray[currentnetwork][i].pairedAxon;
						// Main.axonArray[eur].neuroncheck();
						Main.axonArray[currentnetwork][eur].fired = Main.inputNeuralArray[currentnetwork][i].fired;
						noFires++;
					}
				}
				type++;
				break;
			case 2:
				for (int e = 0; e < Main.neuralArray[currentnetwork].length; e++) {
					if (Main.neuralArray[currentnetwork][e].alreadyFired == false) {
						Main.neuralArray[currentnetwork][e].fireCheck();
						eur = Main.neuralArray[currentnetwork][e].pairedAxon;
						Main.axonArray[currentnetwork][eur]
								.neuronCheck(currentnetwork);
						noFires++;
					}
				}
				type++;
				iscomplete = true;
				break;
			case 1:
				for (int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++) {
					if (Main.outputNeuralArray[currentnetwork][k].alreadyFired == false) {
						Main.outputNeuralArray[currentnetwork][k]
								.fireCheck(currentnetwork);
						eur = Main.outputNeuralArray[currentnetwork][k].pairedAxon;
						Main.axonArray[currentnetwork][eur]
								.neuronCheck(currentnetwork);
						noFires++;
					}
				}
				type++;
				break;
			}
		}
	}

	public static void neuralTick(int currentnetwork) {
		int iteration = 0;
		boolean isComplete = false;
		int brainLength = 4;
		Countries = eco.game.Util.getCountries();
		while (isComplete == false) {
			if (iteration > 3) {
				isComplete = true;
			}
			for (int y = 0; y < brainLength; y++) {
            //    System.out.println("fda");
				neuronAddCheck(currentnetwork);
				fireAllNeurons(currentnetwork);
			}
			resetAllNeurons(currentnetwork);
			iteration++;
		}
		eco.game.Util.putCountries(Countries);
	}

	public static void testInputs(int currentnetwork) {

		Main.inputNeuralArray[currentnetwork][0].input = 1010;
		Main.inputNeuralArray[currentnetwork][1].input = 10101;

	}

}
