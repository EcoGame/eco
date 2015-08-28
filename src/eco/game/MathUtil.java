package eco.game;

import java.util.Random;

/**
 * A class that holds various methods that do some math
 * Mostly to keep other classes less cluttered
 *
 * @author phil
 */
public class MathUtil {
    private static Random random = new Random();

    public static int randInt(int max) { // Returns a random number below max.

        return random.nextInt(max);

    }

    public static int randInt(int min, int max) { // Returns a random number
        // between min and max.

        return min + random.nextInt((max + 1) - min);

    }

    public static float randFloat(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static int computeTotalHunger(Country country) {
        return country.farmer.getTotalHunger()
                + country.warrior.getTotalHunger();
    }

    public static String getWheatRateForDisplay(Country country) {
        int hunger = computeTotalHunger(country);
        int input = country.farmer.getWheatRate()
                * country.farmer.getPop();
        input += country.land.getWheatRate();
        int total = input - hunger;
        if (total < 0) {
            return "dW/dT: " + String.valueOf(total) + " Bushels";
        } else if (total == 0) {
            return "dW/dT: " + "0 Bushels";
        } else {
            return "dW/dT: +" + String.valueOf(total) + " Bushels";
        }
    }

    public static int getWheatRate(Country country) {
        int hunger = computeTotalHunger(country);
        int input = country.farmer.getWheatRate()
                * country.farmer.getPop();
        input += country.land.getWheatRate();
        return input - hunger;
    }

    public static float getTotalPopf(Country country) {
        return country.warrior.getPopFloat()
                + country.farmer.getPopFloat();
    }

    public static int getTotalPop(Country country) {
        return country.warrior.getPop() + country.farmer.getPop();
    }

    public static float calcAverageCountryScore() {
        float total = 0;
        //for (NPCCountry c : PlayerCountry.countries) {
        // total += c.score.scoreAt(Math.max(0, PlayerCountry.year - 1));
        // }
        return total / PlayerCountry.countries.size();
    }
}
