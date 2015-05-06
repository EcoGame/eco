package eco.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class MenuBar {

	/**
	 * 
	 * This class manages the rendering and logic for the menu bar
	 * 
	 * @author phil
	 * 
	 */

	// ===============//
	// Pane Variables //
	// ===============//
	private static int pane = 0;

	private static int logOff = 0;
	private static int logPos;

	private static ToggleButton statsPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f), 32, 6, 2, 7, 2, true);
	private static ToggleButton warPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 32, 32, 6, 2, 7, 2, false);
	private static ToggleButton logPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 64, 32, 6, 2, 7, 2, false);
	private static ToggleButton graphsPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 96, 32, 6, 2, 7, 2, false);

	private static ArrayList<Button> buttons = new ArrayList<Button>();

	
	// =============//
	// Control Pane //
	// =============//
	private static ToggleButton toggleFeedDisplaced = new ToggleButton(900,
			657, 25, 4, 2, 5, 2, true);
	private static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627,
			25, 4, 2, 5, 2, false);

	private static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2,
			1, 2);
	private static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2,
			3, 2);

	private static Button increaseTickRate = new Button(495, 606, 20, 0, 2, 1,
			2);
	private static Button decreaseTickRate = new Button(495, 636, 20, 2, 2, 3,
			2);

	private static ArrayList<Button> pane0Buttons = new ArrayList<Button>();
	
	// =============//
	// Logging Pane //
	// =============//
	private static Button logUp = new Button(40, 575, 48, 0, 2, 1, 2);
	private static Button logDown = new Button(40, 645, 48, 2, 2, 3, 2);
	
	private static ArrayList<Button> pane2Buttons = new ArrayList<Button>();
	
	private static TextureAtlas atlas = Render.atlas;

	static {
		buttons.add(statsPane);
		buttons.add(warPane);
		buttons.add(graphsPane);
		buttons.add(logPane);

		pane0Buttons.add(toggleFeedDisplaced);
		pane0Buttons.add(toggleFavorWarrior);
		pane0Buttons.add(increaseWarriorRatio);
		pane0Buttons.add(decreaseWarriorRatio);
		pane0Buttons.add(increaseTickRate);
		pane0Buttons.add(decreaseTickRate);
		
		pane2Buttons.add(logUp);
		pane2Buttons.add(logDown);

		statsPane.addOverlay(0, 1, 24);
		warPane.addOverlay(1, 1, 24);
		logPane.addOverlay(1, 5, 24);
		graphsPane.addOverlay(0, 5, 24);
	}

	public static void update() {
		if (statsPane.checkForClick()) {
			warPane.setToggle(false);
			graphsPane.setToggle(false);
			logPane.setToggle(false);
			statsPane.setToggle(true);
			pane = 0;
		}
		if (warPane.checkForClick()) {
			statsPane.setToggle(false);
			graphsPane.setToggle(false);
			logPane.setToggle(false);
			warPane.setToggle(true);
			pane = 1;
		}
		if (logPane.checkForClick()) {
			statsPane.setToggle(false);
			graphsPane.setToggle(false);
			warPane.setToggle(false);
			logPane.setToggle(true);
			pane = 2;
		}
		if (graphsPane.checkForClick()) {
			statsPane.setToggle(false);
			logPane.setToggle(false);
			warPane.setToggle(false);
			graphsPane.setToggle(true);
			pane = 3;
		}

		if (pane == 0) {
			if (toggleFeedDisplaced.checkForClick()) {
				PlayerCountry.displacedEat ^= true;
			}
			if (toggleFavorWarrior.checkForClick()) {
				PlayerCountry.favorFarmers ^= true;
			}
			if (increaseWarriorRatio.checkForClick()) {
				if (PlayerCountry.desiredWarriorRatio < 1.0f) {
					PlayerCountry.desiredWarriorRatio += 0.01f;
				}
			}
			if (decreaseWarriorRatio.checkForClick()) {
				if (PlayerCountry.desiredWarriorRatio != 0.0f) {
					PlayerCountry.desiredWarriorRatio -= 0.01f;
				}
			}
			if (increaseTickRate.checkForClick()) {
				if (Main.framesPerTick != 100) {
					Main.framesPerTick++;
				}
			}
			if (decreaseTickRate.checkForClick()) {
				if (Main.framesPerTick != 2) {
					Main.framesPerTick--;
				}
			}
		}
		
		if (pane == 2){
			if (logDown.checkForClick()){
				logOff++;
				logOff = Math.min(logOff, Log.getAll().size());
			}
			if (logUp.checkForClick()){
				logOff--;
				if (logOff < 0){
					logOff = 0;
				}
			}
			if (logOff != 0 && logPos == 0){
				logPos = Log.getAll().size();
			}
			else if (logOff == 0){
				logPos = 0;
			}
		}
	}

	public static void click(float x, float y) {
		for (Button b : buttons) {
			b.click(x, y);
		}
		if (pane == 0) {
			for (Button b : pane0Buttons) {
				b.click(x, y);
			}
		}
		if (pane == 2) {
			for (Button b : pane2Buttons) {
				b.click(x, y);
			}
		}
	}

	public static void render(float x, float y) {
		atlas.getTexture().bind();
		for (Button b : buttons) {
			b.render(x, y);
		}
		if (pane == 0) {
			renderStats();
		} else if (pane == 1){
			
		} else if (pane == 2){
			renderLog();
		} else if (pane == 3){
			renderGraphs();
		}
	}

	public static void render2(float x, float y) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		for (Button b : buttons) {
			b.render2();
		}
	}

	public static void renderGraphs() {
		Graphs.draw(PlayerCountry.year, PlayerCountry.farmer.getfPop()
				+ PlayerCountry.warrior.getwPop(), Wheat.globalWheat,
				PlayerCountry.economy.getTreasury());
	}
	
	public static void renderLog(){
		
		for (Button b : pane2Buttons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}
		
		ArrayList<String> log = Log.getAll();
		
		String str;
		int offx = 100;
		int offy = Main.height / 8 * 6;
		offy += 20;
		
		int logstart = logOff;
		
		for (int i = logstart; i < 8 + logstart; i++){
			try{
			if (logOff == 0){
				str = log.get(log.size() - 1 - i);
			}
			else{
				str = log.get(logPos - 1 - i);	
			}
			Render.font.drawString(offx, offy, str);
			offy += 20;
			}
			catch (Exception e){
				break;
			}
		}
	}

	public static void renderStats() {

		for (Button b : pane0Buttons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}

		/* Draw wheat icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
		glVertex2f(50, 575);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
		glVertex2f(75, 575);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
		glVertex2f(75, 600);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
		glVertex2f(50, 600);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw house icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, false));
		glVertex2f(50, 610);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, false));
		glVertex2f(75, 610);
		glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(1, true));
		glVertex2f(75, 635);
		glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(1, true));
		glVertex2f(50, 635);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw castle icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, false));
		glVertex2f(50, 645);
		glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, false));
		glVertex2f(75, 645);
		glTexCoord2f(atlas.getCoord(1, true), atlas.getCoord(1, true));
		glVertex2f(75, 670);
		glTexCoord2f(atlas.getCoord(1, false), atlas.getCoord(1, true));
		glVertex2f(50, 670);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		renderStats2();

	}

	public static void renderStats2() {
		Render.drawString(String.valueOf(PlayerCountry.warrior.getwPop())
				+ " Warriors", 85, 657);
		Render.drawString(String.valueOf(PlayerCountry.farmer.getfPop())
				+ " Farmers", 85, 627);
		Render.drawString(String.valueOf(PlayerCountry.wheat.gettWheat()), 85,
				587);
		if (Util.getWheatRate() > 0) {
			Render.font.drawString(85 + Render.font.getWidth(String
					.valueOf(PlayerCountry.wheat.gettWheat() + " ")), 587, " ("
					+ Util.getWheatRateForDisplay() + ")", Color.green);
		} else {
			Render.font.drawString(85 + Render.font.getWidth(String
					.valueOf(PlayerCountry.wheat.gettWheat() + " ")), 587, " ("
					+ Util.getWheatRateForDisplay() + ")", Color.red);
		}
		Render.drawString("Conscription Rate: "
				+ ((int) (100 * PlayerCountry.desiredWarriorRatio)) + "%", 285,
				657);
		Render.drawString(
				"Frames Per Tick: " + String.valueOf(Main.framesPerTick), 285,
				627);
		Render.drawString(
				"Feed Displaced: " + String.valueOf(PlayerCountry.displacedEat),
				585, 657);
		Render.drawString(
				"Favor Warrior Rations: "
						+ String.valueOf(!PlayerCountry.favorFarmers), 585, 627);

	}

	public static boolean shouldRenderGraphs() {
		return pane == 3;
	}

}