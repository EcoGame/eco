package eco.game;

import java.util.Random;

public class SplashText {
	
	private static String[] splashes;
	
	private static String splash = "";
	
	static{
		splashes = new String[]{
				"95% Stable",
				"Thousands of bugs!",
				"Artifical but not intelligent",
				"java.lang.nullPointerException: splash not found. At: SplashText.java(15)",
				"10,000 lines!",
				"Just like Minecraft",
				"Your turn to be a conquistador!",
				"How many of these are there?",
				"Font schmont! Who cares if you can read it?",
				"Multicultural!"
		};
	}
	
	public static void newSplash(){
		Random random = new Random();
		splash =  splashes[random.nextInt(splashes.length)];
	}
	
	public static String getSplash(){
		return splash;
	}

}
