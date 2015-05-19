package eco.game;

import java.util.Random;

/**
 * An object that stores additional information about a city, such as the
 * configuration of buildings and name.
 * 
 * @author phil
 * 
 */

public class City {

	private int[][] map = new int[4][4];

	private final String name;

	private boolean usename = true;

	private int pop = 0;

	private Point loc;

	public static String capitalEpithet = "# ";

	public boolean castle = false;
	


	public City(Point key, boolean isCastle) {
		String name = "";
		name = NameGen.generateRandom();
		if (isCastle) {
			name = NameGen.generateCastle();
		}
		if (World.cities.size() == 0) {
			name = capitalEpithet + name;
		}
		loc = key;
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY())) != null) {
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY())).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY())) != null) {
			name = World.cities.get(new Point(loc.getX() - 1, loc.getY())).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX(), loc.getY() + 1)) != null) {
			name = World.cities.get(new Point(loc.getX(), loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX(), loc.getY() - 1)) != null) {
			name = World.cities.get(new Point(loc.getX(), loc.getY() - 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)) != null) {
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)) != null) {
			name = World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)) != null) {
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)) != null) {
			name = World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)).name;
			usename = false;
		}
		this.castle = isCastle;
		if (usename){
			Log.log(PlayerCountry.year, PlayerCountry.name+" - "+name+" is founded");
		}
		else{
			Log.log(PlayerCountry.year, PlayerCountry.name+" - "+name+" has grown");
		}
		this.name = name;
	}

	public City(String name) {
		this.name = name;
	}

	public void updatePop(int newpop) {
		Random random = new Random();
		if (newpop - pop > 0) {
			int buildings = (int) Math.ceil(newpop / 6f);
			int oldbuildings = getSize();
			int deltabuildings = buildings - oldbuildings;
			while (deltabuildings > 0) {
				Point rand = new Point(random.nextInt(4), random.nextInt(4));
				int count = 0;
				while (map[rand.getX()][rand.getY()] == 2) {
					rand = new Point(random.nextInt(4), random.nextInt(4));
					count++;
					if (count > 1000000000){
						break;
					}
				}
				map[rand.getX()][rand.getY()] += 1;
				deltabuildings--;
			}
			pop = newpop;
		} else {
			int buildings = (int) Math.ceil(newpop / 6f);
			int oldbuildings = getSize();
			int deltabuildings = oldbuildings - buildings;

			while (deltabuildings > 0 && getSize() > 0) {
				Point rand = new Point(random.nextInt(4), random.nextInt(4));
				while (map[rand.getX()][rand.getY()] == -1) {
					rand = new Point(random.nextInt(4), random.nextInt(4));
				}
				map[rand.getX()][rand.getY()] -= 1;
				deltabuildings--;
			}
			pop = newpop;
		}
		if (newpop <= 0){
			usename = true;
			castle = false;
		}
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 4; y++){
				if (map[x][y] == -1 && random.nextInt(10) == 0){
					map[x][y] = 0;
				}
			}
		}
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 4; y++){
				if (map[x][y] != 0){
					return;
				}
			}
		}
		World.popdensity[loc.getX()][loc.getY()] = 0;
		World.popmap[loc.getX()][loc.getY()] = 0;
		World.structures[loc.getX()][loc.getY()] = 0;
		World.cities.remove(loc);
	}

	public int getBuilding(int x, int y) {
		return map[x][y];
	}

	public String getName() {
		if (name == ""){
			Log.log(-1, "NAME IS NULL");
		}
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY())) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() + 1, loc.getY())));
		} else if (World.cities.get(new Point(loc.getX() - 1, loc.getY())) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() - 1, loc.getY())));
		} else if (World.cities.get(new Point(loc.getX(), loc.getY() + 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX(), loc.getY() + 1)));
		} else if (World.cities.get(new Point(loc.getX(), loc.getY() - 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX(), loc.getY() - 1)));
		} else if (World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)));
		} else if (World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)));
		} else if (World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)));
		} else if (World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)) != null) {
			usename = shouldUseName(World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)));
		} else{
			usename = true;
		}
		if (usename) {
			return "["+World.popdensity[loc.getX()][loc.getY()]+"] "+name;
		}
		return "";
	}

	public int getSize() {
		int size = 0;
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (map[x][y] == 1) {
					size++;
				}
				if (map[x][y] == 2) {
					size += 2;
				}
			}
		}
		return size;
	}
	
	public Point getLoc(){
		return loc;
	}
	
	public int getPop(){
		return pop;
	}
	
	public static boolean shouldUseName(City city){
		if (city.name == "" || city.name == null){
			return true;
		} else if (city.usename || city.pop < 5){
			return false;
		}
		return true;
	}

}
