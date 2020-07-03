package com.ltse.orders.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
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

	@Test
	void testAllOrdersAccepted() throws PropertiesInitializatonException, IOException {
		String testFileName = "/test_data/all_orders_accepted_input.csv";
		String expectedOutputFileName = "/test_data/all_orders_accepted_output.csv";
		String outputFileName = "/accepted_trades.csv";
		
		orderProcessor = new LTSEOrderProcessor(testFileName);
		orderProcessor.processTradeOrders();

		if (!filesAreEqual(expectedOutputFileName, outputFileName)) {
			fail("The output file does not match the reference data.");
		}
	}
	
	
	boolean filesAreEqual(String testFile, String outputFile) throws IOException {

		InputStream testFileInputStream = LTSEOrderProcessorTest.class.getResourceAsStream(testFile);
		BufferedReader testFileReader = new BufferedReader(new InputStreamReader(testFileInputStream));

		InputStream outputFileInputStream = LTSEOrderProcessorTest.class.getResourceAsStream(outputFile);
		BufferedReader outputFileReader = new BufferedReader(new InputStreamReader(outputFileInputStream));

		String line;
		while ((line = testFileReader.readLine()) != null) {
			if (line != outputFileReader.readLine()) {
				return false;
			}
		}
		return true;
	}

}
