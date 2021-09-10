package de.tuhh.ti5.swp.lcs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * The PrintLoader loads finger prints either from the DB or the samples file and stores them into a
 * list of Fingerprints.
 * 
 * @author Daniel Fischer
 * @version 1.0.
 * @since 2016-12-12.
 */

public class PrintLoader {
	private List<Fingerprint> prints;
	private int[] header;

	/**
	 * The constructor of a Fingerprint.
	 * 
	 * @param path
	 *            string path to the file from where finger prints are located.
	 * @throws IOException
	 *             IOException can be thrown because a BufferedReader is used to read the header of
	 *             a fingerprint file. After the header was extracted the rest of the file is read
	 *             as an object of Property.
	 * 
	 */

	PrintLoader(String path) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line = in.readLine();
		String[] tokens;

		/*** load properties after header was extracted ***/
		Properties props = new Properties();
		props.load(in);

		in.close();

		/*** extract header before properties (finger prints) ***/
		tokens = line.split(" ");
		header = new int[2];
		try {
			header[0] = Integer.parseInt(tokens[0]);
			header[1] = Integer.parseInt(tokens[1]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		prints = new ArrayList<>();
		/*** convert properties to finger prints ***/
		for (String id : props.stringPropertyNames()) {
			String print = props.getProperty(id);
			prints.add(new Fingerprint(id, print));
		}
	}

	/**
	 * Returns the list of fingerprints.
	 * 
	 * @return List<Fingerprint> list of finger prints that were loaded from the file.
	 */

	public List<Fingerprint> getPrints() {
		return prints;
	}

	/**
	 * Returns the number of fingerprints.
	 * 
	 * @return int size of the list with finger prints.
	 */

	public int size() {
		return prints.size();
	}
}
