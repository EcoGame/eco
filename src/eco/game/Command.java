package eco.game;

/**
 * This class does stuff with console input
 *
 * @author phil
 */
public class Command {

    public static void onCommand(String command){
        command = command.toLowerCase();
        switch(command){
            case "foo":
                Log.println("bar!");
                break;
            case "exit":
                Util.quit(0);
                break;
            default:
                Log.println("Unknown command '"+command+"'");
                break;
        }
    }

}
