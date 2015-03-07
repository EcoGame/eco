package eco;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public class ToggleButton extends Button{

  public boolean toggle = false;

	public ToggleButton(float x, float y, float size, int tex, int tey, int texselected, int teyselected, boolean on){
		super(x, y, size, tex, tey, texselected, teyselected);
    toggle = on;
	}

	public void click(float mousex, float mousey){
		Rectangle rect = new Rectangle((int) x, (int)y, (int)size, (int)size);
		if (rect.contains(mousex, mousey)){
			clickFlag = true;
      toggle ^= true;
		}
	}

	public void render(float mousex, float mousey){
		TextureAtlas atlas = Render.atlas;
		if (toggle){
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(texselected, false), atlas.getCoord(teyselected, false));
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(atlas.getCoord(texselected, true), atlas.getCoord(teyselected, false));
			GL11.glVertex2f(x + size, y);
			GL11.glTexCoord2f(atlas.getCoord(texselected, true), atlas.getCoord(teyselected, true));
			GL11.glVertex2f(x + size, y + size);
			GL11.glTexCoord2f(atlas.getCoord(texselected, false), atlas.getCoord(teyselected, true));
			GL11.glVertex2f(x, y + size);
			GL11.glEnd();
		}
		else{
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
			GL11.glVertex2f(x + size, y);
			GL11.glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
			GL11.glVertex2f(x + size, y + size);
			GL11.glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
			GL11.glVertex2f(x, y + size);
			GL11.glEnd();
	}

	}

}
