package de.tuhh.ti5.swp.lcs;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * The Configuration class holds configuration settings for the entire application.
 *
 * @author Daniel Fischer
 * @version 1.0.
 * @since 2016-12-12.
 */

public class Configuration {
	private String path;
	private String samplesPath;
	private String dbPath;
	private String resultPath;
	private Properties props;
	private int threads;
	private double precision;

	/**
	 * The Configuration constructor.
	 * 
	 * @param path
	 *            the path of the configuration file
	 */

	Configuration(String path) {
		System.out.println(path);
		this.path = path;
		props = new Properties();
		try {
			props.load(new FileInputStream(path));
			samplesPath = props.getProperty("samples");
			dbPath = props.getProperty("db");
			resultPath = props.getProperty("result");
			threads = Integer.parseInt(props.getProperty("threads"));
			precision = Double.parseDouble(props.getProperty("precision"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The getPath method returns the path of the configuration file.
	 * 
	 * @return the path to the configuration file
	 */

	public String getPath() {
		return path;
	}

	/**
	 * The getSamplesPath method returns the path of the samples file.
	 * 
	 * @return the path to the file containing Fingerprint samples
	 */

	public String getSamplesPath() {
		return samplesPath;
	}

	/**
	 * The getDBPath method returns the path of the database file.
	 * 
	 * @return the path to the file containing a database of Fingerprints
	 */

	public String getDBPath() {
		return dbPath;
	}

	/**
	 * The getResultPath method returns the destination path to where the results are written.
	 * 
	 * @return the path to the file in which the results are written
	 */

	public String getResultPath() {
		return resultPath;
	}

	/**
	 * The getThreads method returns the number of Worker Threads.
	 * 
	 * @return number of Worker Threads;
	 */

	public int getThreads() {
		return threads;
	}

	/**
	 * The getPrecision method returns the precision value.
	 * 
	 * @return the precision value that is used in the LCS algorithm
	 */

	public double getPrecision() {
		return precision;
	}
}
