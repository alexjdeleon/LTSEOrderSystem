package com.ltse.orders.test;

import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

import com.ltse.orders.model.Trade;
import com.ltse.orders.model.TradeSide;
import com.ltse.orders.util.TradeInputMapper;
/**
 * Tests the trade input mapper
 * @author Alex De Leon
 *
 */
class TradeInputMapperTest {
	
	private static DateFormat transactionDateFormat = new SimpleDateFormat("MM/d/yyyy hh:mm:ss");

	@Test
	void testMapTrade() throws ParseException {
		String tradeToMap = "10/5/2017 10:00:00,Fidelity,1,2,BARK,100,1.195,Buy";
		Trade mappedTrade = TradeInputMapper.mapTrade(tradeToMap);
		assertTrue(mappedTrade.equals(createTestTrade()));
	}
	
	private Trade createTestTrade() throws ParseException {
		// Create a test trade
		Trade testTrade = new Trade();
		testTrade.setTransactionTime(transactionDateFormat.parse("10/5/2017 10:00:00"));
		testTrade.setBroker("Fidelity");
		testTrade.setSequenceId("1");
		testTrade.setType("2");
		testTrade.setSymbol("BARK");
		testTrade.setQuantity(100);
		testTrade.setPrice(1.195);
		testTrade.setSide(TradeSide.Buy);
		return testTrade;
	}


}
