package eco.game;

/**
 * Abstract class that represents a resource used by a country
 *
 * @author phil
 */
public abstract class Resource {

    private int amount;
    private float rotRate;

    public Resource(){
        amount = 0;
        rotRate = 1.0f;
    }

    final public void tick(){
        amount *= rotRate;
        if (amount < 0){
            amount = 0;
        }

        updateTick();
    }

    protected abstract void updateTick();

    public int take(int request){
        if (request > amount){
            request = amount;
            amount = 0;
            return request;
        }
        amount -= request;
        return request;
    }

    public void add(int amount){
        this.amount += amount;
    }

    public void buy(int amount, Country c) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public int sell(int amount, Country c) throws NotImplementedException {
        throw new NotImplementedException();
    }

    public void setRot(float rate){
        rotRate = rate;
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount = amount;
    }
}
