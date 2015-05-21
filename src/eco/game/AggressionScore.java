package eco.game;

public class AggressionScore {
	
	public int value;
	
	private static int min = 0;
	private static int max = 100;
	
	public AggressionScore(){
		value = Util.randInt(0, max / 10);
	}
	
	public void update(){
		
		
		value = Math.max(min, value);
		value = Math.min(max, value);
	}
			

}
