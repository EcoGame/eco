package neural;

public class InputNeuron{
public int input;
public int firevalue;
public int fired;
public int id;
public int pairedaxon;
public int currentvalue;
    public boolean alreadyfired = false;
public boolean highorlow = true; // true = high false = low

public void checkinput(){
    
fired = 0;
    if(highorlow == true){
        if(input > firevalue){
        fired = 1;
            
        }
    } else{
        if(input < firevalue){
        fired = 0;
        }

    }
    alreadyfired = true;
}
    public void reset(){
        currentvalue = 0;
        fired = 0;
        alreadyfired = false;
    }
}
