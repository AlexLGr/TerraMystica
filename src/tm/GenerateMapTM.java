package tm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class that combines methods to instantiate a new map
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class GenerateMapTM {
	
	/**
	 * Textual representation of the primary Terra Mystica map
	 */
	private static final String ORIGINAL_MAP = "U,S,G,B,Y,R,U,K,R,G,B,R,K;" + "Y,I,I,U,K,I,I,Y,K,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,G,I,S,I,I;" + "G,B,Y,I,I,R,B,I,R,I,R,U;" + "K,U,R,B,K,U,S,Y,I,I,G,K,B;"
			+ "S,G,I,I,Y,G,I,I,I,U,S,U;" + "I,I,I,S,I,R,I,G,I,Y,K,B,Y;" + "Y,B,U,I,I,I,B,K,I,S,U,S;"
			+ "R,K,S,B,R,G,Y,U,S,I,B,G,R;";
	
	/**
	 * Textual representation of map released for Fire and Ice expansion
	 */
	private static final String FIRE_ICE_2_MAP = "U,S,G,B,U,R,U,K,R,B,G,R,K;" + "Y,I,I,Y,K,I,I,Y,G,I,I,Y;"
			+ "I,I,K,I,S,I,G,I,K,I,R,I,I;" + "G,B,Y,I,I,R,B,I,R,I,S,U;" + "K,U,R,B,Y,U,G,Y,I,I,G,K,R;"
			+ "S,G,I,I,K,S,I,I,I,U,S,Y;" + "I,I,I,S,I,R,I,G,I,Y,K,B,U;" + "Y,B,U,I,I,I,B,K,I,S,U,R;"
			+ "B,K,S,B,R,G,Y,U,S,I,B,G,S;";

	/**
	 * Textual representation of the map released for Fjords expansion
	 */
	private static final String FJORDS_MAP = "G,K,I,U,Y,S,K,S,Y,R,K,B,Y;" + "B,U,I,B,G,R,I,I,I,I,I,U;"
			+ "S,G,R,I,I,U,I,K,S,U,Y,I,S;" + "I,I,I,S,I,I,G,R,B,G,R,I;" + "R,S,Y,I,B,R,I,U,Y,S,U,I,K;"
			+ "K,U,I,G,Y,G,I,S,B,G,I,S;" + "Y,B,I,K,S,K,B,I,U,K,I,G,R;" + "G,I,U,R,U,Y,R,I,I,I,R,B;"
			+ "K,I,I,G,B,S,B,I,G,Y,K,U,Y;";
	
	/**
	 * Getter method for the primary Terra Mystica map
	 * 
	 * @return MapTM instance
	 */
	public static MapTM getOriginalMap() {
		return convertMap(ORIGINAL_MAP);
	}
	
	/**
	 * Getter method for the map released for Fjords expansion
	 * 
	 * @return MapTM instance
	 */
	public static MapTM getFjords() {
		return convertMap(FJORDS_MAP);
	}
	
	/**
	 * Getter method for the map released for Fire and Ice expansion
	 * 
	 * @return MapTM instance
	 */
	public static MapTM getFireIce2() {
		return convertMap(FIRE_ICE_2_MAP);
	}
	
	/**
	 * Converting the textual representation of the map to MapTM class
	 * 
	 * @param string A textual representation of the map
	 * @return MapTM instance
	 */
	private static MapTM convertMap(String string) {
		MapTM map = new MapTM();
		String[] rows = string.split(";", 0);
		for (int r = 0; r < rows.length; r++) {
			String row = rows[r];
			String[] columns = row.split(",", 0);
			for (int c = 0; c < columns.length; c++) {
				String column = columns[c];
				map.hexes[r][c] = convertLetter(column);
			}
		}
		return map;
	}
	
	/**
	 * Converting a letter representing a color on the map to TypeTerrain instance
	 * 
	 * @param letter A letter representing a color on the map
	 * @return TypeTerrain instance
	 */
	private static TypeTerrain convertLetter(String letter) {
		switch (letter) {
		case "K":
			return TypeTerrain.BLACK;
		case "B":
			return TypeTerrain.BLUE;
		case "U":
			return TypeTerrain.BROWN;
		case "S":
			return TypeTerrain.GRAY;
		case "G":
			return TypeTerrain.GREEN;
		case "R":
			return TypeTerrain.RED;
		case "Y":
			return TypeTerrain.YELLOW;
		case "I":
			return TypeTerrain.RIVER;
		default:
			return TypeTerrain.NONE;
		}
	}
	
	/**
	 * Generating a map with colors randomly assigned to hexagons
	 * 
	 * @return MapTM instance
	 */
	public static MapTM generateRandomMapTM() {
		MapTM map = new MapTM();

		List<TypeTerrain> allTypeTerrain = landTiles();
		allTypeTerrain.addAll(riverTiles());
		Collections.shuffle(allTypeTerrain);

		ArrayList<Coordinate> allCoordinates = Coordinate.allCoordinates();
		for (Coordinate coordinate : allCoordinates) {
			map.hexes[coordinate.row][coordinate.column] = allTypeTerrain.remove(0);
		}

		return map;
	}
	
	/**
	 * Method to obtain land tile colors
	 * 
	 * @return List of type TypeTerrain containing land tile colors
	 */
	protected static List<TypeTerrain> landTiles() {
		List<TypeTerrain> listTypeTerrain = new ArrayList<TypeTerrain>();
		for (int i = 0; i < 11; i++) {
			listTypeTerrain.add(TypeTerrain.YELLOW);
			listTypeTerrain.add(TypeTerrain.BROWN);
			listTypeTerrain.add(TypeTerrain.BLACK);
			listTypeTerrain.add(TypeTerrain.BLUE);
			listTypeTerrain.add(TypeTerrain.GREEN);
			listTypeTerrain.add(TypeTerrain.GRAY);
			listTypeTerrain.add(TypeTerrain.RED);
		}
		return listTypeTerrain;
	}
	
	/**
	 * Method to obtain river tile colors
	 * 
	 * @return List of type TypeTerrain containing river tile colors
	 */
	protected static List<TypeTerrain> riverTiles() {
		List<TypeTerrain> list = new ArrayList<TypeTerrain>();
		for (int i = 0; i < 36; i++)
			list.add(TypeTerrain.RIVER);
		return list;
	}
	
	/**
	 * Print out the version to be used with: https://terra.snellman.net/mapedit/#
	 * 
	 * @param riverABCDEF Map to be printed
	 */
	public static String mapAsText(MapTM riverABCDEF) {
		StringBuilder sb = new StringBuilder();
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 13; c++) {
				if (r % 2 == 1 && c == 12)
					continue;
				switch (riverABCDEF.hexes[r][c]) {
				case BLACK:
					sb.append("K");
					break;
				case BLUE:
					sb.append("B");
					break;
				case BROWN:
					sb.append("U");
					break;
				case GRAY:
					sb.append("S");
					break;
				case GREEN:
					sb.append("G");
					break;
				case RED:
					sb.append("R");
					break;
				case YELLOW:
					sb.append("Y");
					break;
				case RIVER:
					sb.append("I");
					break;
				default:
					break;
				}
				if (r % 2 == 1) {
					if (c < 11)
						sb.append(",");
					else if (c == 11)
						sb.append(",N,\n");
				} else {
					if (c < 12)
						sb.append(",");
					else if (c == 12)
						sb.append(",\n");
				}
			}
		}
		return sb.toString();
	}

}