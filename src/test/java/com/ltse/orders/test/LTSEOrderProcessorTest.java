package com.ltse.orders.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

import com.ltse.orders.LTSEOrderProcessor;
import com.ltse.orders.exceptions.PropertiesInitializatonException;

/**
 * Tests the file generation utility
 * 
 * @author Alex De Leon
 *
 */
class LTSEOrderProcessorTest {

	private LTSEOrderProcessor orderProcessor;
	private String testDataDirectory = "src/main/resources/test_data/";
	
	@Test
	void testMultipleRejectedOrders() throws PropertiesInitializatonException, IOException {
		
		String testFileName = testDataDirectory + "multiple_orders_rejected_in.csv";
		
		orderProcessor = new LTSEOrderProcessor(testFileName);
		orderProcessor.processTradeOrders();
		
		// Compare accepted order files
		String expectedAcceptedOutputFileName = "/test_data/multiple_orders_accepted_out.csv";
		String acceptedOutputFileName = "output/accepted_trades.csv";
		if (!filesAreEqual(expectedAcceptedOutputFileName, acceptedOutputFileName)) {
			fail("The output file does not match the reference data.");
		}
		
		// Compare rejected order files
		String expectedRejectedOutputFileName = "/test_data/multiple_orders_rejected_out.csv";
		String rejectedOutputFileName = "output/rejected_trades.csv";
		if (!filesAreEqual(expectedRejectedOutputFileName, rejectedOutputFileName)) {
			fail("The output file does not match the reference data.");
		}
	}
	
	@Test
	void testAllOrdersAccepted() throws PropertiesInitializatonException, IOException {
		
		String testFileName = testDataDirectory + "all_orders_accepted_in.csv";
		
		String expectedOutputFileName = "/test_data/all_orders_accepted_out.csv";
		String outputFileName = "output/accepted_trades.csv";

		orderProcessor = new LTSEOrderProcessor(testFileName);
		orderProcessor.processTradeOrders();

		if (!filesAreEqual(expectedOutputFileName, outputFileName)) {
			fail("The output file does not match the reference data.");
		}
	}
	
	@Test
	void testValidJSON() throws PropertiesInitializatonException, IOException {
		
		String testFileName = testDataDirectory + "all_orders_accepted_in.csv";
		
		String expectedOutputFileName = "/test_data/valid_json_file.json";
		String outputFileName = "output/accepted_trades.json";

		orderProcessor = new LTSEOrderProcessor(testFileName);
		orderProcessor.processTradeOrders();

		if (!filesAreEqual(expectedOutputFileName, outputFileName)) {
			fail("The output file does not match the reference data.");
		}
	}

	/**
	 * Compares the two files line by line and returns true if the contents are
	 * equal
	 * 
	 * @param testFile
	 * @param outputFileName
	 * @return
	 * @throws IOException
	 */
	boolean filesAreEqual(String testFile, String outputFileName) throws IOException {

		InputStream testFileInputStream = LTSEOrderProcessorTest.class.getResourceAsStream(testFile);
		File outputFile = new File(outputFileName);
		InputStream outputFileInputStream = new FileInputStream(outputFile);

		try (BufferedReader outputFileReader = new BufferedReader(new InputStreamReader(outputFileInputStream));
				BufferedReader testFileReader = new BufferedReader(new InputStreamReader(testFileInputStream))) {

			String testFileLine = testFileReader.readLine();
			String outputFileLine = outputFileReader.readLine();
			while (testFileLine != null) {
				if (!testFileLine.equals(outputFileLine)) {
					return false;
				}
				testFileLine = testFileReader.readLine();
				outputFileLine = outputFileReader.readLine();
			}
		}

		return true;
	}

}
