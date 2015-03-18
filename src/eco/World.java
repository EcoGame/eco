package eco;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

/**
 * This class represents the game world
 * 
 * @author phil
 * 
 */

public class World {

	public static int mapscale = 6;
	public static int mapsize = (int) Math.pow(2, mapscale);
	public static short[][] map = new short[mapsize][mapsize];
	public static float[][] noise = new float[mapsize][mapsize];
	public static short[][] structures = new short[mapsize][mapsize];

	public static short[][] popmap = new short[mapsize][mapsize];
	public static short[][] popdensity = new short[mapsize][mapsize];

	public static long mapseed = "seeds are cool".hashCode();

	public static HashMap<Point, City> cities = new HashMap<Point, City>();

	public static double perlinbias;

	public static int oldFarmers = 0;
	public static int oldWarriors = 0;

	public static int freeAcres = 0;
	public static int totalAcres = 0;

	public static final float randomLocCutoff = 0.3f;

	public static int farmerOverflow = 0;
	public static int warriorOverflow = 0;

	public static int displacedFarmers = 0;
	public static int displacedWarriors = 0;
	public static int displacedPeople = 0;

	public static int extraHouses = 0;
	public static int extraFarmers = 0;
	public static int extraWarriors = 0;

	public static int farmsPerTile = 5;
	public static int housesPerTile = 100;
	public static int castlesPerTile = 5;

	public static float forestHeight = 0.25f; // The lower this is (can go down
												// to -1), the more forests
												// there will be
	private static boolean cutForests = false; // Will forests be removed to
												// build things

	public static void init(int generator) {
		World.generate(generator);
	}

	public static void generate(int generator) {

		mapseed = System.currentTimeMillis();
		map = new short[mapsize][mapsize];
		structures = new short[mapsize][mapsize];

		float[][] heightmap = new float[mapsize][mapsize];

		int centx = (mapsize / 2) - 1;
		int centy = (mapsize / 2) - 1;

		if (generator == 0) {
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
					if (noise[x][y] < 48) {
						map[x][y] = 0;
					} else if (noise[x][y] < 75) {
						map[x][y] = 1;
					} else {
						map[x][y] = 3;
					}
				}
			}

			NoiseSampler.initSimplexNoise((int) mapseed);
			NoiseSampler.setNoiseScale(mapsize / 16);
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (NoiseSampler.getNoise(x, y) >= forestHeight) {
						if (map[x][y] == 1) {
							structures[x][y] = 3;
						}
					}
				}
			}
		}

		if (generator == 1) {
			Random random = new Random();
			for (int i = 0; i < 20; i++) {
				centx = random.nextInt(mapsize / 2) + (mapsize / 4);
				centy = random.nextInt(mapsize / 2) + (mapsize / 4);
				int radius = random.nextInt(mapsize / 32) + (mapsize / 16);
				for (int x = 0; x < mapsize; x++) {
					for (int y = 0; y < mapsize; y++) {
						float distx = (float) Math.pow(centx - x, 2);
						float disty = (float) Math.pow(centy - y, 2);
						float distance = (float) Math.sqrt(distx + disty);
						distance /= radius;
						if (distance > 1) {
							distance = 0f;
						}
						heightmap[x][y] += distance;
					}
				}
			}
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (heightmap[x][y] == 0) {
						heightmap[x][y] = 0.5f;
					}
					heightmap[x][y] /= 2;
					if (x == 0 || y == 0 || x == mapsize - 1
							|| y == mapsize - 1) {
						heightmap[x][y] = 1;
					}
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
					if (noise[x][y] < 48) {
						map[x][y] = 0;
					} else if (noise[x][y] < 75) {
						map[x][y] = 1;
					} else {
						map[x][y] = 3;
					}
				}
			}

			NoiseSampler.initSimplexNoise((int) mapseed);
			NoiseSampler.setNoiseScale(mapsize / 16);
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (NoiseSampler.getNoise(x, y) >= forestHeight) {
						if (map[x][y] == 1) {
							structures[x][y] = 3;
						}
					}
				}
			}
		}

		if (generator == 2) {
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
					noise[x][y] += heightmap[x][y] / 8f;
					noise[x][y] *= 128;
				}
			}

			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (noise[x][y] < 48) {
						map[x][y] = 0;
					} else if (noise[x][y] < 75) {
						map[x][y] = 1;
					} else {
						map[x][y] = 3;
					}
				}
			}

			NoiseSampler.initSimplexNoise((int) mapseed);
			NoiseSampler.setNoiseScale(mapsize / 16);
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (NoiseSampler.getNoise(x, y) >= forestHeight) {
						if (map[x][y] == 1) {
							structures[x][y] = 3;
						}
					}
				}
			}
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] != 0) {
					totalAcres++;
				}
			}
		}
	}

	public static float getHeight(int x, int y) {
		try {
			if (map[x][y] == 0) {
				return 48;
			}
			return noise[x][y];
		} catch (Exception e) {
		}
		return -10;
	}

	public static boolean isValid() {
		for (int x = 0; x < mapsize; x++) {
			if (map[x][0] != 0) {
				return false;
			}
		}
		for (int x = mapsize - 1; x >= 0; x--) {
			if (map[x][0] != 0) {
				return false;
			}
		}
		for (int y = 0; y < mapsize; y++) {
			if (map[0][y] != 0) {
				return false;
			}
		}
		for (int y = mapsize - 1; y >= 0; y--) {
			if (map[0][y] != 0) {
				return false;
			}
		}

		float landmax = 0.7f;
		float landmin = 0.3f;

		int total = mapsize * mapsize;
		int land = 0;
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] != 0) {
					land++;
				}
			}
		}
		float ratio = (float) land / (float) total;
		if (ratio > landmax || ratio < landmin) {
			return false;
		}
		return true;
	}

	public static void updateMap(int farmers, int warriors) {
		Random random = new Random();

		int deltafarmers = farmers - oldFarmers;
		@SuppressWarnings("unused")
		int deltawarriors = warriors - oldWarriors;

		if (deltafarmers > 0) {
			ArrayList<Point> validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1
							&& (structures[x][y] == 0 || (cutForests && structures[x][y] == 3))) {
						validLocs.add(new Point(x, y));
					}
				}
			}
			int newfarmland = deltafarmers;
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 1 && newfarmland > 0) {
						if (popdensity[x][y] < farmsPerTile) {
							int canFit = farmsPerTile - popdensity[x][y];
							if (canFit >= newfarmland) {
								popdensity[x][y] += newfarmland;
								newfarmland = 0;
							} else {
								popdensity[x][y] += canFit;
								newfarmland -= canFit;
							}
						}
					}
				}
			}
			while (newfarmland > 0 && validLocs.size() > 0) {
				Point rand = new Point(random.nextInt(mapsize),
						random.nextInt(mapsize));
				if (calcAcres() / (float) totalAcres >= 0.5f) {
					while (map[rand.getX()][rand.getY()] != 1
							&& (structures[rand.getX()][rand.getY()] == 0 || (cutForests && structures[rand
									.getX()][rand.getY()] == 3))) {
						rand = new Point(random.nextInt(mapsize),
								random.nextInt(mapsize));
					}
					map[rand.getX()][rand.getY()] = 2;
					popmap[rand.getX()][rand.getY()] = 1;
					if (newfarmland >= farmsPerTile) {
						popdensity[rand.getX()][rand.getY()] += farmsPerTile;
						newfarmland -= farmsPerTile;
					} else {
						popdensity[rand.getX()][rand.getY()] += newfarmland;
						newfarmland = 0;
					}
				} else {
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					map[loc.getX()][loc.getY()] = 2;
					popmap[loc.getX()][loc.getY()] = 1;
					validLocs.remove(loc);
					if (newfarmland >= farmsPerTile) {
						popdensity[loc.getX()][loc.getY()] += farmsPerTile;
						newfarmland -= farmsPerTile;
					} else {
						popdensity[loc.getX()][loc.getY()] += newfarmland;
						newfarmland = 0;
					}
				}
			}

			int newhouses = deltafarmers;
			int popToResettle = random.nextInt(newhouses) / 2;
			newhouses -= popToResettle;
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 2 && newhouses > 0) {
						if (popdensity[x][y] < housesPerTile) {
							int canFit = housesPerTile - popdensity[x][y];
							if (canFit >= newhouses) {
								popdensity[x][y] += newhouses;
								newhouses = 0;
							} else {
								popdensity[x][y] += canFit;
								newhouses -= canFit;
							}
						}
					}
				}
			}
			newhouses += popToResettle;
			while (newhouses > 0 && validLocs.size() > 0) {
				Point rand = new Point(random.nextInt(mapsize),
						random.nextInt(mapsize));
				if (calcAcres() / (float) totalAcres >= 0.5f) {
					while (map[rand.getX()][rand.getY()] != 1
							&& (structures[rand.getX()][rand.getY()] == 0 || (cutForests && structures[rand
									.getX()][rand.getY()] == 3))) {
						rand = new Point(random.nextInt(mapsize),
								random.nextInt(mapsize));
					}
					popmap[rand.getX()][rand.getY()] = 2;
					structures[rand.getX()][rand.getY()] = 1;
					cities.put(new Point(rand.getX(), rand.getY()), new City());
					if (newhouses >= housesPerTile) {
						popdensity[rand.getX()][rand.getY()] += housesPerTile;
						newhouses -= housesPerTile;
					} else {
						popdensity[rand.getX()][rand.getY()] += newhouses;
						newhouses = 0;
					}
				} else {
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					structures[loc.getX()][loc.getY()] = 1;
					popmap[loc.getX()][loc.getY()] = 2;
					cities.put(new Point(loc.getX(), loc.getY()), new City());
					validLocs.remove(loc);
					if (newhouses >= housesPerTile) {
						popdensity[loc.getX()][loc.getY()] += housesPerTile;
						newhouses -= housesPerTile;
					} else {
						popdensity[loc.getX()][loc.getY()] += newhouses;
						newhouses = 0;
					}
				}
			}
			oldFarmers = farmers - newfarmland;
			displacedFarmers = newfarmland;
		}

		/*
		 * int count = 0; for (int i = 0; i < mapsize; i++){ for (int k = 0; k <
		 * mapsize; k++){ if (map[i][k] == 2){ count++; } } }
		 * System.out.println(count);
		 */

	}

	public static int calcAcres() {
		int acresPerSquare = 1;

		int count = 0;

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] == 1 && structures[x][y] == 0) {
					count++;
				}
			}
		}
		return count * acresPerSquare;
	}

	public static double getDistanceFromCenter(int x, int y) {
		if (x < mapsize / 2) {
			if (y < mapsize / 2) {
				return Math.sqrt(Math.pow((0 - x), 2) + Math.pow((0 - y), 2));
			} else {
				return Math.sqrt(Math.pow((0 - x), 2)
						+ Math.pow((mapsize - y), 2));
			}
		} else {
			if (y < mapsize / 2) {
				return Math.sqrt(Math.pow((mapsize - x), 2)
						+ Math.pow((0 - y), 2));
			} else {
				return Math.sqrt(Math.pow((mapsize - x), 2)
						+ Math.pow((mapsize - y), 2));
			}
		}
	}

}
