package eco.game;

/**
*
* This class does splash texts
*
* @Author Phil
*
**/


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
                "Multicultural!",
                "Wait wait wait, is it a simulation or a game?",
                "Go inject your bubby somewhere else",
                "Your turn to be a conquistador!",
                "How many of these are there?",
                "Font schmont! Who cares if you can read it?",
                "Et tu, Brute?",
                "Artificial intelligence usually beats real stupidity.",
                "Connecting to the NSA...",
                "Does fuzzy logic tickle?",
                "Relax, it's only ones and zeros",
                "You're lucky if I can read my own code",
                "'Lemme just draw it on the board.'",
                "Never trust an operating system you don't have sources for.",
                "This is a P. Heikompf production",

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
