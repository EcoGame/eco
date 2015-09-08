package eco.ui;

import eco.game.Util;
import eco.render.FPSCounter;
import eco.render.Render;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;

/**
 * Manages the in-game console
 *
 * @author phil
 */
public class IGConsole {

    private static final int historySize = 300;
    private static ArrayList<String> history = new ArrayList<>();
    private static ArrayList<String> commandHistory = new ArrayList<>();
    private static int offset = 0;
    private static int commandOffset = 0;

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

            InputManager.consoleInput.update();
            buffer = Typer.type(buffer, InputManager.consoleInput);
           
            Render.drawConsole();
            FPSCounter.tick();
            Display.update();
            Display.sync(60);
        }
    }

    public static void log(String message){
        message = message.replace(Log.ANSI_RESET, "");
        message = message.replace(Log.ANSI_WHITE, "");
        history.add(message);
        while (history.size() > historySize){
            history.remove(0);
        }
    }

    public static void logCommand(String command){
        commandHistory.add(command);
        while (commandHistory.size() > historySize + 1){
            commandHistory.remove(0);
        }
    }

    public static ArrayList<String> getHistory(){
        return history;
    }

    public static int getOffset(){
        return offset;
    }

    public static void adjustCommandOffset(int amount) {
        commandOffset += amount;
        commandOffset = Math.max(0, commandOffset);
        commandOffset = Math.min(commandHistory.size() - 1, commandOffset);
        if (commandOffset < 0){
            commandOffset = 0;
        }
        buffer = commandHistory.get(commandHistory.size() - 1 - commandOffset);
    }

    public static void adjustOffset(int amount) {
        offset += amount;
        offset = Math.max(0, offset);
        offset = Math.min(history.size() - 1, offset);
        if (offset < 0){
            offset = 0;
        }
    }

    public static void reset(){
        if (running){
            history.clear();
            commandHistory.clear();
            offset = 0;
            commandOffset = 0;
        }
    }

}
