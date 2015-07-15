package eco.game;

/**
 *
 * This class represents a countries wood stockpile,
 * and handles adding, removing, buying, and rotting the stone
 *
 * @author phil
 *
 */

public class Stone {
    
    private int stone;
    
    public boolean buyStone = true;
    
    private int stonePrice = 150;
    
    public Stone(){
        stone = 1000;
    }
    
    public int takeStone(int request, Economy economy){
        if (request > stone){
            if (!buyStone){
                request = stone;
                stone = 0;
                return request;
            } else{
                int missing = request - stone;
                int canBuy = (int) Math.ceil(economy.takeMoney(missing * stonePrice) / (float) stonePrice);
                return request - (missing - canBuy);
            }
        }
        stone -= request;
        return request;
    }
    
    public void addStone(int toAdd){
        stone += toAdd;
    }
    
    public int getStone() {
        return stone;
    }
    
    public void setStone(int stone) {
        this.stone = stone;
    }
    
    
    
}
