package eco.game;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

/**
 * A <i>ToggleButton</i> behaves just like a normal <i>Button</i>, but will
 * toggle to it's second texture when pressed, not just when moused over
 * 
 * @author phil
 * 
 */

public class ToggleButton extends Button {

	private boolean toggle = false;

	public ToggleButton(float x, float y, float size, int tex, int tey,
			int texselected, int teyselected, boolean on) {
		super(x, y, size, tex, tey, texselected, teyselected);
		toggle = on;
	}

	public void click(float mousex, float mousey) {
		Rectangle rect = new Rectangle((int) getX(), (int) getY(),
				(int) getSize(), (int) getSize());
		if (rect.contains(mousex, mousey)) {
			setClickFlag(true);
			toggle ^= true;
		}
	}

	public void render(float mousex, float mousey) {
		TextureAtlas atlas = Render.atlas;
		float x = getX();
		float y = getY();
		float size = getSize();
		int texselected = getTexselected();
		int teyselected = getTeyselected();
		int tex = getTex();
		int tey = getTey();

		if (toggle) {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(texselected, false),
					atlas.getCoord(teyselected, false));
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(atlas.getCoord(texselected, true),
					atlas.getCoord(teyselected, false));
			GL11.glVertex2f(x + size, y);
			GL11.glTexCoord2f(atlas.getCoord(texselected, true),
					atlas.getCoord(teyselected, true));
			GL11.glVertex2f(x + size, y + size);
			GL11.glTexCoord2f(atlas.getCoord(texselected, false),
					atlas.getCoord(teyselected, true));
			GL11.glVertex2f(x, y + size);
			GL11.glEnd();
		} else {
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(tex, false),
					atlas.getCoord(tey, false));
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(atlas.getCoord(tex, true),
					atlas.getCoord(tey, false));
			GL11.glVertex2f(x + size, y);
			GL11.glTexCoord2f(atlas.getCoord(tex, true),
					atlas.getCoord(tey, true));
			GL11.glVertex2f(x + size, y + size);
			GL11.glTexCoord2f(atlas.getCoord(tex, false),
					atlas.getCoord(tey, true));
			GL11.glVertex2f(x, y + size);
			GL11.glEnd();
		}
		if (getOversize() == 24 || true){

			float offx = (size - getOversize()) / 2;
			float offy = (size - getOversize()) / 2;
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(getOvertex(), false),
					atlas.getCoord(getOvertey(), false));
			GL11.glVertex2f(x + offx, y + offy);
			GL11.glTexCoord2f(atlas.getCoord(getOvertex(), true),
					atlas.getCoord(getOvertey(), false));
			GL11.glVertex2f(x + offx + getOversize(), y + offy);
			GL11.glTexCoord2f(atlas.getCoord(getOvertex(), true),
					atlas.getCoord(getOvertey(), true));
			GL11.glVertex2f(x + getOversize() + offx, y + offy + getOversize());
			GL11.glTexCoord2f(atlas.getCoord(getOvertex(), false),
					atlas.getCoord(getOvertey(), true));
			GL11.glVertex2f(x + offx, y + offy + getOversize());
			GL11.glEnd();
		}
	}
	
	public void setToggle(boolean toggle){
		this.toggle = toggle;
	}
	public boolean getToggle(){
		return this.toggle;
	}

}
