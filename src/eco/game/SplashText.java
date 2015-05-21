package eco.game;

import java.util.Random;

public class SplashText {
	
	private static String[] splashes;
	
	private static String splash = "";
	
	static{
		splashes = new String[]{
				"95% Stabe",
				"Thousands of bugs!",
				"Artifical but not intelligent",
				"java.lang.nullPointerException: splash not found. At: SplashText.java(15)",
				"10,000 lines!",
				"Multicultural!",
				"Just like Minecraft",
				"Your turn to be a conquistador!",
				"How many of these are there?",
				"Font schmont! Who cares if you can read it?",
				"What is the difference between snowmen and snowwomen? Snowballs.",
				"How do astronomers organize a party? They planet.",
				"I went to the bank the other day and asked the banker to check my balance, so she pushed me!",
				"Did you hear about the kidnapping at school? It's okay. He woke up.",
				"If you ever get cold, just stand in the corner of a room for a while. They're normally around 90 degrees.",
				"I was wondering why the ball kept getting bigger and bigger, and then it hit me.",
				"What do cars eat on their toast? Traffic jam.",
				"Et tu, Brute?",
				"Artificial intelligence usually beats real stupidity.",
				"Gotta catch 'em all!",
				"Why is wood made out of splinters?",
				"Connecting to the NSA...",
				"Persistent pain is something that conflicted with his cosy picture of the world.",
				"Does fuzzy logic tickle?",
				"Relax, it's only ones and zeros",
				"You're lucky if I can read my own code",
				"Computers are like air conditioners, they stop working properly if you open Windows.",
				"'Lemme just draw it on the board.'",
				"Never trust an operating system you don't have sources for.",
				"Windows - The best $89 solitaire game you can buy",
				"This is a P. Heikompf production",
				"Did you hear about the Italian chef that died? Yeah, he pasta way."
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
