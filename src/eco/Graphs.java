package eco;

public class Graphs {
	static int size = 75;
	static int[] prices = new int[size];
	static int[] pop = new int[size];
	static int[] year = new int[size];
	static int[] tax = new int[size];
	static int[] displaced = new int[size];
    public static void draw(int year, int wheatPrice, int tPop, int taxRevenue){
        int y = 620;
        int x = 854;
        int shift = 100;
        int perGraphShift = 80;
        int labelDistance = 60;
        int num = 0;


        prices[74] = wheatPrice;
        for(int i=0; i<prices.length; i++){
            	World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (prices[i]), 1));
         }
		for(int i=0; i<prices.length - 1; i++){
            prices[i] = prices[i + 1];
        }
		World.messages.add(new Message("Price", (x - (perGraphShift * num) - shift), y - labelDistance, 1));
		num++;



        pop[74] = tPop;
        for(int i=0; i<pop.length; i++){
            	World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (pop[i]), 1));
         }
    	for(int i=0; i<pop.length - 1; i++){
            pop[i] = pop[i + 1];
        }
		World.messages.add(new Message("Pop", (x - (perGraphShift * num) - shift), y - labelDistance, 1));
		num++;


		displaced[74] = World.displacedPeople;
    	for(int i=0; i<displaced.length; i++){
    		World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (displaced[i]), 1));
    	}
    	for(int i=0; i<displaced.length - 1; i++){
        	displaced[i] = displaced[i + 1];
    	}
    	World.messages.add(new Message("(????)", (x - (perGraphShift * num) - shift), y - labelDistance, 1));
    	num++;
    }


}
