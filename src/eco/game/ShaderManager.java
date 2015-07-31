package eco.game;

import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ShaderManager {

    public static boolean useShader;

    public static int billboard = 0;
    public static int rotLocation;

    public static void init(){
        int vertShader = 0, fragShader = 0;

        try {
            vertShader = createShader("../shaders/billboard.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragShader = createShader("../shaders/billboard.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
        }
        catch(Exception exc) {
            exc.printStackTrace();
            return;
        }
        finally {
            if(vertShader == 0 || fragShader == 0)
                return;
        }

        billboard = ARBShaderObjects.glCreateProgramObjectARB();

        if(billboard == 0)
            return;

        ARBShaderObjects.glAttachObjectARB(billboard, vertShader);
        ARBShaderObjects.glAttachObjectARB(billboard, fragShader);

        ARBShaderObjects.glLinkProgramARB(billboard);
        if (ARBShaderObjects.glGetObjectParameteriARB(billboard, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
            Log.info(getLogInfo(billboard));
            return;
        }

        ARBShaderObjects.glValidateProgramARB(billboard);
        if (ARBShaderObjects.glGetObjectParameteriARB(billboard, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
            Log.info(getLogInfo(billboard));
            return;
        }

        useShader = true;

        Log.success("Loaded 1 shaders");

        int loc = GL20.glGetUniformLocation(billboard, "texture1");
        GL20.glUniform1i(loc, 0);
        rotLocation = GL20.glGetUniformLocation(billboard, "rotation");
    }

    private static int createShader(String filename, int shaderType) throws Exception {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

            if(shader == 0)
                return 0;

            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
            ARBShaderObjects.glCompileShaderARB(shader);

            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)

            return shader;
        }
        catch(Exception exc) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw exc;
        }
        return shader;
    }

    private static String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();

        FileInputStream in = new FileInputStream(filename);

        Exception exception = null;

        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));

            Exception innerExc= null;
            try {
                String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
                exception = exc;
            }
            finally {
                try {
                    reader.close();
                }
                catch(Exception exc) {
                    if(innerExc == null)
                        innerExc = exc;
                    else
                        exc.printStackTrace();
                }
            }

            if(innerExc != null)
                throw innerExc;
        }
        catch(Exception exc) {
            exception = exc;
        }
        finally {
            try {
                in.close();
            }
            catch(Exception exc) {
                if(exception == null)
                    exception = exc;
                else
                    exc.printStackTrace();
            }

            if(exception != null)
                throw exception;
        }

        return source.toString();
    }

    private static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
}
