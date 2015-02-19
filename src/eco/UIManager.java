package eco;

import java.util.ArrayList;

public class UIManager{
	public static Button testButton = new Button(100, 10, 50, 2, 2);

	public static void update(){
		if (testButton.checkForClick()){
			World.messages.add(new Message("dank buttons", 10, 30, 30));
		}
	}

	public static void click(float x, float y){
		testButton.click(x, y);
	}

	public static void render(){
		testButton.render();
	}

}
