package neural;
public class Main{
    public static RelayNeuron[][] neuralArray = new RelayNeuron[10][10];
    public static InputNeuron[][] inputNeuralArray = new InputNeuron[10][10];
    public static OutputNeuron[][] outputNeuralArray = new OutputNeuron[10][10];
    public static  Axon[][] axonArray = new Axon[10][30];
    public static int[][][][][] axonArraytofill = new int[10][3][10][5][2];
	public static int[][][][] neuronArrayfill = new int[10][3][10][2];
    public static int[][][][] axonArraytoprint = new int[3][10][5][2];
	public static int[][][] neuronArrayprint = new int[3][10][2];
    public static int totalnumberofint = 30;
    public static boolean[] workingnetworks = new boolean[10];
    //Finals
    public static final int  INPUTIDOFFSET = 0;
    public static final int  RELAYIDOFFSET = 100;
    public static final int OUTPUTIDOFFSET = 200;

public static void main(String [] args) {
    //init();
    // NeuralManager.testinputs();
  //  NeuralManager.neuraltick();

}



    public static void init(){
        for(int x=0; x < neuralArray.length; x++){
        for(int i =0; i < neuralArray[x].length; i++){
            neuralArray[x][i] = new RelayNeuron();

            neuralArray[x][i].id = i;
         
        }   
    }
    for(int f=0; f<inputNeuralArray.length; f++){
        for(int q=0; q < inputNeuralArray[f].length; q++){
            inputNeuralArray[f][q] = new InputNeuron();

            inputNeuralArray[f][q].id = q;
          
        }
    }
    for (int z = 0; z < outputNeuralArray.length; z++){
         for(int w =0; w < outputNeuralArray[z].length; w++){
            outputNeuralArray[z][w] = new OutputNeuron();

            outputNeuralArray[z][w].id = w;
           
         }}
    for(int m =0; m < axonArray.length; m++){
        for(int i =0; i < axonArray[m].length; i++){
            axonArray[m][i] = new Axon();
            axonArray[m][i].id = i;
        }
    }
    }





}
