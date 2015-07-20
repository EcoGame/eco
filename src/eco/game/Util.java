package eco.game;

/**
 * A class that contains various utilities and convenience methods
 *
 * @author phil, nate, connor, will
 */

public class Util {

    public static void readError() {
        Message.addMessage(new Message(
                "------------------------------------------", 100, 100, 300));
        Message.addMessage(new Message("Failed to load save!", 100, 130, 300));
        Message.addMessage(new Message(
                "The file either disappeared or is corrupt!", 100, 160, 300));
        Message.addMessage(new Message(
                "------------------------------------------", 100, 190, 300));
    }

    public static void readSuccess() {
        Message.addMessage(new Message("----------------------------------",
                100, 100, 300));
        Message.addMessage(new Message("Loaded game state from save file!",
                100, 130, 300));
        Message.addMessage(new Message("----------------------------------",
                100, 160, 300));
    }

    public static NPCCountry[] getCountries() {
        return PlayerCountry.countries
                .toArray(new NPCCountry[PlayerCountry.countries.size()]);
    }

    public static void putCountries(NPCCountry[] toPut) {
        PlayerCountry.countries.clear();
        for (NPCCountry c : toPut) {
            Country.countries.add(c);
        }
    }

    public static Point getRandomUnclaimedTile() {
        int x = MathUtil.randInt(1, World.mapsize - 2);
        int y = MathUtil.randInt(1, World.mapsize - 2);
        ;
        while (!World.isDryLand(x, y) || !World.isUnclaimed(x, y)) {
            x = MathUtil.randInt(1, World.mapsize - 2);
            y = MathUtil.randInt(1, World.mapsize - 2);
            ;
        }
        return new Point(x, y);
    }

    public static void quit(int exitCode) {
        Log.stop("Stopping (exit code " + exitCode + ")");
        Console.moveBack();
        Console.close();
        System.exit(exitCode);
    }

}
