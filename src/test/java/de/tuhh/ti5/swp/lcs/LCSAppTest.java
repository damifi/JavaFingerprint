package de.tuhh.ti5.swp.lcs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URISyntaxException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class LCSAppTest extends TestCase {

	/**
	 * Wait maximum MAX_SLEEP_PERIODS times SLEEP_DURATION ms for LCS algorithm to finish.
	 */
	static final int MAX_SLEEP_PERIODS = 1200;
	static final int SLEEP_DURATION = 100;
	static final int NANOSECS_PER_MS = 1000000;

	/**
	 * testFiles must contain all test files. The order must be as indicated by following static
	 * variables.
	 */
	private String[] testFiles = { "res/test_lcsapp1.properties", "res/test_lcsapp2.properties",
			"res/test_lcsapp3.properties", "res/test_lcsapp4.properties", "res/test_matrixA.dat",
			"res/test_matrixB.dat", "res/test_output.dat", "res/test_output_expected.dat",
			"res/testsmall_matrixA.dat", "res/testsmall_matrixB.dat",
			"res/testsmall_output_expected.dat", "res/testsmall_lcsapp.properties" };

	static final int CONFIG1 = 0;
	static final int CONFIG2 = 1;
	static final int CONFIG3 = 2;
	static final int CONFIG4 = 3;
	static final int DB = 4;
	static final int FINGERPRINT = 5;
	static final int OUTPUT = 6;
	static final int OUTPUT_EXPECTED = 7;
	static final int DB_SMALL = 8;
	static final int FINGERPRINT_SMALL = 9;
	static final int OUTPUT_SMALL_EXPECTED = 10;
	static final int CONFIG_SMALL = 11;

	/**
	 * Create the test case.
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public LCSAppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(LCSAppTest.class);
	}

	/**
	 * Test whether provided test files exist.
	 * 
	 * @throws URISyntaxException
	 *             Thrown if URI syntax bad. Which is not the case.
	 * 
	 */
	public void testTestFilesExist() throws URISyntaxException {

		for (String file : testFiles) {
			if (file.equalsIgnoreCase(testFiles[OUTPUT])) {
				continue;
			}
			if (!new File(file).exists()) {
				fail("test file " + file + " does not exist.");
			}
		}
	}

	/**
	 * Test whether main() of LCSApp produces output file.
	 * 
	 */
	public void testLcsAppMainProducesOutputFile() {
		testLcs(CONFIG_SMALL, OUTPUT, OUTPUT_SMALL_EXPECTED, false);
	}

	/**
	 * Verify that output file contains expected content for simple example.
	 */
	public void testLcsAppMainSimpleOutputFileCorrect() {
		testLcs(CONFIG_SMALL, OUTPUT, OUTPUT_SMALL_EXPECTED, true);
	}

	/**
	 * Verify that using more CPUs, output is created faster.
	 */
	public void testParallelSpeedup() {

		int cores = Runtime.getRuntime().availableProcessors();
		long executionTime = Long.MAX_VALUE;
		for (int threads = 1; threads <= cores; threads++) {
			long time = Long.MAX_VALUE;
			switch (threads) {
			case 1:
				time = testLcs(CONFIG1, OUTPUT, OUTPUT_EXPECTED, true);
				break;
			case 2:
				time = testLcs(CONFIG2, OUTPUT, OUTPUT_EXPECTED, true);
				break;
			case 3:
				time = testLcs(CONFIG3, OUTPUT, OUTPUT_EXPECTED, true);
				break;
			case 4:
				time = testLcs(CONFIG4, OUTPUT, OUTPUT_EXPECTED, true);
				break;
			default:
				// ignore if more than 4 cores
				return;
			}

			if (time >= executionTime) {
				fail("Running with " + threads + " threads (" + time
						+ "ms) was not faster than running with " + (threads - 1) + " threads ("
						+ executionTime + ").");
			} else {
				executionTime = time;
			}

		}

	}

	/**
	 * Run the LCS algorithms and checks output.
	 * 
	 * LCSApp.main() may run asynchronously and there is no READY callback. Workaround: Check every
	 * SLEEP_DURATION ms if output file does exist. Once it exists, this test assumes that LCS
	 * algorithm is done. Timeout after MAX_SLEEP_PERIODS sleeping periods.
	 * 
	 * @param configFile
	 *            config file to be used.
	 * @param outputFile
	 *            file where output is expected to be written.
	 * @param outputExpectedFile
	 *            file with expected output. outputFile must be equal to outputExpectedFile
	 *            afterwards.
	 * @param verifyOutput
	 *            if false outputFile is not verified (compared to outputExpectedFile).
	 * @return milliseconds of execution time on success. Resolution: SLEEP_DURATION ms
	 */
	private long testLcs(int configFile, int outputFile, int outputExpectedFile,
			boolean verifyOutput) {

		deleteFile(outputFile);

		System.out.println("*** Starting LCS algorithm for " + testFiles[configFile] + "...");

		long startTime = System.nanoTime();
		long elapsedTime = Long.MAX_VALUE;
		LCSApp.main(new String[] { testFiles[configFile] });

		int sleepPeriods = MAX_SLEEP_PERIODS;
		try {
			File output = new File(testFiles[outputFile]);
			while (!output.exists()) {
				if (sleepPeriods == 0) {
					fail("LCS algorithm took too long. Output file " + testFiles[outputFile]
							+ " still does not exist.");
				}
				Thread.sleep(SLEEP_DURATION);
				sleepPeriods--;
			}
			// output now exists. LCS algorithm is done.
			elapsedTime = (System.nanoTime() - startTime) / NANOSECS_PER_MS;
			System.out.println("*** LCS algorithm for " + testFiles[configFile] + " done in "
					+ elapsedTime + "ms.");
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		if (verifyOutput) {
			assertTrue("output file " + testFiles[outputFile] + " does not equal expected output "
					+ testFiles[outputExpectedFile] + ".",
					compareFiles(testFiles[outputFile], testFiles[outputExpectedFile]));
		}

		return elapsedTime;
	}

	/**
	 * Compares two files. Ignoring spaces at end of lines and of files.
	 * 
	 * @param file1
	 *            first file to compare with
	 * @param file2
	 *            this other file.
	 * @return true if files are identical.
	 */
	private static boolean compareFiles(String file1, String file2) {

		boolean same = true;
		try {
			File f1 = new File(file1);
			File f2 = new File(file2);

			FileReader fR1 = new FileReader(f1);
			FileReader fR2 = new FileReader(f2);

			BufferedReader reader1 = new BufferedReader(fR1);
			BufferedReader reader2 = new BufferedReader(fR2);

			String line1 = reader1.readLine();
			String line2 = reader2.readLine();

			while ((line1 != null) && (line2 != null)) {
				if (!line1.trim().equalsIgnoreCase(line2.trim())) {
					same = false;
					break;
				}

				line1 = reader1.readLine();
				line2 = reader2.readLine();

			}
			reader1.close();
			reader2.close();

		} catch (Exception e) {
			fail(e.getMessage());
			e.printStackTrace();
			return false;
		}

		return same;

	}

	/**
	 * Delete file.
	 * 
	 * @param file
	 *            file to be deleted.
	 */
	private void deleteFile(int file) {
		File output = new File(testFiles[file]);
		if (output.exists()) {
			output.delete();
		}
	}
}
