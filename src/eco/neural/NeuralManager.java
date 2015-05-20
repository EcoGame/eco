package eco.neural;
import java.util.Random;
import eco.game.*;

/**
 * A class that manages the neural network
 *
 * @author will
 * @param parentOne the chromosome of the first parent
 * @param parentTwo the chromosome of the second parent
 * @return a new semirandom child chromosome
 *
 */
public Country[] eco.game.Country = new Countrys;
public class NeuralManager{

    /*public static Random random = new Random();

     public int start(){
     
        int one = randInt(1,5);
        int two = randInt(1,5);
        int child = spawnChildChromosome(one, two);
        System.out.println("One: " + one);
        System.out.println("Two: " + two);
        System.out.println("Child: " + child);
        System.out.println("One: " + Integer.toBinaryString(one));
        System.out.println("Two: " + Integer.toBinaryString(two));
        System.out.println("Child: " + Integer.toBinaryString(child));
     
     }

     public static int randInt(int min, int max) { //Returns a random number between min and max.

        return min + Main.random.nextInt((max + 1)- min);
     
     }

     public int spawnChildChromosome(int parentOne, int parentTwo) {
     
        int base = parentOne & parentTwo;
        Random rand = new Random();
        int powers2[] = {0x2, 0xC, 0xF0, 0xFF00, 0xFFFF0000};
        int exponents[] = {1, 2, 4, 8, 16};
        int max = Math.max(parentOne, parentTwo);
        int result = 0;
        System.out.println(max);
     
        for (int m = 4; m >= 0; m--) {
            if ((max & powers2[m])!=0) {
                max >>= exponents[m];
                result |= exponents[m];
            }
        }
     
        result+=1;
        System.out.println(result);
     
        for (int n = 0; n < result; n ++){
            if (((parentOne^parentTwo)&n) == 0){
            base |= rand.nextInt(2)<<n; // 0001; 0010; 0100; 1000;
            }
        }
     
        return base;
     }*/

    public static void axonpairing(int currentnetwork) {
        
        boolean iscomplete = false;
        int type = 0;
        int idtotrans = 0;
        int id= 0;
        while(iscomplete == false){

            switch(type){
                case 0:
                    for(int i =0; i < Main.inputNeuralArray[currentnetwork].length; i++){
                        idtotrans= Main.inputNeuralArray[currentnetwork][i].id;
                        Main.inputNeuralArray[currentnetwork][i].pairedaxon = id;
                        Main.axonArray[currentnetwork][id].pairedneuronid = idtotrans + Main.INPUTIDOFFSET;
                        Main.axonArray[currentnetwork][id].typebondedto =0;
                        Main.axonArray[currentnetwork][id].id= id;
                        id++;
                    }
                    type++;
                    break;
                case 1:
                    for(int i =0; i < Main.outputNeuralArray[currentnetwork].length; i++){
                        idtotrans= Main.outputNeuralArray[currentnetwork][i].id;
                        Main.outputNeuralArray[currentnetwork][i].pairedaxon = id;
                        Main.axonArray[currentnetwork][id].pairedneuronid = idtotrans+ Main.OUTPUTIDOFFSET;
                        Main.axonArray[currentnetwork][id].typebondedto =1;
                        Main.axonArray[currentnetwork][id].id= id;
                        id++;
                    }
                    type++;
                    break;
                case 2:
                    for(int i =0; i < Main.neuralArray[currentnetwork].length; i++){
                        Main.neuralArray[currentnetwork][i].pairedaxon = id;
                        idtotrans= Main.neuralArray[currentnetwork][i].id;
                        Main.axonArray[currentnetwork][id].pairedneuronid = idtotrans + Main.RELAYIDOFFSET;
                        Main.axonArray[currentnetwork][id].typebondedto =2;
                        Main.axonArray[currentnetwork][id].id= id;
                        id++;
                    }
                    type++;
                    break;
                default:
                    iscomplete = true;
                    break;
            }
            
        }
    }

    public static void neuromaker(int currentnetwork) {
        boolean iscomplete = false;
        int type = 0;
        while(iscomplete == false){
            
            switch(type){
                case 0:
                    for(int x= 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++){
                        Main.inputNeuralArray[currentnetwork][x].firevalue = Main.neuronArrayfill[currentnetwork][type][x][0];
                        Main.inputNeuralArray[currentnetwork][x].stream = Main.neuronArrayfill[currentnetwork][type][x][1];
                    }
                    type++;
                    break;
                case 1:
                    for(int x= 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++){
                        Main.outputNeuralArray[currentnetwork][x].firevalue = Main.neuronArrayfill[currentnetwork][type][x][0];
                        Main.outputNeuralArray[currentnetwork][x].action = Main.neuronArrayfill[currentnetwork][type][x][1];
                    }
                    type++;
                    break;
                case 2:
                    for(int x= 0; Main.neuronArrayfill[currentnetwork][type].length > x; x++){
                        Main.neuralArray[currentnetwork][x].firevalue = Main.neuronArrayfill[currentnetwork][type][x][0];
                    }
                    type++;
                    break;
                default:
                    iscomplete = true;
                    break;
            }
        }
    }
    
    public static void resetallneurons(int currentnetwork) {
        
        boolean iscomplete = false;
        int eur;
        int type = 0;
        while(iscomplete == false){
            switch(type){
                case 0:
                    for(int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++){
                        Main.inputNeuralArray[currentnetwork][i].reset();
                        eur= Main.inputNeuralArray[currentnetwork][i].pairedaxon;
                        Main.axonArray[currentnetwork][eur].reset();
                    }
                    type++;
                    break;
                case 1:
                    for(int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++){
                        Main.outputNeuralArray[currentnetwork][k].reset();
                        eur = Main.outputNeuralArray[currentnetwork][k].pairedaxon;
                        Main.axonArray[currentnetwork][eur].reset();
                    }
                    type++;
                    break;
                case 2:
                    for(int e = 0; e < Main.neuralArray.length; e++){
                        Main.neuralArray[currentnetwork][e].reset();
                        eur = Main.neuralArray[currentnetwork][e].pairedaxon;
                        Main.axonArray[currentnetwork][eur].reset();
                    }
                    type++;
                    iscomplete = true;
                    break;
            }
        }
    }
    
    public static void neuronaddcheck(int currentnetwork) {
        
        boolean iscomplete = false;
        int propagation= 0;
        int type = 1;
        int eur = 0;
        
        while(iscomplete == false){
            
            switch(type){
                case 0:
                    for(int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
                        Main.inputNeuralArray[currentnetwork][i].checkinput();
                        eur= Main.inputNeuralArray[currentnetwork][i].pairedaxon;
                        Main.axonArray[currentnetwork][eur].fired = Main.inputNeuralArray[currentnetwork][i].fired;
                    }
                    type++;
                    break;
                case 1:
                    for(int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++){
                        eur = Main.outputNeuralArray[currentnetwork][k].pairedaxon;
                        Main.axonArray[currentnetwork][eur].pullfires(currentnetwork);
                        Main.outputNeuralArray[currentnetwork][k].currentvalue = Main.axonArray[currentnetwork][eur].transfervalue;
                        //Main.outputneuralArray[k].firecheck();
                        //Main.axonArray[eur].neuroncheck();
                    }
                    type++;
                    break;
                case 2:
                    for(int e = 0; e < Main.neuralArray[currentnetwork].length; e++){
                        eur = Main.neuralArray[currentnetwork][e].pairedaxon;
                        Main.axonArray[currentnetwork][eur].pullfires(currentnetwork);
                        Main.neuralArray[currentnetwork][e].currentvalue = Main.axonArray[currentnetwork][eur].transfervalue;
                        //  Main.outputneuralArray[e].firecheck();
                        //  Main.axonArray[eur].neuroncheck();
                    }
                    iscomplete = true;
            }
        }
    }
    
    public static void fireallneurons(int currentnetwork) {
        
        boolean iscomplete = false;
        int nofires = 0;
        int iteration =0;
        int propagation= 0;
        int type = 0;
        int eur= 0;
        
        while(iscomplete == false) {
            switch(type){
                case 0:
                    for(int i = 0; i < Main.inputNeuralArray[currentnetwork].length; i++) {
                        if(Main.inputNeuralArray[currentnetwork][i].alreadyfired == false) {
                            Main.inputNeuralArray[currentnetwork][i].checkinput();
                            propagation = Main.inputNeuralArray[currentnetwork][i].fired;
                            eur= Main.inputNeuralArray[currentnetwork][i].pairedaxon;
                            // Main.axonArray[eur].neuroncheck();
                            Main.axonArray[currentnetwork][eur].fired = Main.inputNeuralArray[currentnetwork][i].fired;
                            nofires++;
                        }
                    }
                    type++;
                    break;
                case 1:
                    for(int k = 0; k < Main.outputNeuralArray[currentnetwork].length; k++){
                        if(Main.outputNeuralArray[currentnetwork][k].alreadyfired == false){
                            Main.outputNeuralArray[currentnetwork][k].firecheck();
                            eur = Main.outputNeuralArray[currentnetwork][k].pairedaxon;
                            Main.axonArray[currentnetwork][eur].neuroncheck(currentnetwork);
                            nofires++;
                        }
                    }
                    type++;
                    break;
                case 2:
                    for(int e = 0; e < Main.neuralArray[currentnetwork].length; e++){
                        if(Main.neuralArray[currentnetwork][e].alreadyfired == false){
                            Main.neuralArray[currentnetwork][e].firecheck();
                            eur = Main.neuralArray[currentnetwork][e].pairedaxon;
                            Main.axonArray[currentnetwork][eur].neuroncheck(currentnetwork);
                            nofires++;
                        }
                    }
                    type++;
                    iscomplete = true;
            }
        }
    }

    public static void neuraltick(int currentnetwork) {
        
        int iteration= 0;
        boolean iscomplete =false;
        int brainlength = 4;
        Country[] =  eco.game.Util.getCountries();
        while(iscomplete == false) {
            if(iteration > 3) {
              iscomplete = true;
            }
            for(int y = 0; y < brainlength; y++ ) {
                neuronaddcheck(currentnetwork);
                fireallneurons(currentnetwork);
            }
                resetallneurons(currentnetwork);
                iteration++;
        }
    }
    
    public static void testinputs(int currentnetwork) {
    
        Main.inputNeuralArray[currentnetwork][0].input = 1010;
        Main.inputNeuralArray[currentnetwork][1].input = 10101;

    }
    // Get Countries
    // Util.getCountries
    // Country[] countires =
    // Put them back when your done
    // Util.putCountries(countries[]); 
}
