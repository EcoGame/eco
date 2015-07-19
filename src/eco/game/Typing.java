package eco.game;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 * This class handles typing needs.
 *
 * @author connor
 */

public class Typing {

    public static String currentMenuName(String word, int cursorTick) {

        if (Display.isCloseRequested()) {
            Util.quit(0);
        }

        boolean cursorOn = false;
        if (cursorTick % 13 > 3) {
            cursorOn = true;
        }

        boolean didletter = false;
        if (RenderUtil.font.getWidth(word + "|") < 185) {
            while (Keyboard.next()) {
                if (Keyboard.getEventKeyState()) {
                    didletter = true;
                    switch (Keyboard.getEventKey()) {
                        case Keyboard.KEY_Q:
                            word = word + "q";
                            break;
                        case Keyboard.KEY_W:
                            word = word + "w";
                            break;
                        case Keyboard.KEY_E:
                            word = word + "e";
                            break;
                        case Keyboard.KEY_R:
                            word = word + "r";
                            break;
                        case Keyboard.KEY_T:
                            word = word + "t";
                            break;
                        case Keyboard.KEY_Y:
                            word = word + "y";
                            break;
                        case Keyboard.KEY_U:
                            word = word + "u";
                            break;
                        case Keyboard.KEY_I:
                            word = word + "i";
                            break;
                        case Keyboard.KEY_O:
                            word = word + "o";
                            break;
                        case Keyboard.KEY_P:
                            word = word + "p";
                            break;
                        case Keyboard.KEY_A:
                            word = word + "a";
                            break;
                        case Keyboard.KEY_S:
                            word = word + "s";
                            break;
                        case Keyboard.KEY_D:
                            word = word + "d";
                            break;
                        case Keyboard.KEY_F:
                            word = word + "f";
                            break;
                        case Keyboard.KEY_G:
                            word = word + "g";
                            break;
                        case Keyboard.KEY_H:
                            word = word + "h";
                            break;
                        case Keyboard.KEY_J:
                            word = word + "j";
                            break;
                        case Keyboard.KEY_K:
                            word = word + "k";
                            break;
                        case Keyboard.KEY_L:
                            word = word + "l";
                            break;
                        case Keyboard.KEY_Z:
                            word = word + "z";
                            break;
                        case Keyboard.KEY_X:
                            word = word + "x";
                            break;
                        case Keyboard.KEY_C:
                            word = word + "c";
                            break;
                        case Keyboard.KEY_V:
                            word = word + "v";
                            break;
                        case Keyboard.KEY_B:
                            word = word + "b";
                            break;
                        case Keyboard.KEY_N:
                            word = word + "n";
                            break;
                        case Keyboard.KEY_M:
                            word = word + "m";
                            break;
                        case Keyboard.KEY_SPACE:
                            word = word + " ";
                            break;
                        case Keyboard.KEY_DELETE:
                            if (word.length() > 1) {
                                if (word.contains("|")) {
                                    word = word.replace("|", "");
                                }
                                word = word.substring(0, word.length() - 1);
                                word = word + "|";
                            }
                            break;
                        case Keyboard.KEY_RETURN:
                            word = "@";
                            break;
                        case Keyboard.KEY_ESCAPE:
                            word = "^";
                            break;
                        case Keyboard.KEY_BACK:
                            if (word.length() > 1) {
                                if (word.contains("|")) {
                                    word = word.replace("|", "");
                                }
                                word = word.substring(0, word.length() - 1);
                                word = word + "|";
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            if (!didletter) {
                if (cursorOn) {
                    if (!word.contains("|")) {
                        word = word + "|";
                        return word;
                    }
                } else {
                    word = word.replace("|", "");
                }
                didletter = true;
            }
        } else {
            if (word.length() > 0) {
                word = word.substring(0, word.length() - 1);
                return word;
            }
            return word;
        }
        if (word.contains("|")) {
            if (!(word.substring(word.length(), word.length()) == "|")) {
                word = word.replace("|", "");
                word = word + "|";
                return word;
            }
        }
        return word;
    }
}
