package eco.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;

public class MenuBar {

	/**
	 *
	 * This class manages the rendering and logic for the menu bar
	 *
	 * @author phil, connor
	 *
	 */

	// ===============//
	// Pane Variables //
	// ===============//
	private static int pane = 0;

	private static ToggleButton statsPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f), 32, 6, 2, 7, 2, true);
	private static ToggleButton econPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 32, 32, 6, 2, 7, 2, false);
	private static ToggleButton warPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 64, 32, 6, 2, 7, 2, false);
	private static ToggleButton logPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 96, 32, 6, 2, 7, 2, false);
	private static ToggleButton graphsPane = new ToggleButton(0,
			10 + (6f * Main.height / 8f) + 128, 32, 6, 2, 7, 2, false);

	private static ArrayList<Button> buttons = new ArrayList<Button>();
	private static TextureAtlas atlas = Render.atlas;

	// =============//
	// Control Pane //
	// =============//


	private static ArrayList<Button> pane0Buttons = new ArrayList<Button>();

	// =========//
	// War Pane //
	// =========//
	private static Button warUp = new Button(40, 575, 48, 0, 2, 1, 2);
	private static Button warDown = new Button(40, 645, 48, 2, 2, 3, 2);

	private static TextButton warOne = new TextButton(100,
			(Main.height / 8f * 6f) + 16, 176, 20, 6, 2, 7, 2, "Declare War");
	private static TextButton warTwo = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 1), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warThree = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 2), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warFour = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 3), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warFive = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 4), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warSix = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 5), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warSeven = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 6), 176, 20, 6, 2, 7, 2,
			"Declare War");
	private static TextButton warEight = new TextButton(100,
			(Main.height / 8f * 6f) + 16 + (20 * 7), 176, 20, 6, 2, 7, 2,
			"Declare War");

	private static ArrayList<Button> pane1Buttons = new ArrayList<Button>();

	private static int warOff = 0;
	private static int warPos;

	private static int warState = 2;
	private static String warName = "";
	private static int gold;
	private static int land;
	private static int wheat;

	// =============//
	// Logging Pane //
	// =============//
	private static Button logUp = new Button(40, 575, 48, 0, 2, 1, 2);
	private static Button logDown = new Button(40, 645, 48, 2, 2, 3, 2);

	private static ArrayList<Button> pane2Buttons = new ArrayList<Button>();

	private static int logOff = 0;
	private static int logPos;

	// =======//
	// Graphs //
	// =======//

	private static Graph popGraph = new Graph(75, 710, "Population",
			new Treble<>(0f, 153f / 255f, 204f / 255f));
	private static Graph globalWheatGraph = new Graph(75 + (150 * 1) + 75, 710,
			"World Wheat", new Treble<>(208f / 255f, 194f / 255f, 109f / 255f));
	private static Graph moneyGraph = new Graph(75 + (150 * 2) + (75 * 2), 710,
			"Treasury", new Treble<>(100f / 255f, 191f / 255f, 115f / 255f));
	private static Graph scoreGraph = new Graph(75 + (150 * 3) + (75 * 3), 710,
			"Score", new Treble<>(167 / 255f, 76 / 255f, 76 / 255f));
	private static Graph scoreGraphOther = new Graph(75 + (150 * 3) + (75 * 3),
			710, "Score (Other)",
			new Treble<>(129 / 255f, 87 / 255f, 87 / 255f));

	private static ArrayList<Graph> graphs = new ArrayList<Graph>();
	
	// =============//
	// Economy Pane //
	// =============//
	private static ToggleButton toggleFeedDisplaced = new ToggleButton(900,
			657, 25, 4, 2, 5, 2, true);
	private static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627,
			25, 4, 2, 5, 2, false);

	private static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2,
			1, 2);
	private static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2,
			3, 2);

	private static ToggleButton toggleCutTrees = new ToggleButton(900, 597, 25,
			4, 2, 5, 2, true);

	private static ToggleButton toggleForceConscription = new ToggleButton(900,
			567, 25, 4, 2, 5, 2, true);

	private static ToggleButton toggleVeryFast = new ToggleButton(35, 595, 25, 8, 5, 9, 5,
			 false);
	private static ToggleButton toggleFast = new ToggleButton(70, 595, 25, 8, 5, 9, 5,
	 false);
	private static ToggleButton toggleNormal = new ToggleButton(105, 595, 25, 8, 6, 9, 6,
	 true);
	private static ToggleButton toggleSlow = new ToggleButton(140, 595, 25, 8, 6, 9, 6,
	 false);
	private static ToggleButton togglePaused = new ToggleButton(175, 595, 25, 8, 4, 9, 4,
			 false);
	
	private static ArrayList<Button> pane4Buttons = new ArrayList<Button>();

	static {
		buttons.add(statsPane);
		buttons.add(warPane);
		buttons.add(graphsPane);
		buttons.add(logPane);
		buttons.add(econPane);

		pane4Buttons.add(toggleFeedDisplaced);
		pane4Buttons.add(toggleFavorWarrior);
		pane4Buttons.add(increaseWarriorRatio);
		pane4Buttons.add(decreaseWarriorRatio);
		pane4Buttons.add(toggleFast);
		pane4Buttons.add(toggleSlow);
		pane4Buttons.add(toggleNormal);
		pane4Buttons.add(toggleVeryFast);
		pane4Buttons.add(togglePaused);
		pane4Buttons.add(toggleCutTrees);
		pane4Buttons.add(toggleForceConscription);

		pane1Buttons.add(warUp);
		pane1Buttons.add(warDown);
		pane1Buttons.add(warOne);
		pane1Buttons.add(warTwo);
		pane1Buttons.add(warThree);
		pane1Buttons.add(warFour);
		pane1Buttons.add(warFive);
		pane1Buttons.add(warSix);
		pane1Buttons.add(warSeven);
		pane1Buttons.add(warEight);

		pane2Buttons.add(logUp);
		pane2Buttons.add(logDown);

		graphs.add(popGraph);
		graphs.add(globalWheatGraph);
		graphs.add(moneyGraph);
		graphs.add(scoreGraph);
		graphs.add(scoreGraphOther);
		scoreGraphOther.hideStats();

		statsPane.addOverlay(0, 1, 24);
		warPane.addOverlay(1, 1, 24);
		logPane.addOverlay(1, 5, 24);
		graphsPane.addOverlay(0, 5, 24);
		econPane.addOverlay(8, 0, 24);
	}

	public static void update() {
		if (statsPane.checkForClick()) {
			warPane.setToggle(false);
			graphsPane.setToggle(false);
			logPane.setToggle(false);
			statsPane.setToggle(true);
			econPane.setToggle(false);
			pane = 0;
		}
		if (warPane.checkForClick()) {
			statsPane.setToggle(false);
			graphsPane.setToggle(false);
			logPane.setToggle(false);
			warPane.setToggle(true);
			econPane.setToggle(false);
			pane = 1;
		}
		if (logPane.checkForClick()) {
			statsPane.setToggle(false);
			graphsPane.setToggle(false);
			warPane.setToggle(false);
			logPane.setToggle(true);
			econPane.setToggle(false);
			pane = 2;
		}
		if (graphsPane.checkForClick()) {
			statsPane.setToggle(false);
			logPane.setToggle(false);
			warPane.setToggle(false);
			graphsPane.setToggle(true);
			econPane.setToggle(false);
			pane = 3;
		}
		if (econPane.checkForClick()) {
			statsPane.setToggle(false);
			logPane.setToggle(false);
			warPane.setToggle(false);
			graphsPane.setToggle(false);
			econPane.setToggle(true);
			pane = 4;
		}

		if (pane == 4) {
			if (toggleFeedDisplaced.checkForClick()) {
				PlayerCountry.displacedEat ^= true;
			}
			if (toggleFavorWarrior.checkForClick()) {
				PlayerCountry.favorFarmers ^= true;
			}
			if (toggleCutTrees.checkForClick()) {
				World.cutForests ^= true;
			}
			if (toggleForceConscription.checkForClick()) {
				PlayerCountry.forceConscription ^= true;
			}
			if (increaseWarriorRatio.checkForClick()) {
				if (PlayerCountry.desiredWarriorRatio < 1.0f) {
					PlayerCountry.desiredWarriorRatio += 0.01f;
				}
			}
			if (decreaseWarriorRatio.checkForClick()) {
				if (PlayerCountry.desiredWarriorRatio > 0.0f) {
					PlayerCountry.desiredWarriorRatio -= 0.01f;
				} else {
					PlayerCountry.desiredWarriorRatio = 0.0f;
				}
			}
			if (toggleFast.checkForClick()) {
				Main.framesPerTick = 9;
				toggleFast.setToggle(true);
				toggleSlow.setToggle(false);
				toggleNormal.setToggle(false);
				togglePaused.setToggle(false);
				toggleVeryFast.setToggle(false);
			}
			if (toggleSlow.checkForClick()) {
				Main.framesPerTick = 36;
				toggleFast.setToggle(false);
				toggleSlow.setToggle(true);
				toggleNormal.setToggle(false);
				togglePaused.setToggle(false);
				toggleVeryFast.setToggle(false);
			}
			if (toggleNormal.checkForClick()) {
				Main.framesPerTick = 18;
				toggleFast.setToggle(false);
				toggleSlow.setToggle(false);
				toggleNormal.setToggle(true);
				togglePaused.setToggle(false);
				toggleVeryFast.setToggle(false);
			}
			if (toggleVeryFast.checkForClick()) {
				Main.framesPerTick = 3;
				toggleFast.setToggle(false);
				toggleSlow.setToggle(false);
				toggleNormal.setToggle(false);
				togglePaused.setToggle(false);
				toggleVeryFast.setToggle(true);
			}
			if (togglePaused.checkForClick()) {
				Main.framesPerTick = 0x2000000;
				toggleFast.setToggle(false);
				toggleSlow.setToggle(false);
				toggleNormal.setToggle(false);
				togglePaused.setToggle(true);
				toggleVeryFast.setToggle(false);
			}
		}
		if (pane == 1) {
			if (warDown.checkForClick()) {
				warOff++;
				warOff = Math.min(warOff, PlayerCountry.countries.size());
			}
			if (warUp.checkForClick()) {
				warOff--;
				if (warOff < 0) {
					warOff = 0;
				}
			}
			if (warOff != 0 && warPos == 0) {
				warPos = PlayerCountry.countries.size();
			} else if (warOff == 0) {
				warPos = 0;
			}
			try {
				if (warOne.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 1);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warTwo.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 2);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warThree.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 3);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warFour.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 4);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warFive.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 5);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warSix.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 6);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warSeven.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 7);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
				if (warEight.checkForClick()) {
					Country c = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - warOff - 8);
					if (c != null && !c.dead) {
						War.warWith(c);
						warName = "The " + PlayerCountry.name + "-" + c.name
								+ " war";
						updateWar(warName);
					}
				}
			} catch (Exception e) {

			}
		}

		if (pane == 2) {
			if (logDown.checkForClick()) {
				logOff++;
				logOff = Math.min(logOff, Log.getAll().size());
			}
			if (logUp.checkForClick()) {
				logOff--;
				if (logOff < 0) {
					logOff = 0;
				}
			}
			if (logOff != 0 && logPos == 0) {
				logPos = Log.getAll().size();
			} else if (logOff == 0) {
				logPos = 0;
			}
		}

		popGraph.tick(PlayerCountry.farmer.getfPop()
				+ PlayerCountry.warrior.getwPop());
		globalWheatGraph.tick(PlayerCountry.wheat.gettWheat());
		moneyGraph.tick(PlayerCountry.economy.getTreasury());
		scoreGraph.tick(PlayerCountry.score.scoreAt(Math.max(0,
				PlayerCountry.year - 1)));
		scoreGraphOther.tick(Util.calcAverageCountryScore());

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
		if (pane == 1) {
			for (Button b : pane1Buttons) {
				b.click(x, y);
			}
		}
		if (pane == 2) {
			for (Button b : pane2Buttons) {
				b.click(x, y);
			}
		}
		if (pane == 4) {
			for (Button b : pane4Buttons) {
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
		} else if (pane == 1) {
			renderWar();
		} else if (pane == 2) {
			renderLog();
		} else if (pane == 3) {
			renderGraphs();
		} else if (pane == 4){
			renderEcon();
		}
	}

	public static void render2(float x, float y) {
		Render.initOrtho();
		for (Button b : buttons) {
			b.render2();
		}
		if (pane == 3) {
			renderGraphs2();
		}
	}
	
	public static void renderEcon(){
		for (Button b : pane4Buttons){
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}
		
		renderEcon2();
	}
	
	public static void renderEcon2(){
		for (Button b : pane4Buttons){
			b.render2();
		}
		
		Render.drawString("Conscription Rate: "
				+ ((int) (100 * PlayerCountry.desiredWarriorRatio)) + "%", 285,
				657);
		if(toggleFast.getToggle() == true) {
			Render.drawString("Game Speed: Fast", 35, 567);
		} else if(toggleSlow.getToggle() == true) {
			Render.drawString("Game Speed: Slow", 35, 567);
		} else if(toggleNormal.getToggle() == true) {
			Render.drawString("Game Speed: Normal", 35, 567);
		} else if(toggleVeryFast.getToggle() == true) {
			Render.drawString("Game Speed: Very Fast", 35, 567);
		} else if(togglePaused.getToggle() == true) {
			Render.drawString("Game Speed: Paused", 35, 567);
		}
		Render.drawString(
				"Feed Displaced: " + String.valueOf(PlayerCountry.displacedEat),
				585, 657);
		Render.drawString(
				"Favor Warrior Rations: "
						+ String.valueOf(!PlayerCountry.favorFarmers), 585, 627);
		Render.drawString("Cut Forests: " + String.valueOf(World.cutForests),
				585, 597);
		Render.drawString(
				"Force Conscription: "
						+ String.valueOf(PlayerCountry.forceConscription), 585,
				567);
	}

	public static void renderWar() {
		for (int i = 0; i < pane1Buttons.size(); i++) {
			if (i < 2) {
				pane1Buttons.get(i).render(Mouse.getX(),
						Main.height - Mouse.getY());
				continue;
			}
			try {
				Country c = PlayerCountry.countries.get(PlayerCountry.countries
						.size() - warOff - (i - 2) - 1);
				if (c == null) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			pane1Buttons.get(i)
					.render(Mouse.getX(), Main.height - Mouse.getY());
		}

		if (warState != 0 && warState != 2) {
			/* Draw wheat icon */
			glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
			glVertex2f(750, 575);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
			glVertex2f(775, 575);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
			glVertex2f(775, 600);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
			glVertex2f(750, 600);
			glEnd();
			glColor3f(1.0f, 1.0f, 1.0f);

			/* Draw wheat icon */
			glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
			glVertex2f(750, 610);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
			glVertex2f(775, 610);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
			glVertex2f(775, 635);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
			glVertex2f(750, 635);
			glEnd();
			glColor3f(1.0f, 1.0f, 1.0f);

			/* Draw wheat icon */
			glBegin(GL_QUADS);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, false));
			glVertex2f(750, 645);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, false));
			glVertex2f(775, 645);
			glTexCoord2f(atlas.getCoord(0, true), atlas.getCoord(3, true));
			glVertex2f(775, 670);
			glTexCoord2f(atlas.getCoord(0, false), atlas.getCoord(3, true));
			glVertex2f(750, 670);
			glEnd();
			glColor3f(1.0f, 1.0f, 1.0f);
		}

		renderWar2();
	}

	public static void renderGraphs() {
		for (Graph g : graphs) {
			g.render();
		}
	}

	public static void renderGraphs2() {
		for (Graph g : graphs) {
			g.render2();
		}
	}

	public static void renderLog() {

		for (Button b : pane2Buttons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}

		ArrayList<String> log = Log.getAll();

		String str;
		int offx = 100;
		int offy = Main.height / 8 * 6;
		offy += 20;

		int logstart = logOff;

		for (int i = logstart; i < 8 + logstart; i++) {
			try {
				if (logOff == 0) {
					str = log.get(log.size() - 1 - i);
				} else {
					str = log.get(logPos - 1 - i);
				}
				Render.font.drawString(offx, offy, str);
				offy += 20;
			} catch (Exception e) {
				break;
			}
		}
	}

	public static void renderWar2() {
		for (int i = 0; i < pane1Buttons.size(); i++) {
			if (i < 2) {
				pane1Buttons.get(i).render2();
				continue;
			}
			try {
				Country c = PlayerCountry.countries.get(PlayerCountry.countries
						.size() - warOff - (i - 2) - 1);
				if (c == null) {
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			pane1Buttons.get(i).render2();
		}

		int offx = 300;
		int offy = Main.height / 8 * 6;
		offy += 20;

		int logstart = warOff;

		for (int i = logstart; i < 8 + logstart; i++) {
			try {
				String name = "";
				Country country = null;
				if (warOff == 0) {
					name = PlayerCountry.countries.get(PlayerCountry.countries
							.size() - 1 - i).name;
					country = PlayerCountry.countries
							.get(PlayerCountry.countries.size() - 1 - i);
				} else {
					name = PlayerCountry.countries.get(warPos - 1 - i).name;
					country = PlayerCountry.countries.get(warPos - 1 - i);
				}
				if (!country.dead) {
					Render.font.drawString(offx, offy, name);
					Render.font.drawString(offx + Render.font.getWidth(name)
							+ 10, offy,
							"[" + Integer.toString(country.aggression.aggressionScore)
									+ "]", new Color(
									(country.aggression.aggressionScore * 2) / 255f,
									1 - (country.aggression.aggressionScore * 2 / 255f),
									20 / 255f));
					int tempoff = offx
							+ Render.font.getWidth(name)
							+ 20
							+ Render.font.getWidth("["
									+ Integer
											.toString(country.aggression.aggressionScore)
									+ "]");
					Render.font.drawString(tempoff, offy,
							"[" + Integer.toString(country.getScore()) + "]",
							new Color(102, 186, 233));
					offy += 20;
				} else {
					Render.font.drawString(offx, offy, name);
					Render.font.drawString(offx + Render.font.getWidth(name)
							+ 10, offy,
							"[" + Integer.toString(country.aggression.aggressionScore)
									+ "]", new Color(
									(country.aggression.aggressionScore * 2) / 255f,
									1 - (country.aggression.aggressionScore * 2 / 255f),
									20 / 255f));
					int tempoff = offx
							+ Render.font.getWidth(name)
							+ 20
							+ Render.font.getWidth("["
									+ Integer
											.toString(country.aggression.aggressionScore)
									+ "]");
					Render.font.drawString(tempoff, offy, "[In Exile]",
							new Color(195, 65, 65));
					offy += 20;
				}
			} catch (Exception e) {
				break;
			}
		}

		if (warState == 0) {
			Render.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
			int off = Render.font.getWidth(warName) + 10;
			Render.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
					"- Stalemate!", new Color(0.75f, 0.75f, 0.25f));
		} else if (warState == 1) {
			Render.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
			int off = Render.font.getWidth(warName) + 10;
			Render.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
					"- Victory!", new Color(0.25f, 0.75f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 37, "Wheat: "
					+ wheat, new Color(0.25f, 0.75f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 77, "Money: "
					+ gold, new Color(0.25f, 0.75f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 117, "Land: "
					+ land, new Color(0.25f, 0.75f, 0.25f));
		} else if (warState == -1) {
			Render.font.drawString(800, (Main.height / 8 * 6) + 12, warName);
			int off = Render.font.getWidth(warName) + 10;
			Render.font.drawString(800 + off, (Main.height / 8 * 6) + 12,
					"- Defeat!", new Color(0.75f, 0.25f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 37, "Wheat: "
					+ wheat, new Color(0.75f, 0.25f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 77, "Money: "
					+ gold, new Color(0.75f, 0.25f, 0.25f));
			Render.font.drawString(800, (Main.height / 8 * 6) + 117, "Land: "
					+ land, new Color(0.75f, 0.25f, 0.25f));
		} else if (warState == 2) {
			Render.font.drawString(800, (Main.height / 8 * 6) + 12,
					"No Recent Wars");
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

		/* Draw log icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(7, false), atlas.getCoord(4, false));
		glVertex2f(950, 593);
		glTexCoord2f(atlas.getCoord(7, true), atlas.getCoord(4, false));
		glVertex2f(975, 593);
		glTexCoord2f(atlas.getCoord(7, true), atlas.getCoord(4, true));
		glVertex2f(975, 617);
		glTexCoord2f(atlas.getCoord(7, false), atlas.getCoord(4, true));
		glVertex2f(950, 617);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw sword icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(3, false));
		glVertex2f(950, 563);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(3, false));
		glVertex2f(975, 563);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(3, true));
		glVertex2f(975, 587);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(3, true));
		glVertex2f(950, 587);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw coin icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, false));
		glVertex2f(950, 653);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, false));
		glVertex2f(975, 653);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(0, true));
		glVertex2f(975, 677);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(0, true));
		glVertex2f(950, 677);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw land icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, false));
		glVertex2f(950, 623);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, false));
		glVertex2f(975, 623);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(1, true));
		glVertex2f(975, 647);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(1, true));
		glVertex2f(950, 647);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);

		/* Draw stone icon */
		glBegin(GL_QUADS);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(2, false));
		glVertex2f(950, 683);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(2, false));
		glVertex2f(975, 683);
		glTexCoord2f(atlas.getCoord(8, true), atlas.getCoord(2, true));
		glVertex2f(975, 707);
		glTexCoord2f(atlas.getCoord(8, false), atlas.getCoord(2, true));
		glVertex2f(950, 707);
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
		Render.drawString(
				"Occupied Territories: "
						+ String.valueOf(PlayerCountry.land.getLand()), 985,
				627);
		Render.drawString(
				"Money: " + String.valueOf(PlayerCountry.economy.getTreasury()),
				985, 657);
		Render.drawString(
				"Wood: " + String.valueOf(PlayerCountry.wood.getWood()), 985,
				597);
		Render.drawString("Aggression: ", 985, 567);
		int value = PlayerCountry.aggression.aggressionScore;
		Render.font.drawString(985 + 15 + Render.font.getWidth("Aggression"),
				567, String.valueOf(PlayerCountry.aggression.aggressionScore), new Color(
						(value * 2) / 255f, 1 - (value * 2 / 255f), 20 / 255f));
		Render.drawString(
				"Stone: " + String.valueOf(PlayerCountry.stone.getStone()),
				985, 687);
	}

	public static boolean shouldRenderGraphs() {
		return pane == 3;
	}

	public static void reset() {
		warState = 2;
		wheat = 0;
		gold = 0;
		land = 0;
		for (Graph g : graphs) {
			g.reset();
		}
	}

	public static void setPane(int newPane){
		pane = newPane;
	}

	public static void updateWar(String name){
		warState = War.winLose;
		warName = name;
		wheat = War.wheatLoss;
		gold = War.moneyLoss;
		land = War.landLoss;
	}

}
