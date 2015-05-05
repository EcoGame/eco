package neural;

/**
 * This class does something
 *
 * @author will
 *
 */

public class RelayNeuron {
    
    public int firevalue =1;
    public int id;
    public int pairedaxon;
    public int fired = 0;
    public int currentvalue;
    public boolean alreadyfired = false;

    public void addToCurrent(int x){

        currentvalue =  currentvalue + x;
    }

    public int firecheck() {
        
        if (currentvalue > firevalue) {
            fired = 1;
            //System.out.println("relayneuron " + id + " pairedaxon "+ pairedaxon + " current value "+ currentvalue);    alreadyfired = true;
        }
        return fired;
    }
    
    public void reset() {
        currentvalue = 0;
        fired = 0;
        alreadyfired = false;
    }
}
