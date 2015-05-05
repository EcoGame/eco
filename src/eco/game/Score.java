package eco.game;

import java.util.Arrays;

public class Score {
    
    public int[] scores = new int[PlayerCountry.ticks];
    public int[] growths = new int[PlayerCountry.ticks];
    public int tickGrowth = 0;
    public int tickScore = 0;
    
    public Score(){
        
    }
    
    public int calculateTickScore(int tick, int fPop, int wPop, int tWheat, int tMoney){
        int tickScore = fPop + wPop + tWheat + tMoney;
        //System.out.println("Tick: " + tick);
        
        scores[tick - 1] = tickScore;
        //System.out.println("scores[" + (tick - 1) + "] == " + scores[tick - 1]);
        
        return tickScore;
        
        
    }
    
    public int calculateTickGrowth(int tick){
        int i = 0;
        for (i = 0; i < scores.length - 1; i++)
        {
            if (scores[i] == 0)
            {
                break;
            }
        }
        if(tick > 1){
            int tickGrowth = scores[i - 1] - scores[i - 2];
            growths[tick - 1] = tickGrowth;
            return tickGrowth;
        }
        else{
            return 0;
        }
        
        
    }
    
    public int calculateAvgScore(int tick){
        //scores[tick - 1] = tickScore;
        
        int count = 0;
        int total = 0;
        int i = 42;
        for (i = 0; i < scores.length - 1; i++){
            //System.out.println(count);
            if (scores[i] > 0){
                total += scores[i];
                count++;
                //System.out.println("Count: " + count);

            }
            else{
                break;
            }
        }
        //System.out.println("scores[" + (i - 1) + "] == " + scores[(i - 1)]);
        int score = total / count;
        return score;
        
    }
    
    public int calculateAvgGrowth(int tick){
        //growths[tick - 1] = calculateTickGrowth(tick);
    
        int count = 0;
        int total = 0;
    
        for (int i = 0; i < growths.length - 1; i++)
        {
            if (growths[i] > 0)
            {
                total += growths[i];
                count++;
            }
        }
        if(count != 0){
            int avgGrowth = total / count;
            return avgGrowth;
        }
        else{
            int avgGrowth = 0;
            return avgGrowth;
        }
    }
    
    public int calculatePeakScore(int tick){
        int peak = 0;
        for (int i = 0; i < scores.length - 1; i++)
        {
            if (scores[i] > peak){
                peak = scores[i];
            }
        }
        return peak;
        
    }
    
    public int calculateTotalScore(int tick){
        int total = 0;
        for (int i = 1; i < scores.length - 1; i++){
            total = total + scores[i];
        }
        return total;
        
        
    }
    
    
}
