package eco.neural;

/**
 * A class that handles input
 *
 * @author will
 *
 */

public class InputNeuron {

    public int input;
    public int fireValue;
    public int fired;
    public int id;
    public int pairedAxon;
    public int stream;
    public int currentValue;
    public boolean alreadyFired = false;
    public boolean highOrLow = true; // true = high false = low

    public void checkInput(int currentnetwork) {
        input = InputStream.readInput(currentnetwork, stream);
       // System.out.println("stream" + stream);
        //System.out.println("input" + input);
        fired = 0;
        if (highOrLow == true) {
            if (input > fireValue) {
                fired = 1;
              //  System.out.println("fire high");

            }else{
                fired = 0;
            }

        } else {
            if (input < fireValue) {
                fired = 1;
            } else {
                fired = 0;
            }
        }

        alreadyFired = true;
    }

    public void reset() {
        input = 0;
        currentValue = 0;
        fired = 0;
        alreadyFired = false;

    }
}
