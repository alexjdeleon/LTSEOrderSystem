package com.ltse.orders;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ltse.orders.data.LTSEDataRepository;
import com.ltse.orders.exceptions.PropertiesInitializatonException;
import com.ltse.orders.model.Trade;
import com.ltse.orders.util.PropertyManager;
import com.ltse.orders.util.StringUtils;
import com.ltse.orders.util.TradeFileOutputUtil;
import com.ltse.orders.util.TradeInputMapper;

/**
 * Trade order processor which reads in and processes trades in accordance to
 * the system requirements
 * 
 * @author Alex De Leon
 */
public class LTSEOrderProcessor {

	private PropertyManager propManager = PropertyManager.getInstance();
	private LTSEDataRepository dataRepository = new LTSEDataRepository();
	private String tradeCsvPath;

	// Trade lists
	private List<Trade> currentTrades;
	private List<Trade> acceptedTrades = new ArrayList<>();
	private List<Trade> rejectedTrades = new ArrayList<>();

	// A map of the encountered brokers and encountered sequence ids for said
	// brokers
	private Map<String, List<String>> brokerSequenceIdMap = new HashMap<>();

	// Stores a counter for the amount of trades encountered for a given minute in
	// time for a given broker
	private Map<String, Integer> tradeCountPerBrokerPerMinute = new HashMap<>();

	/**
	 * 
	 * @param tradeCsvPath file to be processed
	 * @throws PropertiesInitializatonException
	 */
	public LTSEOrderProcessor(String tradeCsvPath) throws PropertiesInitializatonException {
		this.tradeCsvPath = Paths.get(tradeCsvPath).toString();
	}

	/**
	 * Processes the list of orders that is read from a file
	 * 
	 * @throws IOException
	 */
	public void processTradeOrders() throws IOException {
		currentTrades = TradeInputMapper.mapInputTransactions(tradeCsvPath);
		System.out.println("Processing trades...");
		currentTrades.forEach(t -> {
			// Reject if not all required fields are present
			if (!tradeFieldsAreValid(t)) {
				rejectWithMessage(t, "One or more of the required fields are missing.");
			}
			// Reject if the symbol being traded is not part of the exchange
			else if (!dataRepository.isTradedSymbol(t.getSymbol())) {
				rejectWithMessage(t, "This trade's symbol is not part of the given exchange");
			}
			// Reject if the broker trading is not part of the system
			else if (!dataRepository.isValidFirm(t.getBroker())) {
				rejectWithMessage(t, "This trade's broker is not in the system.");
			}
			// Reject if the trader's sequence id has already been encountered for the given
			// broker
			else if (!sequenceIdUniqueForBroker(t)) {
				rejectWithMessage(t, "The given sequence id has already been processed for the given broker.");
			}
			// Reject if the current trade is the 4th or more in the same minute for the
			// same broker
			else if (brokerTradeLimitExceeded(t)) {
				rejectWithMessage(t, "This trade exceeds the 3 trades per broker per minute limit.");
			} else {
				t.setAccepted(true);
				acceptedTrades.add(t);
			}
		});
		System.out.println("Finished processing with " + acceptedTrades.size() + " accepted trades and "
				+ rejectedTrades.size() + " rejected trades.");
		// Write results to CSV files
		Boolean includeHeader = propManager.getBooleanProperty("data.output.include.header");
		String outputDirectory = propManager.getProperty("data.output.directory");
		TradeFileOutputUtil.writeTradeSummariesToOuputFile(acceptedTrades, outputDirectory + "/accepted_trades.csv",
				includeHeader);
		TradeFileOutputUtil.writeTradeSummariesToOuputFile(rejectedTrades, outputDirectory + "/rejected_trades.csv",
				includeHeader);

		if (propManager.getBooleanProperty("data.output.generate.json")) {
			// Write results to JSON files
			TradeFileOutputUtil.writeTradeListToJSONFile(acceptedTrades, outputDirectory + "/accepted_trades.json");
			TradeFileOutputUtil.writeTradeListToJSONFile(rejectedTrades, outputDirectory + "/rejected_trades.json");
		}
		System.out.println("Processing complete.");

	}

	/**
	 * Rejects a trade with a given message
	 * 
	 * @param t
	 * @param message
	 */
	private void rejectWithMessage(Trade t, String message) {
		t.setAccepted(false);
		t.setRejectionMessage(message);
		rejectedTrades.add(t);
	}

	/**
	 * Checks if the current broker has exceeded the 3 trades per minute rule
	 * 
	 * @param t
	 * @return
	 */
	private boolean brokerTradeLimitExceeded(Trade t) {
		// Represents the current minute in time of the transaction time stamp
		Instant transactionMinuteInTime = t.getTransactionTime().toInstant().truncatedTo(ChronoUnit.MINUTES);
		String brokerMinuteKey = t.getBroker() + "_" + transactionMinuteInTime.toString();
		Integer currentCount = tradeCountPerBrokerPerMinute.get(brokerMinuteKey);
		if (currentCount == null) {
			tradeCountPerBrokerPerMinute.put(brokerMinuteKey, 1);
		} else if (currentCount < 3) {
			tradeCountPerBrokerPerMinute.put(brokerMinuteKey, currentCount + 1);
		} else {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the current sequence ID is unique for the given broker
	 * 
	 * @param t
	 * @return true if the sequence id is unique for the given broker
	 */
	private boolean sequenceIdUniqueForBroker(Trade t) {
		String broker = t.getBroker();
		if (brokerSequenceIdMap.get(broker) == null) {
			List<String> sequenceIds = new ArrayList<>();
			sequenceIds.add(t.getSequenceId());
			brokerSequenceIdMap.put(broker, sequenceIds);
			return true;
		}

		List<String> sequenceIds = brokerSequenceIdMap.get(broker);
		if (sequenceIds.contains(t.getSequenceId()))
			return false;
		else {
			return sequenceIds.add(t.getSequenceId());
		}
	}

	/**
	 * Verifies that all required fields are present for a given Trade
	 * 
	 * @return
	 */
	private boolean tradeFieldsAreValid(Trade trade) {
		// @formatter:off
		return trade != null
				&& !StringUtils.isBlank(trade.getBroker()) 
				&& !StringUtils.isBlank(trade.getSymbol())
				&& !StringUtils.isBlank(trade.getType()) 
				&& !StringUtils.isBlank(trade.getSequenceId()) 
				&& trade.getQuantity() != null
				&& trade.getSide() != null 
				&& trade.getPrice() != null;
		// @formatter:on
	}
}
