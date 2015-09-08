package eco.ui;

import eco.game.ThreadManager;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * This class manages reading in console input
 * runs on another thread for simplicity
 *
 * @author phil
 */
public class Console implements Runnable {

    private static Scanner scanner;
    private static volatile boolean running = true;

    public static void init() {
        Log.success("Started the console");
        scanner = new Scanner(System.in);
        scanner.useDelimiter(Pattern.compile("[\\n;]"));
        printIndicator();
        ThreadManager.addJob(new Console());
    }

    public static void printIndicator() {
        System.out.print("> ");
    }

    public static boolean moveBack() {
        if (scanner == null) {
            return false;
        }
        System.out.print("\033[2000D");
        return true;
    }

    @Override
    public void run() {
        while (running) {
            String command = scanner.next();
            Command.onCommand(command);
            printIndicator();
        }
        scanner.close();
    }

    public static void close() {
        running = false;
    }
}
