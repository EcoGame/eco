package eco.game;

import java.util.ArrayList;
import org.lwjgl.opengl.Display;

import org.lwjgl.input.Mouse;

/**
 * This class handles the rendering, updating, and passing of click events for
 * buttons
 *
 * @author phil, connor
 *
 */

public class UIManager {

  public static String word = "";
  public static int cursorTick = 1;

	private static ToggleButton toggleFeedDisplaced = new ToggleButton(900, 657, 25, 4, 2, 5, 2, true);
	private static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627, 25, 4, 2, 5, 2, false);

	private static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2, 1, 2);
	private static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2, 3, 2);

	private static Button increaseTickRate = new Button(495, 606, 20, 0, 2, 1, 2);
	private static Button decreaseTickRate = new Button(495, 636, 20, 2, 2, 3, 2);

	public static TextButton startSaveGame1 = new TextButton((Main.width / 2) - 128, 256, 256, 32, 6, 2, 7, 2, Main.saveName1);
	public static TextButton startSaveGame2 = new TextButton((Main.width / 2) - 128, 286, 256, 32, 6, 2, 7, 2, Main.saveName2);
	public static TextButton startSaveGame3 = new TextButton((Main.width / 2) - 128, 316, 256, 32, 6, 2, 7, 2, Main.saveName3);
	public static TextButton startSaveGame4 = new TextButton((Main.width / 2) - 128, 346, 256, 32, 6, 2, 7, 2, Main.saveName4);
	public static TextButton startSaveGame5 = new TextButton((Main.width / 2) - 128, 376, 256, 32, 6, 2, 7, 2, Main.saveName5);

	private static ToggleTextButton generatorIsland = new ToggleTextButton((Main.width / 2) + 256, 256, 256, 32, 6, 2, 7, 2, "Island", true);
	private static ToggleTextButton generatorArchipelago = new ToggleTextButton((Main.width / 2) + 256, 320, 256, 32, 6, 2, 7, 2, "Archipelago", false);
	private static ToggleTextButton generatorMountains = new ToggleTextButton((Main.width / 2) + 256, 384, 256, 32, 6, 2, 7, 2, "Mountains", false);

	private static TextButton exitToMenu = new TextButton((Main.width / 2) - 128, 256, 256, 32, 6, 2, 7, 2, "Exit Game");

	private static ArrayList<Button> buttons = new ArrayList<Button>();
	private static ArrayList<Button> menuButtons = new ArrayList<Button>();
	private static ArrayList<Button> pauseButtons = new ArrayList<Button>();

	/*
	 * First define your button above, then register it below
	 */

	static {
		buttons.add(toggleFeedDisplaced);
		buttons.add(toggleFavorWarrior);
		buttons.add(increaseWarriorRatio);
		buttons.add(decreaseWarriorRatio);
		buttons.add(increaseTickRate);
		buttons.add(decreaseTickRate);

		menuButtons.add(startSaveGame1);
		menuButtons.add(startSaveGame2);
		menuButtons.add(startSaveGame3);
		menuButtons.add(startSaveGame4);
		menuButtons.add(startSaveGame5);
		menuButtons.add(generatorIsland);
		menuButtons.add(generatorArchipelago);
		menuButtons.add(generatorMountains);

		pauseButtons.add(exitToMenu);
	}

	/*
	 * Write button logic in this method, it should be in the form: if
	 * (myButton.checkForClick()){ myAction() }
	 */

	public static void update() {
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

	public static void updatePaused() {
		if (exitToMenu.checkForClick()) {
			Main.shouldBeInMenu = true;
			Main.shouldQuit = true;
		}
	}

	/*
	 * Write main menu button logic in this method, it should be in the form: if
	 * (myMenuButton.checkForClick()){ myMenuAction() }
	 */

	public static void updateMenu() {
        
	if (startSaveGame1.checkForClick()) {
		if (!Util.doesSaveExist(1)){
  		Main.saveName1 = "";
            while (!word.equals("@")) {       
                cursorTick++;
		        word = Typing.currentMenuName(word, cursorTick);
		        if (word.equals("@")){
		            break;
		        }
                Main.saveName1 = word;
                startSaveGame1.setText(word);
                Render.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if(Main.saveName1.contains("|")) {
            Main.saveName1 = Main.saveName1.replace("|", "");
            startSaveGame1.setText(Main.saveName1);
        }
        Main.currentSave = 1;
        Main.shouldQuit = false;
        PlayerCountry.initGame();
        PlayerCountry.gameLoop();
    }

    if (startSaveGame2.checkForClick()) {
        if (!Util.doesSaveExist(2)){
            Main.saveName2 = "";
            while (!word.equals("@")) {
                cursorTick++;
                word = Typing.currentMenuName(word, cursorTick);
                if (word.equals("@")){
                    break;
                }
                Main.saveName2 = word;
                startSaveGame2.setText(word);
                Render.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if(Main.saveName2.contains("|")) {
            Main.saveName2 = Main.saveName2.replace("|", "");
            startSaveGame2.setText(Main.saveName2);
        }
        Main.currentSave = 2;
        Main.shouldQuit = false;
        PlayerCountry.initGame();
        PlayerCountry.gameLoop();
    }
        
    if (startSaveGame3.checkForClick()) {
        if (!Util.doesSaveExist(3)){
            Main.saveName3 = "";
            while (!word.equals("@")) {
                cursorTick++;
                word = Typing.currentMenuName(word, cursorTick);
                if (word.equals("@")){
                    break;
                }
                Main.saveName3 = word;
                startSaveGame3.setText(word);
                Render.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if(Main.saveName3.contains("|")) {
            Main.saveName3 = Main.saveName3.replace("|", "");
            startSaveGame3.setText(Main.saveName3);
        }
        Main.currentSave = 3;
        Main.shouldQuit = false;
        PlayerCountry.initGame();
        PlayerCountry.gameLoop();

    }
	
    if (startSaveGame4.checkForClick()) {
		if (!Util.doesSaveExist(4)){           
            Main.saveName4 = "";
            while (!word.equals("@")) {
                cursorTick++;
                word = Typing.currentMenuName(word, cursorTick);
                if (word.equals("@")){
                    break;
                }
                Main.saveName4 = word;
                startSaveGame4.setText(word);
                Render.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if(Main.saveName4.contains("|")) {
            Main.saveName4 = Main.saveName4.replace("|", "");
            startSaveGame4.setText(Main.saveName4);
        }
        Main.currentSave = 4;
        Main.shouldQuit = false;
        PlayerCountry.initGame();
        PlayerCountry.gameLoop();

    }
        
    if (startSaveGame5.checkForClick()) {
        if (!Util.doesSaveExist(5)){
            Main.saveName5 = "";
            while (!word.equals("@")) {
                cursorTick++;
                word = Typing.currentMenuName(word, cursorTick);
                if (word.equals("@")){
                    break;
                }
                Main.saveName5 = word;
                startSaveGame5.setText(word);
                Render.initOrtho();
                renderMenu();
                renderMenu2();
                Render.drawMainMenu();
                Display.update();
                Display.sync(20);
            }
        }
        if(Main.saveName5.contains("|")) {
            Main.saveName5 = Main.saveName5.replace("|", "");
            startSaveGame5.setText(Main.saveName5);
        }
        Main.currentSave = 5;
        Main.shouldQuit = false;
        PlayerCountry.initGame();
        PlayerCountry.gameLoop();

    }
        
		if (generatorIsland.checkForClick()) {
			PlayerCountry.generatorToUse = 0;
			generatorArchipelago.setToggle(false);
			generatorMountains.setToggle(false);
		}

		if (generatorArchipelago.checkForClick()) {
			PlayerCountry.generatorToUse = 1;
			generatorIsland.setToggle(false);
			generatorMountains.setToggle(false);
		}

		if (generatorMountains.checkForClick()) {
			PlayerCountry.generatorToUse = 2;
			generatorIsland.setToggle(false);
			generatorArchipelago.setToggle(false);
		}
	}

	public static void click(float x, float y) {
		for (Button b : buttons) {
			b.click(x, y);
		}
	}

	public static void clickMenu(float x, float y) {
		for (Button b : menuButtons) {
			b.click(x, y);
		}
	}

	public static void clickPause(float x, float y) {
		for (Button b : pauseButtons) {
			b.click(x, y);
		}
	}

	public static void renderMenu() {
		for (Button b : menuButtons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}
	}

	public static void renderPause() {
		for (Button b : pauseButtons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}
	}

	public static void render() {
		for (Button b : buttons) {
			b.render(Mouse.getX(), Main.height - Mouse.getY());
		}
	}

	public static void renderMenu2() {
		for (Button b : menuButtons) {
			b.render2();
		}
	}

	public static void renderPause2() {
		for (Button b : pauseButtons) {
			b.render2();
		}
	}

	public static void render2() {
		for (Button b : buttons) {
			b.render2();
		}
	}

}
