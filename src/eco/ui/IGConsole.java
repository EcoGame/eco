package eco.ui;

import eco.game.Util;
import eco.render.FPSCounter;
import eco.render.Render;
import org.lwjgl.opengl.Display;

/**
 * Manages the in-game console
 *
 * @author phil
 */
public class IGConsole {

    public static final boolean enabled = true;
    public static String buffer;
    public static boolean running;
    public static String ps1 = "game@eco $ ";

    public static void consoleLoop(){
        if (!enabled){
            return;
        }
        buffer = "";
        running = true;
        Render.initConsoleRender();

        while(running){
            if (Display.isCloseRequested()) {
                Util.quit(0);
            }
            Command.update();

            InputManager.updateConsole();
            buffer = buffer.toLowerCase();

            Render.drawConsole();
            FPSCounter.tick();
            Display.update();
            Display.sync(60);
        }
    }

}
