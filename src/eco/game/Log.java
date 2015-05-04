package eco.game;

import java.util.ArrayList;

public class Log {
	
	private static ArrayList<String> log = new ArrayList<String>();
	
	private static boolean instant = true;
	
	public static void log(int year, String message){
		message = "["+year+"] "+message;
		if (instant){
			System.out.println(message);
		}
			log.add(message);
	}
	
	public static void dump(){
		for (String s : log){
			System.out.println(s);
		}
	}

}
