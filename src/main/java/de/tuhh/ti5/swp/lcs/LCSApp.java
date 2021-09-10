package de.tuhh.ti5.swp.lcs;

import java.io.IOException;

/**
 * The LCSApp program that compares finger prints against a database and writes results back.
 *
 * @author Rustam Magomedov.
 * @version 1.0.
 * @since 2016-12-12.
 */

public class LCSApp {

	/**
	 * This is the main method, program execution point.
	 * 
	 * @param args
	 *            command line arguments through which a path to a configuration file gets
	 *            extracted.
	 */

	public static void main(String[] args) {
		try {
			Configuration config = new Configuration(args[0]);
			PrintLoader prints = new PrintLoader(config.getSamplesPath());
			PrintLoader entries = new PrintLoader(config.getDBPath());
			ThreadPool pool = new ThreadPool(config.getThreads());
			String[] results = new String[prints.size()];
			ResultWriter result;
			LCSTask task;

			for (Fingerprint print : prints.getPrints()) {
				task = new LCSTask(print, entries.getPrints(), config.getPrecision(), results);
				pool.execute(task);
			}
			pool.awaitTermination();

			result = new ResultWriter(config.getResultPath(), results);
			result.write();

			System.out.println("Finished");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
