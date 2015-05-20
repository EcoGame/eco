package eco.neural;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class that handles reading the neurons
 *
 * @author will
 *
 */

public class NeuronReader {
	 //public static int[][][][] axonArraytofill = new int[10][3][10][5][2];
	//public static int[][][] neuronArrayfill = new int[10][3][10][2];
	public static void connectionreader(int num) {
		 char code;
		int type;
		int axonid;
		int filler;
		try {
			File file = new File("./eco/txt/connections"+ num +".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				code = line.charAt(0);

				String[] tokenSpace = line.split(" ");

			switch(code){

			case 'a':
			type = 0;

 			axonid= Integer.parseInt(tokenSpace[1]);

			for(int x= 0; x < Main.axonArraytofill[num][type][axonid].length; x++){
			for(int k = 0; k < Main.axonArraytofill[num][type][axonid][x].length; k++){
				Main.axonArraytofill[num][type][axonid][x][k] = Integer.parseInt(tokenSpace[2+(x*2)+k]);

				}
			}
			break;
			case 'b':
			type = 1;

 			axonid= Integer.parseInt(tokenSpace[1]);

			for(int x= 0; x < Main.axonArraytofill[num][type][axonid].length; x++){
			for(int k = 0; k < Main.axonArraytofill[num][type][axonid][x].length; k++){
				Main.axonArraytofill[num][type][axonid][x][k] = Integer.parseInt(tokenSpace[2+(x*2)+k]);


				}
			}
			break;
			case 'c':
			type = 2;

 			axonid= Integer.parseInt(tokenSpace[1]);

			for(int x= 0; x < Main.axonArraytofill[num][type][axonid].length; x++){
			for(int k = 0; k < Main.axonArraytofill[num][type][axonid][x].length; k++){
				Main.axonArraytofill[num][type][axonid][x][k] = Integer.parseInt(tokenSpace[2+(x*2)+k]);


				}
			}
			break;

					}
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		public static void attributereader(int num) {
        char code;
		int type;
		int neuronid;
		int stream;
		int filler;
		try {
			File file = new File("./eco/txt/neuron"+ num+".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				code = line.charAt(0);


				String[] tokenSpace = line.split(" ");

			switch(code){

			case 'a':
			type = 0;

 			neuronid= Integer.parseInt(tokenSpace[1])- Main.INPUTIDOFFSET;
			
			for( int x = 0; Main.neuronArrayfill[num][type][neuronid].length > x; x++){
			Main.neuronArrayfill[num][type][neuronid][x]= Integer.parseInt(tokenSpace[2+x]);
			}
			break;
			case 'b':
			type = 1;

 			neuronid= Integer.parseInt(tokenSpace[1])- Main.OUTPUTIDOFFSET;
			for( int x = 0; Main.neuronArrayfill[num][type][neuronid].length > x; x++){
			Main.neuronArrayfill[num][type][neuronid][x]= Integer.parseInt(tokenSpace[2+x]);
			}
			break;
			case 'c':
			type = 2;

 			neuronid= Integer.parseInt(tokenSpace[1])- Main.RELAYIDOFFSET;
			for( int x = 0; Main.neuronArrayfill[num][type][neuronid].length > x; x++){
			Main.neuronArrayfill[num][type][neuronid][x]= Integer.parseInt(tokenSpace[2+x]);
			}
			break;

					}
			}
			fileReader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
