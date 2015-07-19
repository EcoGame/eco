package eco.game;

import java.util.ArrayList;

public class EventLog {

    private static ArrayList<String> log = new ArrayList<String>();

    private static int mode = 0;

    public static void log(int year, String message) {
        if (mode == 0) {
            message = "[" + year + "] " + message;
            log.add(message);
        }
        if (mode == 1) {
            if (year == -1) {
                message = "[Debug] " + message;
                log.add(message);
            } else {
                message = "[" + year + "] " + message;
                log.add(message);
            }
        } else if (mode == 2) {
            if (year == -1) {
                message = "[Debug] " + message;
                log.add(message);
            }
        }
    }

    public static ArrayList<String> getAll() {
        return log;
    }

    public static void dump() {
        for (String s : log) {
            System.out.println(s);
        }
    }

}
