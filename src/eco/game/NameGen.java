package eco.game;

import java.util.Random;

/**
 * 
 * This class generates random names
 * 
 * @author nate
 * 
 */

/* Name of the class has to be "Main" only if the class is public. */
public class NameGen {

	public static String[] uno = new String[] { "new", "wam", "will",
			"notting", "burn"

	};
	public static String[] dos = new String[] { "ton", "stead", "ham", "land",
			"ness", "port"

	};
	public static String[] tres = new String[] { "ton", "stead", "ham", "new",
			"land", "ness", "port", "arden", "wam", "burn", "don", "phil",
			"will"

	};
	public static String[] consonants = new String[] { "b", "c", "d", "f", "g",
			"k", "l", "m", "n", "n", "n", "n", "p", "q", "r", "s", "t", "r",
			"s", "t", "r", "s", "t", "r", "s", "t", "v",
			// "w",
			// "x",
			// "z",
			"th", "sh", "ch", "ph", "phil", "ing"

	};

	public static String[] vowels = new String[] { "a", "e", "e", "e", "i",
			"o", "u", "y", "a", "e", "e", "i", "o", "u", "y", "oo", "ue", "ee",
	// "ai"

	};

	public static String[] suffixes = new String[] { "stan", "grad", "bad",
			"ville", "stead", " On the Sea", "land", "wood", "field", "shire",
			"burg", "furt", "ham", "dale", "port", "ford", "ia", "mare",
			"polis", "ago", "uim"

	};

	public static String[] spanishAdjectives = new String[] { "blancos",
			"amarillos", "dorados", "gordos", "pequenos", "grandes", "altos",
			"llenos" };

	public static String[] spanishWordsO = new String[] { "gato", "globo",
			"asalto", "pato", "inodoro", "malo", "aguacate", "chicle", "bajo",
			"juego", "jugo", "rayo", "ojo", "rojo", "lago", "camino",
			"infierno", "fuego", "rio", "lobo", "oro", "torrente", "veneno",
			"pulpo", "caballero", "pajaro", "plato", "trono", "abalorio",
			"abandono", "ajo", "banco", "bando", "borujo", "cacho",
			"cigarillo", "churro", "dinero", "durazno", "enimigo", "epico",
			"exclusivo", "mercado", "mercado", "burro", "fracaso", "conflicto",
			"turbulento", "cero", "mundo"

	};

	public static String[] spanishWordsA = new String[] { "nube", "pintura",
			"nada", "tierra", "ropa", "mantequilla", "iglesia", "mano",
			"montana",
			"tortilla",
			"ola",
			"ardilla", // squirrel
			"vibora", // viper
			"ven", "bruja", "espada", "piedra", "joya", "babosa", "calavera",
			"defensa", "elitista", "mesa", "medusa", "abeja", "paloma",
			"calabaza", "soldadura", "corona"

	};

	public static String generateRandom() {
		String name = "";
		int num = randInt(1, 10);
		switch (num) {
		case 1:
			name = generateSpanish(); // + ".es";
			break;
		case 2:
			name = generateSyllables(); // + ".uz";
			break;
		case 3:
			name = generateJapanese(); // + ".jp";
			break;
		default:
			num = randInt(1, 2);
			if (num == 1) {
				name = generateJapanese();
			}
            name = generateSyllables();
		}
		return name;
	}

	public static String generateSpanish() {
		boolean gender = random.nextBoolean();
		boolean plurality = random.nextBoolean();
		boolean adjective = random.nextBoolean();
		int num = randInt(1, spanishAdjectives.length - 1);
		String name = "";
		if (gender) {
			if (plurality) {
				name = "Los "
						+ spanishWordsO[randInt(1, spanishWordsO.length - 1)]
						+ "s";
				name = name.substring(0, 4)
						+ name.substring(4, 5).toUpperCase()
						+ name.substring(5);
				if (adjective) {
					name = name
							+ " "
							+ spanishAdjectives[num].substring(0, 1)
									.toUpperCase()
							+ spanishAdjectives[num].substring(1);
				}
			} else {
				name = "El "
						+ spanishWordsO[randInt(1, spanishWordsO.length - 1)];
				name = name.substring(0, 3)
						+ name.substring(3, 4).toUpperCase()
						+ name.substring(4);
				if (adjective) {
					name = name
							+ " "
							+ spanishAdjectives[num].substring(0, 1)
									.toUpperCase()
							+ spanishAdjectives[num].substring(1,
									spanishAdjectives[num].length() - 1);
				}
			}
		} else {
			if (plurality) {
				name = "Las "
						+ spanishWordsA[randInt(1, spanishWordsA.length - 1)]
						+ "s";
				name = name.substring(0, 4)
						+ name.substring(4, 5).toUpperCase()
						+ name.substring(5);
				if (adjective) {
					name = name
							+ " "
							+ spanishAdjectives[num].substring(0, 1)
									.toUpperCase()
							+ spanishAdjectives[num].substring(1,
									spanishAdjectives[num].length() - 2) + "as";
				}
			} else {
				name = "La "
						+ spanishWordsA[randInt(1, spanishWordsA.length - 1)];
				name = name.substring(0, 3)
						+ name.substring(3, 4).toUpperCase()
						+ name.substring(4);
				if (adjective) {
					name = name
							+ " "
							+ spanishAdjectives[num].substring(0, 1)
									.toUpperCase()
							+ spanishAdjectives[num].substring(1,
									spanishAdjectives[num].length() - 2) + "a";
				}
			}
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	public static String[] noveltyPrefixes = new String[] { "Connor's",
			"Phil's", "Nate's", "Will's", "Mr. Drew's", "Greenspan",
			"Krugman's"

	};

	public static String[] noveltySuffixes = new String[] { "of Bailey",
			"of Heikoop", "of Delgado", "of Sidley-Parker", "de Leon"

	};

	public static String[] castleSuffixes = new String[] { "fard", "ton",
			"wood", "ham", "hampton", "bury", "ester", "aster", "chester",
			"seed", "hurst", "ville", "stow", "don", "worth", "aple",
			"minster", "more", "roth", "wick", "port", "field", "dill", "sham",
			"hollow", "borough", "dale", "pass", "cove", "tail", "cliff",
			"born", "borne", "bourne", "marsh", "firth", "isle", "broth",
			"well", "mouth", "fall", "lock", "shop", "shoppe", "shoppe" };

	public static String[] castlePrefixes = new String[] { "wolf", "deer",
			"weasal", "sea", "grist", "axe", "cone", "hammer",

			"strath", "mill", "horn", "stone", "west", "north", "south",
			"beck", "wake", "holly", "white", "black", "blue", "grey",
			"silver", "iron", "yellow", "brown", "gold", "bronze", "bull",
			"finch", "dragon", "draco", "trout", "dry", "sharp", "cray",
			"long", "short", "aber", "ruther", "cardinal", "arrow", "dagger",
			"spears", "cannon", "pike", "siege", "boat", "salt", "bell",
			"dire", "hill", "leaf", "vine", "tree", "ivy", "garth", "apple",
			"ample", "skag", "smoke", "berx", "wind", "star", "sun", "moon",
			"frost", "bleak", "air", "brae", "dawn", "weld", "hull", "hell",
			"saxon", "aqua", "death", "mir", "warring", "ram", "deer",
			"lizard", "card", "fire", "flame", "blaze", "char", "ember",
			"flare", "ox", "mag", "veil", "vail", "mount", "valley", "heaven",
			"stein",

			"fortran", "cobol",

			"tiger"

	};

	public static String[] castleSynonyms = new String[] { "stronghold",
			"acropolis", "citadel", "fortress", "establishment",
			"fortification", "keep", "hold", "manor", "mansion", "palace",
			"safehold", "villa", "tower", "bastion", "castle", "garrison",
			"rampart",

			"abode", "residency", "estate"

	};

	public static String[] castlePreSynonyms = new String[] { "fort", "camp",
			"castle"

	};

	public static String[] castleAdjectives = new String[] { "big", "great",
			"soaring", "towering", "elevated", "giant", "skyscraping",

			"shining", "glimmering", "lustrous", "radiant",

			"immortal", "enduring", "everlasting", "imperishable",
			"indestructible", "invincible", "invulnerable", "impenetrable",

			"decrepit", "deteriorated", "crippled", "battered",
			"weather-beaten"

	};

	public static String[] japaneseKatakana = new String[] { "a", "e", "i",
			"o", "u", "ya", "yu", "yo", "ka", "ki", "ku", "ke", "ko", "sa",
			"si", "su", "se", "so", "ta", "ti", "tu", "te", "to", "na", "ni",
			"nu", "ne", "no", "ha", "hi", "hu", "he", "ho", "ma", "mi", "mu",
			"me", "mo", "ra", "ri", "ru", "re", "ro", "wa", "wi", "wu", "we",
			"wo", "ga", "gi", "gu", "ge", "go", "za", "zi", "zu", "ze", "zo",
			"da", "di", "du", "de", "do"

	};

	public static String[] historicalLeaders = new String[] { "Napoleon",
			"Genghis", "Pizarro", "Pachacuti", "Claudius", "Ghandi", "Mandela",
			"Alexander the Great", "Solomon", "Washington", "Henry VIII", "Pedro II",
			"Suleiman the Magnificent", "Theodora",

	};

	public static Random random = new Random();

	public static void list(int loops, int choice) {
		if (choice == 1) {
			for (int i = 0; i < loops; i++) {
				System.out.println(generateRandom());
			}
		}
		System.out.println("\n\n\n\n");
		for (int i = 0; i < loops; i++) {
			System.out.println(generateCastle());
		}

		System.out.println("===== DONE!!! ======");

	}

	public static String generateCastle() {
		int choice = randInt(1, 20);
		int otherChoice = randInt(1, 20);
		int otherOtherChoice = randInt(1, 6);
		int preOrSuff = randInt(1, 10);
		int historical = randInt(1, 2);
		String name = "";
		if (choice == 1 && otherOtherChoice > 1 && otherChoice > 1) {
			int num = randInt(1, noveltyPrefixes.length - 1);
			name = name + noveltyPrefixes[num].substring(0, 1).toUpperCase()
					+ noveltyPrefixes[num].substring(1) + " ";
		}
		if (historical == 1) {
			if (choice == 2 && otherOtherChoice > 1 && otherChoice > 1) {
				int num = randInt(1, historicalLeaders.length - 1);
				name = name + "The "
						+ historicalLeaders[num].substring(0, 1).toUpperCase()
						+ historicalLeaders[num].substring(1) + " ";
				num = randInt(1, castleSynonyms.length - 1);
				name = name + castleSynonyms[num].substring(0, 1).toUpperCase()
						+ castleSynonyms[num].substring(1) + " ";
				return name;
			}
		}
		if (otherOtherChoice == 1) {
			int num = randInt(1, castleAdjectives.length - 1);
			name = name + castleAdjectives[num].substring(0, 1).toUpperCase()
					+ castleAdjectives[num].substring(1) + " ";
			num = randInt(1, castleSynonyms.length - 1);
			name = name + castleSynonyms[num].substring(0, 1).toUpperCase()
					+ castleSynonyms[num].substring(1) + " ";
			return name;
		}
		if (preOrSuff == 1) {
			int num = randInt(1, castlePreSynonyms.length - 1);
			name = name + castlePreSynonyms[num].substring(0, 1).toUpperCase()
					+ castlePreSynonyms[num].substring(1) + " ";
		}
		int num = randInt(1, castlePrefixes.length - 1);
		name = name + castlePrefixes[num].substring(0, 1).toUpperCase()
				+ castlePrefixes[num].substring(1);
		num = randInt(1, castleSuffixes.length - 1);
		name = name + castleSuffixes[num] + " ";
		if (preOrSuff > 1) {
			num = randInt(1, castleSynonyms.length - 1);
			name = name + castleSynonyms[num].substring(0, 1).toUpperCase()
					+ castleSynonyms[num].substring(1) + " ";
		}
		if (otherChoice == 1 && otherOtherChoice > 1 && choice > 2) {
			num = randInt(1, noveltySuffixes.length - 1);
			name = name + noveltySuffixes[num].substring(0, 1).toUpperCase()
					+ noveltySuffixes[num].substring(1);
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	/*
	 * public static void main (String[] args) throws java.lang.Exception {
	 * System.out.println("Ten random names below!");
	 * 
	 * for(int i = 0; i < 10; i++){ //System.out.println(generate(uno, dos,
	 * tres)); System.out.println(generateSyllables(consonants, vowels,
	 * suffixes)); }
	 * 
	 * }
	 */
	public static int randInt(int min, int max) { // Returns a random number
													// between min and max.
		return min + random.nextInt((max + 1) - min);
	}

	public static String generate() {
		int length = randInt(0, 1);
		String name = "";
		for (int i = 0; i <= length; i++) {
			if (i == 0)
				name = name + uno[randInt(1, uno.length - 1)];
			else
				name = name + dos[randInt(1, dos.length - 1)];
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}

	public static String generateSyllables() {
		int length = randInt(1, 2);
		String name = "";
		for (int i = 0; i <= length; i++) {
			if (i == 0)
				name = name + consonants[randInt(1, consonants.length - 1)]
						+ vowels[randInt(0, vowels.length - 1)];
			else
				name = name + consonants[randInt(1, consonants.length - 1)]
						+ vowels[randInt(0, vowels.length - 1)];
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		int chance = (randInt(1, 100));
		if (chance < 25) {
			name = name + suffixes[randInt(0, suffixes.length - 1)];
		} else if (chance >= 25 && chance < 75) {
			name = name + consonants[randInt(0, consonants.length - 1)];
		}
		return name;
	}

	public static String generateJapanese() {
		int length = randInt(1, 3);
		String name = "";
		for (int i = 0; i <= length; i++) {
			name = name + japaneseKatakana[randInt(1, japaneseKatakana.length - 1)];
		}
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		return name;
	}
    
    public static String generateWarWin(){
       String[] victorySlang = new String[] { "annihilated",
			"slaughetered", "exterminated", "obliterate", "butchered", "executed", "t-bagged", "massacred", "killed off", "stamped out", "won the war against", "won the fight against", "destroy", "eradicated", "neutralized", "erased", "abolished", "strangled every citizen of", "wasted", "wiped out"};
        return victorySlang[randInt(0, victorySlang.length)];
    }
    
    public static String generateWarLoss(){
        String[] lossSlang = new String[] {"perished by the sword of", "expired during the war with", "succumbed to", "met its demise in the war with", "was ruined by", "is now in ruins after war with", "lost the war with", "was unsuccessful in the war with", "failed in the war with", "crumbled in the war with", "fell in the war with"};
        return lossSlang[randInt(0, lossSlang.length)];
    }
    
    public static String generateCountry(){
        String[] fictionalPlaces = new String[] {"Atlantis", "Arstotzka", "Narnia", "Babar's Kingdom", "Elbonia", "Hundred Acre Wood", "The Shire", "Grand Fenwick", "Hyrule", "Kyrat", "Loompa Land", "Mushroom Kingdom", "Oceania", "Oz", "Panem", "Rook Island", "Shangri-La", "Smallville", "Gotham", "Mos Eisley", "Emerald City", "Springfield", "South Park", "Los Santos", "El Dorado", "Essos", "Westeros", "King's Landing", "Hogwarts", "Whoville", "Neverland", "Bikini Bottom", "Pandora", "Wonderland", "Mister Roger's Neighborhood", "Camelot", "Utopia"};
        return fictionalPlaces[randInt(0, fictionalPlaces.length - 1)];
            
    }
    
        
        
}
