package eco;

public class Message {

	public String message;
	public float x;
	public float y;
	public int time;
	
	public Message(String message, float x, float y){
		this.message = message;
		this.x = x;
		this.y = y;
		time = 60 * 6;
	}

	public Message(String message, float x, float y, int time){
		this.message = message;
		this.x = x;
		this.y = y;
		this.time = time;
	}
	
	
}
