package eco.neural;

import java.util.Random;

/**
 * A class that handles the genetic mixing
 * 
 * @author will
 * @param parentOne
 *            the chromosome of the first parent
 * @param parentTwo
 *            the chromosome of the second parent
 * @return a new semirandom child chromosome
 * 
 */

public class GeneticMixer {

	public static Random random = new Random();

	public static void start() {

		int one = eco.game.Util.randInt(1, 5);
		int two = eco.game.Util.randInt(1, 5);
		int child = spawnChildChromosome(one, two);
		System.out.println("One: " + one);
		System.out.println("Two: " + two);
		System.out.println("Child: " + child);
		System.out.println("One: " + Integer.toBinaryString(one));
		System.out.println("Two: " + Integer.toBinaryString(two));
		System.out.println("Child: " + Integer.toBinaryString(child));

	}

	public static int spawnChildChromosome(int parentOne, int parentTwo) {

		int base = parentOne & parentTwo;
		Random rand = new Random();
		int powers2[] = { 0x2, 0xC, 0xF0, 0xFF00, 0xFFFF0000 };
		int exponents[] = { 1, 2, 4, 8, 16 };
		int max = Math.max(parentOne, parentTwo);
		int result = 0;
		System.out.println(max);

		for (int m = 4; m >= 0; m--) {
			if ((max & powers2[m]) != 0) {
				max >>= exponents[m];
				result |= exponents[m];
			}
		}

		result += 1;
		System.out.println(result);

		for (int n = 0; n < result; n++) {
			if (((parentOne ^ parentTwo) & n) == 0) {
				base |= rand.nextInt(2) << n; // 0001; 0010; 0100; 1000;
			}

		}
		return base;
	}
}
