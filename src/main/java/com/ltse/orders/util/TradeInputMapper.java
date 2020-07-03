package com.ltse.orders.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ltse.orders.model.Trade;
import com.ltse.orders.model.TradeSide;

/**
 * Mapping utility for the comma separated input file containing trade transactions.
 * 
 * @author Alex De Leon
 *
 */
public class TradeInputMapper {

	private static DateFormat transactionDateFormat = new SimpleDateFormat("MM/d/yyyy hh:mm:ss");

	/**
	 * Reads in the given input file, maps each line to a {@link Trade} object, and
	 * returns a list of all of the trades in the file
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static List<Trade> mapInputTransactions(String filePath) throws IOException {
		System.out.println("Reading input file - " + filePath);
		File inputFile = new File(filePath);
		InputStream inputFileStream = new FileInputStream(inputFile);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputFileStream));

		List<Trade> trades = new ArrayList<Trade>();
		// Skip the header, and map each line to a Trade object
		System.out.println("Mapping input...");
		trades = reader.lines().skip(1).map(TradeInputMapper::mapTrade).collect(Collectors.toList());
		reader.close();
		System.out.println("Finished mapping " + trades.size() + " trades for processing.");
		return trades;
	}

	/**
	 * Parses the comma separated input string and maps it to a {@link Trade} object
	 * 
	 * @param inputString
	 * @return
	 * @throws ParseException
	 */
	public static Trade mapTrade(String inputString) {
		String[] tradeLine = inputString.split(",");
		Trade trade = new Trade();
		// Transaction time
		if (!StringUtils.isBlank(tradeLine[0])) {
			try {
				trade.setTransactionTime(transactionDateFormat.parse(tradeLine[0]));
			} catch (ParseException e) {
				// Invalid date format found, leave null so that the trade can be rejected
			}
		}
		// Broker name
		if (!StringUtils.isBlank(tradeLine[1])) {
			trade.setBroker(tradeLine[1]);
		}
		// Sequence ID
		if (!StringUtils.isBlank(tradeLine[2])) {
			trade.setSequenceId(tradeLine[2]);
		}
		// Type
		if (!StringUtils.isBlank(tradeLine[3])) {
			trade.setType(tradeLine[3]);
		}
		// Symbol
		if (!StringUtils.isBlank(tradeLine[4])) {
			trade.setSymbol(tradeLine[4]);
		}
		// Quantity
		if (!StringUtils.isBlank(tradeLine[5])) {
			try {
				trade.setQuantity(Integer.parseInt(tradeLine[5]));
			} catch(NumberFormatException nfe) {
				// invalid quantity, leave null so that the trade can be rejected
			}
		}
		// Price
		if (!StringUtils.isBlank(tradeLine[6])) {
			try {
				trade.setPrice(Double.parseDouble(tradeLine[6]));
			} catch (NumberFormatException nfe) {
				// invalid price format, leave null so that the trade can be rejected
			}
		}
		// Side
		if (tradeLine.length > 7 && !StringUtils.isBlank(tradeLine[7])) {
			try {
				trade.setSide(TradeSide.valueOf(tradeLine[7]));
			} catch (IllegalArgumentException e) {
				// invalid side found, leave null so that the trade can be rejected
			}
		}

		return trade;

	}
}
