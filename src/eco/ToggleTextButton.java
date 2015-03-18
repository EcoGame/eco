package eco;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

/**
 * A <i>ToggleTextButton</i> behaves just like a normal <i>TextButton</i>, but
 * will toggle to it's second texture when pressed, not just when moused over
 * 
 * @author phil
 * 
 */

public class ToggleTextButton extends Button {

	private float width;
	private float height;

	private String text;

	private boolean toggle;

	public ToggleTextButton(float x, float y, float width, float height,
			int tex, int tey, int texselected, int teyselected, String text,
			boolean on) {
		super(x, y, width, tex, tey, texselected, teyselected);
		this.width = width;
		this.height = height;
		this.text = text;
		toggle = on;
	}

	public void click(float mousex, float mousey) {
		Rectangle rect = new Rectangle((int) getX(), (int) getY(), (int) width,
				(int) height);
		if (rect.contains(mousex, mousey)) {
			setClickFlag(true);
			toggle ^= true;
		}
	}

	public void render(float mousex, float mousey) {
		TextureAtlas atlas = Render.atlas;
		Rectangle rect = new Rectangle((int) getX(), (int) getY(), (int) width,
				(int) height);
		if (rect.contains(mousex, mousey) || toggle) {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(getTexselected(), false),
					atlas.getCoord(getTeyselected(), false));
			GL11.glVertex2f(getX(), getY());
			GL11.glTexCoord2f(atlas.getCoord(getTexselected(), true),
					atlas.getCoord(getTeyselected(), false));
			GL11.glVertex2f(getX() + width, getY());
			GL11.glTexCoord2f(atlas.getCoord(getTexselected(), true),
					atlas.getCoord(getTeyselected(), true));
			GL11.glVertex2f(getX() + width, getY() + height);
			GL11.glTexCoord2f(atlas.getCoord(getTexselected(), false),
					atlas.getCoord(getTeyselected(), true));
			GL11.glVertex2f(getX(), getY() + height);
			GL11.glEnd();
		} else {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(getTex(), false),
					atlas.getCoord(getTey(), false));
			GL11.glVertex2f(getX(), getY());
			GL11.glTexCoord2f(atlas.getCoord(getTex(), true),
					atlas.getCoord(getTey(), false));
			GL11.glVertex2f(getX() + width, getY());
			GL11.glTexCoord2f(atlas.getCoord(getTex(), true),
					atlas.getCoord(getTey(), true));
			GL11.glVertex2f(getX() + width, getY() + height);
			GL11.glTexCoord2f(atlas.getCoord(getTex(), false),
					atlas.getCoord(getTey(), true));
			GL11.glVertex2f(getX(), getY() + height);
			GL11.glEnd();
		}

	}

	public void render2() {
		int centX = (int) getX();
		centX += (width - Render.font.getWidth(text)) / 2;
		Render.drawString(text, centX,
				(int) getY() + ((height - Render.font.getHeight(text)) / 2));
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

}