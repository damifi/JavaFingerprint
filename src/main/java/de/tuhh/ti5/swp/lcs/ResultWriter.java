package de.tuhh.ti5.swp.lcs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * The ResultWriter class is used to write into a file containing the results of the LCS algorithm.
 * 
 * @author Daniel Fischer
 * @version 1.0.
 * @since 2016-12-12.
 */

public class ResultWriter {
	private String destination;
	private String[] results;

	/**
	 * The Constructor of ResultWriter.
	 * 
	 * @param path
	 *            path to the destination at which the result file is created
	 * @param list
	 *            list of results that is written into the result file
	 */

	ResultWriter(String path, String[] list) {
		destination = path;
		results = list;
	}

	/**
	 * The write() method writes the list of results into a result file at the location specified in
	 * the Constructor.
	 */

	public void write() {
		synchronized (results) {
			PrintWriter file;
			try {
				file = new PrintWriter(destination);
				for (String result : results) {
					file.println(result);
				}
				file.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
