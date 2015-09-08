package eco.ui;

import org.lwjgl.input.Keyboard;

/**
 * This class manages typing into a text field
 *
 * @author phil
 */
public class Typer {

    private static final int blinkInterval = 32;
    private static int blinkCount = 0;
    private static boolean blink = false;
    public static final String blinkCharacter = "|";

    public static String type(String buffer, IInputManager inputManager){
        if (buffer.endsWith(blinkCharacter)){
            buffer = buffer.replace(blinkCharacter, "");
        }
        while(Keyboard.next()){
            if (Keyboard.getEventKeyState()){
                switch(Keyboard.getEventKey()){
                    case Keyboard.KEY_DELETE:
                    case Keyboard.KEY_BACK:
                        if (buffer.length() != 0){
                            buffer = buffer.substring(0, buffer.length() - 1);
                        }
                        break;
                    case Keyboard.KEY_SPACE:
                        buffer = buffer + " ";
                        break;
                    default:
                        String str = Keyboard.getKeyName(Keyboard.getEventKey());
                        if (str.length() == 1){
                            str = str.toLowerCase();
                            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)){
                                str = str.toUpperCase();
                            }
                            buffer = buffer + str;
                        }
                        break;
                }
                inputManager.run(Keyboard.getEventKey());
            }
        }

        updateBlink();
        if (blink){
            buffer = buffer + blinkCharacter;
        }
        return buffer;
    }

    private static void updateBlink(){
        blinkCount++;
        if (blinkCount > blinkInterval){
            blinkCount = 0;
            blink ^= true;
        }
    }

}
