package eco;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputManager {

	public static final float moveSpeed = 0.1f;

	public static void updateMenu(){
		while (Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				switch (Keyboard.getEventKey()){
					case Keyboard.KEY_F10:
						Util.takeScreenshot();
						break;
				}
			}
		}
		while(Mouse.next()) {
			if (Mouse.getEventButton() > -1) {
				if (Mouse.getEventButtonState()) {
					UIManager.clickMenu(Mouse.getX(), Display.getHeight() - Mouse.getY());
				}
				else{
				}
			}
		}
	}

	public static void updatePaused(){
		while (Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				switch (Keyboard.getEventKey()){
					case Keyboard.KEY_F10:
						Util.takeScreenshot();
						break;
				}
			}
		}
		while(Mouse.next()) {
			if (Mouse.getEventButton() > -1) {
				if (Mouse.getEventButtonState()) {
					UIManager.clickPaused(Mouse.getX(), Display.getHeight() - Mouse.getY());
				}
				else{
				}
			}
		}
	}

	public static void update(){
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			Render.rot -= 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			Render.rot += 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Render.z += moveSpeed;
			Render.camera.moveForward(moveSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Render.x -= moveSpeed;
			Render.camera.moveRight(moveSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Render.z -= moveSpeed;
			Render.camera.moveBack(moveSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Render.x += moveSpeed;
			Render.camera.moveLeft(moveSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			Render.camera.moveDown(moveSpeed);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			Render.camera.moveUp(moveSpeed);
		}
		while (Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				switch (Keyboard.getEventKey()){
					case Keyboard.KEY_G:
						Main.debug ^= true;
						//System.out.println("DEBUG MODE IS TOGGLED!");
						break;
					case Keyboard.KEY_Q:
						Render.camera = new Camera(-World.mapsize / 2f * Render.tilesize, -4f, World.mapsize / 2f * Render.tilesize);
						//System.out.println("Reset camera to default location");
						break;
					case Keyboard.KEY_H:
					case Keyboard.KEY_GRAVE:
						Main.fullDebug ^= true;
						break;
					case Keyboard.KEY_M:
						Render.multithreading ^= true;
						break;
                    case Keyboard.KEY_LMETA:
                            Util.createSave();
                            System.out.println("Saved");
						break;
					case Keyboard.KEY_P:
						Main.popDiags ^= true;
						break;
					case Keyboard.KEY_ESCAPE:
						Main.paused ^= true;
						break;
                    case Keyboard.KEY_F10:
							Util.takeScreenshot();
							break;
				}
			}
		}
		while(Mouse.next()) {
			if (Mouse.getEventButton() > -1) {
				if (Mouse.getEventButtonState()) {
					UIManager.click(Mouse.getX(), Display.getHeight() - Mouse.getY());
				}
				else{
				}
			}
		}
	}

}
