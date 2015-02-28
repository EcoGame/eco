package eco;

public class NoiseSampler {
	
	public static int octaves = 8;
	
	private static SimplexNoise simplexNoise;
	
	private static int noiseScale;
	
	private static float amplitude = 0.5f;
	
	private static float persistance = 0.5f;
	
	public static float getNoise(float x, float y){
		float[] noise = new float[octaves];
		for (int i = 0; i < octaves; i++){
			noise[i] = (float) simplexNoise.noise((x * Math.pow(2, i)) / noiseScale, 
												  (y * Math.pow(2, i)) / noiseScale);
		}
		
		float result = 0.0f;
		float totalAmplitude = 0.0f;
		float amp = amplitude;
		for (int i = 0; i < octaves; i++){
			totalAmplitude += amp;
			result += noise[i] * amp;
			amp *= persistance;
		}
		return result / totalAmplitude;
	}
	
	public static void initSimplexNoise(int seed){
		simplexNoise = new SimplexNoise(seed);
	}
	
	public static void setNoiseScale(int scale){
		noiseScale = scale * 2;
	}

}
