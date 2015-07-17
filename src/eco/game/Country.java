package eco.game;

import java.util.ArrayList;

/**
 * An basic representation of a nation-state.
 * PlayerCountry and NPCCountry extend this
 *
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

    // ================//
    // Instanced Stuff //
    // ================//
    public Wheat wheat = new Wheat();
    public Farmer farmer = new Farmer();
    public Warrior warrior = new Warrior();
    public Economy economy = new Economy();
    public Land land = new Land();
    public Wood wood = new Wood();
    public Stone stone = new Stone();
    public AggressionScore aggression = new AggressionScore();
    public Score score = new Score();

    public String name = NameGen.generateCountry();

    public Country(){
        countries.add(this);
    }

    public static void globalTick() {
        for (Country c : countries){
            c.tick();
        }

        Wheat.globalRot(globalWheatRot);
    }

    public void tick() {
        // ==================//
        // Population growth //
        // ==================//

        if (World.displacedFarmers != 0) {
            farmer.setOldFPop(farmer.getfPop());
        }

        if (World.displacedWarriors != 0) {
            warrior.setOldWPop(warrior.getwPop());
        }

        if (World.displacedFarmers == 0 && World.displacedWarriors == 0) {
            float newPopulation = farmer.fPop(fBirthRate, fDeathRate) + warrior.wPop(wBirthRate, wDeathRate);
            float newWarriors = newPopulation * desiredWarriorRatio;
            newPopulation -= newWarriors;
            farmer.addPop(newPopulation);
            warrior.addPop(newWarriors);
            warrior.setOldWPop(warrior.getwPop());
            farmer.setOldFPop(farmer.getfPop());
        }

        if (forceConscription && balanceCooldown == 0) {
            float totalpop = farmer.getFloatFPop() + warrior.getFloatWPop();
            float warriors = totalpop * desiredWarriorRatio;
            float farmers = totalpop - warriors;
            warrior.setwPop((int) warriors);
            warrior.setFloatWPop(warriors);
            farmer.setfHunger((int) farmers);
            farmer.setFloatFPop(farmers);
        } else if (balanceCooldown != 0) {
            balanceCooldown--;
        }

        // =================//
        // Wheat Production //
        // =================//
        wheat.tWheat(farmer.getfPop(), farmer);
        land.updateWheat(wheat);

        farmer.setTotalHunger(farmer.fHunger() * farmer.getfPop());
        warrior.setTotalHunger(warrior.wHunger() * warrior.getwPop());

        int warriorWheat = warrior.getTotalHunger();
        int farmerWheat = farmer.getTotalHunger();

        if (favorFarmers) {
            farmerWheat = wheat.eatWheat(farmerWheat, economy);
            warriorWheat = wheat.eatWheat(warriorWheat, economy);
        } else {
            warriorWheat = wheat.eatWheat(warriorWheat, economy);
            farmerWheat = wheat.eatWheat(farmerWheat, economy);
        }

        if (farmerWheat != 0) {
            int fDeath = Math.round(((float) farmerWheat / (float) farmer
                    .getfHunger()) * farmerDeathRatio);
            farmer.addPop(fDeath);
            fDeathRate = Math.min(1f, fDeathRate + 0.001f);
        } else {
            fDeathRate = Math.max(0f, fDeathRate - 0.001f);
        }

        if (warriorWheat != 0) {
            int wDeath = Math
                    .round(((float) warriorWheat / (float) warrior.getwHunger())
                            * warriorDeathRatio);
            warrior.addPop(-wDeath);
            wDeathRate = Math.min(1f, wDeathRate + 0.001f);
        } else {
            wDeathRate = Math.max(0f, wDeathRate - 0.001f);
        }

        if (displacedEat) {
            int displacedHungerConst = farmer.getfHunger() / 2;
            int displacedHunger = World.displacedPeople * displacedHungerConst;
            displacedHunger = wheat.eatWheat(displacedHunger, economy);
            if (displacedHunger != 0) {
                int displacedDeath = Math.round(((float) displacedHunger
                        / (float) farmer.getfHunger() / 2f) * 1f);
                World.displacedPeople -= displacedDeath;
            }
        } else {
            int displacedHungerConst = farmer.getfHunger() / 2;
            int displacedHunger = World.displacedPeople * displacedHungerConst;
            if (displacedHunger != 0) {
                int displacedDeath = Math.round(((float) displacedHunger
                        / (float) farmer.getfHunger() / 2f) * 1f);
                World.displacedPeople -= displacedDeath;
            }
        }

        // ==============//
        // Other Updates //
        // ==============//
        World.updateWood(this);
        wood.update();
        World.updateStone(this);

        wheat.update(economy);
        wheat.rot(wheatRot);

        // ======//
        // Score //
        // ======//
        int tick = year;
        score.calculateTickScore(tick, farmer.getfPop(), warrior.getwPop(), wheat.gettWheat(), economy.getTreasury());
        score.calculateAvgScore(tick);
        score.calculateTickGrowth(tick);
        score.calculateAvgGrowth(tick);
        score.calculatePeakScore(tick);
        score.calculateTotalScore(tick);
        aggression.calculateAggressionScore(warrior.getwPop(), false);


        updateTick();
    }

    public abstract void updateTick();

}
