package com.ltse.orders;

import java.io.IOException;

import com.ltse.orders.exceptions.PropertiesInitializatonException;
import com.ltse.orders.util.StringUtils;

/**
 * A Trade order processing system developed in accordance to the following
 * requirements:
 * <ul>
 * <li>Only orders that have values for the fields of 'broker', 'symbol',
 * 'type', 'quantity', 'sequence id', 'side', and 'price' should be
 * accepted.</li>
 * <li>Only orders for symbols actually traded on the exchange should be
 * accepted</li>
 * <li>Each broker may only submit three orders per minute: any additional
 * orders in should be rejected</li>
 * <li>Within a single broker's trades ids must be unique. If ids repeat for the
 * same broker, only the first message with a given id should be accepted.</li>
 * </ul>
 * The output should be two files with lists of the broker and id of accepted
 * and rejected orders, in the order in which the orders were sent. If you
 * complete that, there is also an extra credit: Pick a message format you would
 * use to pass information to another system you were writing, and in addition
 * to the two files with lists of broker names and ids, print out two more
 * files: one with all the accepted orders and one with all the rejected orders
 * 
 * @author Alex De Leon
 *
 */
public class LTSEOrderSystem {

	private static final String DEFAULT_INPUT_FILE_NAME = "trades.csv";

	/**
	 * 
	 * @param args the first argument should contain the name of the file containing
	 *             the trades to be processed
	 */
	public static void main(String... args) {
		String inputFileName;
		if (args.length > 0 && !StringUtils.isBlank(args[0])) {
			inputFileName = args[0];
		} else {
			inputFileName = DEFAULT_INPUT_FILE_NAME;
		}

		LTSEOrderProcessor orderProcessor;
		try {
			orderProcessor = new LTSEOrderProcessor(inputFileName);
			orderProcessor.processTradeOrders();
		} catch (PropertiesInitializatonException e) {
			System.out.println(
					"The LTSE Order System could not start up because the properties file could not be found or is invalid.");
		} catch (IOException e) {
			System.out.println(
					"The LTSE Order system could not start because the given input file is invalid or does not exist.");
		}

	}

}
