package eco.game;

import java.util.ArrayList;

/**
 * This class defines a message with a location and duration.
 * 
 * @author phil
 * 
 */

public class Message {

	private String message;
	private float x;
	private float y;
	private int time;

	private static ArrayList<Message> messages = new ArrayList<Message>();

	public Message(String message, float x, float y) {
		this.message = message;
		this.x = x;
		this.y = y;
		time = 60 * 6;
	}

	public Message(String message, float x, float y, int time) {
		this.message = message;
		this.x = x;
		this.y = y;
		this.time = time;
	}

	public static ArrayList<Message> getMessages() {
		for (Message m : messages) {
			m.time--;
		}
		for (int i = 0; i < messages.size(); i++) {
			if (messages.get(i).time <= 0) {
				messages.remove(i);
			}
		}
		return messages;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public String getMessage() {
		return message;
	}

	public static void addMessage(Message message) {
		messages.add(message);
	}

}
