package eco.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * This class handles keyboard and mouse input, passing it along to where ever
 * it needs to go
 *
 * @author phil, connor
 */

public class InputManager {

    private static final float moveSpeed = 0.1f;

    public static void update() {
        if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
            Render.camera.yaw -= 0.5f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
            Render.camera.yaw += 0.5f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            Render.camera.moveForward(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            Render.camera.moveRight(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            Render.camera.moveBack(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            Render.camera.moveLeft(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            Render.camera.moveDown(moveSpeed);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            Render.camera.moveUp(moveSpeed);
        }
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_G:
                        System.out.println("DEBUG MODE IS TOGGLED!");
                        break;
                    case Keyboard.KEY_Q:
                        Render.camera = new Camera(-World.mapsize / 2f
                                * Render.tilesize, -4f, World.mapsize / 2f
                                * Render.tilesize);
                        System.out.println("Reset camera to default location");
                        break;
                    case Keyboard.KEY_H:
                    case Keyboard.KEY_GRAVE:
                        Main.fullDebug ^= true;
                        break;
                    case Keyboard.KEY_ESCAPE:
                        Main.paused ^= true;
                        if (Main.paused == true) {
                            ThreadManager.addJob(new SaveTask());
                        }
                        break;
                    case Keyboard.KEY_F10:
                        RenderUtil.takeScreenshot();
                        break;
                    case Keyboard.KEY_O:
                        Render.overhead ^= true;
                        break;
                }
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() > -1) {
                if (Mouse.getEventButtonState()) {
                    UIManager.click(Mouse.getX(),
                            Display.getHeight() - Mouse.getY());
                } else {
                }
            }
        }
    }

    public static void updateMenu() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_F10:
                        RenderUtil.takeScreenshot();
                        break;
                }
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() > -1) {
                if (Mouse.getEventButtonState()) {
                    UIManager.clickMenu(Mouse.getX(), Display.getHeight()
                            - Mouse.getY());
                } else {
                }
            }
        }
    }

    public static void updatePause() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_F10:
                        RenderUtil.takeScreenshot();
                        break;
                    case Keyboard.KEY_ESCAPE:
                        Main.paused ^= true;
                        break;
                }
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() > -1) {
                if (Mouse.getEventButtonState()) {
                    UIManager.clickPause(Mouse.getX(), Display.getHeight()
                            - Mouse.getY());
                } else {
                }
            }
        }
    }

    public static void updateGameOver() {
        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                switch (Keyboard.getEventKey()) {
                    case Keyboard.KEY_F10:
                        RenderUtil.takeScreenshot();
                        break;
                }
            }
        }
        while (Mouse.next()) {
            if (Mouse.getEventButton() > -1) {
                if (Mouse.getEventButtonState()) {
                    UIManager.clickGameOver(Mouse.getX(), Display.getHeight()
                            - Mouse.getY());
                } else {
                }
            }
        }
    }

}
