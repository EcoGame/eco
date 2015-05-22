package eco.game;

public class SaveTask implements Runnable {
	
	/**
	 * This class is an implementation of <i>Runnable</i> and is used to
	 * asynchronously save the world.
	 * 
	 * @author phil
	 * 
	 */

	@Override
	public void run() {
		Util.createSave();
	}

}
