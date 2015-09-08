package eco.ui;

import eco.game.Main;
import eco.game.SaveTask;
import eco.game.ThreadManager;
import eco.game.World;
import eco.render.Camera;
import eco.render.Render;
import eco.render.RenderUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * A collection of various implementations of IInputManager
 *
 * @author phil
 */
public final class InputManager {

    public static final IInputManager gameInput = new IInputManager() {
        @Override
        public void run(int keycode) {
            switch (keycode){
                case Keyboard.KEY_Q:
                    Render.camera = new Camera(-World.mapsize / 2f
                            * Render.tilesize, -4f, World.mapsize / 2f
                            * Render.tilesize);
                    Log.info("Reset camera to default location");
                    break;
                case Keyboard.KEY_GRAVE:
                case Keyboard.KEY_TAB:
                    IGConsole.consoleLoop();
                    break;
                case Keyboard.KEY_ESCAPE:
                    Main.paused ^= true;
                    if (Main.paused) {
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

        @Override
        public void update(){
            final float moveSpeed = 0.1f;

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

            while (Mouse.next()) {
                if (Mouse.getEventButton() > -1) {
                    if (Mouse.getEventButtonState()) {
                        UIManager.click(Mouse.getX(),
                                Display.getHeight() - Mouse.getY());
                    }
                }
            }
        }
    };

    public static final IInputManager consoleInput = new IInputManager() {
        @Override
        public void update() {
            IGConsole.adjustOffset(Mouse.getDWheel() / 100);
        }

        @Override
        public void run(int keycode) {
            switch (keycode) {
                case Keyboard.KEY_GRAVE:
                case Keyboard.KEY_TAB:
                    IGConsole.running = false;
                    break;
                case Keyboard.KEY_RETURN:
                    IGConsole.buffer = IGConsole.buffer.replace(Typer.blinkCharacter, " ");
                    IGConsole.logCommand(IGConsole.buffer);
                    Command.onCommand(IGConsole.buffer);
                    IGConsole.clear = "|";
                    break;
                case Keyboard.KEY_F10:
                    RenderUtil.takeScreenshot();
                    break;
                case Keyboard.KEY_UP:
                    IGConsole.adjustCommandOffset(1);
                    break;
                case Keyboard.KEY_DOWN:
                    IGConsole.adjustCommandOffset(-1);
                    break;
            }
        }
    };

    public static final IInputManager menuInput = new IInputManager() {
        @Override
        public void update() {
            while (Mouse.next()) {
                if (Mouse.getEventButton() > -1) {
                    if (Mouse.getEventButtonState()) {
                        UIManager.clickMenu(Mouse.getX(), Display.getHeight()
                                - Mouse.getY());
                    }
                }
            }
        }

        @Override
        public void run(int keycode) {
            switch (keycode) {
                case Keyboard.KEY_GRAVE:
                case Keyboard.KEY_TAB:
                    IGConsole.consoleLoop();
                    break;
                case Keyboard.KEY_F10:
                    RenderUtil.takeScreenshot();
                    break;
            }
        }
    };

    public static final IInputManager pausedInput = new IInputManager() {
        @Override
        public void update() {
            while (Mouse.next()) {
                if (Mouse.getEventButton() > -1) {
                    if (Mouse.getEventButtonState()) {
                        UIManager.clickPause(Mouse.getX(), Display.getHeight()
                                - Mouse.getY());
                    }
                }
            }
        }

        @Override
        public void run(int keycode) {
            switch (keycode) {
                case Keyboard.KEY_F10:
                    RenderUtil.takeScreenshot();
                    break;
                case Keyboard.KEY_ESCAPE:
                    Main.paused ^= true;
                    break;
            }
        }
    };

    public static final IInputManager gameOverInput = new IInputManager() {
        @Override
        public void update() {
            while (Mouse.next()) {
                if (Mouse.getEventButton() > -1) {
                    if (Mouse.getEventButtonState()) {
                        UIManager.clickGameOver(Mouse.getX(), Display.getHeight()
                                - Mouse.getY());
                    }
                }
            }
        }

        @Override
        public void run(int keycode) {
            switch (keycode) {
                case Keyboard.KEY_F10:
                    RenderUtil.takeScreenshot();
                    break;
            }
        }
    };

}
