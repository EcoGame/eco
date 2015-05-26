package eco.neural;

/**
 * A class that handles connections
 *
 * @author will
 *
 */

public class ConnectionHandler {

	public static void connectionMaker(int k, int type, int currentnetwork) {
      // System.out.println("diags" + k  + " type"+ type + "curr" +currentnetwork);
		switch (type) {

		case 0:
			/*
			 * neuronid = neuronid - Main.INPUTIDOFFSET; for(int x= 0; x<
			 * Main.axonArray[neuronid].connections.length; x++){ for(int q= 0;
			 * q< Main.axonArray[neuronid].connections[x].length; q++){
			 * Main.axonArray[neuronid].connections[x][q]=
			 * Main.axonArraytofill[type][neuronid][x][q]; } }
			 */

			break;

		case 1:
			int u = k - 30;
			for (int x = 0; x < Main.axonArray[currentnetwork][k].connections.length; x++) {
				for (int q = 0; q < Main.axonArray[currentnetwork][k].connections[x].length; q++) {
					Main.axonArray[currentnetwork][k].connections[x][q] = Main.axonArraytofill[currentnetwork][type][u][x][q];
					//System.out.println(Main.axonArray[currentnetwork][k].connections[x][q]+ "output");
				}
			}
			break;

		case 2:
			u = k - 50;
			for (int x = 0; x < Main.axonArray[currentnetwork][k].connections.length; x++) {
				for (int q = 0; q < Main.axonArray[currentnetwork][k].connections[x].length; q++) {
					Main.axonArray[currentnetwork][k].connections[x][q] = Main.axonArraytofill[currentnetwork][type][u][x][q];
                  //  System.out.println(Main.axonArray[currentnetwork][k].connections[x][q] + "important numbers");
				}
			}
			break;

		}
	}
}
