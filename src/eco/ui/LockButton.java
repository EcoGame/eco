package eco.ui;

import java.awt.*;

public class LockButton extends ToggleButton {

    public boolean lock = true;

    public LockButton(float x, float y, float size, int tex, int tey,
                      int texselected, int teyselected, IClickEvent action, boolean on) {
        super(x, y, size, tex, tey, texselected, teyselected, action, on);
    }

    public void click(float mousex, float mousey) {
        Rectangle rect = new Rectangle((int) getX(), (int) getY(),
                (int) getSize(), (int) getSize());
        if (rect.contains(mousex, mousey)) {
            super.setToggle(true);
            if (lock) {
                lock = false;
            } else {
                setClickFlag(true);
                action.onClick(this);
            }
        }
    }

    public void render(float mousex, float mousey) {
        Rectangle rect = new Rectangle((int) getX(), (int) getY(),
                (int) getSize(), (int) getSize());
        if (!rect.contains(mousex, mousey)) {
            setToggle(false);
            lock = true;
        }
        super.render(mousex, mousey);
    }

}
