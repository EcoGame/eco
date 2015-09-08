package eco.ui;

import org.newdawn.slick.Color;

/**
 * Final class to store colors for the console
 *
 * @author phil
 */
public final class Colors {

    public static final ExtColor black = new ExtColor(0xff181818);
    public static final ExtColor red = new ExtColor(0xffab4642);
    public static final ExtColor green = new ExtColor(0xffa1b56c);
    public static final ExtColor yellow = new ExtColor(0xfff7c188);
    public static final ExtColor blue = new ExtColor(0xff7cafc2);
    public static final ExtColor purple = new ExtColor(0xffba8baf);
    public static final ExtColor cyan = new ExtColor(0xff86c1b9);
    public static final ExtColor white = new ExtColor(0xfff8f8f8);

}

/**
 * A small class to override toString()
 *
 * @author phil
 */
class ExtColor extends Color{

    private String value;

    public ExtColor(int value) {
        super(value);
        this.value = Integer.toHexString(value);
    }

    @Override
    public String toString(){
        return "0x"+value;
    }
}
