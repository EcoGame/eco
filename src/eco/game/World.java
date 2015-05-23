package eco.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * This class represents the game world
 * 
 * @author phil
 * 
 */

public class World {

	public static int mapscale = 6;
	public static int mapsize = (int) Math.pow(2, mapscale);
	public static volatile short[][] map = new short[mapsize][mapsize];
	public static float[][] noise = new float[mapsize][mapsize];
	public static short[][] structures = new short[mapsize][mapsize];
	public static short[][] decorations = new short[mapsize][mapsize];

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
	public static int housesPerTile = 250;
	public static int castlesPerTile = 125;

	public static float forestHeight = 0.25f; // The lower this is (can go down
												// to -1), the more forests
												// there will be
	public static boolean cutForests = true; // Will forests be removed to
												// build things

	public static int woodPerHouse = 2;
	public static int stonePerCastle = 2;
	public static int stoneRate = 2;

	public static Random random;

	public static int woodRate = 1;

	public static void init(int generator) {
		World.generate(generator);
	}

	public static void generate(int generator) {
		decorations = new short[mapsize][mapsize];
		mapseed = System.currentTimeMillis();
		map = new short[mapsize][mapsize];
		structures = new short[mapsize][mapsize];

		float[][] heightmap = new float[mapsize][mapsize];

		int centx = (mapsize / 2) - 1;
		int centy = (mapsize / 2) - 1;

		random = new Random(mapseed);

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

			for (int x = 0; x < mapsize; x++) {
				map[x][0] = 0;
				map[x][mapsize - 1] = 0;
			}
			for (int y = 0; y < mapsize; y++) {
				map[0][y] = 0;
				map[mapsize - 1][y] = 0;
			}

			NoiseSampler.initSimplexNoise((int) mapseed);
			NoiseSampler.setNoiseScale(mapsize / 32);
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

		Random random = new Random();
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
						if (random.nextInt(60) == 0) {
							map[x][y] = 5;
						}
					} else {
						map[x][y] = 3;
					}
				}
			}

			NoiseSampler.initSimplexNoise((int) mapseed);
			NoiseSampler.setNoiseScale(mapsize * 128);
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

		if (totalAcres < 100) {
			generate(generator);
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] == 0) {
					if (random.nextInt(2000) == 0) {
						decorations[x][y] = 1;
						int size = random.nextInt(4);
						Point loc = new Point(x, y);
						try {
							for (int i = 0; i < size; i++) {
								if (random.nextInt(2) == 0) {
									loc.setX(loc.getX() + 1);
								} else {
									loc.setX(loc.getX() - 1);
								}
								if (random.nextInt(2) == 0) {
									loc.setY(loc.getY() + 1);
								} else {
									loc.setY(loc.getY() - 1);
								}
								if (map[loc.getX()][loc.getY()] == 0) {
									decorations[loc.getX()][loc.getY()] = 1;
								}
							}
						} catch (Exception e) {
						}
					}
				}
				if (random.nextInt(100) == 0) {
					decorations[x][y] = 2;
				}
				if (random.nextInt(30) == 0) {
					if (map[x][y] != 0) {
						decorations[x][y] = 3;
					}
				}
				if (random.nextInt(60) == 0) {
					if (map[x][y] != 0) {
						decorations[x][y] = 4;
					}
				}
				if (random.nextInt(100) == 0) {
					if (map[x][y] != 0) {
						decorations[x][y] = 5;
					}
				}
				if (random.nextInt(50) == 0) {
					if (map[x][y] == 1) {
						decorations[x][y] = 6;
					}
				}
			}
		}
		if (isAllOcean()) {
			generate(generator);
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

	public static boolean isAllOcean() {
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] != 0) {
					return false;
				}
			}
		}
		return true;
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

		int deltawarriors = warriors - oldWarriors;

		if (deltafarmers > 0) {
			ArrayList<Point> validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (map[x][y] == 1
							&& (structures[x][y] == 0 || structures[x][y] == 4)) {
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
				if (calcAcres() / (float) totalAcres >= 1.0f) {
					while (map[rand.getX()][rand.getY()] != 1
							&& (structures[rand.getX()][rand.getY()] == 0 || structures[rand
									.getX()][rand.getY()] == 4)) {
						rand = new Point(random.nextInt(mapsize),
								random.nextInt(mapsize));
					}
					map[rand.getX()][rand.getY()] = 2;
					structures[rand.getX()][rand.getY()] = 0;
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
					structures[loc.getX()][loc.getY()] = 0;
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
			int popToResettle = 0;
			if (random.nextInt(100) == 0) {
				// popToResettle = random.nextInt(newhouses) / 2;
				// newhouses -= popToResettle;
			}
			int woodused = 0;
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 2 && newhouses > 0) {
						if (popdensity[x][y] < housesPerTile) {
							int canFit = housesPerTile - popdensity[x][y];
							if (canFit >= newhouses) {
								popdensity[x][y] += newhouses;
								woodused += newhouses * woodPerHouse;
								newhouses = 0;
							} else {
								popdensity[x][y] += canFit;
								woodused += canFit * woodPerHouse;
								newhouses -= canFit;
							}
						}
					}
				}
			}
			newhouses += popToResettle;
			validLocs = new ArrayList<Point>();
			int nearCityBias = 50;
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if ((map[x][y] == 1 || map[x][y] == 1)
							&& (structures[x][y] == 0 || structures[x][y] == 4)) {
						validLocs.add(new Point(x, y));
						if (popmap[x][y + 1] == 2) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x + 1][y + 1] == 2) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x][y - 1] == 2) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x - 1][y] == 2) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
					}
				}
			}
			while (newhouses > 0 && validLocs.size() > 0) {
				Point rand = new Point(random.nextInt(mapsize),
						random.nextInt(mapsize));
				if (calcAcres() / (float) totalAcres >= 1.0f) {
					while (map[rand.getX()][rand.getY()] != 1
							&& (structures[rand.getX()][rand.getY()] == 0 || structures[rand
									.getX()][rand.getY()] == 4)) {
						rand = new Point(random.nextInt(mapsize),
								random.nextInt(mapsize));
					}
					popmap[rand.getX()][rand.getY()] = 2;
					structures[rand.getX()][rand.getY()] = 1;
					cities.put(new Point(rand.getX(), rand.getY()), new City(
							rand, false));
					// map[rand.getX()][rand.getY()] = 3;
					if (newhouses >= housesPerTile) {
						popdensity[rand.getX()][rand.getY()] += housesPerTile;
						woodused += housesPerTile * woodPerHouse;
						newhouses -= housesPerTile;
					} else {
						popdensity[rand.getX()][rand.getY()] += newhouses;
						woodused += newhouses * woodPerHouse;
						newhouses = 0;
					}
				} else {
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					structures[loc.getX()][loc.getY()] = 1;
					popmap[loc.getX()][loc.getY()] = 2;
					cities.put(new Point(loc.getX(), loc.getY()), new City(loc,
							false));
					// map[loc.getX()][loc.getY()] = 3;
					validLocs.remove(loc);
					if (newhouses >= housesPerTile) {
						popdensity[loc.getX()][loc.getY()] += housesPerTile;
						woodused += housesPerTile * woodPerHouse;
						newhouses -= housesPerTile;
					} else {
						popdensity[loc.getX()][loc.getY()] += newhouses;
						woodused += newhouses * woodPerHouse;
						newhouses = 0;
					}
				}
			}
			int realWoodUsed = PlayerCountry.wood.takeWood(woodused,
					PlayerCountry.economy);
			int woodDiff = woodused - realWoodUsed;
			oldFarmers = farmers
					- Math.max(newfarmland, newhouses
							+ (woodDiff / woodPerHouse));
			displacedFarmers = Math.max(newfarmland, newhouses
					+ (woodDiff / woodPerHouse));
		} else {
			ArrayList<Point> validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 1) {
						validLocs.add(new Point(x, y));
					}
				}
			}
			int farmstoremove = -deltafarmers;
			while (farmstoremove > 0 && validLocs.size() > 0) {
				Point loc = validLocs.get(random.nextInt(validLocs.size()));
				int pop = popdensity[loc.getX()][loc.getY()];
				if (farmstoremove >= pop) {
					farmstoremove -= pop;
					popdensity[loc.getX()][loc.getY()] = 0;
					popmap[loc.getX()][loc.getY()] = 0;
					structures[loc.getX()][loc.getY()] = 0;

					validLocs.remove(loc);
					map[loc.getX()][loc.getY()] = 1;
				} else {
					popdensity[loc.getX()][loc.getY()] -= farmstoremove;
					if (popdensity[loc.getX()][loc.getY()] <= 0) {
						popdensity[loc.getX()][loc.getY()] = 0;
						popmap[loc.getX()][loc.getY()] = 0;
						validLocs.remove(loc);
						structures[loc.getX()][loc.getY()] = 0;
						map[loc.getX()][loc.getY()] = 1;
					}
					farmstoremove = 0;
				}
			}
			validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 2) {
						validLocs.add(new Point(x, y));
					}
				}
			}
			int housestoremove = -deltafarmers;
			while (housestoremove > 0 && validLocs.size() > 0) {
				int popthispass = housestoremove;
				if (housestoremove > 10) {
					popthispass = random.nextInt(housestoremove);
				}
				Point loc = validLocs.get(random.nextInt(validLocs.size()));
				int pop = popdensity[loc.getX()][loc.getY()];
				if (popthispass >= pop) {
					popdensity[loc.getX()][loc.getY()] = 0;
					popmap[loc.getX()][loc.getY()] = 0;
					housestoremove -= pop;
					cities.remove(loc);
					validLocs.remove(loc);
				} else {
					popdensity[loc.getX()][loc.getY()] -= popthispass;
					if (popdensity[loc.getX()][loc.getY()] <= 0) {
						popdensity[loc.getX()][loc.getY()] = 0;
						popmap[loc.getX()][loc.getY()] = 0;
						cities.remove(loc);
						validLocs.remove(loc);
					}
					housestoremove -= popthispass;
				}
			}
			oldFarmers = farmers + Math.max(housestoremove, farmstoremove);
		}

		if (deltawarriors > 0) {
			ArrayList<Point> validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if ((map[x][y] == 1 || map[x][y] == 1)
							&& (structures[x][y] == 0) || structures[x][y] == 4) {
						validLocs.add(new Point(x, y));
					}
				}
			}
			int stoneUsed = 0;
			int newcastles = deltawarriors;
			int popToResettle = 0;
			if (random.nextInt(100) == 0) {
				// popToResettle = random.nextInt(newcastles) / 2;
				// newcastles -= popToResettle;
			}

			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 3 && newcastles > 0) {
						if (popdensity[x][y] < castlesPerTile) {
							int canFit = castlesPerTile - popdensity[x][y];
							if (canFit >= newcastles) {
								popdensity[x][y] += newcastles;
								stoneUsed += newcastles * stonePerCastle;
								newcastles = 0;
							} else {
								popdensity[x][y] += canFit;
								stoneUsed += canFit * stonePerCastle;
								newcastles -= canFit;
							}
						}
					}
				}
			}
			newcastles += popToResettle;
			validLocs = new ArrayList<Point>();
			int nearCityBias = 50;
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if ((map[x][y] == 1 || map[x][y] == 3)
							&& (structures[x][y] == 0 || structures[x][y] == 4)) {
						validLocs.add(new Point(x, y));
						if (popmap[x][y + 1] == 3) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x + 1][y + 1] == 3) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x][y - 1] == 3) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
						if (popmap[x - 1][y] == 3) {
							for (int i = 0; i < nearCityBias; i++) {
								validLocs.add(new Point(x, y));
							}
						}
					}
				}
			}
			while (newcastles > 0 && validLocs.size() > 0) {
				Point rand = new Point(random.nextInt(mapsize),
						random.nextInt(mapsize));
				if (calcAcres() / (float) totalAcres >= 1.0f) {
					while ((map[rand.getX()][rand.getY()] != 1 && map[rand
							.getX()][rand.getY()] != 3)
							&& (structures[rand.getX()][rand.getY()] == 0 || structures[rand
									.getX()][rand.getY()] == 4)) {
						rand = new Point(random.nextInt(mapsize),
								random.nextInt(mapsize));
					}
					popmap[rand.getX()][rand.getY()] = 3;
					structures[rand.getX()][rand.getY()] = 2;
					cities.put(new Point(rand.getX(), rand.getY()), new City(
							rand, true));
					// map[rand.getX()][rand.getY()] = 3;
					if (newcastles >= castlesPerTile) {
						popdensity[rand.getX()][rand.getY()] += castlesPerTile;
						stoneUsed += castlesPerTile * stonePerCastle;
						newcastles -= castlesPerTile;
					} else {
						popdensity[rand.getX()][rand.getY()] += newcastles;
						stoneUsed += newcastles * stonePerCastle;
						newcastles = 0;
					}
				} else {
					Point loc = validLocs.get(random.nextInt(validLocs.size()));
					structures[loc.getX()][loc.getY()] = 2;
					popmap[loc.getX()][loc.getY()] = 3;
					cities.put(new Point(loc.getX(), loc.getY()), new City(loc,
							true));
					// map[loc.getX()][loc.getY()] = 3;
					validLocs.remove(loc);
					if (newcastles >= castlesPerTile) {
						popdensity[loc.getX()][loc.getY()] += castlesPerTile;
						stoneUsed += castlesPerTile * stonePerCastle;
						newcastles -= castlesPerTile;
					} else {
						popdensity[loc.getX()][loc.getY()] += newcastles;
						stoneUsed += newcastles * stonePerCastle;
						newcastles = 0;
					}
				}
			}
			int realStoneUsed = PlayerCountry.stone.takeStone(stoneUsed,
					PlayerCountry.economy);
			int stoneDiff = stoneUsed - realStoneUsed;
			oldWarriors = Math.max(0, warriors - (newcastles + (stoneDiff / stonePerCastle)));
			displacedWarriors = newcastles + (stoneDiff / stonePerCastle);
		} else {
			ArrayList<Point> validLocs = new ArrayList<Point>();
			for (int x = 0; x < mapsize; x++) {
				for (int y = 0; y < mapsize; y++) {
					if (popmap[x][y] == 3) {
						validLocs.add(new Point(x, y));
					}
				}
			}
			int castlestoremove = -deltawarriors;
			while (castlestoremove > 0 && validLocs.size() > 0) {
				int popthispass = castlestoremove;
				if (castlestoremove > 10) {
					popthispass = random.nextInt(castlestoremove);
				}
				Point loc = validLocs.get(random.nextInt(validLocs.size()));
				int pop = popdensity[loc.getX()][loc.getY()];
				if (popthispass > pop) {
					popdensity[loc.getX()][loc.getY()] = 0;
					popmap[loc.getX()][loc.getY()] = 0;
					cities.remove(loc);
					// cities.get(loc).updatePop(0);
					// structures[loc.getX()][loc.getY()] = 4;
					castlestoremove -= pop;
					validLocs.remove(loc);
				} else {
					popdensity[loc.getX()][loc.getY()] -= popthispass;
					if (popdensity[loc.getX()][loc.getY()] <= 0) {
						popdensity[loc.getX()][loc.getY()] = 0;
						popmap[loc.getX()][loc.getY()] = 0;
						cities.remove(loc);
						// cities.get(loc).updatePop(0);
						// structures[loc.getX()][loc.getY()] = 4;
						validLocs.remove(loc);
					}
					castlestoremove -= popthispass;
				}
			}
			oldWarriors = warriors + castlestoremove;
		}

		/*
		 * int count = 0; for (int i = 0; i < mapsize; i++){ for (int k = 0; k <
		 * mapsize; k++){ if (map[i][k] == 2){ count++; } } }
		 * System.out.println(count);
		 */

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (structures[x][y] != 0 && decorations[x][y] != 0) {
					decorations[x][y] = 0;
				}
			}
		}

		NoiseSampler.initSimplexNoise((int) mapseed);
		NoiseSampler.setNoiseScale(mapsize * 32);
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (structures[x][y] == 0 && map[x][y] == 1) {
					if (random.nextInt(500) == 0
							&& NoiseSampler.getNoise(x, y) >= forestHeight) {
						structures[x][y] = 3;
					}
				}
			}
		}

		for (int x = mapsize - 1; x >= 0; x--) {
			for (int y = 0; y < mapsize; y++) {
				if (x == mapsize - 1) {
					if (decorations[x][y] == 2) {
						decorations[x][y] = 0;
					}
				} else {
					if (decorations[x][y] == 2) {
						if (decorations[x + 1][y] == 0) {
							decorations[x + 1][y] = 2;
						}
						decorations[x][y] = 0;
					}
				}
				if (x == 0) {
					if (random.nextInt(100) == 0) {
						decorations[x][y] = 2;
					}
				}
			}
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				City c = cities.get(new Point(x, y));
				if (c != null) {
					if (popdensity[x][y] <= 0) {
						cities.remove(new Point(x, y));
						popmap[x][y] = 0;
						structures[x][y] = 0;
					}
				}
			}
		}

		boolean needscapital = true;

		for (City c : new ArrayList<City>(cities.values())) {
			if (c.getName().contains(City.capitalEpithet) && c.isUsingName()) {
				needscapital = false;
			}
		}

		try {
			if (needscapital) {
				ArrayList<City> possible = new ArrayList<City>(cities.values());
				ArrayList<City> real = new ArrayList<City>();
				for (City c : possible) {
					if (c.isUsingName()) {
						real.add(c);
					}
				}
				int size = real.size();
				City c = real.get(random.nextInt(size));
				c.makeCapital();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (map[x][y] != 0 && noise[x][y] <= 48) {
					noise[x][y] = 49;
				}
			}
		}

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

	public static int calcTilePop() {
		int count = 0;
		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (structures[x][y] != 1) {
					count += popdensity[x][y];
				}
			}
		}
		return count;
	}

	public static void updateWood() {

		int toAdd = 0;

		if (!cutForests) {
			return;
		}

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (structures[x][y] == 3) {
					toAdd += woodRate;
					if (random.nextInt(100) == 0) {
						structures[x][y] = 0;
					}
				}
			}
		}

		PlayerCountry.wood.addWood(toAdd);
	}

	public static void updateStone() {

		int toAdd = 0;

		for (int x = 0; x < mapsize; x++) {
			for (int y = 0; y < mapsize; y++) {
				if (decorations[x][y] == 14) {
					toAdd += stoneRate / 2;
				}
				if (map[x][y] == 3) {
					toAdd += stoneRate;
				}
			}
		}

		PlayerCountry.stone.addStone(toAdd);
	}
}
