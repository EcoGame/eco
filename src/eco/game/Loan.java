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
    
    public void payInterest(Country borrower, Country lender){
        int interestPayment;
        for(int i = 0; i < maxLoans; i++){
            if(coupons[i] != 0){
               interestPayment = (int)(principals[i] * coupons[i]);
                if(borrower.economy.getTreasury() - interestPayment <= 0){
                    bankruptcy(borrower);
                }
                
            }
        }
        
    }
    
    public void payInterest(PlayerCountry borrower, Country lender){
        
    }
    
    public void payInterest(Country borrower, PlayerCountry lender){
        
        
    }
    
    public double calculateInterest(){
        return 4.4;
        
        
    }
    
    public void bankruptcy(Country insolvent){
        
    }
    
    public void bankruptcy(PlayerCountry insolvent){
        
    }
    
    
}
