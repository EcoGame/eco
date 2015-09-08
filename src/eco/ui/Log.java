package eco.ui;

/**
 * A utility class to make logging information, warnings, and errors easier
 * Colors output with ansi escape codes
 *
 * @author phil
 */
public class Log {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[1;30m";
    public static final String ANSI_RED = "\u001B[1;31m";
    public static final String ANSI_GREEN = "\u001B[1;32m";
    public static final String ANSI_YELLOW = "\u001B[1;33m";
    public static final String ANSI_BLUE = "\u001B[1;34m";
    public static final String ANSI_PURPLE = "\u001B[1;35m";
    public static final String ANSI_CYAN = "\u001B[1;36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static int level = 0;
    // -1 - none
    // 0 - all
    // 1 - no info
    // 2 - warnings and severe
    // 3 - only severe


    public static void info(String message) {
        if (level == -1) {
            return;
        }
        if (level == 0) {
            boolean moved = false;
            if (Console.moveBack()) {
                moved = true;
            }
            if (IGConsole.running){
                IGConsole.log(ANSI_WHITE + "[Info] " + message + ANSI_RESET);
            } else {
                System.out.println(ANSI_WHITE + "[Info] " + message + ANSI_RESET);
                if (moved) {
                    Console.printIndicator();
                }
            }
        }
    }

    public static void setLevel(int newlevel) {
        if (newlevel < -1 || newlevel > 3) {
            warning("Logger level out of bounds! Keeping previous level");
            return;
        }
        level = newlevel;
    }

    public static void severe(String message) {
        if (level == -1) {
            return;
        }
        boolean moved = false;
        if (Console.moveBack()) {
            moved = true;
        }
        if (IGConsole.running){
            IGConsole.log(ANSI_RED + "[Severe] " + message + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "[Severe] " + message + ANSI_RESET);
            if (moved) {
                Console.printIndicator();
            }
        }
    }

    public static void success(String message) {
        if (level == -1) {
            return;
        }
        if (level <= 1) {
            boolean moved = false;
            if (Console.moveBack()) {
                moved = true;
            }
            if (IGConsole.running){
                IGConsole.log(ANSI_GREEN + "[Success] " + message + ANSI_RESET);
            } else {
                System.out.println(ANSI_GREEN + "[Success] " + message + ANSI_RESET);
                if (moved) {
                    Console.printIndicator();
                }
            }
        }
    }

    public static void title(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void stop(String message) {
        if (IGConsole.running){
            IGConsole.log(ANSI_CYAN + message + ANSI_RESET);
        } else {
            Console.moveBack();
            System.out.println(ANSI_CYAN + message + ANSI_RESET);
        }
    }

    public static void warning(String message) {
        if (level == -1) {
            return;
        }
        if (level <= 2) {
            boolean moved = false;
            if (IGConsole.running){
                IGConsole.log(ANSI_YELLOW + "[Warning] " + message + ANSI_RESET);
            } else {
                if (Console.moveBack()) {
                    moved = true;
                }
                System.out.println(ANSI_YELLOW + "[Warning] " + message + ANSI_RESET);
                if (moved) {
                    Console.printIndicator();
                }
            }
        }
    }

    public static void print(String message) {
        if (IGConsole.running){
            IGConsole.log(message);
        } else {
            boolean moved = false;
            if (Console.moveBack()) {
                moved = true;
            }
            System.out.print(message);
            if (moved) {
            }
        }
    }

    public static void println(String message) {
        if (IGConsole.running){
            IGConsole.log(message);
        } else {
            boolean moved = false;
            if (Console.moveBack()) {
                moved = true;
            }
            System.out.println(message);
            if (moved) {
                Console.printIndicator();
            }
        }
    }

    public static void print(int message) {
        boolean moved = false;
        if (Console.moveBack()) {
            moved = true;
        }
        System.out.print(message);
        if (moved) {
        }
    }

    public static void println(int message) {
        boolean moved = false;
        if (Console.moveBack()) {
            moved = true;
        }
        System.out.println(message);
        if (moved) {
            Console.printIndicator();
        }
    }

    public static void print(float message) {
        boolean moved = false;
        if (Console.moveBack()) {
            moved = true;
        }
        System.out.print(message);
        if (moved) {
        }
    }

    public static void println(float message) {
        boolean moved = false;
        if (Console.moveBack()) {
            moved = true;
        }
        System.out.println(message);
        if (moved) {
            Console.printIndicator();
        }
    }


}
