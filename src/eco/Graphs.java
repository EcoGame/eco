package eco;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

/**
 * This class displays graphs of various things
 * 
 * @author nate
 * 
 */

public class Graphs {

	private static int size = 75;
	private static int[] pops = new int[size];
	private static int[] wheats = new int[size];
	private static float[] moneys = new float[size];

	public static void draw(int year, int population, int wheat, float money) {
		int y = 740;
		int x = 1225;
		int shift = 100;
		int perGraphShift = 80;
		int labelDistance = 60;
		int num = 0;
		int height = 90;
		int maxOne = 0;
		int maxTwo = 0;
		int maxThree = 0;

		for (int i = 0; i < pops.length; i++) {
			maxOne = (int) Math.max(maxOne, pops[i]);
		}

		for (int i = 0; i < wheats.length; i++) {
			maxTwo = (int) Math.max(maxTwo, wheats[i]);
		}

		for (int i = 0; i < moneys.length; i++) {
			maxThree = (int) Math.max(maxThree, moneys[i]);
		}

		// World.messages.add(new Message(String.valueOf(maxOne), (x -
		// (perGraphShift * num) - shift + size + 20), y - height - 100, 1));
		// World.messages.add(new Message(String.valueOf(maxOne/2), (x -
		// (perGraphShift * num) - shift + size + 20), y - height - 50, 1));
		Render.font.drawString(
				(x - (perGraphShift * num) - shift + size - 270), y - height
						- 105, String.valueOf(maxThree),
				new Color(41, 152, 104));
		Render.font.drawString(
				(x - (perGraphShift * num) - shift + size - 270), y - height
						- 55, String.valueOf(maxThree / 2), new Color(41, 152,
						104));
		Render.font.drawString(
				(x - (perGraphShift * num) - shift + size - 270), y - height
						- 5, "0", new Color(41, 152, 104));

		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20),
				y - height - 105, String.valueOf(maxOne),
				new Color(1, 169, 212));
		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20),
				y - height - 55, String.valueOf(maxOne / 2), new Color(1, 169,
						212));
		Render.font.drawString((x - (perGraphShift * num) - shift + size + 20),
				y - height - 5, "0", new Color(1, 169, 212));

		pops[74] = population;
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glLineWidth(3f);
		GL11.glBegin(GL11.GL_LINE_STRIP);		
		for (int i = 0; i < pops.length - 1; i++) {
			// World.messages.add(new Message("\u25A0 .", (x - (perGraphShift *
			// num) - shift) + i, (y - shift) - (int) (prices[i] *
			// ((float)height/maxOne)), 1));
			//Render.font.drawString((x - (perGraphShift * num) - shift) + i,
				//	(y - shift) - (int) (pops[i] * ((float) height / maxOne)),
					//"\u25A0 .", new Color(1, 169, 212));
			GL11.glVertex2f((float)(x - (perGraphShift * num) - shift) + i,
					(y - shift) - (pops[i] * ((float) height / maxOne)));
			System.out.println((x - (perGraphShift * num) - shift) + i);
		}
		GL11.glEnd();
		for (int i = 0; i < pops.length - 1; i++){
			pops[i] = pops[i + 1];
		}


		num++;

		wheats[74] = wheat;
		GL11.glColor3f(238 / 255f,187 / 255f,66 / 255f);
		for (int i = 0; i < wheats.length - 1; i++) {
			// World.messages.add(new Message("\u25A0 .", (x - (perGraphShift *
			// num) - shift) + i, (y - shift) - (int) (pop[i] *
			// ((float)height/maxTwo)), 1));
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f((x - (perGraphShift * num) - shift) + i,
							(y - shift)
									- (wheats[i] * ((float) height / maxTwo)));
			GL11.glVertex2f((x - (perGraphShift * num) - shift) + i + 1,
					(y - shift)
							- (wheats[i + 1] * ((float) height / maxTwo)));
			GL11.glEnd();

		}
		for (int i = 0; i < wheats.length - 1; i++) {
			wheats[i] = wheats[i + 1];
		}

		num++;

		moneys[74] = money;
		GL11.glColor3f(42 / 255f, 152 / 255f, 104 / 255f);
		for (int i = 0; i < moneys.length - 1; i++) {
			// World.messages.add(new Message("\u25A0 .", (x - (perGraphShift *
			// num) - shift) + i, (y - shift) - (int) (displaced[i] *
			// ((float)height/maxThree)), 1));
			
			GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f((x - (perGraphShift * num) - shift) + i,
					(y - shift)
							- (moneys[i] * ((float) height / maxThree)));
			GL11.glVertex2f((x - (perGraphShift * num) - shift) + i + 1,
					(y - shift)
							- (moneys[i + 1] * ((float) height / maxThree)));
			GL11.glEnd();

		}
		for (int i = 0; i < moneys.length - 1; i++) {
			moneys[i] = moneys[i + 1];
		}
		num++;
		GL11.glColor3f(1f, 1f, 1f);

		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Render.font.drawString((x - (perGraphShift * (num - 1)) - shift), y
				- labelDistance, "Money");
		Render.font.drawString((x - (perGraphShift * (num - 2)) - shift), y
				- labelDistance, "Wheat");
		Render.font.drawString((x - (perGraphShift * (num - 3)) - shift), y
				- labelDistance, "People");


		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

}
