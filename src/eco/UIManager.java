package eco;


public class UIManager{
	public static Button testButton = new Button(800, 100, 50, 1, 2);

	public static void update(){
		if (testButton.checkForClick()){
			//World.messages.add(new Message("dank buttons", 10, 30, 30));
			Render.multithreading ^= true;
		}
	}

	public static void click(float x, float y){
		testButton.click(x, y);
	}

	public static void render(){
		testButton.render();
	}

}
