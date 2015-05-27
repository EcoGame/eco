package eco.neural;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/*import org.lwjgl.BufferUtils;
 import org.lwjgl.opengl.Display;
 import org.lwjgl.opengl.GL11;*/

/**
 * A class that contains various utilities and convenience methods
 *
 * @author connor
 *
 */

public class Util {

	public static void createConnectionsFile(int p) {
		int type = 0;
		boolean iscomplete = false;
		String path = null;

			path = "../newtxt/connections" + p + ".ann";
			System.out.println("printing connect" + p);
		try {
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
		//	System.out.println("fe1");
			while (iscomplete == false) {
				switch (type) {

				case 0:
					for (int r = 0; r < Main.inputNeuralArray[p].length; r++) {
						BW.write("a " + r + " 0 0 0 0 0 0 0 0 0 0 0 0 0");
						BW.newLine();
					}
					type++;
					break;

				case 1:
					for (int k = 0; k < Main.outputNeuralArray[p].length; k++) {
						BW.write("b "
								+ k
								+ " "
								+ Main.axonArraytoprint[p][type][k][0][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][0][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][1][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][1][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][2][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][2][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][3][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][3][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][4][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][4][1]);
						BW.newLine();
					}
					type++;
					break;
				case 2:
					for (int k = 0; k < Main.neuralArray[p].length; k++) {
						BW.write("c "
								+ k
								+ " "
								+ Main.axonArraytoprint[p][type][k][0][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][0][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][1][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][1][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][2][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][2][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][3][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][3][1]
								+ " "
								+ Main.axonArraytoprint[p][type][k][4][0]
								+ " "
								+ Main.axonArraytoprint[p][type][k][4][1]);
						BW.newLine();
					}
					type++;
					break;
				case 3:
					iscomplete = true;
					break;

				}
			}
			//System.out.println("fe");
			BW.close();
		} catch (IOException ex) {
			System.out.println("1IOException");
		}

	}

	public static void createNeuralFile( int p) {
		int type = 0;
		boolean iscomplete = false;
		String path = null;

		try {
			path = "../newtxt/neuron" + p + ".ann";
			System.out.println("printing connect " + p);
			/*

			 * if (!Main.isInEclipse) {
			 * System.out.println("PROBABLE ERROR WITH NEURAL FILES"); }
			 */
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
			while (iscomplete == false) {
				switch (type) {

				case 0:
					for (int r = 0; r < Main.inputNeuralArray[p].length; r++) {
						BW.write("a "
								+ r
								+ " "
								+ Main.neuronArrayprint[p][type][r][0]
								+ " "
								+ Main.neuronArrayprint[p][type][r][1] + " " + Main.highOrLowtoprint[p][r][0]);
						BW.newLine();
					}
					type++;
					break;

				case 1:
					for (int k = 0; k < Main.outputNeuralArray[p].length; k++) {
						BW.write("b "
								+ (k + Main.OUTPUT_ID_OFFSET)
								+ " "
								+ Main.neuronArrayprint[p][type][k][0]
								+ " "
								+ Main.neuronArrayprint[p][type][k][1]);
						BW.newLine();
					}
					type++;
					break;
				case 2:
					for (int k = 0; k < Main.neuralArray[p].length; k++) {
						BW.write("c "
								+ (k + Main.RELAY_ID_OFFSET)
								+ " "
								+ Main.neuronArrayprint[p][type][k][0]
								+ " "
								+ Main.neuronArrayprint[p][type][k][1]);
						BW.newLine();
					}
					type++;
					break;
				case 3:
					iscomplete = true;
					break;

				}
			}
			BW.close();
		} catch (IOException ex) {
			System.out.println("3IOException");
		}

	}
	public static void createCurrentState() {
boolean iscomplete = false;
int type =0;
		try {
			String path = null;
			 path = "../newtxt/currentstate.ann";
			/*
			 * if (!Main.isInEclipse) {
			 * System.out.println("PROBABLE ERROR WITH NEURAL FILES"); }
			 */
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
			while (iscomplete == false) {
				switch (type) {

				case 0:

						BW.write("g " + NeuronReader.generation);
						BW.newLine();

					type++;
					break;

				case 1:

						BW.write("q");

						BW.newLine();

					type++;
					break;
				case 2:
					for (int k = 0; k < 10; k++) {
						BW.write("y "+ k + " "+ GeneticMaster.fitness[k]);
						BW.newLine();
					}
					type++;
					break;
				case 3:
					iscomplete = true;
					break;

				}
			}
			BW.close();
		} catch (IOException ex) {
			System.out.println("2IOException");
		}

	}

}
