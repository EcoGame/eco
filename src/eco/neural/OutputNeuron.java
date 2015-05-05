package neural;

/**
 * This class does something
 *
 * @author will
 *
 */

public class OutputNeuron {

    public int firevalue = 2;
    public int currentvalue;
    public int fired;
    public int id;
    public int pairedaxon;
    public int action = 0;
    public boolean alreadyfired = false;

    public void addToCurrent(int x) {

        currentvalue = x + currentvalue;
    
    }
    
    public int firecheck() {
        if (currentvalue > firevalue) {
            fired = 1;
            //System.out.println("outputneuron " + id + " pairedaxon "+ pairedaxon + " current value "+ currentvalue);
            alreadyfired = true;
            OutputActions.actions(action);
        }
        return fired;
    }
    
    public void reset() {
        currentvalue = 0;
        fired = 0;
        alreadyfired = false;
    }
    
}
