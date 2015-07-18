package eco.game;

public class War {

    /**
     *
     * This class does war between the player and other countries
     * It figures out who wins, and what reparations are paid
     *
     * @author phil
     *
     */

    public static int winLose = 0;
    public static int wheatLoss = 0;
    public static int landLoss = 0;
    public static int moneyLoss = 0;

    private static final int minLand = 0;

    private static int aggressionConstant = 4;

    /*public static void attackPlayer(NPCCountry c){
        // Book-keeping stuff
        int diff = c.aggression.aggressionScore - PlayerCountry.aggression.aggressionScore;
        diff = (int) Math.ceil(diff / (float) aggressionConstant);
        c.aggression.aggressionScore += diff;
        warWith(c);
        String warName = "The " + PlayerCountry.name + "-" + c.name
                + " war";
        MenuBar.updateWar(warName);
        MenuBar.setPane(1);
    }

    public static void warWith(NPCCountry c) {
        // Book-keeping stuff
        EventLog.log(PlayerCountry.year, PlayerCountry.name + " declares war on "
                + c.name + "!");
        PlayerCountry.balanceCooldown = 5;

        int diff = PlayerCountry.aggression.aggressionScore - c.aggression.aggressionScore;
        diff = (int) Math.ceil(diff / (float) aggressionConstant);
        PlayerCountry.aggression.aggressionScore += diff;

        // Get army sizes
        int playerArmy = PlayerCountry.warrior.getwPop();
        int otherArmy = c.warrior.getwPop();

        // Apply some rng
        playerArmy *= (Math.max(World.random.nextFloat(), 0.5f));
        otherArmy *= (Math.max(World.random.nextFloat(), 0.5f));

        // Calculate outcome
        // result > 0: player win
        // result < 0: country win
        // result = 0: stalemate
        int result = playerArmy - otherArmy;

        // Calculate and apply losses
        if (result > 0) {
            playerArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
            otherArmy *= World.random.nextFloat();
        } else if (result < 0) {
            otherArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
            playerArmy *= World.random.nextFloat();
        } else {
            otherArmy *= World.random.nextFloat();
            playerArmy *= World.random.nextFloat();
        }
        PlayerCountry.warrior.setwPop(playerArmy);
        PlayerCountry.warrior.setFloatWPop(playerArmy);
        c.warrior.setwPop(otherArmy);
        c.warrior.setFloatWPop(otherArmy);

        // Reparations
        if (result == 0) {
            EventLog.log(PlayerCountry.year, "The war with " + c.name
                    + " ends in stalemate!");
            winLose = 0;
            return;
        } else if (result > 0) {
            winLose = 1;
            int wheat = Math.min(c.wheat.gettWheat(), result * 4);
            int money = Math.min(c.economy.getTreasury(), result * 16);
            int land = Math.min(c.landsize + c.land.getLand(), result * 2);
            PlayerCountry.wheat.settWheat(PlayerCountry.wheat.gettWheat()
                    + wheat);
            PlayerCountry.economy.setTreasury(PlayerCountry.economy
                    .getTreasury() + money);
            PlayerCountry.land.addLand(land);
            c.wheat.settWheat(c.wheat.gettWheat() - wheat);
            c.economy.setTreasury(c.economy.getTreasury() - money);
            c.takeLand(land);
            EventLog.log(PlayerCountry.year,
                    PlayerCountry.name + " " + NameGen.generateWarWin() + " "
                            + c.name + "!");
            wheatLoss = wheat;
            landLoss = land;
            moneyLoss = money;
        } else {
            winLose = -1;
            int wheat = Math.min(PlayerCountry.wheat.gettWheat(), -result * 4);
            int money = Math.min(PlayerCountry.economy.getTreasury(),
                    -result * 16);
            int land = -result * 2;
            PlayerCountry.wheat.settWheat(PlayerCountry.wheat.gettWheat()
                    - wheat);
            PlayerCountry.economy.setTreasury(PlayerCountry.economy
                    .getTreasury() - money);
            PlayerCountry.land.addLand(-land);
            c.wheat.settWheat(c.wheat.gettWheat() + wheat);
            c.economy.setTreasury(c.economy.getTreasury() + money);
            EventLog.log(PlayerCountry.year,
                    PlayerCountry.name + " " + NameGen.generateWarLoss() + " "
                            + c.name + "!");
            if (PlayerCountry.land.getLand() < minLand) {
                Main.gameOver = true;
                Main.reason = "You've been annexed by " + c.name + "!";
            }
            wheatLoss = -wheat;
            landLoss = -land;
            moneyLoss = -money;
        }
    }

    public static void warWith(NPCCountry a, NPCCountry b){
        if (a.dead || b.dead){
            return;
        }
        // Book-keeping stuff
        EventLog.log(PlayerCountry.year, a.name + " declares war on "
                + b.name + "!");

        int diff = a.aggression.aggressionScore - b.aggression.aggressionScore;
        diff = (int) Math.ceil(diff / (float) aggressionConstant);
        a.aggression.aggressionScore += diff;

        // Get army sizes
        int playerArmy = a.warrior.getwPop();
        int otherArmy = b.warrior.getwPop();

        // Apply some rng
        playerArmy *= (Math.max(World.random.nextFloat(), 0.5f));
        otherArmy *= (Math.max(World.random.nextFloat(), 0.5f));

        // Calculate outcome
        // result > 0: player win
        // result < 0: country win
        // result = 0: stalemate
        int result = playerArmy - otherArmy;

        // Calculate and apply losses
        if (result > 0) {
            playerArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
            otherArmy *= World.random.nextFloat();
        } else if (result < 0) {
            otherArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
            playerArmy *= World.random.nextFloat();
        } else {
            otherArmy *= World.random.nextFloat();
            playerArmy *= World.random.nextFloat();
        }
        a.warrior.setwPop(playerArmy);
        a.warrior.setFloatWPop(playerArmy);
        b.warrior.setwPop(otherArmy);
        b.warrior.setFloatWPop(otherArmy);

        // Reparations
        if (result == 0) {
            EventLog.log(PlayerCountry.year, "The war with " + b.name
                    + " ends in stalemate!");
            winLose = 0;
            return;
        } else if (result > 0) {
            winLose = 1;
            int wheat = Math.min(b.wheat.gettWheat(), result * 4);
            int money = Math.min(b.economy.getTreasury(), result * 16);
            int land = Math.min(b.landsize + b.land.getLand(), result * 2);
            a.wheat.settWheat(a.wheat.gettWheat()
                    + wheat);
            a.economy.setTreasury(a.economy
                    .getTreasury() + money);
            a.land.addLand(land);
            b.wheat.settWheat(b.wheat.gettWheat() - wheat);
            b.economy.setTreasury(b.economy.getTreasury() - money);
            b.takeLand(land);
            EventLog.log(PlayerCountry.year,
                    a.name + " " + NameGen.generateWarWin() + " "
                            + b.name + "!");
            wheatLoss = wheat;
            landLoss = land;
            moneyLoss = money;
        } else {
            winLose = -1;
            int wheat = Math.min(a.wheat.gettWheat(), -result * 4);
            int money = Math.min(a.economy.getTreasury(),
                    -result * 16);
            int land = Math.min(a.landsize + a.land.getLand(), -result * 2);
            a.wheat.settWheat(a.wheat.gettWheat()
                    - wheat);
            a.economy.setTreasury(a.economy
                    .getTreasury() - money);
            a.land.addLand(-land);
            b.takeLand(-land);
            b.wheat.settWheat(b.wheat.gettWheat() + wheat);
            b.economy.setTreasury(b.economy.getTreasury() + money);
            EventLog.log(PlayerCountry.year,
                    a.name + " " + NameGen.generateWarLoss() + " "
                            + b.name + "!");
            if (a.land.getLand() < minLand) {
                a.dead = true;
            }
            wheatLoss = -wheat;
            landLoss = -land;
            moneyLoss = -money;
        }
        if (b.land.getLand() < minLand) {
            b.dead = true;
        }
    }*/

}
