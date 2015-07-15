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


    public static int spawnChildChromosome(int parentOne, int parentTwo, int lowerlimit, int higherlimit) {
            // starts with the base as anything the same between them
            int base = parentOne & parentTwo;
            int randInt = eco.game.Util.randInt(lowerlimit, higherlimit);
            // combines base with a random integer
            base += (parentOne^parentTwo)&(randInt);
            return base;
        }
        public static int spawnChildChromosome1(int parentOne, int parentTwo) {
                // starts with the base as anything the same between them
                int base = parentOne & parentTwo;
                int randInt = random.nextInt();
                // combines base with a random integer
                base += (parentOne^parentTwo)&(randInt);
                return base;
            }
            public static int mutator(int lowerlimit, int higherlimit){
                int randInt = eco.game.Util.randInt(lowerlimit, higherlimit);
                return randInt;
            }
}
