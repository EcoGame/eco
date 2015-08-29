package eco.game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An basic representation of a nation-state.
 * PlayerCountry and NPCCountry extend this
 * <p/>
 * Subclasses can define custom tick behavior by
 * overriding updateTick()
 *
 * @author phil
 */

public abstract class Country {

    // ========================== //
    // Global Simulation Settings //
    // ========================== //

    public static final int ticks = 2000;
    public static int year = 0;

    public static ArrayList<Country> countries = new ArrayList<>();

    public static int generatorToUse = 0;

    public static float globalWheatRot = 0.9f;

    public static final int startLand = 10000;

    private static int landClaimed = 0;

    // =====================//
    // Simulation Variables //
    // =====================//
    public float fBirthRate = 0.03f;
    public float fDeathRate = 0.02f;
    public float wBirthRate = 0.008f;
    public float wDeathRate = 0.002f;

    public float farmerDeathRatio = 0.75f;
    public float warriorDeathRatio = 0.75f;
    public float desiredWarriorRatio = 0.15f;
    public float desiredFarmerRatio = 1f - desiredWarriorRatio;

    public boolean favorFarmers = true;
    public boolean displacedEat = true;

    public float wheatRot = globalWheatRot;

    public boolean forceConscription = true;

    public int balanceCooldown = 0;

    public Point start;

    // ================//
    // Instanced Stuff //
    // ================//
    public Wheat wheat = new Wheat();
    public Economy economy = new Economy();
    public Land land = new Land();
    public Wood wood = new Wood();
    public Stone stone = new Stone();
    public AggressionScore aggression = new AggressionScore();
    public Score score = new Score();

    public ArrayList<SocialClass> classes = new ArrayList<>();
    public Farmer farmer;
    public Warrior warrior;

    public String name = NameGen.generateCountry();

    public Treble<Float, Float, Float> color = new Treble<>(1f, 1f, 1f);

    public Country() {
        farmer = new Farmer(0.03f, 0.02f, 0.85f);
        farmer.setBias(1);
        classes.add(farmer);
        warrior = new Warrior(0.008f, 0.002f, 0.15f);
        classes.add(warrior);
        Collections.sort(classes);

        countries.add(this);
    }

    public static void init(){
        Country pc = countries.get(0);
        countries.clear();;
        countries.add(pc);
        for (int i = 0; i < 10; i++){
            new NPCCountry();
        }
    }

    public static void globalTick() {
        for (Country c : countries) {
            c.tick();
        }
    }

    final public void tick() {
        // ==================//
        // Population growth //
        // ==================//

        float newPopulation = 0f;
        for (SocialClass sc : classes) {
            newPopulation += sc.updatePop();
        }
        if (forceConscription && balanceCooldown == 0) {
            for (int i = 0; i < classes.size() - 1; i++) {
                newPopulation = classes.get(i).rebalance(newPopulation);
            }
            classes.get(classes.size() - 1).rebalanceLast(newPopulation);
        } else if (balanceCooldown != 0) {
            balanceCooldown--;
        }

        // =================//
        // Wheat Production //
        // =================//
        wheat.add(farmer.getPop() * farmer.getWheatRate());
        land.updateWheat(wheat);


        for (SocialClass sc : classes) {
            wheat.setAmount(sc.updateHunger(wheat.getAmount()));
            sc.tick();
        }

        // ==============//
        // Other Updates //
        // ==============//
        wood.tick();
        stone.tick();
        wheat.tick();

        // ======//
        // Score //
        // ======//
        int tick = year;
        score.calculateTickScore(tick, farmer.getPop(), warrior.getPop(), wheat.getAmount(), economy.getTreasury());
        score.calculateAvgScore(tick);
        score.calculateTickGrowth(tick);
        score.calculateAvgGrowth(tick);
        score.calculatePeakScore(tick);
        score.calculateTotalScore(tick);
        aggression.calculateAggressionScore(warrior.getPop(), false);


        updateTick();
    }

    public abstract void updateTick();

    public static Treble<Float, Float, Float> getTerritoryColor(int id) {
        if (id == 0 || id == -1) {
            return null;
        }
        return countries.get(id - 1).color;
    }

    protected void claimInitialLand(Point start) {
        ArrayList<Point> queue = new ArrayList<>();
        queue.add(start);

        Point point;
        int x;
        int y;
        while (!queue.isEmpty()){
            point = queue.get(0);
            x = point.getX();
            y = point.getY();
            queue.remove(0);

            World.claimLand(point.getX(), point.getY(), getClaimId());
            landClaimed++;

            if (World.isDryLand(x - 1, y) && World.isUnclaimed(x - 1, y)) {
                if (!queue.contains(new Point(x - 1, y))) {
                    queue.add(new Point(x - 1, y));
                }
            }
            if (World.isDryLand(x + 1, y) && World.isUnclaimed(x + 1, y)) {
                if (!queue.contains(new Point(x + 1, y))) {
                    queue.add(new Point(x + 1, y));
                }
            }
            if (World.isDryLand(x, y - 1) && World.isUnclaimed(x, y - 1)) {
                if (!queue.contains(new Point(x, y - 1))) {
                    queue.add(new Point(x, y - 1));
                }
            }
            if (World.isDryLand(x, y + 1) && World.isUnclaimed(x, y + 1)) {
                if (!queue.contains(new Point(x, y + 1))) {
                    queue.add(new Point(x, y + 1));
                }
            }
        }

    }

    public short getClaimId(){
        return (short) (countries.indexOf(this) + 1);
    }

}
