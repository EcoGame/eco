package eco;

import java.util.ArrayList;
import java.util.Random;

public class World {

	public static int mapscale = 6;
	public static int mapsize = (int) Math.pow(2, mapscale);
	public static short[][] map = new short[mapsize][mapsize];
	public static short[][] structures = new short[mapsize][mapsize];

	public static long mapseed = "seeds arentr kool".hashCode();

	public static double perlinbias;

	public static ArrayList<Message> messages = new ArrayList<Message>();

	public static int population = 0;
	public static int farmers = 0;
	public static int warriors = 0;

	public static void generate(){

		perlinbias = (double) ((float) mapsize / (float) mapseed);

		map = new short[mapsize][mapsize];
		structures = new short[mapsize][mapsize];

		map = new short[mapsize][mapsize];

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				float value = (float) Perlin.noise((x + perlinbias) / (float) mapsize * 2, (y + perlinbias) / (float) mapsize * 2, 8);
				float dist = (float) getDistanceFromCenter(x, y);
				float bias = 256f * (1f - (dist / (mapsize / 2f)));
				value -= bias;
				if ( value <= 64f){
					map[x][y] = 0;
				}
				else {
					map[x][y] = 1;
				}
				if (x <= 4 || x >= mapsize - 4){
					map[x][y] = 0;
				}
				if (y <= 4 || y >= mapsize - 4){
					map[x][y] = 0;
				}
			}
		}

		Random random = new Random();

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (random.nextInt(100) == 0) {
					if (map[x][y] != 0 && structures[x][y] == 0) {
						//structures[x][y] = 1;
					}
				}
				if (random.nextInt(200) == 0) {
					if (map[x][y] != 0 && structures[x][y] == 0) {
						//structures[x][y] = 2;
					}
				}
			}
		}
	}

	public static boolean isValid() {
		for (int x = 0; x < mapsize; x++) {
			if (map[x][0] != 0){
				return false;
			}
		}
		for (int x = mapsize - 1; x >= 0; x--) {
			if (map[x][0] != 0){
				return false;
			}
		}
		for (int y = 0; y < mapsize; y++) {
			if (map[0][y] != 0){
				return false;
			}
		}
		for (int y = mapsize - 1; y >= 0; y--) {
			if (map[0][y] != 0){
				return false;
			}
		}

		float landmax = 0.7f;
		float landmin = 0.3f;

		int total = mapsize * mapsize;
		int land = 0;
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] != 0){
					land ++;
				}
			}
		}
		float ratio = (float) land / (float) total;
		if (ratio > landmax || ratio < landmin) {
			return false;
		}
		return true;
	}

	public static int updatePop(int pop) {

		boolean dec = (pop < population);
		Random random = new Random();

		if (dec) {
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++){
				for (int y = 0; y < mapsize; y++){
					if (map[x][y] == 1 && structures[x][y] == 1){
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int decrease = population - pop;
			for (int i = 0; i < population - pop && validLocs.size() > 0; i++) {
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				structures[loc.x][loc.y] = 0;
				validLocs.remove(loc);
				decrease--;
			}
			population = pop;
			return decrease;

		}
		if (!dec) {
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1 && structures[x][y] == 0) {
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int increase = pop - population;
			for (int i = 0; i < pop - population && validLocs.size() > 0; i++) {
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				structures[loc.x][loc.y] = 1;
				validLocs.remove(loc);
				increase--;
			}
			population = pop;
			return increase;
		}
		population = pop;
		return 0;
	}
	public static int updateFarms(int farms) {
		boolean dec = (farms < farmers);
		Random random = new Random();

		if (dec) {
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++){
				for (int y = 0; y < mapsize; y++){
					if (map[x][y] == 1 && structures[x][y] == 0){
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int decrease = farmers - farms;
			for (int i = 0; i < farmers - farms && validLocs.size() > 0; i++) {
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				map[loc.x][loc.y] = 1;
				validLocs.remove(loc);
				decrease--;
			}
			farmers = farms;
			return decrease;

		}
		if (!dec) {
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1 && structures[x][y] == 0) {
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int increase = farms - farmers;
			for (int i = 0; i < farms - farmers && validLocs.size() > 0; i++){
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				map[loc.x][loc.y] = 2;
				validLocs.remove(loc);
				increase--;
			}
			farmers = farms;
			return increase;
		}
		farmers = farms;
		return 0;
	}
	public static int updateWarriors(int warrs) {
		boolean dec = (warrs < warriors);
		Random random = new Random();

		if (dec){
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1 && structures[x][y] == 1) {
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int decrease = warriors - warrs;
			for (int i = 0; i < warriors - warrs && validLocs.size() > 0; i++) {
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				structures[loc.x][loc.y] = 0;
				validLocs.remove(loc);
				decrease--;
			}
			warriors = warrs;
			return decrease;

		}
		if (!dec){
			ArrayList<Tuple<Integer, Integer>> validLocs = new ArrayList<Tuple<Integer, Integer>>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1 && structures[x][y] == 0) {
						validLocs.add(new Tuple<Integer, Integer>(x, y));
					}
				}
			}

			int increase = warrs - warriors;
			for (int i = 0; i < warrs - warriors && validLocs.size() > 0; i++) {
				Tuple<Integer, Integer> loc = validLocs.get(random.nextInt(validLocs.size()));
				structures[loc.x][loc.y] = 2;
				validLocs.remove(loc);
				increase--;
			}
			warriors = warrs;
			return increase;
		}
		warriors = warrs;
		return 0;
	}

	public static int calcAcres() {
		int acresPerSquare = 1;

		int count = 0;

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++){
				if (map[x][y] == 1 && structures[x][y] == 0) {
					count++;
				}
				if (map[x][y] == 2 && structures[x][y] == 0) {
					count++;
				}
			}
		}
		return count * acresPerSquare;
	}

	public static double getDistanceFromCenter(int x, int y){
		if (x < mapsize / 2){
			if (y < mapsize / 2){
				return Math.sqrt(Math.pow((0 - x), 2) + Math.pow((0 - y), 2));
			}
			else{
				return Math.sqrt(Math.pow(( 0 - x), 2) + Math.pow((mapsize - y), 2));
			}
		}
		else{
			if (y < mapsize / 2){
				return Math.sqrt(Math.pow((mapsize - x), 2) + Math.pow((0 - y), 2));
			}
			else{
				return Math.sqrt(Math.pow((mapsize - x), 2) + Math.pow((mapsize - y), 2));
			}
		}
	}

}
