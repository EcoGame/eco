package eco.neural;

public class Axon{
    int currentnetwork;
int id;
int pairedneuronid;
int[][] connections = new int[5][2];
int typebondedto=0;
    int fired;
    int currentvalue =0;
    int transfervalue=0;
boolean isused = false;

    public void pullfires(int currentnetwork){
                switch(typebondedto){

            case 0:
                int q = 0;

                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.INPUTIDOFFSET].length; r++){
                    q= connections[r][0];

                    transfervalue = transfervalue + (connections[r][1] * Main.inputNeuralArray[currentnetwork][q].fired);

                }

                break;

            case 1:
                int e = 0;

                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.OUTPUTIDOFFSET].length; r++){


                    e=connections[r][0];

                    transfervalue = transfervalue + (connections[r][1] * Main.axonArray[currentnetwork][e].fired);

                }
                break;

            case 2:
                int k = 0;

                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.RELAYIDOFFSET].length; r++){

                    k=connections[r][0];

                    transfervalue = transfervalue + (connections[r][1] * Main.axonArray[currentnetwork][k].fired);

                }
                break;

        }

    }

    public void neuroncheck(int currentnetwork){
        int k= 0;
            switch(typebondedto){

                case 0:
                    k = pairedneuronid - Main.INPUTIDOFFSET;
                    fired = Main.inputNeuralArray[currentnetwork][k].fired;

                    break;

                case 1:
                    k = pairedneuronid - Main.OUTPUTIDOFFSET;
                    fired = Main.outputNeuralArray[currentnetwork][k].fired;

                    break;

                case 2:
                     k = pairedneuronid - Main.RELAYIDOFFSET;
                    fired = Main.neuralArray[currentnetwork][k].fired;

                    break;

            }

    }
    public void reset(){
        transfervalue = 0;
    currentvalue = 0;
    fired = 0;
    }
}
