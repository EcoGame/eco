package eco;

import org.lwjgl.input.Mouse;

public class UIManager{
	public static ToggleButton toggleFeedDisplaced = new ToggleButton(900, 657, 25, 4, 2, 5, 2, true);
	public static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627, 25, 4, 2, 5, 2, false);

	public static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2, 1, 2);
	public static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2, 3, 2);

	public static Button increaseTickRate = new Button(495, 606, 20, 0, 2, 1, 2);
	public static Button decreaseTickRate = new Button(495, 636, 20, 2, 2, 3, 2);

	public static TextButton startTempGame = new TextButton((Main.width / 2) - 128, 256, 256, 32, 6, 2, 7, 2, "Start Test Game");

	public static ToggleTextButton generatorIsland = new ToggleTextButton((Main.width / 2) + 256, 256, 256, 32, 6, 2, 7, 2, "Island", true);
	public static ToggleTextButton generatorArchipelago = new ToggleTextButton((Main.width / 2) + 256, 320, 256, 32, 6, 2, 7, 2, "Archipelago", false);
	public static ToggleTextButton generatorMountains = new ToggleTextButton((Main.width / 2) + 256, 384, 256, 32, 6, 2, 7, 2, "Mountains (WIP)", false);

	public static void update(){
		if (toggleFeedDisplaced.checkForClick()){
			Main.displacedEat ^= true;
		}
		if (toggleFavorWarrior.checkForClick()){
			Main.favorFarmers ^= true;
		}
		if (increaseWarriorRatio.checkForClick()){
			if (Main.desiredWarriorRatio != 1.0f){
				Main.desiredWarriorRatio += 0.01f;
			}
		}
		if (decreaseWarriorRatio.checkForClick()){
			if (Main.desiredWarriorRatio != 0.0f){
				Main.desiredWarriorRatio -= 0.01f;
			}
		}
		if (increaseTickRate.checkForClick()){
			if (Main.framesPerTick != 100){
				Main.framesPerTick++;
			}
		}
		if (decreaseTickRate.checkForClick()){
			if (Main.framesPerTick != 2){
				Main.framesPerTick--;
			}
		}
	}

	public static void clickMenu(float x, float y){
		startTempGame.click(x, y);
		generatorIsland.click(x, y);
		generatorArchipelago.click(x, y);
		generatorMountains.click(x, y);
	}

	public static void renderMenu(){
		startTempGame.render(Mouse.getX(), Main.height - Mouse.getY());
		generatorIsland.render(Mouse.getX(), Main.height - Mouse.getY());
		generatorArchipelago.render(Mouse.getX(), Main.height - Mouse.getY());
		generatorMountains.render(Mouse.getX(), Main.height - Mouse.getY());
	}

	public static void renderMenu2(){
		startTempGame.render2();
		generatorIsland.render2();
		generatorArchipelago.render2();
		generatorMountains.render2();
	}

	public static void render2(){

	}

	public static void updateMenu(){
		if (startTempGame.checkForClick()){
			Main.initTempGame();
			Main.shouldBeInMenu = false;
		}
		if (generatorIsland.checkForClick()){
			Main.generatorToUse = 0;
			generatorArchipelago.toggle = false;
			generatorMountains.toggle = false;
		}

		if (generatorArchipelago.checkForClick()){
			Main.generatorToUse = 1;
			generatorIsland.toggle = false;
			generatorMountains.toggle = false;
		}

		if (generatorMountains.checkForClick()){
			Main.generatorToUse = 2;
			generatorIsland.toggle = false;
			generatorArchipelago.toggle = false;
		}
	}

	public static void click(float x, float y){
		toggleFeedDisplaced.click(x, y);
		toggleFavorWarrior.click(x, y);
		increaseWarriorRatio.click(x, y);
		decreaseWarriorRatio.click(x, y);
		increaseTickRate.click(x, y);
		decreaseTickRate.click(x, y);
	}

	public static void render(){
		toggleFeedDisplaced.render(Mouse.getX(), Main.height - Mouse.getY());
		toggleFavorWarrior.render(Mouse.getX(), Main.height - Mouse.getY());
		increaseWarriorRatio.render(Mouse.getX(), Main.height - Mouse.getY());
		decreaseWarriorRatio.render(Mouse.getX(), Main.height - Mouse.getY());
		increaseTickRate.render(Mouse.getX(), Main.height - Mouse.getY());
		decreaseTickRate.render(Mouse.getX(), Main.height - Mouse.getY());
	}

}
