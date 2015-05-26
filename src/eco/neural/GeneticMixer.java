package eco.neural;
import eco.game.*;
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


	public static int spawnChildChromosome(int parentOne, int parentTwo) {
			// starts with the base as anything the same between them
			int base = parentOne & parentTwo;
			int randInt = random.nextInt();
			// combines base with a random integer
			base += (parentOne^parentTwo)&(randInt);
			return base;
		}
}
