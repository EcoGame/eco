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

	public static void createconnectionsFile(int currentnetwork) {
		int type = 0;
		boolean iscomplete = false;
		String path = null;
		try {
			path = "newtxt/connnections" + currentnetwork + ".txt";
			/*
			 * if (!Main.isInEclipse) {
			 * System.out.println("PROBABLE ERROR WITH NEURAL FILES"); }
			 */
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
			while (iscomplete == false) {
				switch (type) {

				case 1:
					for (int r = 0; r < 10; r++) {
						BW.write("a " + r + " 0 0 0 0 0 0 0 0 0 0 0 0 0");
						BW.newLine();
					}
					type++;
					break;

				case 2:
					for (int k = 0; k < 10; k++) {
						BW.write("b "
								+ k
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][0][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][0][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][1][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][1][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][2][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][2][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][3][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][3][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][4][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][4][1]);
						BW.newLine();
					}
					type++;
					break;
				case 3:
					for (int k = 0; k < 10; k++) {
						BW.write("b "
								+ k
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][0][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][0][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][1][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][1][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][2][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][2][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][3][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][3][1]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][4][0]
								+ " "
								+ Main.axonArraytoprint[currentnetwork][type][k][4][1]);
						BW.newLine();
					}
					type++;
					break;
				case 4:
					iscomplete = true;
					break;

				}
			}
			BW.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}

	}

	public static void createneuralFile(int currentnetwork) {
		int type = 0;
		boolean iscomplete = false;
		String path = null;
		try {
			path = "newtxt/neuron" + currentnetwork + ".txt";
			/*
			 * if (!Main.isInEclipse) {
			 * System.out.println("PROBABLE ERROR WITH NEURAL FILES"); }
			 */
			File fOut = new File(path);
			FileOutputStream FOS = new FileOutputStream(fOut);
			BufferedWriter BW = new BufferedWriter(new OutputStreamWriter(FOS));
			while (iscomplete == false) {
				switch (type) {

				case 1:
					for (int r = 0; r < 10; r++) {
						BW.write("a "
								+ r
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][r][0]
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][r][1]);
						BW.newLine();
					}
					type++;
					break;

				case 2:
					for (int k = 0; k < 10; k++) {
						BW.write("b "
								+ (k + Main.OUTPUT_ID_OFFSET)
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][k][0]
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][k][1]);
						BW.newLine();
					}
					type++;
					break;
				case 3:
					for (int k = 0; k < 10; k++) {
						BW.write("c "
								+ (k + Main.RELAY_ID_OFFSET)
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][k][0]
								+ " "
								+ Main.neuronArrayprint[currentnetwork][type][k][1]);
						BW.newLine();
					}
					type++;
					break;
				case 4:
					iscomplete = true;
					break;

				}
			}
			BW.close();
		} catch (IOException ex) {
			System.out.println("IOException");
		}

	}

}
