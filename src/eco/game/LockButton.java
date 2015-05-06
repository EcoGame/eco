package eco.game;

import java.awt.Rectangle;

public class LockButton extends ToggleButton {
	
	public boolean lock = true;

	public LockButton(float x, float y, float size, int tex, int tey,
			int texselected, int teyselected, boolean on) {
		super(x, y, size, tex, tey, texselected, teyselected, on);
	}
	
	public void click(float mousex, float mousey) {
		Rectangle rect = new Rectangle((int) getX(), (int) getY(),
				(int) getSize(), (int) getSize());
		if (rect.contains(mousex, mousey)) {
			super.setToggle(true);
			if (lock){
				lock = false;
			}
			else{
				setClickFlag(true);
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
