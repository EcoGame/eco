package eco.game;

import org.lwjgl.opengl.GL11;
import java.awt.Rectangle;

/**
 * An object to represent a visual button. When the button is clicked,
 * <i>clickFlag</i> will be set to true. The click flag can be polled using
 * checkForClick(), and will be reset to false during this.
 * 
 * @author phil
 * 
 */

public class Button {

    private float x;
    private float y;

    private float size;

    private boolean clickFlag = false;

    private int tex;
    private int tey;

    private int texselected;

    private int teyselected;
    
    private int overtex;
    private int overtey;
    
    private int oversize;

    public Button(float x, float y, float size, int tex, int tey,
            int texselected, int teyselected) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.tex = tex;
        this.tey = tey;
        this.texselected = texselected;
        this.teyselected = teyselected;
    }

    public void click(float mousex, float mousey) {
        Rectangle rect = new Rectangle((int) x, (int) y, (int) size, (int) size);
        if (rect.contains(mousex, mousey)) {
            clickFlag = true;
        }
    }

    public boolean checkForClick() {
        if (clickFlag) {
            clickFlag = false;
            return true;
        }
        return false;
    }

    public void render(float mousex, float mousey) {
        TextureAtlas atlas = Render.atlas;
        Rectangle rect = new Rectangle((int) x, (int) y, (int) size, (int) size);
        if (rect.contains(mousex, mousey)) {
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
        if (oversize == 24 || true){

            float offx = (size - oversize) / 2;
            float offy = (size - oversize) / 2;
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(atlas.getCoord(overtex, false),
                    atlas.getCoord(overtey, false));
            GL11.glVertex2f(x + offx, y + offy);
            GL11.glTexCoord2f(atlas.getCoord(overtex, true),
                    atlas.getCoord(overtey, false));
            GL11.glVertex2f(x + offx + oversize, y + offy);
            GL11.glTexCoord2f(atlas.getCoord(overtex, true),
                    atlas.getCoord(overtey, true));
            GL11.glVertex2f(x + oversize + offx, y + offy + oversize);
            GL11.glTexCoord2f(atlas.getCoord(overtex, false),
                    atlas.getCoord(overtey, true));
            GL11.glVertex2f(x + offx, y + offy + oversize);
            GL11.glEnd();
        }

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public boolean isClickFlag() {
        return clickFlag;
    }

    public void setClickFlag(boolean clickFlag) {
        this.clickFlag = clickFlag;
    }

    public int getTex() {
        return tex;
    }

    public void setTex(int tex) {
        this.tex = tex;
    }

    public int getTey() {
        return tey;
    }

    public void setTey(int tey) {
        this.tey = tey;
    }

    public int getTexselected() {
        return texselected;
    }

    public void setTexselected(int texselected) {
        this.texselected = texselected;
    }

    public int getTeyselected() {
        return teyselected;
    }

    public void setTeyselected(int teyselected) {
        this.teyselected = teyselected;
    }

    public void render2() {

    }
    
    public void addOverlay(int tex, int tey, int size){
        overtex = tex;
        overtey = tey;
        this.oversize = size;
    }

    public int getOvertex() {
        return overtex;
    }

    public void setOvertex(int overtex) {
        this.overtex = overtex;
    }

    public int getOvertey() {
        return overtey;
    }

    public void setOvertey(int overtey) {
        this.overtey = overtey;
    }

    public int getOversize() {
        return oversize;
    }

    public void setOversize(int oversize) {
        this.oversize = oversize;
    }
    
    

}
