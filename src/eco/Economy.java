package eco;

import org.newdawn.slick.Color;
import static org.lwjgl.opengl.GL11.glColor4f;
import org.lwjgl.opengl.GL11;

public class Graphs {
	static int size = 75;
	static int[] prices = new int[size];
	static int[] pop = new int[size];
	static int[] year = new int[size];
	static int[] tax = new int[size];
	static int[] displaced = new int[size];
    public static void draw(int year, int wheatPrice, int tPop, int taxRevenue){
        int y = 740;
        int x = 1225;
        int shift = 100;
        int perGraphShift = 80;
        int labelDistance = 60;
        int num = 0;
        int height = 90;
        int maxOne = wheatPrice + 100;
        int maxTwo = 200;
        int maxThree = 200;
        
        
        //World.messages.add(new Message(String.valueOf(maxOne), (x - (perGraphShift * num) - shift + size + 20), y - height - 100, 1));
		//World.messages.add(new Message(String.valueOf(maxOne/2), (x - (perGraphShift * num) - shift + size + 20), y - height - 50, 1));
		Render.font.drawString((x - (perGraphShift * num) - shift + size - 270), y - height - 105, String.valueOf(maxTwo), Color.orange);
		Render.font.drawString((x - (perGraphShift * num) - shift + size - 270), y - height - 55, String.valueOf(maxTwo/2), Color.orange);
		Render.font.drawString((x - (perGraphShift * num) - shift + size - 270), y - height - 5, "0", Color.orange);

		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20), y - height - 105, String.valueOf(maxOne), Color.pink);
		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20), y - height - 55, String.valueOf(maxOne/2), Color.pink);
		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20), y - height - 5, "0", Color.pink);



        prices[74] = wheatPrice;
        for(int i=0; i<prices.length; i++){
            	//World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (int) (prices[i] * ((float)height/maxOne)), 1));
            	Render.font.drawString((x - (perGraphShift * num) - shift) + i,(y - shift) - (int) (prices[i] * ((float)height/maxOne)), "\u25A0 .", Color.pink);

         }
		for(int i=0; i<prices.length - 1; i++){
            prices[i] = prices[i + 1];
        }
		World.messages.add(new Message("Price", (x - (perGraphShift * num) - shift), y - labelDistance, 1));

		num++;
		
        pop[74] = tPop;
        for(int i=0; i<pop.length; i++){
            	//World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (int) (pop[i] * ((float)height/maxTwo)), 1));
            	Render.font.drawString((x - (perGraphShift * num) - shift) + i,(y - shift) - (int) (pop[i] * ((float)height/maxTwo)), "\u25A0 .", Color.orange);

         }
    	for(int i=0; i<pop.length - 1; i++){
            pop[i] = pop[i + 1];
        }
		World.messages.add(new Message("Pop", (x - (perGraphShift * num) - shift), y - labelDistance, 1));
		num++;


		displaced[74] = World.displacedPeople;
    	for(int i=0; i<displaced.length; i++){
    		//World.messages.add(new Message("\u25A0 .", (x - (perGraphShift * num) - shift) + i, (y - shift) - (int) (displaced[i] * ((float)height/maxThree)), 1));
    		Render.font.drawString((x - (perGraphShift * num) - shift) + i,(y - shift) - (int) (displaced[i] * ((float)height/maxThree)), "\u25A0 .", Color.orange);

    	}
    	for(int i=0; i<displaced.length - 1; i++){
        	displaced[i] = displaced[i + 1];
    	}
    	World.messages.add(new Message("(????)", (x - (perGraphShift * num) - shift), y - labelDistance, 1));
    	num++;
    	
    	
    	GL11.glColor4f(1f, 1f, 1f, 1f);
    }


}
