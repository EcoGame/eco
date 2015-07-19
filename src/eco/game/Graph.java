package eco.game;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Graph {

    /**
     * An object that represents a graph
     * Contains methods for drawing and updating
     * Based on nates <i>Graphs</i> code
     *
     * @author phil
     */

    private static int size = 150;

    private static int height = 90;

    private float[] contents = new float[size];

    private float x;
    private float y;

    private Treble<Float, Float, Float> color;

    private Treble<Float, Float, Float> axisColor = new Treble<>(0.4f, 0.4f, 0.4f);

    private String title;

    private boolean justGraph = false;

    public Graph(float x, float y, String title, Treble<Float, Float, Float> treble) {
        this.x = x;
        this.y = y;
        this.title = title;
        this.color = treble;
    }

    public void tick(float value) {
        contents[size - 1] = value;
        for (int i = 0; i < size - 1; i++) {
            contents[i] = contents[i + 1];
        }
    }

    public void render() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(3f);

        GL11.glColor3f(color.x, color.y, color.z);

        float max = 0;

        for (int i = 0; i < size; i++) {
            max = Math.max(max, contents[i]);
        }

        /* Graph */
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < size; i++) {
            GL11.glVertex2f(x + i, y - (contents[i] * height / (float) max));
        }
        GL11.glEnd();

        /* Axis */
        GL11.glColor3f(axisColor.x, axisColor.y, axisColor.z);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x, y - height);
        GL11.glVertex2f(x, y);
        GL11.glVertex2f(x + size, y);
        GL11.glEnd();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(1f, 1f, 1f);
    }

    public void render2() {
        if (justGraph) {
            return;
        }

        RenderUtil.font.drawString(x + ((size - RenderUtil.font.getWidth(title)) / 2), y - height - 15, title, new Color(color.x, color.y, color.z));

        float max = 0;

        for (int i = 0; i < size; i++) {
            max = Math.max(max, contents[i]);
        }

        RenderUtil.font.drawString(x - 5 - RenderUtil.font.getWidth("0"), y - 10, "0", new Color(color.x, color.y, color.z));
        RenderUtil.font.drawString(x - 5 - RenderUtil.font.getWidth(String.valueOf((int) max / 2)), y - 5 - (height / 2), String.valueOf((int) max / 2), new Color(color.x, color.y, color.z));
        RenderUtil.font.drawString(x - 5 - RenderUtil.font.getWidth(String.valueOf((int) max)), y - 5 - (height), String.valueOf((int) max), new Color(color.x, color.y, color.z));

    }

    public void hideStats() {
        justGraph = true;
    }

    public void reset() {
        contents = new float[size];
    }

}
