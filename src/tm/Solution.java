package tm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import tm.Coordinate;
import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;

/**
 * Class representing a solution (an optimized map)
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class Solution implements Comparable<Solution> {
	
	/**
	 * Map object
	 */
	public MapTM map;

	public Solution(MapTM map) {
		this.map = map.copy();
		this.score = Double.NaN;
	}
	
	/**
	 * Current fitness value of the map
	 */
	private double score;
	
	/**
	 * Getter method of the score with regard to the chosen fitness function
	 * @param fitnessFunction Fitness function used to evaluate the map
	 * @return Map score
	 */
	public double getScore(Method fitnessFunction) {

		if (!Double.isNaN(score))
			return score;
		this.score = 0;
		MapTM ABCDEF = this.map.copy();
		this.score += getScore(ABCDEF, fitnessFunction);

		return this.score;
	}

	/**
	 * Calculating the score for a single configuration.
	 * 
	 * @param mapConfig Map to be evaluated
	 * @param fitness_function Fitness function used to evaluate the map
	 * @return Map score
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private double getScore(MapTM mapConfig, Method fitness_function) {
		GraphTM graph = new GraphTM(mapConfig);
		double score = 0;
		try {
			score += (Double) fitness_function.invoke(graph);
		} catch (Exception e) {
			e.printStackTrace();
			return Double.NEGATIVE_INFINITY;
		}
		return score;
	}

	/**
	 * Swapping two hexagons in the map
	 * 
	 * @param chosen First hexagon
	 * @param nb Second hexagon
	 * @param sol Solution in which the swapping will be performed
	 */
	public static void perturb(Coordinate chosen, Coordinate nb, Solution sol) {
		
		int row1 = chosen.row;
		int row2 = nb.row;
		int col1 = chosen.column;
		int col2 = nb.column;
		
		TypeTerrain buff = sol.map.hexes[row1][col1];
		sol.map.hexes[row1][col1] = sol.map.hexes[row2][col2];
		sol.map.hexes[row2][col2] = buff;
	}

	@Override
	public int compareTo(Solution arg0) {
		if (this.score > arg0.score) {
			return 1;
		}
		if (this.score < arg0.score) {
			return -1;
		}
		return 0;
	}
}

