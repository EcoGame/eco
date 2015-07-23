package eco.game;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 * A class that represents a camera for use with the openGl projection matrix.
 * Has methods for movement in six directions, along with a method look() that
 * will apply the matrix transformations that make the camera function. Defaults
 * to a 30 degree pitch and 0 degree yaw
 *
 * @author phil
 */

public class Camera {

    private Vector3f position;
    public float yaw = 0.0f;
    public float pitch = 30.0f;

    public Matrix4f projection = new Matrix4f();
    public Matrix4f view = new Matrix4f();
    public Matrix4f model = new Matrix4f();

    public Camera(float x, float y, float z) {
        position = new Vector3f(x, y, z);

        float fieldOfView = Main.fov;
        float aspectRatio = (float)Main.width / (float)Main.height;
        float near_plane = 0.1f;
        float far_plane = 100000000000000f;

        float y_scale = (float) Math.cos(Math.toRadians(fieldOfView / 2f)) / (float) Math.sin(Math.toRadians(fieldOfView / 2f));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far_plane - near_plane;

        projection.m00 = x_scale;
        projection.m11 = y_scale;
        projection.m22 = -((far_plane + near_plane) / frustum_length);
        projection.m23 = -1;
        projection.m32 = -((2 * near_plane * far_plane) / frustum_length);
    }

    public void moveForward(float distance) {
        position.x += distance * (float) Math.sin(Math.toRadians(yaw));
        position.z -= distance * (float) Math.cos(Math.toRadians(yaw));
    }

    public void moveBack(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw));
    }

    public void moveLeft(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw - 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw - 90));
    }

    public void moveRight(float distance) {
        position.x -= distance * (float) Math.sin(Math.toRadians(yaw + 90));
        position.z += distance * (float) Math.cos(Math.toRadians(yaw + 90));
    }

    public void moveUp(float distance) {
        position.y -= distance;
    }

    public void moveDown(float distance) {
        position.y += distance;
    }

    public void look() {
        model = new Matrix4f();
        if (!Render.overhead) {
            GL11.glRotatef(pitch, 1, 0f, 0f);
            GL11.glRotatef(yaw, 0f, 1f, 0f);
            GL11.glTranslatef(-position.x, position.y, -position.z);
        } else {
            GL11.glRotatef(yaw, 0f, 0f, 1f);
            GL11.glRotatef(0, 1f, 0f, 0f);
            GL11.glTranslatef(-position.x, -1.7f, -position.z);
        }
        Matrix4f.scale(new Vector3f(1f, 1f, 1f), model, model);
        Matrix4f.translate(new Vector3f(-position.x, position.y, -position.z), model, model);
        Matrix4f.rotate((float) Math.toRadians(pitch), new Vector3f(1, 0, 0),
                model, model);
        Matrix4f.rotate((float) Math.toRadians(yaw + Render.rot), new Vector3f(0, 1, 0),
                model, model);

        Matrix4f.mul(model, projection, model);
    }

}
