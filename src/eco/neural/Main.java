package eco.neural;

public class Main{
    public static RelayNeuron[] neuralArray = new RelayNeuron[10];
    public static InputNeuron[] inputNeuralArray = new InputNeuron[10];
    public static OutputNeuron[] outputNeuralArray = new OutputNeuron[10];
    public static  Axon[] axonArray = new Axon[30];
    public static int[][][][] axonArraytofill = new int[3][10][5][2];
	public static int[][][] neuronArrayfill = new int[3][10][2];
    public static int totalnumberofint = 30;
    public static boolean[] workingnetworks = new boolean[10];
    //Finals
    public static final int  INPUTIDOFFSET = 0;
    public static final int  RELAYIDOFFSET = 100;
    public static final int OUTPUTIDOFFSET = 200;

public static void main(String [] args) {
    init();
    NeuralManager.testinputs();
    NeuralManager.neuraltick();

}



    public static void init(){
        for(int i =0; i < neuralArray.length; i++){
            neuralArray[i] = new RelayNeuron();

            neuralArray[i].id = i;
         
        }
        for(int q=0; q < inputNeuralArray.length; q++){
            inputNeuralArray[q] = new InputNeuron();

            inputNeuralArray[q].id = q;
          
        }
         for(int w =0; w < outputNeuralArray.length; w++){
            outputNeuralArray[w] = new OutputNeuron();

            outputNeuralArray[w].id = w;
           
        }
        for(int i =0; i < axonArray.length; i++){
            axonArray[i] = new Axon();
            axonArray[i].id = i;
        }
    
        


	NeuralManager.axonpairing();
      	NeuralManager.neuromaker();
       
             for(int k=0; axonArray.length > k; k++){

            ConnectionHandler.connectionmaker(k, axonArray[k].typebondedto);

        }

    }





}
