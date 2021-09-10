package de.tuhh.ti5.swp.lcs;

import java.util.Arrays;

/**
 * This is the LCSAlgorithm class implements the LCS algorithm, with associated compare method.
 * 
 * @author  Aleksej Davletcurin
 * @version 1.0.
 * @since   2016-12-18.
 */

public class LCSAlgorithm {

	/**
	 * This is compare method, which compares two finger prints.
	 * 
	 * @param probe
	 * 		finger print being searched for. 
	 * @param entry
	 * 		finger print from the database being searched against.
	 * @param precision 
	 * 		tolerable deviation value.
	 * @return int
	 * 		returns the computed length. 
	 */
	
	protected int compare(double[] probe, double[] entry, double precision) {
		int[][] matrix = new int[probe.length + 1][entry.length + 1];
		for (int i = 1; i <= probe.length; i++) {
			for (int j = 1; j <= entry.length; j++) {
				if (Math.abs(probe[i - 1] - entry[j - 1]) <= precision) {
					matrix[i][j] = matrix[i - 1][j - 1] + 1; 
				} else {
					if (matrix[i][j - 1] > matrix[i - 1][j]) {
						matrix[i][j] = matrix[i][j - 1];	
					} else {
						matrix[i][j] = matrix[i - 1][j];
					}
				}
			}
		}
		return matrix[probe.length][entry.length];
	}

	/**
	 * This is countIDs method, which compares two finger prints.
	 * 
	 * @param ids
	 * 		space separated ids of finger prints. 
	 * @return int
	 * 		returns the ids count, which are contained in the string. 
	 */
	
	protected int countIDs(String ids) {
		return (ids.length() - ids.replace(" ", "").length()) + 1;
	}
	
	/**
	 * This is countIDs method, which compares two finger prints.
	 * 
	 * @param ids
	 * 		space separated ids of finger prints. 
	 * @return String
	 * 		space separated, sorted string of ids. 
	 */
	
	protected String sortIDs(String ids) {
		String[] tokenIDs = ids.split(" ");
		int[] intIDs = new int[tokenIDs.length];
		for (int i = 0; i < intIDs.length; i++) {
			intIDs[i] = Integer.parseInt(tokenIDs[i]);
		}
		Arrays.sort(intIDs);
		for (int i = 0; i < intIDs.length; i++) {
			tokenIDs[i] = "" + intIDs[i];
		}		
		return String.join(" ", tokenIDs);
	}	
}
