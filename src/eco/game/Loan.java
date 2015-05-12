package eco.game;


public class Loan {
    public int maxLoans = 5;
    public float[] coupons = new float[maxLoans];
    public int[] principals = new int[maxLoans];
    public int[] maturityDates = new int[maxLoans];

    
    public Loan(){
        
        
    }
    
    public void takeLoan(){
        
        
        
        
    }
    
    public void payInterest(){
        for(int i = 0; i < maxLoans; i++){
            if(coupons[i] != 0){
                
            }
        }
        
    }
    
    public float calculateInterest(){
        return 4.4f;
        
        
    }
    
    
}
