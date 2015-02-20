package eco;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public class Button{

	public float x;
	public float y;

	public float size;

	private boolean clickFlag = false;

	public int tex;
	public int tey;
	
	public Button(float x, float y, float size, int tex, int tey){
		this.x = x;
		this.y = y;
		this.size = size;
		this.tex = tex;
		this.tey = tey;
	}

	public void click(float mousex, float mousey){
		Rectangle rect = new Rectangle((int) x, (int)y, (int)size, (int)size);
		if (rect.contains(mousex, mousey)){	
			clickFlag = true;		
		}
	}

	public boolean checkForClick(){
		if (clickFlag){
			clickFlag = false;
			return true;		
		}
		return false;
	}

	public void render(){
		TextureAtlas atlas = Render.atlas;		
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
