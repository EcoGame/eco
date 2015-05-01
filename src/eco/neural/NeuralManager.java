package eco.neural;

import java.util.Random;

public class NeuralManager{


/**
 * @param parentOne the chromosome of the first parent
 * @param parentTwo the chromosome of the second parent
 * @return a new semirandom child chromosome


 */

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

public static void axonpairing(){
  boolean iscomplete = false;
  int type = 0;
  int idtotrans = 0;
int id= 0;
  while(iscomplete == false){

          switch(type){

     case 0:
       for(int i =0; i < Main.inputNeuralArray.length; i++){

                       idtotrans= Main.inputNeuralArray[i].id;
         
	    Main.inputNeuralArray[i].pairedaxon = id;
            Main.axonArray[id].pairedneuronid = idtotrans + Main.INPUTIDOFFSET;

           Main.axonArray[id].typebondedto =0;

            Main.axonArray[id].id= id;

           id++;
      

        }
      type++;

      break;
      case 1:
       for(int i =0; i < Main.outputNeuralArray.length; i++){
      
            idtotrans= Main.outputNeuralArray[i].id;
          
		Main.outputNeuralArray[i].pairedaxon = id;
             Main.axonArray[id].pairedneuronid = idtotrans+ Main.OUTPUTIDOFFSET;
           
           Main.axonArray[id].typebondedto =1;


            Main.axonArray[id].id= id;
           
             id++;


        }
      type++;
            break;
      case 2:
      for(int i =0; i < Main.neuralArray.length; i++){

		Main.neuralArray[i].pairedaxon = id;
            idtotrans= Main.neuralArray[i].id;
          
            Main.axonArray[id].pairedneuronid = idtotrans + Main.RELAYIDOFFSET;
                    Main.axonArray[id].typebondedto =2;



            Main.axonArray[id].id= id;
         
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

public static void neuromaker(){
boolean iscomplete = false;
int type = 0;
while(iscomplete == false){
    switch(type){

     case 0:
	for(int x= 0; Main.neuronArrayfill[type].length > x; x++){

       		Main.inputNeuralArray[x].firevalue = Main.neuronArrayfill[type][x][0];
          
		}
      type++;
      break;
      case 1:
       		for(int x= 0; Main.neuronArrayfill[type].length > x; x++){


       		Main.outputNeuralArray[x].firevalue = Main.neuronArrayfill[type][x][0];
		Main.outputNeuralArray[x].action = Main.neuronArrayfill[type][x][1];
               
                

		}
      type++;
      break;
	case 2:
      for(int x= 0; Main.neuronArrayfill[type].length > x; x++){


       		Main.neuralArray[x].firevalue = Main.neuronArrayfill[type][x][0];
         

		}
      type++;
      break;
      default:
            
        iscomplete = true;
        break;
    }
  }

}
    public static void resetallneurons(){
      boolean iscomplete = false;
      int eur;
      int type = 0;

      while(iscomplete == false){
        switch(type){

            case 0:
                for(int i = 0; i < Main.inputNeuralArray.length; i++){
                    Main.inputNeuralArray[i].reset();
                    eur= Main.inputNeuralArray[i].pairedaxon;

                    Main.axonArray[eur].reset();

                }
                type++;
                break;
            case 1:
                for(int k = 0; k < Main.outputNeuralArray.length; k++){


                Main.outputNeuralArray[k].reset();
                
                eur= Main.outputNeuralArray[k].pairedaxon;
                Main.axonArray[eur].reset();

                }
                type++;
                break;
            case 2:
                for(int e = 0; e < Main.neuralArray.length; e++){

                  Main.neuralArray[e].reset();
                  eur= Main.neuralArray[e].pairedaxon;
                  Main.axonArray[eur].reset();


                }
                type++;
                  iscomplete = true;

                }

      }


    }
    public static void neuronaddcheck(){
        boolean iscomplete = false;
        int propagation= 0;
        int type = 1;
        int eur= 0;
        while(iscomplete == false){
            switch(type){

                case 0:
                    for(int i = 0; i < Main.inputNeuralArray.length; i++){
                        Main.inputNeuralArray[i].checkinput();
                        eur= Main.inputNeuralArray[i].pairedaxon;

                        Main.axonArray[eur].fired = Main.inputNeuralArray[i].fired;

                    }
                    type++;
                    break;
                case 1:
                    for(int k = 0; k < Main.outputNeuralArray.length; k++){
                        eur = Main.outputNeuralArray[k].pairedaxon;
                        Main.axonArray[eur].pullfires();
                    Main.outputNeuralArray[k].currentvalue = Main.axonArray[eur].transfervalue;
                        //Main.outputneuralArray[k].firecheck();
                        //Main.axonArray[eur].neuroncheck();

                    }
                    type++;
                    break;
                case 2:
                    for(int e = 0; e < Main.neuralArray.length; e++){
                        eur = Main.neuralArray[e].pairedaxon;
                      
                        Main.axonArray[eur].pullfires();
                        Main.neuralArray[e].currentvalue = Main.axonArray[eur].transfervalue;
                
                      //  Main.outputneuralArray[e].firecheck();
                      //  Main.axonArray[eur].neuroncheck();


                    }
                    iscomplete = true;


                    }
            }
        }
        public static void fireallneurons(){
          boolean iscomplete = false;
            int nofires = 0;
            int iteration =0;
          int propagation= 0;
          int type = 0;
          int eur= 0;
          while(iscomplete == false){
            switch(type){

                case 0:
                
                    for(int i = 0; i < Main.inputNeuralArray.length; i++){
                        if(Main.inputNeuralArray[i].alreadyfired == false){
                        Main.inputNeuralArray[i].checkinput();
                        propagation = Main.inputNeuralArray[i].fired;

                        eur= Main.inputNeuralArray[i].pairedaxon;
                      //  Main.axonArray[eur].neuroncheck();
                        Main.axonArray[eur].fired = Main.inputNeuralArray[i].fired;
                       
                            nofires++;
                        }
                    }
                    type++;
                    break;
                case 1:
                    for(int k = 0; k < Main.outputNeuralArray.length; k++){
                        if(Main.outputNeuralArray[k].alreadyfired == false){

                    Main.outputNeuralArray[k].firecheck();
                    eur = Main.outputNeuralArray[k].pairedaxon;
                    Main.axonArray[eur].neuroncheck();
                            nofires++;
                        }

                    }
                    type++;
                    break;
                case 2:
                    for(int e = 0; e < Main.neuralArray.length; e++){
                        if(Main.neuralArray[e].alreadyfired == false){
                      Main.neuralArray[e].firecheck();
                      eur = Main.neuralArray[e].pairedaxon;
                      Main.axonArray[eur].neuroncheck();
                            nofires++;
                        }
                    }
                    type++;
                      iscomplete = true;

                    }
             

          }
            
        }
   
        public static void neuraltick(){
          int iteration= 0;
          boolean iscomplete =false;
            int brainlength = 4;
          while(iscomplete == false){

            if(iteration > 3){
              iscomplete = true;
            }
              for(int y = 0; y < brainlength; y++ ){
            neuronaddcheck();
            fireallneurons();
              }
              resetallneurons();
              
            iteration++;

          }

        }
        public static void testinputs(){
          Main.inputNeuralArray[0].input = 1010;
          Main.inputNeuralArray[1].input = 10101;

        }
    }
