package tm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is used to refer to individual coordinates of hexagons on the map
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class Coordinate {
	/**
	 * Row from 0 to 8
	 */
	public int row;
	/**
	 * Column from 0 to 12
	 */
	public int column;
	
	/**
	 * Constructor method
	 */
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Determines whether this coordinate is located on the border of the map
	 * 
	 * @return True if the coordinate is on the border, False otherwise
	 */
	public boolean isBorder() {
		if (row == 0 || row == 8 || column == 0 || column == 12) {
			return true;
		} else {
			if (row % 2 == 1 && column == 11) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Checks if two coordinates are equal to each other (have the same row and column)
	 * 
	 * @param obj A coordinate to be compared
	 * @return True if the coordinate are equal, False otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		Coordinate c = (Coordinate) obj;
		if (c.row == this.row && c.column == this.column)
			return true;
		else
			return false;
	}
	
	/**
	 * Outputs the hash code of the coordinate
	 * 
	 * @return Hash code
	 */
	@Override
	public int hashCode() {
		return (this.row + "," + this.column).hashCode();
	}

	/**
	 * Obtaining all valid coordinates located in the map
	 * 
	 * @return ArrayList of coordinates
	 */
	public static ArrayList<Coordinate> allCoordinates() {
		ArrayList<Coordinate> allCoordinates = new ArrayList<Coordinate>();
		for (int row = 0; row < 9; row++) {
			for (int column = 0; column < 12; column++) {
				allCoordinates.add(new Coordinate(row, column));
			}
			if (row % 2 == 0)
				allCoordinates.add(new Coordinate(row, 12));
		}
		return allCoordinates;
	}
	
	/**
	 * Determining the coordinates that are adjacent to the given coordinate 
	 * 
	 * @return Set of coordinates
	 */
	public Set<Coordinate> neighbourCoordinates() {
		Set<Coordinate> neighbourCoordinates = new HashSet<Coordinate>();

		boolean isShorterRow = (row % 2 != 0);

		if (row > 0 && row < 8 && column > 0 && column < 11) {
			neighbourCoordinates.add(new Coordinate(row, column - 1));
			neighbourCoordinates.add(new Coordinate(row, column + 1));
			neighbourCoordinates.add(new Coordinate(row - 1, column));
			neighbourCoordinates.add(new Coordinate(row + 1, column));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			} else {
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			}
		}

		if (row == 0) {
			if (column != 0) {
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			}
			if (column != 12) {
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
			}
		}

		if (row == 8) {
			if (column != 0) {
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
			}
			if (column != 12) {
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column));
			}
		}

		if (column == 0) {
			neighbourCoordinates.add(new Coordinate(row - 1, column));
			neighbourCoordinates.add(new Coordinate(row, column + 1));
			neighbourCoordinates.add(new Coordinate(row + 1, column));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}

		if (column == 12) {
			neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
			neighbourCoordinates.add(new Coordinate(row, column - 1));
			neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
			if (isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}

		if (column == 11) {
			if (!isShorterRow) {
				neighbourCoordinates.add(new Coordinate(row - 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row - 1, column));
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row, column + 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
			} else {
				neighbourCoordinates.add(new Coordinate(row - 1, column));
				neighbourCoordinates.add(new Coordinate(row - 1, column + 1));
				neighbourCoordinates.add(new Coordinate(row, column - 1));
				neighbourCoordinates.add(new Coordinate(row + 1, column));
				neighbourCoordinates.add(new Coordinate(row + 1, column + 1));
			}
		}
		return neighbourCoordinates;
	}

}