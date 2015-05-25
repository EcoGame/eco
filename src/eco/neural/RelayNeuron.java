package eco.neural;

/**
 * This class does something
 *
 * @author will
 *
 */

public class RelayNeuron {

	public int fireValue = 1;
	public int id;
	public int pairedAxon;
	public int fired = 0;
	public int currentValue;
	public boolean alreadyFired = false;

	public void addToCurrent(int x) {

		currentValue = currentValue + x;
	}

	public int fireCheck() {
	//	System.out.println("RelayCurrentvalue" + currentValue + "id" + id + "paired axon" +pairedAxon);
		if (currentValue > fireValue) {
			fired = 1;
          //  System.out.println("df");
			// System.out.println("relayneuron " + id + " pairedaxon "+
			// pairedaxon + " current value "+ currentvalue); alreadyfired =
			// true;
		}
		return fired;
	}

	public void reset() {
		currentValue = 0;
		fired = 0;
		alreadyFired = false;
	}
}
