package com.ltse.orders.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ltse.orders.model.Trade;

/**
 * Utility class used to output the processed trades to files
 * 
 * @author Alex De Leon
 *
 */
public class TradeFileOutputUtil {
	
	/**
	 * Writes the list of trade summaries (Broker and Sequence ID) to a file with
	 * the given name
	 * 
	 * @param trades to be written to a file
	 * @param fileName 
	 * @param includeHeader if true, header names are included in the output file
	 * @throws IOException 
	 */
	public static void writeTradeSummariesToOuputFile(List<Trade> trades, String fileName, boolean includeHeader) throws IOException {
		System.out.println("Writing output file - " + fileName);
		Path outputPath = Paths.get(fileName);
        Files.createDirectories(outputPath.getParent());
        
		try (PrintWriter writer = new PrintWriter(fileName)) {

			StringBuilder sb = new StringBuilder();
			// Append header
			if(includeHeader) {
		      sb.append("Broker,Sequence ID\n");
			}

			// Append trades
			trades.stream().forEach(t -> {
				sb.append(t.getBroker() + "," + t.getSequenceId());
				sb.append("\n");
			});

			writer.write(sb.toString());

		} 
	}

	/**
	 * Uses the GSon library to convert the trade transaction objects to JSON and
	 * outputs the given list to a file
	 * 
	 * @param trades
	 * @param fileName
	 * @throws IOException 
	 */
	public static void writeTradeListToJSONFile(List<Trade> trades, String fileName) throws IOException {
		System.out.println("Writing output file - " + fileName);
		Path outputPath = Paths.get(fileName);
        Files.createDirectories(outputPath.getParent());
        
		try (PrintWriter writer = new PrintWriter(new File(fileName))) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			StringBuilder sb = new StringBuilder();
			trades.stream().forEach(t -> {
				String tradeJson = gson.toJson(t);
				sb.append(tradeJson + "\n");
			});
			writer.write(sb.toString());
		}
	}
	
}
