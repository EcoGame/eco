package neural;
public class ConnectionHandler{

public static void connectionmaker(int k, int type){
    switch(type){
  case 0:
/*neuronid = neuronid - Main.INPUTIDOFFSET;

for(int x= 0; x< Main.axonArray[neuronid].connections.length;  x++){
for(int q= 0; q< Main.axonArray[neuronid].connections[x].length; q++){
  Main.axonArray[neuronid].connections[x][q]= Main.axonArraytofill[type][neuronid][x][q];

}
}*/
            
            break;


    case 1:
            int u=k-10;
         
for(int x= 0; x< Main.axonArray[k].connections.length;  x++){
for(int q= 0; q< Main.axonArray[k].connections[x].length; q++){
  Main.axonArray[k].connections[x][q]= Main.axonArraytofill[type][u][x][q];
    
}
}

      break;

    case 2:
             u =k-20;
           
for(int x= 0; x< Main.axonArray[k].connections.length;  x++){
for(int q= 0; q< Main.axonArray[k].connections[x].length; q++){
  Main.axonArray[k].connections[x][q]= Main.axonArraytofill[type][u][x][q];
    
}
}

      break;





}




}
}
