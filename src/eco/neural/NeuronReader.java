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
    public static int generation;
    @SuppressWarnings("unused")
    public static void connectionReader(int num) {
        char code;
        int type;
        int axonId;
        int filler;
        try {
            File file = new File("../neurons/connections" + num + ".ann");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                code = line.charAt(0);

                String[] tokenSpace = line.split(" ");

                switch (code) {

                case 'a':
                    type = 0;

                    axonId = Integer.parseInt(tokenSpace[1]);

                    for (int x = 0; x < Main.axonArraytofill[num][type][axonId].length; x++) {
                        for (int k = 0; k < Main.axonArraytofill[num][type][axonId][x].length; k++) {
                            Main.axonArraytofill[num][type][axonId][x][k] = Integer
                                    .parseInt(tokenSpace[2 + (x * 2) + k]);

                        }
                    }
                    break;
                case 'b':
                    type = 1;

                    axonId = Integer.parseInt(tokenSpace[1]);

                    for (int x = 0; x < Main.axonArraytofill[num][type][axonId].length; x++) {
                        for (int k = 0; k < Main.axonArraytofill[num][type][axonId][x].length; k++) {
                            Main.axonArraytofill[num][type][axonId][x][k] = Integer
                                    .parseInt(tokenSpace[2 + (x * 2) + k]);

                        }
                    }
                    break;
                case 'c':
                    type = 2;

                    axonId = Integer.parseInt(tokenSpace[1]);

                    for (int x = 0; x < Main.axonArraytofill[num][type][axonId].length; x++) {
                        for (int k = 0; k < Main.axonArraytofill[num][type][axonId][x].length; k++) {
                            Main.axonArraytofill[num][type][axonId][x][k] = Integer
                                    .parseInt(tokenSpace[2 + (x * 2) + k]);
                          //  System.out.println("f" +Main.axonArraytofill[num][type][axonId][x][k]);
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

    @SuppressWarnings("unused")
    public static void attributeReader(int num) {
        char code;
        int type;
        int neuronid;
        int stream;
        int filler;
        try {
            File file = new File("../neurons/neuron" + num + ".ann");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                code = line.charAt(0);

                String[] tokenSpace = line.split(" ");

                switch (code) {

                case 'a':
                    type = 0;

                    neuronid = Integer.parseInt(tokenSpace[1])
                            - Main.INPUT_ID_OFFSET;


                        Main.neuronArrayfill[num][type][neuronid][0] = Integer
                                .parseInt(tokenSpace[2]);
                        Main.neuronArrayfill[num][type][neuronid][1] = Integer
                        .parseInt(tokenSpace[3]);
                                                Main.highOrLow[num][neuronid][0] = Integer
                        .parseInt(tokenSpace[4]);
                    break;
                case 'b':
                    type = 1;

                    neuronid = Integer.parseInt(tokenSpace[1])
                            - Main.OUTPUT_ID_OFFSET;
                    for (int x = 0; Main.neuronArrayfill[num][type][neuronid].length > x; x++) {
                        Main.neuronArrayfill[num][type][neuronid][x] = Integer
                                .parseInt(tokenSpace[2 + x]);
                    }
                    break;
                case 'c':
                    type = 2;

                    neuronid = Integer.parseInt(tokenSpace[1])
                            - Main.RELAY_ID_OFFSET;
                            //System.out.println(neuronid);
                    for (int x = 0; Main.neuronArrayfill[num][type][neuronid].length > x; x++) {
                        Main.neuronArrayfill[num][type][neuronid][x] = Integer
                                .parseInt(tokenSpace[2 + x]);
                    }
                    break;

                }
            }
            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void stateReader() {

            char code;
            int type;

            int filler;
            try {
                File file = new File("../neurons/currentstate.ann");
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
                    case 'q':
                        // use this for something
                        break;
                    case 'y':
                        // going to be used to indicate that a network is current operational
                        break;
                        case 'n':
                        //going to be used to inicate
                        break;
                    }
                }
                fileReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
