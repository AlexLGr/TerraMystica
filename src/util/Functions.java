package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tm.GraphTM;
import tm.MapTM;
import tm.TypeTerrain;

/**
 * Class combining some useful auxiliary functions.
 * 
 * @author l.araujo
 *
 */

public class Functions {
	
	/**
	 * Obtaining the union of two lists 
	 */
	public static <T> List<T> union(List<T> list1, List<T> list2) {
		Set<T> set = new HashSet<T>();

		set.addAll(list1);
		set.addAll(list2);

		return new ArrayList<T>(set);
	}
	
	/**
	 * Obtaining the intersection of two lists 
	 */
	public static <T> List<T> intersection(List<T> list1, List<T> list2) {
		List<T> list = new ArrayList<T>();

		for (T t : list1) {
			if (list2.contains(t)) {
				list.add(t);
			}
		}

		return list;
	}
	
	/**
	 * Converting a set to list
	 */
	public static <T> List<T> convertList(Set<T> set) {
		List<T> list = new ArrayList<T>();

		for (T t : set)
			list.add(t);

		return list;
	}
	
	/**
	 * Obtaining first n elements of the list
	 */
	public static <T> ArrayList<T> firstN(List<T> list, int n) {
		ArrayList<T> firstN = new ArrayList<T>();

		for (int i = 0; i < list.size() && i < n; i++)
			firstN.add(list.get(i));

		return firstN;
	}

	/**
	 * Auxiliary method for generating matrices Colour x Colour.
	 * 
	 */
	public static int convertTerrainToInt(TypeTerrain terrain) {
		switch (terrain) {
		case BLACK:
			return 0;
		case BLUE:
			return 1;
		case GREEN:
			return 2;
		case GRAY:
			return 3;
		case RED:
			return 4;
		case YELLOW:
			return 5;
		case BROWN:
			return 6;
		case RIVER:
			return 7;
		default:
			// TypeTerrain.NONE
			return -1;
		}
	}
	
	/**
	 * Converting integer value to TypeTerrain
	 */
	public static TypeTerrain convertIntToTerrain(int i) {
		switch (i) {
		case 0:
			return TypeTerrain.BLACK;
		case 1:
			return TypeTerrain.BLUE;
		case 2:
			return TypeTerrain.GREEN;
		case 3:
			return TypeTerrain.GRAY;
		case 4:
			return TypeTerrain.RED;
		case 5:
			return TypeTerrain.YELLOW;
		case 6:
			return TypeTerrain.BROWN;
		case 7:
			return TypeTerrain.RIVER;
		default:
			return TypeTerrain.NONE;
		}
	}

	/**
     * Use Files class from Java 1.7 to write files, internally uses OutputStream
     * @param data String to be written into the file
     * @param fileName Path to the file to be written
     */
    public static void writeFile(String data, String fileName) {
        try {
            Files.write(Paths.get(fileName), data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}