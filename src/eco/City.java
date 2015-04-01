package eco;

import java.util.Random;

/**
 * An object that stores additional information about a city, such as the
 * configuration of buildings and name.
 * 
 * @author phil, nate
 * 
 */

public class City {

	private int[][] map = new int[4][4];

	private String name;
	
	private boolean usename = true;

	private int pop = 0;
	
	private Point loc;
	
	public static String capitalEpithet = "# ";
	
	public boolean castle = false;
	
	public City(Point key){
		new City(key, false);
	}

	public City(Point key, boolean isCastle) {
		name = NameGen.generateRandom();
		if (isCastle){
            name = NameGen.generateCastle();
		}
		if (World.cities.size() == 0){
			name = capitalEpithet+name;
		}
		loc = key;
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY())) != null){
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY())).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY())) != null){
			name =  World.cities.get(new Point(loc.getX() - 1, loc.getY())).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX(), loc.getY() + 1)) != null){
			name = World.cities.get(new Point(loc.getX(), loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX(), loc.getY() - 1)) != null){
			name = World.cities.get(new Point(loc.getX(), loc.getY() - 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)) != null){
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)) != null){
			name =  World.cities.get(new Point(loc.getX() - 1, loc.getY() + 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)) != null){
			name = World.cities.get(new Point(loc.getX() + 1, loc.getY() - 1)).name;
			usename = false;
		}
		if (World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)) != null){
			name = World.cities.get(new Point(loc.getX() - 1, loc.getY() - 1)).name;
			usename = false;
		}
		this.castle = isCastle;
	}

	public City(String name) {
		this.name = name;
	}

	public void updatePop(int newpop) {
		//System.out.println("updating city");
		Random random = new Random();
		if (newpop - pop > 0) {
			int buildings = (int) Math.ceil(newpop / 6f);
			int oldbuildings = getSize();
			int deltabuildings = buildings - oldbuildings;
			// System.out.println(pop+","+oldbuildings+","+buildings);
			while (deltabuildings > 0) {
				Point rand = new Point(random.nextInt(4), random.nextInt(4));
				while (map[rand.getX()][rand.getY()] == 2) {
					rand = new Point(random.nextInt(4), random.nextInt(4));
				}
				map[rand.getX()][rand.getY()] += 1;
				deltabuildings--;
			}
			pop = newpop;
		}
		else {
			int buildings = (int) Math.floor(newpop / 6f);
			int oldbuildings = getSize();
			int deltabuildings = oldbuildings - buildings;

			while (deltabuildings > 0 && getSize() > 0) {
				Point rand = new Point(random.nextInt(4), random.nextInt(4));
				while (map[rand.getX()][rand.getY()] == 0) {
					rand = new Point(random.nextInt(4), random.nextInt(4));
				}
				map[rand.getX()][rand.getY()] -= 1;
				deltabuildings--;
			}
			pop = newpop;
		}
	}


	public int getBuilding(int x, int y) {
		return map[x][y];
	}

	public String getName() {
		if (usename){
			return name;
		}
		return "";
	}
	
	private static String[] castlePrefix = new String[] {
		"Fort","Castle","Camp",
	};
	
	private static String[] castleSuffix = new String[] {
		"Castle","Citadel","Garrision","Redoubt","Acropolis","Bulwark","Bastion","Fortress","Vallation","Embankment","Stronghold"
	};
	
	public int getSize(){
		int size = 0;
		for (int x = 0; x < 4; x++){
			for (int y = 0; y < 4; y++){
				if (map[x][y] == 1){
					size++;
				}
				if (map[x][y] == 2){
					size += 2;
				}
			}
		}
		return size;
	}

}
