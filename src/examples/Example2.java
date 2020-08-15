package examples;

import java.lang.reflect.Method;
import tm.GenerateMapTM;
import tm.GraphTM;
import tm.MapTM;
import tm.Solution;

/**
 * In this example we show how to initialize a random map and evolve it by swapping hexagons
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class Example2 {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {

		// Generating a random map
		MapTM map = GenerateMapTM.generateRandomMapTM();
		
		// Initializing a solution class
		Solution solution = new Solution(map);
		
		// Specifying the method used to evaluate a map
		Method fitnessFunction = GraphTM.class.getMethod("evaluate");
		
		// Initial evaluation of the map
		System.out.printf("Initial score = %.1f\n", solution.getScore(fitnessFunction));
		
		// Randomly picking two hexagons and swapping them
		Solution neighbour = new Solution(solution.map.copy());
		Solution.perturb(neighbour);
		
		// Evaluating an evolved map
		System.out.printf("Score after single iteration = %.1f\n", neighbour.getScore(fitnessFunction));

	}
}
