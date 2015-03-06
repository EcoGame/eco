package eco;

import org.lwjgl.input.Mouse;

public class UIManager{
	public static ToggleButton toggleFeedDisplaced = new ToggleButton(900, 657, 25, 4, 2, 5, 2, true);
	public static ToggleButton toggleFavorWarrior = new ToggleButton(900, 627, 25, 4, 2, 5, 2, false);

	public static Button increaseWarriorRatio = new Button(525, 642, 20, 0, 2, 1, 2);
	public static Button decreaseWarriorRatio = new Button(525, 672, 20, 2, 2, 3, 2);

	public static Button increaseTickRate = new Button(495, 606, 20, 0, 2, 1, 2);
	public static Button decreaseTickRate = new Button(495, 636, 20, 2, 2, 3, 2);

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
