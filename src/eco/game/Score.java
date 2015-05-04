package eco.game;


public class Score {
    
    public static int[] avgScore = new int[PlayerCountry.ticks];
    public static void calculateFitness(int tick){
        int tickScore = PlayerCountry.farmer.getfPop() + PlayerCountry.warrior.getwPop() +
        PlayerCountry.wheat.gettWheat() +
        PlayerCountry.economy.getTreasury();
        System.out.println(tickScore);
        //System.out.println(Arrays.toString(avgScore));
        avgScore[tick] = tickScore;
        
        int count = 0;
        int total = 0;
        
        for (int i = 0; i < avgScore.length; i++)
        {
            if (avgScore[i] > 0)
            {
                total += avgScore[i];
                count++;
            } 
        }
        
        int score = total / count;
        //System.out.println("Average: " + score);
        
        
    }
    
    
    
    
}
