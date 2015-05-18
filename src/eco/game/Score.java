package eco.game;


public class Score {
    
    public int[] scores = new int[PlayerCountry.ticks];
    public int[] growths = new int[PlayerCountry.ticks];
    public int tickGrowth = 0;
    public int tickScore = 0;
    public int avgScore = 0;
    public int avgGrowth = 0;
    public int peakScore = 0;
    public int totalScore = 0;
    
    public Score(){
        
    }
    
    public int calculateTickScore(int tick, int fPop, int wPop, int tWheat, int tMoney){
        double unroundedScore = fPop + (wPop * 1.25) + (tWheat / 14)  + (tMoney / 10000);
        System.out.println("Unrounded: " + unroundedScore);
        int tickScore = (int) Math.round(unroundedScore);
        //14 is the amount of wheat each farmer can make
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
            try{
	        	int tickGrowth = scores[i - 1] - scores[i - 2];
	            growths[tick - 1] = tickGrowth;
            }
            catch(Exception e){
            	
            }
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
        try{
        int score = total / count;
        avgScore = score;
        	return score;
        }
        catch(Exception e){
        	return 0;
        }
        
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
            avgGrowth = total / count;
            return avgGrowth;
        }
        else{
            avgGrowth = 0;
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
        peakScore = peak;
        return peak;
        
    }
    
    public int calculateTotalScore(int tick){
        int total = 0;
        for (int i = 1; i < scores.length - 1; i++){
            total = total + scores[i];
        }
        totalScore = total;
        return total;
        
        
    }
    
    public int scoreAt(int tick){
        return scores[tick];
    }
    
    
}
