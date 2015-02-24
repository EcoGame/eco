package eco;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class InputManager {

	public static void update(){
		if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
			Render.rot -= 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
			Render.rot += 0.5f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			Render.z += 0.1f;
			Render.camera.moveForward(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Render.x -= 0.1f;
			Render.camera.moveRight(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			Render.z -= 0.1f;
			Render.camera.moveBack(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Render.x += 0.1f;
			Render.camera.moveLeft(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			Render.camera.moveDown(0.1f);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			Render.camera.moveUp(0.1f);
		}
		while (Keyboard.next()){
			if (Keyboard.getEventKeyState()){
				switch (Keyboard.getEventKey()){
					case Keyboard.KEY_G:
						Main.debug ^= true;
						System.out.println("DEBUG MODE IS TOGGLED!");
						break;
					case Keyboard.KEY_Q:
						Render.camera = new Camera(-World.mapsize / 2f * Render.tilesize,
										-4f, World.mapsize / 2f * Render.tilesize);
						System.out.println("Reset camera to default location");
						break;
					case Keyboard.H:
					case Keyboard.KEY_GRAVE:
						Main.fullDebug ^= true;
						break;
					case Keyboard.KEY_M:
						Render.multithreading ^= true;
						break;
					case Keyboard.KEY_P:
						Main.popDiags ^= true;
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
