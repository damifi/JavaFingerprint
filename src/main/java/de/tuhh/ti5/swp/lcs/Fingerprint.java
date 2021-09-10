package de.tuhh.ti5.swp.lcs;

/**
 * The Fingerprint class represents a fingerprint and stores its information.
 * 
 * @author Daniel Fischer
 * @version 1.0.
 * @since 2016-12-12.
 */

public class Fingerprint {
	private int id;
	private double[] value;

	/**
	 * The Fingerprint constructor. Takes the ID and the Value of a fingerprint as Strings and
	 * converts them to int and double[].
	 * 
	 * @param id
	 *            Fingerprint ID as a String
	 * @param print
	 *            string with Fingerprint values which are converted to doubles
	 */

	Fingerprint(String id, String print) {
		this.id = Integer.parseInt(id);
		String[] samples = print.split(" ");
		value = new double[print.split(" ").length];
		for (int i = 0; i < samples.length; i++) {
			value[i] = Double.parseDouble(samples[i]);
		}
	}

	/**
	 * The getID method returns the ID of the fingerprint.
	 * 
	 * @return value of fingerprint ID.
	 */

	public int getID() {
		return id;
	}

	/**
	 * The getValue method returns a double array of finger print values.
	 * 
	 * @return double[] array of fingerprint values that were extracted from the file.
	 */

	public double[] getValue() {
		return value;
	}
}
