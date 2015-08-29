package eco.game;

public class NotImplementedException extends Exception {

    public NotImplementedException(String message){
        super(message);
    }

    public NotImplementedException(){
        super("Requested feature has not been implemented!");
    }
}
