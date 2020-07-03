package com.ltse.orders.data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import com.ltse.orders.exceptions.PropertiesInitializatonException;
import com.ltse.orders.util.PropertyManager;

/**
 * Repository for the reference data used when processing the trade transactions
 * 
 * @author Alex De Leon
 *
 */
public class LTSEDataRepository {

	private PropertyManager propManager = PropertyManager.getInstance();
	private Set<String> firms;
	private Set<String> tradedSymbols;

	public LTSEDataRepository() throws PropertiesInitializatonException {
		initializeData();
	}

	/**
	 * Reads in the data for the firms and symbols and stores them internally
	 * 
	 * @throws PropertiesInitializatonException
	 */
	private void initializeData() {
		initializeFirms();
		initializeSymbols();
	}

	/**
	 * Reads in the firms file and stores the values in a Set
	 * 
	 * @throws PropertiesInitializatonException
	 */
	private void initializeFirms() {
		String firmsFile = propManager.getProperty("data.source.firms");

		InputStream firmsInputStream = LTSEDataRepository.class.getResourceAsStream(firmsFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(firmsInputStream));

		firms = new HashSet<String>();
		br.lines().forEach(f -> firms.add(f));

	}

	/**
	 * Reads in the symbols file and stores the values in a Set
	 * 
	 * @throws UnsupportedEncodingException
	 * @throws PropertiesInitializatonException
	 */
	private void initializeSymbols() {
		String symbolsFile = propManager.getProperty("data.source.symbols");

		InputStream symbolsInputStream = LTSEDataRepository.class.getResourceAsStream(symbolsFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(symbolsInputStream));

		tradedSymbols = new HashSet<String>();
		br.lines().forEach(s -> tradedSymbols.add(s));

	}

	/**
	 * @param symbol
	 * @return true if the given symbol is part of the stored symbols collection
	 */
	public boolean isTradedSymbol(String symbol) {
		return tradedSymbols.contains(symbol);
	}

	/**
	 * @param firm
	 * @return true if the given firm is part of the stored firm collection
	 */
	public boolean isValidFirm(String firm) {
		return firms.contains(firm);
	}

	public Set<String> getFirms() {
		return firms;
	}

	public Set<String> getTradedSymbols() {
		return tradedSymbols;
	}

}
