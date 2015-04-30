package neural;

public class Axon{
int id;
int pairedneuronid;
int[][] connections = new int[5][2];
int typebondedto=0;
    int fired;
    int currentvalue =0;
    int transfervalue=0;
boolean isused = false;

    public void pullfires(){
                switch(typebondedto){

            case 0:
                int q = 0;
                
                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.INPUTIDOFFSET].length; r++){
                    q= connections[r][0];
                  
                    transfervalue = transfervalue + (connections[r][1] * Main.inputNeuralArray[q].fired);
                    
                }

                break;

            case 1:
                int e = 0;
              
                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.OUTPUTIDOFFSET].length; r++){
                  

                    e=connections[r][0];
                    
                    transfervalue = transfervalue + (connections[r][1] * Main.axonArray[e].fired);
             
                }
                break;

            case 2:
                int k = 0;
               
                for(int r = 0; r < Main.axonArraytofill[typebondedto][pairedneuronid- Main.RELAYIDOFFSET].length; r++){
                 
                    k=connections[r][0];
                   
                    transfervalue = transfervalue + (connections[r][1] * Main.axonArray[k].fired);
                   
                }
                break;

        }

    }

    public void neuroncheck(){
        int k= 0;
            switch(typebondedto){

                case 0:
                    k = pairedneuronid - Main.INPUTIDOFFSET;
                    fired = Main.inputNeuralArray[k].fired;
                    
                    break;

                case 1:
                    k = pairedneuronid - Main.OUTPUTIDOFFSET;
                    fired = Main.outputNeuralArray[k].fired;
                   
                    break;

                case 2:
                     k = pairedneuronid - Main.RELAYIDOFFSET;
                    fired = Main.neuralArray[k].fired;
                   
                    break;

            }

    }
    public void reset(){
        transfervalue = 0;
    currentvalue = 0;
    fired = 0;
    }
}
