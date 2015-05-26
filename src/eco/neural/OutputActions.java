package eco.neural;

import eco.game.Country;
import eco.game.War;

/**
 * This class does something
 * 
 * @author will
 * 
 */

public class OutputActions {

	public static void actions(int k, int currentnetwork) {
		Country c = NeuralManager.Countries[currentnetwork];
		
		Country[] otherCountries = new Country[9];
		
		int place = 0;
		for (int i = 0; i < 10; i++){
			if (NeuralManager.Countries[i] != c){
				otherCountries[place] = NeuralManager.Countries[i];
				place++;
			}
		}
		
		switch (k) {
		case 0:
			c.desiredWarriorRatio += 0.01f;
			c.desiredWarriorRatio = Math.min(1.0f, c.desiredWarriorRatio);
			c.desiredFarmerRatio = 1f - c.desiredWarriorRatio;
			break;
		case 1:
			c.desiredWarriorRatio -= 0.01f;
			c.desiredWarriorRatio = Math.max(0.0f, c.desiredWarriorRatio);
			c.desiredFarmerRatio = 1f - c.desiredWarriorRatio;
			break;
		case 2:
			c.displacedEat = true;
			break;
		case 3:
			c.displacedEat = false;
			break;
		case 4:
			c.favorFarmers = false;
			break;
		case 5:
			c.favorFarmers = true;
			break;
		case 6:
			c.cutForests = true;
			break;
		case 7:
			c.cutForests = false;
			break;
		case 8:
			c.forceConscription = true;
			break;
		case 9:
			c.forceConscription = false;
			break;
		case 10:
			War.warWith(c, otherCountries[0]);
			break;
		case 11:
			War.warWith(c, otherCountries[1]);
			break;
		case 12:
			War.warWith(c, otherCountries[2]);
			break;
		case 13:
			War.warWith(c, otherCountries[3]);
			break;
		case 14:
			War.warWith(c, otherCountries[4]);
			break;
		case 15:
			War.warWith(c, otherCountries[5]);
			break;
		case 16:
			War.warWith(c, otherCountries[6]);
			break;
		case 18:
			War.warWith(c, otherCountries[7]);
			break;
		case 19:
			War.warWith(c, otherCountries[8]);
			break;
		}

	}

}
