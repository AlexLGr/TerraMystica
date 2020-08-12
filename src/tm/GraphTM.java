package tm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

import tm.Coordinate;
import tm.NodeHexe;
import tm.TypeTerrain;

/**
 * Graph representation of the Terra Mystica map
 * 
 * @author l.araujo
 * @author a.grichshenko
 *
 */

public class GraphTM {
	
	/**
	 * Hashtable of coordinates and hexagons
	 */
	public Hashtable<Coordinate, NodeHexe> hash;

	/**
	 * Generate a graph
	 * 
	 * @param mapTM Object of MapTM class from which a graph is to be generated
	 */
	public GraphTM(MapTM mapTM) {
		hash = new Hashtable<Coordinate, NodeHexe>();
		for (int row = 0; row <= 8; row++) {
			for (int column = 0; column <= 12; column++) {
				if (row % 2 == 1 && column == 12)
					// this hexe does not exist
					continue;
				// Adding node to the hash table
				NodeHexe n = new NodeHexe(row, column, mapTM.hexes[row][column]);
				hash.put(new Coordinate(row, column), n);
				// Setting neighbours of node n

				boolean isShorterRow = (row % 2 != 0);

				// INTERIOR HEXES
				if (row > 0 && row < 8 && column > 0 && column < 11) {
					n.coordNeighbours.add(new Coordinate(row, column - 1));
					n.coordNeighbours.add(new Coordinate(row, column + 1));
					n.coordNeighbours.add(new Coordinate(row - 1, column));
					n.coordNeighbours.add(new Coordinate(row + 1, column));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					} else {
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					}
					continue;
				}

				if (row == 0) {
					if (column != 0) {
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					}
					if (column != 12) {
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
					}
					continue;
				}

				if (row == 8) {
					if (column != 0) {
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
					}
					if (column != 12) {
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column));
					}
					continue;
				}

				if (column == 0) {
					n.coordNeighbours.add(new Coordinate(row - 1, column));
					n.coordNeighbours.add(new Coordinate(row, column + 1));
					n.coordNeighbours.add(new Coordinate(row + 1, column));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}

				if (column == 12) {
					n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
					n.coordNeighbours.add(new Coordinate(row, column - 1));
					n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
					if (isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}

				if (column == 11) {
					if (!isShorterRow) {
						n.coordNeighbours.add(new Coordinate(row - 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row - 1, column));
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row, column + 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
					} else {
						n.coordNeighbours.add(new Coordinate(row - 1, column));
						n.coordNeighbours.add(new Coordinate(row - 1, column + 1));
						n.coordNeighbours.add(new Coordinate(row, column - 1));
						n.coordNeighbours.add(new Coordinate(row + 1, column));
						n.coordNeighbours.add(new Coordinate(row + 1, column + 1));
					}
					continue;
				}
			}
		}
	}
	
	/**
	 * Obtain the first node of the graph
	 * 
	 * @return NodeHexe instance with the coordinate (0, 0)
	 */
	public NodeHexe getFirstNode() {
		return hash.get(new Coordinate(0, 0));
	}
	
	/**
	 * Graph traversal using DFS of only land hexagons
	 * 
	 * @param length Current length of a connected component
	 * @param key Current coordinate being processed
	 * @return Length of a connected component
	 */
	public int DFS2(int length, Coordinate key) {
		this.hash.get(key).processed = true;
		NodeHexe current_node = this.hash.get(key);
		for (Coordinate c : current_node.coordNeighbours) {
			if (this.hash.containsKey(c) && this.hash.get(c).typeTerrain != TypeTerrain.RIVER
					&& this.hash.get(c).processed == false) {
				length++;
				length = this.DFS2(length, c);
			}
		}
		return length;
	}
	
	/**
	 * Graph traversal using DFS of only river hexagons
	 * 
	 * @param length Current length of a connected component
	 * @param key Current coordinate being processed
	 * @return Length of a connected component
	 */
	private int DFS(int length, Coordinate key) {
		this.hash.get(key).processed = true;
		NodeHexe current_node = this.hash.get(key);
		for (Coordinate c : current_node.coordNeighbours) {
			if (this.hash.containsKey(c) && this.hash.get(c).typeTerrain == TypeTerrain.RIVER
					&& this.hash.get(c).processed == false) {
				length++;
				length = this.DFS(length, c);
			}
		}
		return length;
	}
	
	/**
	 * Determine the number of errors associated with connected river components
	 * 
	 * @return n-2, for every n separate connected river component
	 */
	public int connectedRivers() {
		int errors = 0;
		ArrayList<Integer> cc = new ArrayList<>();
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate key : keys) {
			if (this.hash.get(key).typeTerrain == TypeTerrain.RIVER && this.hash.get(key).processed == false) {
				int length = 1;
				cc.add(this.DFS(length, key));
			}
		}
		if (cc.size() <= 2) {
			errors = 0;
		} else {
			errors = cc.size() - 2;
		}

		return errors;
	}
	
	/**
	 * Determine the number of errors associated with neighbours of river hexagons.
	 * If the number of river neighbours of a river hexagon is not between 1-3, increase error count by one
	 * 
	 * @return Number of errors
	 */
	public int riverNeighbourhood() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate coordinate : keys) {
			if (this.hash.get(coordinate).typeTerrain == TypeTerrain.RIVER) {
				int nb_count = 0;
				NodeHexe current_node = this.hash.get(coordinate);
				ArrayList<Coordinate> nbs = new ArrayList<Coordinate>();
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					if (nb_node.typeTerrain == TypeTerrain.RIVER) {
						nbs.add(c);
						nb_count++;
					}
				}
				if (nb_count == 0) {
					errors++;
				}
				if (nb_count == 1 && coordinate.isBorder() == false) {
					errors++;
				}
				if (nb_count == 2 && nbs.get(0).isBorder() == true && nbs.get(1).isBorder() == true) {
					errors++;
				}
				if (nb_count > 3) {
					errors++;
				}
			}
			if (this.hash.get(coordinate).typeTerrain != TypeTerrain.RIVER) {
				int nb_rivers = 0;
				NodeHexe current_node = this.hash.get(coordinate);
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					if (nb_node.typeTerrain == TypeTerrain.RIVER) {
						nb_rivers++;
					}
				}
				if (nb_rivers == current_node.coordNeighbours.size()) {
					errors++;
				}
			}
		}
		return errors;
	}
	
	/**
	 * Determine the number of errors associated with two neighbouring hexagons being of the same color
	 * 
	 * @return Number of occurences of two neighbouring hexagons being of the same color
	 */
	public int sameColorNeighbours() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate key : keys) {
			if (this.hash.get(key).typeTerrain != TypeTerrain.RIVER) {
				NodeHexe current_node = this.hash.get(key);
				for (Coordinate c : current_node.coordNeighbours) {
					NodeHexe nb_node = this.hash.get(c);
					// NONE is not considered. An empty map does not violate any rules
					if (current_node.typeTerrain.compareTo(nb_node.typeTerrain) == 0
							&& current_node.typeTerrain != TypeTerrain.NONE) {
						errors++;
					}
				}
			}
		}
		return errors;
	}
	
	/**
	 * Determine the number of errors associated with neighbouring that can be terraformed using only one spade
	 * 
	 * @return Number of occurences when none of the neighbours of a hexagon can be terraformed using only one spade
	 */
	public int oneSpadeNeighbour() {
		int errors = 0;
		Set<Coordinate> keys = this.hash.keySet();

		for (Coordinate currentC : keys) {
			NodeHexe currentN = this.hash.get(currentC);
			if (currentN.typeTerrain == TypeTerrain.RIVER || currentN.typeTerrain == TypeTerrain.NONE)
				continue;

			// From now on, assume that current_node is a land hexe
			int numLandNeighbours = 0;
			boolean found = false;
			for (Coordinate neighbourC : this.hash.get(currentC).coordNeighbours) {
				NodeHexe neighbourN = this.hash.get(neighbourC);
				if (neighbourN.typeTerrain != TypeTerrain.RIVER && neighbourN.typeTerrain != TypeTerrain.NONE)
					numLandNeighbours++;
				if (TypeTerrain.spadeDistance(currentN.typeTerrain, neighbourN.typeTerrain) == 1)
					found = true;
			}
			if (numLandNeighbours >= 3 && !found) {
				errors++;
			}
		}
		return errors;
	}
	
	/**
	 * Fitness function
	 * 
	 * @return Fitness value of a map
	 */
	public double evaluate() {
			int req1 = 1 * this.sameColorNeighbours();
			int req2 = 1 * this.oneSpadeNeighbour();
			int req3 = 1 * this.connectedRivers();
			int req4 = 1 * this.riverNeighbourhood();
	
			int errors = req1 + req2 + req3 + req4;
			return 1.0 * errors;
	}
	
}