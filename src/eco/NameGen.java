package eco;

import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class NameGen {

		public static String[] uno = new String[] {
			"new",
			"wam",
			"phil",
			"will",
			"notting",
			"burn"
			
		};
		public static String[] dos = new String[] {
			"ton",
			"stead",
			"ham",
			"land",
			"ness",
			"port"
			
		};
		public static String[] tres = new String[] {
			"ton",
			"stead",
			"ham",
			"new",
			"land",
			"ness",
			"port",
			"arden",
			"wam",
			"burn",
			"don",
			"phil",
			"will"
			
		};
		public static String[] consonants = new String[] {
			"b",
			"c",
			"d",
			"f",
			"g",
			"j",
			"k",
			"l",
			"m",
			"n",
			"n",
			"n",
			"n",
			"p",
			"q",
			"r",
			"s",
			"t",
			"r",
			"s",
			"t",
			"r",
			"s",
			"t",
			"r",
			"s",
			"t",
			"v",
			"w",
			"x",
			"z",
			"th",
			"sh",
			"ch",
			"phil",
			"ing"
		
		};
		
		public static String[] vowels = new String[] {
			"a",
			"e",
			"e",
			"e",
			"e",
			"i",
			"o",
			"u",
			"y",
			"oo",
			"ue",
			"ee",
			"ai"
			
		
		};
		
		public static String[] suffixes = new String[] {
			"stan",
			"grad"
		
		};

		public static Random random = new Random();
	
	
	

	/*public static void main (String[] args) throws java.lang.Exception
	{
		System.out.println("Ten random names below!");
		
		for(int i = 0; i < 10; i++){
			//System.out.println(generate(uno, dos, tres));
			System.out.println(generateSyllables(consonants, vowels, suffixes));
		}

	}
	*/
	public static int randInt(int min, int max) { //Returns a random number between min and max.
    	return min + random.nextInt((max + 1)- min);
	}
	public static String generate(){
		int length = randInt(0,1);
		String name = "";
		for(int i = 0; i <= length; i++){
			if(i == 0)
				name = name + uno[randInt(1, uno.length - 1)];
			else
				name = name + dos[randInt(1, dos.length - 1)];
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		return name ;
	}
	
	public static String generateSyllables(){
		int length = randInt(1,3);
		String name = "";
		for(int i = 0; i <= length; i++){
			if(i == 0)
				name = name + consonants[randInt(1, consonants.length - 1)] + vowels[randInt(0, vowels.length - 1)];
			else
				name = name + consonants[randInt(1, consonants.length - 1)] + vowels[randInt(0, vowels.length - 1)];
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		if(randInt(1,100) > 75)
			name = name + suffixes[randInt(0, suffixes.length - 1)];
		return name ;
	}
}
