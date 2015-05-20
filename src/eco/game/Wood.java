package eco.game;

public class Wood {
	
	private int wood;
	private float rotRate;
	
	public boolean buyWood = true;
	
	private int woodPrice = 100;
	
	public Wood(){
		wood = 0;
		rotRate = 0.99f;
	}
	
	public int takeWood(int request, Economy economy){
		if (request > wood){
			if (!buyWood){
				request = wood;
				wood = 0;
				return request;
			} else{
				int missing = request - wood;
				int canBuy = (int) Math.ceil(economy.takeMoney(missing * woodPrice) / (float) woodPrice);
				return request - (missing - canBuy);
			}
		}
		wood -= request;
		return request;
	}
	
	public void addWood(int toAdd){
		wood += toAdd;
	}
	
	public void update(){
		wood *= rotRate;
	}

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public float getRotRate() {
		return rotRate;
	}

	public void setRotRate(float rotRate) {
		this.rotRate = rotRate;
	}
	
	

}
