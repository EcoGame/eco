package eco;

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

	private String name;

	private int pop = 0;

	public City() {
		Random random = new Random();
		name = names[random.nextInt(names.length)];
	}

	public City(String name) {
		this.name = name;
	}

	public void updatePop(int newpop) {
		Random random = new Random();
		if (newpop - pop > 0) {
			int buildings = (int) Math.round(newpop / 6f);
			int oldbuildings = (int) Math.round(pop / 6f);
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
	}

	// Names generated using mithrilandmages.com
	// Thanks!
	private static final String[] names = new String[] { "Northhallterton",
			"Bath", "Worstead", "Gravesend", "Warton", "Norton", "Dereham",
			"Stratton", "Pebmarsh", "Amersham", "Montgomery", "Wedmore",
			"Chepstow", "Ilchester", "Newmarket", "Stretton", "Cricklade",
			"Saurm", "Waltham", "Barborough", "Deal", "Criccieth", "Manorbier",
			"Brackley", "Trent", "Chirbury", "Petersfield", "Silchester",
			"Plymoth", "Grinstead", "Liverpool", "Swansea", "Wenlock",
			"Furness", "Bridgewater", "Bloxham", "Byland", "Hindon", "Newport",
			"Hawarden", "Bebington", "Brassington", "Eccleshall", "Edmunds",
			"Tintagel", "Craydon", "Mardon", "Fowey", "Otterburn", "Brinkburn", };

	public int getBuilding(int x, int y) {
		return map[x][y];
	}

	public String getName() {
		return name;
	}

}
