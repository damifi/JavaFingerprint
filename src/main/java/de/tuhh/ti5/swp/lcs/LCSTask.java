package de.tuhh.ti5.swp.lcs;

import java.util.List;

/**
 * This is the LCSTask class which represents a smallest working unit for a thread pool.
 * 
 * @author Aleksej Davletcurin
 * @version 1.0
 * @since 2016-12-18.
 */

public class LCSTask extends LCSAlgorithm implements Runnable {
	
	private Fingerprint probe;
	private List<Fingerprint> entries;
	private double precision;
	private String[] results;
	private String result;

	/**
	 * The LCSTask constructor.
	 * 
	 * @param probe
	 *            finger print being searched for.
	 * @param entries
	 *            database of finger prints being checked against.
	 * @param precision
	 *            tolerable deviation value.
	 * @param list
	 *            variable to write results to, which get printed later.
	 */

	LCSTask(Fingerprint probe, List<Fingerprint> entries, double precision, String[] list) {
		this.probe = probe;
		this.entries = entries;
		this.precision = precision;
		results = list;
	}

	@Override
	public void run() {
		int newLen = -1;
		int oldLen = -1;
		String stringIDs = "";
		for (Fingerprint entry : entries) {
			newLen = compare(probe.getValue(), entry.getValue(), precision);
			if (oldLen < newLen) {
				oldLen = newLen;
				stringIDs = "" + entry.getID();
			} else if (oldLen == newLen) {
				stringIDs += " " + entry.getID();
			}
		}

		result = probe.getID() + ": " + oldLen + " " + countIDs(stringIDs) + " "
				+ sortIDs(stringIDs);
		synchronized (results) {
			results[probe.getID()] = result;
		}
	}
}