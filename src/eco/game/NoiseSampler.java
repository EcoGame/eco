package eco.game;

/**
 * This class samples multiple octaves of open simplex noise, and blends them
 * together.
 *
 * @author phil
 */

public class NoiseSampler {

    private static int octaves = 8;

    private static OpenSimplexNoise simplexNoise;

    private static int noiseScale;

    private static float amplitude = 0.5f;

    private static float persistance = 0.5f;

    public static float getNoise(float x, float y) {
        float[] noise = new float[octaves];
        for (int i = 0; i < octaves; i++) {
            noise[i] = (float) simplexNoise.eval((x * Math.pow(2, i))
                    / noiseScale, (y * Math.pow(2, i)) / noiseScale);
        }

        float result = 0.0f;
        float totalAmplitude = 0.0f;
        float amp = amplitude;
        for (int i = 0; i < octaves; i++) {
            totalAmplitude += amp;
            result += noise[i] * amp;
            amp *= persistance;
        }
        return result / totalAmplitude;
    }

    public static void initSimplexNoise(int seed) {
        simplexNoise = new OpenSimplexNoise(seed);
    }

    public static void setNoiseScale(int scale) {
        noiseScale = scale * 2;
    }

}
