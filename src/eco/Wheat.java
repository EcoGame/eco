package eco;

public class Wheat {

    public static int tWheat;
    public static int wheatPrice;
    public static int unemployedFarmers = 0;
    public static int employedFarmers = 0;

    public static int farmPacks(int tAcres) {

        int farmPacks = tAcres/5;
        return farmPacks;

    }

    public static int unemployedFarmers(int farmPacks, int fPop) {

        unemployedFarmers = fPop - farmPacks;

        if(unemployedFarmers < 0) {
            unemployedFarmers = 0;
        }

        return unemployedFarmers;

    }

    public static int employedFarmers(int fPop, int unemployedFarmers) {

        employedFarmers = (fPop - unemployedFarmers);
        return employedFarmers;

    }

    public static int tWheat(int employedFarmers) {

        tWheat = employedFarmers * 40;
        return tWheat;

    }

}
