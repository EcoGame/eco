package eco.game;


/**
 * This class represents and simulates another country
 *
 * @author phil, nate
 */
public class NPCCountry extends Country {

    // ================//
    // Main Variables //
    // ===============//

    // ==================//
    // NPCCountry Variables //
    // ==================//
    public String name;

    // ====================//
    // Simulation Settings //
    // ====================//
    public Score score = new Score();
    public boolean favorFarmers = true;
    public boolean displacedEat = true;
    public float desiredWarriorRatio = 0.15f;
    public float desiredFarmerRatio = 0.85f;
    public float fBirthRate = 0.03f;
    public float fDeathRate = 0.02f;
    public float fDefaultDeathRate = 0.02f;
    public float wBirthRate = 0.008f;
    public float wDeathRate = 0.002f;
    public float wDefaultDeathRate = 0.002f;
    public float farmerDeathRatio = 0.75f;
    public float warriorDeathRatio = 0.75f;

    public static float wheatRot = 0.7f;

    public int landsize;

    public boolean cutForests = false;

    public boolean forceConscription = false;

    public boolean dead = false;

    public AggressionScore aggression = new AggressionScore();

    // ================//
    // Other Variables //
    // ================//
    public int maxpop = 10000;
    public int displaced = 0;

    public NPCCountry() {

        super();
        name = NameGen.generateRandom();

        fBirthRate = MathUtil.randFloat(0.01f, 0.07f);
        fDeathRate = 0.02f;
        fDefaultDeathRate = 0.02f;
        wBirthRate = MathUtil.randFloat(0.001f, 0.01f);
        wDeathRate = 0.002f;
        wDefaultDeathRate = 0.002f;
        farmerDeathRatio = MathUtil.randFloat(0.00f, 1f);
        warriorDeathRatio = MathUtil.randFloat(0.00f, 1f);


        claimInitialLand(Util.getRandomUnclaimedTile());
        color = RenderUtil.getColor(MathUtil.randInt(32) * 8, MathUtil.randInt(32) * 8, MathUtil.randInt(32) * 8);

    }

    @Override
    public void updateTick() {

        // ====//
        // War //
        // ====//
        //int diff = (Math.abs(aggression.aggressionScore - PlayerCountry.aggression.aggressionScore)) + 1;
        //int warMul = 100;
        //if (PlayerCountry.year < 250){
        //     warMul = 1000;
        //}
        // if (World.random.nextInt(diff * warMul) == 0){
        //War.attackPlayer(this);
        // }


        // ================//
        //  Neural Network //
        // ================//
        //eco.neural.NeuralManager.neuralTick(PlayerCountry.countries.indexOf(this));
        // if (dead){
        //    aggression.aggressionScore = 0;
        // }
    }

    public int getScore() {
        try {
            return score.scoreAt(PlayerCountry.year - 1);
        } catch (Exception e) {
            return 0;
        }
    }

    public void takeLand(int toTake) {
        if (toTake >= land.getLand()) {
            toTake -= land.getLand();
            land.setLand(0);
            landsize -= toTake;
        } else {
            land.setLand(land.getLand() - toTake);
        }
    }

}
