package eco;

public class Stockpile {
	
	public static int wheatStored = 0;
	public static int deficit = 0;
	public static int maxWheatLevel = 100;
	public static int minWheatLevel = -100;
	
	public static float wheatTax = 0.3f;
	public static float realRate;
	
	public static void addWheat(int toAdd){
		wheatStored++;
		if (wheatStored >= maxWheatLevel){
			//sell
		}
	}
	
	public static void tax(){
		realRate = (Farmer.fPop()) / (Warrior.wPop() * 5f);
		if (realRate > 1){
			realRate = wheatTax;
		}
		else{
			realRate *= wheatTax;
		}
		int toAdd = (int) (Farmer.totalHarvest * realRate);
		addWheat(toAdd);
		Farmer.totalHarvest -= toAdd;
	}
	
	public static int takeWheat(int request){
		if (wheatStored >= request){
			wheatStored -= request;
			return request;
		}
		else{
			deficit = wheatStored - request;
			int toReturn = wheatStored;
			wheatStored = 0;
			if (deficit <= minWheatLevel){
				//buy until deficit is at minimum level or funds are exhausted
			}
		}
		return 0;
	}

}
