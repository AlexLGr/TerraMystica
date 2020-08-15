package examples;

import tm.GenerateMapTM;
import tm.GraphTM;
import tm.MapTM;
import util.WriteImage;

/**
 * In this example we show how to evaluate a map and obtain its image
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class Example1 {
	
	public static void main(String[] args) {

		// Obtaining a map object from a textual representation of the map
		MapTM map = GenerateMapTM.getOriginalMap();
		
		// Creating a graph from the map
		GraphTM graph = new GraphTM(map);
		
		// Evaluating the map
		System.out.printf("Score = %.1f\n", graph.evaluate());
		
		// Generating a PNG
		WriteImage.generatePNG(map, "Original map", "output/original-map.PNG");

	}
}
