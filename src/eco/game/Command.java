package eco.game;

import java.util.ArrayList;

/**
 * This class does stuff with console input
 *
 * @author phil
 */
public class Command {

    private static ArrayList<String> commandBuffer = new ArrayList<>();
    private static ArrayList<String[]> argumentBuffer = new ArrayList<>();

    private static ArrayList<String> helpCommands = new ArrayList<>();

    static {
        helpCommands.add("debug -> toggles debug mode");
        helpCommands.add("foo -> a test command");
        helpCommands.add("splash -> changes the menu splashtext");
        helpCommands.add("exit -> exits the program");
        helpCommands.add("exitgame -> returns to the menu");
        helpCommands.add("testgame -> starts a test game that will not be saved");
        helpCommands.add("skycolor -> changes the sky color");
        helpCommands.add("help -> list all commands");
    }

    public static void onCommand(String command) {
        command = command.toLowerCase();
        if (command.isEmpty()) {
            Log.println("Please enter a command!");
            return;
        }
        String[] parts = command.split(" ");
        commandBuffer.add(parts[0]);
        if (parts.length >= 2) {
            String[] newParts = new String[parts.length - 1];
            System.arraycopy(parts, 1, newParts, 0, parts.length - 1);
            argumentBuffer.add(newParts);
        } else {
            argumentBuffer.add(new String[0]);
        }
    }

    public static void update() {
        for (int i = 0; i < commandBuffer.size(); i++) {
            String command = commandBuffer.get(i);
            String[] args = argumentBuffer.get(i);
            switch (command) {
                case "foo":
                    Log.println("bar!");
                    break;
                case "debug":
                    Main.fullDebug ^= true;
                    World.invalidateChunkCache();
                    break;
                case "splash":
                    SplashText.newSplash();
                    break;
                case "exit":
                    Util.quit(0);
                    break;
                case "exitgame":
                    clearBuffers();
                    exitGame(args);
                    break;
                case "testgame":
                    clearBuffers();
                    PlayerCountry.testGame();
                    break;
                case "skycolor":
                    skyColor(args);
                    break;
                case "help":
                    help(args);
                    break;
                default:
                    Log.println("Unknown command '" + command + "'");
            }
        }
        clearBuffers();
    }

    private static void skyColor(String[] args) {
        if (args != null && args.length == 1 && args[0].equals("reset")) {
            RenderUtil.setDefaultClearColor();
            return;
        }
        if (args == null || args.length != 3) {
            Log.println("Invalid arguments! skycolor <r> <g> <b>");
            return;
        }
        try {
            int r = Integer.valueOf(args[0]);
            int g = Integer.valueOf(args[1]);
            int b = Integer.valueOf(args[2]);
            RenderUtil.setClearColor(r, g, b);
        } catch (NumberFormatException e) {
            Log.println("Error parsing arguments");
        }
    }

    private static void exitGame(String[] args) {
        if (PlayerCountry.playerCountry == null) {
            Log.println("Not in a game!");
            return;
        }
        Main.shouldBeInMenu = true;
        Main.shouldQuit = true;
    }

    private static void help(String[] args) {
        Log.println(Log.ANSI_BLUE + "Eco Commands" + Log.ANSI_RESET);
        for (String s : helpCommands) {
            Log.println(s);
        }
    }

    private static void clearBuffers() {
        commandBuffer.clear();
        argumentBuffer.clear();
    }

}
