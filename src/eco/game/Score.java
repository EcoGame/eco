package eco.game;

public class Score {
    public static void calculateFitness(){
        int score = Main.farmer.fPop() + Main.warrior.wPop() +
        Main.wheat.gettWheat() +
        Main.economy.getTreasury() +
        System.out.println(score);
        
    }
    
    
    
    
}
