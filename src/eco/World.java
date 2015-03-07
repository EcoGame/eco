package eco;

import java.util.ArrayList;
import java.util.Random;

public class World {

	public static int mapscale = 6;
	public static int mapsize = (int) Math.pow(2, mapscale);
	public static short[][] map = new short[mapsize][mapsize];
	public static float[][] noise = new float[mapsize][mapsize];
	public static short[][] structures = new short[mapsize][mapsize];

	public static long mapseed = "seeds are cool".hashCode();

	public static double perlinbias;

	public static ArrayList<Message> messages = new ArrayList<Message>();

	public static int oldFarmers = 0;
	public static int oldWarriors = 0;

	public static int freeAcres = 0;
	public static int totalAcres = 0;

	public static final float randomLocCutoff = 0.3f;

	public static int farmerOverflow = 0;
	public static int warriorOverflow = 0;

	public static int displacedFarmers= 0;
	public static int displacedWarriors = 0;
	public static int displacedPeople = 0;

	public static int extraHouses = 0;
	public static int extraFarmers = 0;
	public static int extraWarriors = 0;

	public static float forestHeight = 0.25f; // The lower this is (can go down to -1), the more forests there will be
	public static boolean cutForests = true; // Will forests be removed to build things

	public static void generate(){

		mapseed = System.currentTimeMillis();
		map = new short[mapsize][mapsize];
		structures = new short[mapsize][mapsize];

		float[][] heightmap = new float[mapsize][mapsize];

		int centx = (mapsize / 2) - 1;
		int centy = (mapsize / 2) - 1;

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				float distx = (float) Math.pow(centx - x, 2);
				float disty = (float) Math.pow(centy - y, 2);
				float distance = (float) Math.sqrt(distx + disty);
				distance /= mapsize;
				heightmap[x][y] = distance;
			}
		}

		NoiseSampler.initSimplexNoise((int) mapseed);
		NoiseSampler.setNoiseScale(mapsize / 4);


		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				noise[x][y] = NoiseSampler.getNoise(x, y);
				noise[x][y] += 1;
				noise[x][y] /= 2;
				noise[x][y] -= heightmap[x][y];
				noise[x][y] *= 128;
			}
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (noise[x][y] < 48){
					map[x][y] = 0;
				}
				else if (noise[x][y] < 75){
					map[x][y] = 1;
				}
				else{
					map[x][y] = 3;
				}
			}
		}

		NoiseSampler.initSimplexNoise((int) mapseed);
		NoiseSampler.setNoiseScale(mapsize / 16);
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (NoiseSampler.getNoise(x, y) >= forestHeight){
					if (map[x][y] == 1){
						structures[x][y] = 3;
					}
				}
			}
		}

		for (int x = 0; x < mapsize; x++){
			for (int y = 0; y < mapsize; y++){
				if (map[x][y] != 0){
					totalAcres++;
				}
			}
		}
	}

	public static float getHeight(int x, int y){
		try{
			if (map[x][y] == 0){
				return 48;
			}
			return noise[x][y];
		}
		catch(Exception e){}
		return -10;
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

	public static void updateMap(int farmers, int warriors){

		Random random = new Random();

		int deltaFarmer = farmers - oldFarmers + extraFarmers;
		int deltaWarriors = warriors - oldWarriors + extraWarriors;

		ArrayList<Point> validLocs = new ArrayList<Point>();
		for (int x = 0; x < mapsize; x++){
			for (int y = 0; y < mapsize; y++){
				if (map[x][y] == 1){
					if (structures[x][y] == 0 || (cutForests && structures[x][y] == 3)){
						validLocs.add(new Point(x, y));
					}
				}
			}
		}

		if (deltaFarmer > 0){
			int newFarms = deltaFarmer;
			if ((float) freeAcres / (float) totalAcres >= randomLocCutoff){
				while (newFarms > 0){
					int ranx = random.nextInt(mapsize);
					int rany = random.nextInt(mapsize);
					if (map[ranx][rany] == 1){
						if (structures[ranx][rany] == 0 || (cutForests && structures[ranx][rany] == 3)){
							map[ranx][rany] = 2;
							structures[ranx][rany] = 0;
							newFarms--;
						}
					}
				}
			}
			else{
				while (newFarms > 0 && validLocs.size() > 0){
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					map[loc.getX()][loc.getY()] = 2;
					structures[loc.getX()][loc.getY()] = 0;
					validLocs.remove(loc);
					newFarms--;
				}
			}
			int newHouses = deltaFarmer;
			if ((float) freeAcres / (float) totalAcres >= randomLocCutoff){
				while (newHouses > 0){
					int ranx = random.nextInt(mapsize);
					int rany = random.nextInt(mapsize);
					if (map[ranx][rany] == 1){
						if (structures[ranx][rany] == 0 || (cutForests && structures[ranx][rany] == 3)){
							structures[ranx][rany] = 1;
							newHouses--;
						}
					}
				}
			}
			else{
				while (newHouses > 0 && validLocs.size() > 0){
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					structures[loc.getX()][loc.getY()] = 1;
					validLocs.remove(loc);
					newHouses--;
				}
			}
			displacedFarmers += newHouses;
		}
		validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++){
				for (int y = 0; y < mapsize; y++){
				if (map[x][y] == 1){
					if (structures[x][y] == 0 || (cutForests && structures[x][y] == 3) || map[x][y] == 3){
							validLocs.add(new Point(x, y));
						}
					}
				}
			}
		if (deltaWarriors > 0){
			int newCastles = deltaWarriors;
			if ((float) freeAcres / (float) totalAcres >= randomLocCutoff){
				while (newCastles > 0){
					int ranx = random.nextInt(mapsize);
					int rany = random.nextInt(mapsize);
					if (map[ranx][rany] == 1 || map[ranx][rany] == 3){
						if (structures[ranx][rany] == 0 || (cutForests && structures[ranx][rany] == 3)){
							structures[ranx][rany] = 2;
							newCastles--;
						}
					}
				}
			}
			else{
				while (newCastles > 0 && validLocs.size() > 0){
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					structures[loc.getX()][loc.getY()] = 2;
					validLocs.remove(loc);
					newCastles--;
				}
			}
			displacedWarriors += newCastles;
		}

		oldFarmers = farmers;
		oldWarriors = warriors;
	}

	public static int calcAcres() {
		int acresPerSquare = 1;

		int count = 0;

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++){
				if (map[x][y] == 1 && structures[x][y] == 0) {
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
