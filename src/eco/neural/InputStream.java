package eco.neural;
import  eco.game;
public class InputStream{

public static int readinput(int currentnetwork,int id){
int k = 0;
switch(id){

    case 0: 
        k = NeuralManager.Country[currentnetwork].getfPop();
        return k;
    break;

    case 1:
        k = NeuralManager.Country[currentnetwork].getwPop();
        return k;
        break;
    case 2:
        k = NeuralManager.Country[currentnetwork].gettWheat();
        return k;
        break;
                                  
        case 3:
        NeuralManager.Country[currentnetwork].getTreasury();
        break;
        
    case 4:
}

return 0;
}







}
