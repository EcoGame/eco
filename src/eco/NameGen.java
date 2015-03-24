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
			//"w",
			//"x",
			//"z",
			"th",
			"sh",
			"ch",
			"ph",
			"phil",
			"ing"
		
		};
		
		public static String[] vowels = new String[] {
			"a",
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
			"grad",
			"bad",
			"ville",
			"stead",
			" On the Sea",
			"land",
			"wood",
			"field",
			"shire",
			"burg",
			"furt",
			"ham",
			"dale",
			"port",
			"ford",
			"ia",
			"mare",
			"polis",
			"ago",
			"uim"
			
		
		};
		
		public static String[] spanishAdjectives = new String[]{
			"blancos",
			"amarillos",
			"dorados",
			"gordos",
			"pequenos",
			"grandes",
			"llenos"
		};
		
		public static String[] spanishWordsO = new String[]{
			"gato",
			"globo",
			"alto",
			"asalto",
			"pato",
			"inodoro",
			"malo",
			"aguacate",
			"chicle",
			"bajo",
			"juego",
			"jugo",
			"rayo",
			"ojo",
			"rojo",
			"lago",
			"camino",
			"infierno",
			"fuego",
			"rio",
			"lobo",
			"oro",
			"torrente",
			"veneno",
			"pulpo",
			"caballero",
			"pajaro",
			"plato",
			"trono",
			"mundo"
			
			
		};
		
		public static String[] spanishWordsA = new String[] {
			"nube",
			"pintura",
			"nada",
			"tierra",
			"ropa",
			"mantequilla",
			"iglesia",
			"mano",
			"montana",
			"tortilla",
			"ola",
			"ardilla", //squirrel
			"vibora", //viper
			"ven",
			"bruja",
			"espada",
			"piedra",
			"joya",
			"corona"
			
		};

		public static Random random = new Random();
		
		
	//public static void main(String[] args){
	//	list(20);
		
	//}	
	public static void list(int loops){
		for(int i = 0; i < loops; i++){
			System.out.println(generateRandom());
		}
		System.out.println("===== DONE!!! ======");
	
	
	}
	
	
	

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
    	return min + random.nextInt((max + 1) - min);
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
	
	public static String generateRandom(){
		String name = "";
		int num = randInt(0,2);
		switch (num){
			case 1: name = generateSpanish();
					break;
			case 2: name = generateSyllables();
					break;
		}
		return name;
	}
	
	public static String generateSpanish(){
		boolean gender = random.nextBoolean();
		boolean plurality = random.nextBoolean();
		boolean adjective = random.nextBoolean();
		int num = randInt(1, spanishAdjectives.length - 1);
		String name = "";
		if(gender){
			if(plurality){
				name = "Los " + spanishWordsO[randInt(1, spanishWordsO.length - 1)] + "s";
				name = name.substring(0,4) + name.substring(4,5).toUpperCase() + name.substring(5);
				if(adjective){
					name = name + " " + spanishAdjectives[num].substring(0,1).toUpperCase() + spanishAdjectives[num].substring(1);
				}
			}
			else{
				name = "El " + spanishWordsO[randInt(1, spanishWordsO.length - 1)];
				name = name.substring(0,3) + name.substring(3,4).toUpperCase() + name.substring(4);
				if(adjective){
					name = name + " " + spanishAdjectives[num].substring(0,1).toUpperCase() + spanishAdjectives[num].substring(1, spanishAdjectives[num].length() - 1);
				}
			}
		}
		else{
			if(plurality){
				name = "Las " + spanishWordsA[randInt(1, spanishWordsA.length - 1)] + "s";
				name = name.substring(0,4) + name.substring(4,5).toUpperCase() + name.substring(5);
				if(adjective){
					name = name + " " + spanishAdjectives[num].substring(0,1).toUpperCase() + spanishAdjectives[num].substring(1, spanishAdjectives[num].length() - 2) + "as";
				}
			}
			else{
				name = "La " + spanishWordsA[randInt(1, spanishWordsA.length - 1)];
				name = name.substring(0,3) + name.substring(3,4).toUpperCase() + name.substring(4);
				if(adjective){
					name = name + " " + spanishAdjectives[num].substring(0,1).toUpperCase() + spanishAdjectives[num].substring(1, spanishAdjectives[num].length() - 2) + "a";
				}
			}	
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		return name;
	}
	
	public static String generateSyllables(){
		int length = randInt(1,2);
		String name = "";
		for(int i = 0; i <= length; i++){
			if(i == 0)
				name = name + consonants[randInt(1, consonants.length - 1)] + vowels[randInt(0, vowels.length - 1)];
			else
				name = name + consonants[randInt(1, consonants.length - 1)] + vowels[randInt(0, vowels.length - 1)];
		}
		name = name.substring(0,1).toUpperCase() + name.substring(1);
		int chance = (randInt(1,100));
		if(chance < 25){
			name = name + suffixes[randInt(0, suffixes.length - 1)];
		}
		else if (chance >= 25 && chance < 75){
			name = name + consonants[randInt(0, consonants.length -1)];
		}
		return name ;
	}
}
