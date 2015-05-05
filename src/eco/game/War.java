package eco.game;

public class War {
	
	public static void warWith(Country c){
		
		Log.log(PlayerCountry.year, PlayerCountry.name + " declares war on "+c.name+"!");
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
		if (result > 0){
			playerArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
			otherArmy *= World.random.nextFloat();
		} else if (result < 0){
			otherArmy *= Math.min(World.random.nextFloat() * 4, 0.5f);
			playerArmy *= World.random.nextFloat();
		} else{
			otherArmy *= World.random.nextFloat();
			playerArmy *= World.random.nextFloat();
		}
		PlayerCountry.warrior.setwPop(playerArmy);
		PlayerCountry.warrior.setFloatWPop(playerArmy);
		c.warrior.setwPop(otherArmy);
		c.warrior.setFloatWPop(otherArmy);
		
		// Reparations
		if (result == 0){
			Log.log(PlayerCountry.year, "The war with "+c.name+" ends in stalemate!");
			return;
		} else if (result > 0){
			int wheat = Math.min(c.wheat.gettWheat(), result * 4);
			int money = Math.min(c.economy.getTreasury(), result * 16);
			int land = Math.min(c.landsize, result * 2);
			PlayerCountry.wheat.settWheat(PlayerCountry.wheat.gettWheat() + wheat);
			PlayerCountry.economy.setTreasury(PlayerCountry.economy.getTreasury() + money);
			PlayerCountry.territories += land;
			c.wheat.settWheat(c.wheat.gettWheat() - wheat);
			c.economy.setTreasury(c.economy.getTreasury() - money);
			c.landsize -= land;
			Log.log(PlayerCountry.year, PlayerCountry.name + " wins the war with "+c.name+"!");
		} else {
			int wheat = Math.min(PlayerCountry.wheat.gettWheat(), -result * 4);
			int money = Math.min(PlayerCountry.economy.getTreasury(), -result * 16);
			int land = -result * 2;
			PlayerCountry.wheat.settWheat(PlayerCountry.wheat.gettWheat() - wheat);
			PlayerCountry.economy.setTreasury(PlayerCountry.economy.getTreasury() - money);
			c.wheat.settWheat(c.wheat.gettWheat() + wheat);
			c.economy.setTreasury(c.economy.getTreasury() + money);
			Log.log(PlayerCountry.year, PlayerCountry.name + " loses the war with "+c.name+"!");
			System.out.println(land);
			if (land > 10){
				Main.gameOver = true;
				Main.reason = "You've been annexed by "+c.name+"!";
			}
		}
	}

}
