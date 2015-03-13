package eco;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

public class ToggleTextButton extends Button{

  public float width;
  public float height;

  public String text;

  public boolean toggle;

  public ToggleTextButton(float x, float y, float width, float height, int tex, int tey, int texselected, int teyselected, String text, boolean on){
    super(x, y, width, tex, tey, texselected, teyselected);
    this.width = width;
    this.height = height;
    this.text = text;
    toggle = on;
  }

  public void click(float mousex, float mousey){
    Rectangle rect = new Rectangle((int) x, (int)y, (int)width, (int)height);
    if (rect.contains(mousex, mousey)){
      clickFlag = true;
      toggle ^= true;
    }
  }

  public void render(float mousex, float mousey){
    TextureAtlas atlas = Render.atlas;
    Rectangle rect = new Rectangle((int) x, (int)y, (int)width, (int)height);
    if (rect.contains(mousex, mousey) || toggle){
      GL11.glBegin(GL11.GL_QUADS);
      GL11.glTexCoord2f(atlas.getCoord(texselected, false), atlas.getCoord(teyselected, false));
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(atlas.getCoord(texselected, true), atlas.getCoord(teyselected, false));
      GL11.glVertex2f(x + width, y);
      GL11.glTexCoord2f(atlas.getCoord(texselected, true), atlas.getCoord(teyselected, true));
      GL11.glVertex2f(x + width, y + height);
      GL11.glTexCoord2f(atlas.getCoord(texselected, false), atlas.getCoord(teyselected, true));
      GL11.glVertex2f(x, y + height);
      GL11.glEnd();
    }
    else{
      GL11.glBegin(GL11.GL_QUADS);
      GL11.glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, false));
      GL11.glVertex2f(x, y);
      GL11.glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, false));
      GL11.glVertex2f(x + width, y);
      GL11.glTexCoord2f(atlas.getCoord(tex, true), atlas.getCoord(tey, true));
      GL11.glVertex2f(x + width, y + height);
      GL11.glTexCoord2f(atlas.getCoord(tex, false), atlas.getCoord(tey, true));
      GL11.glVertex2f(x, y + height);
      GL11.glEnd();
    }


  }

  public void render2(){
    int centX = (int) x;
    centX += (width - Render.font.getWidth(text)) / 2;
    Render.drawString(text, centX, (int) y + ((height - Render.font.getHeight(text)) / 2));
  }

}
