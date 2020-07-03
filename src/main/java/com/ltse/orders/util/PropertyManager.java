package com.ltse.orders.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ltse.orders.data.LTSEDataRepository;
import com.ltse.orders.exceptions.PropertiesInitializatonException;

/**
 * Singleton property manager for the LTSE Order System
 * 
 * @author Alex De Leon
 *
 */
public class PropertyManager {

	private static String APPLICATION_PROPERTIES_FILENAME = "/application.properties";

	private static PropertyManager propertyManager;

	private Properties applicationProperties;

	/**
	 * Reads in the application properties and initializes the property manager
	 * 
	 * @throws PropertiesInitializatonException
	 */
	private PropertyManager() throws PropertiesInitializatonException {
		try (InputStream input = LTSEDataRepository.class.getResourceAsStream(APPLICATION_PROPERTIES_FILENAME);) {
			applicationProperties = new Properties();
			applicationProperties.load(input);
		} catch (IOException ex) {
			throw new PropertiesInitializatonException();
		}
	}

	/**
	 * Returns the singleton instance of the application's property manager
	 * 
	 * @return
	 * @throws PropertiesInitializatonException if the application properties cannot
	 *                                          be found or loaded
	 */
	public static PropertyManager getInstance() throws PropertiesInitializatonException {
		if (propertyManager == null) {
			synchronized (PropertyManager.class) {
				// Double check to ensure atomic initialization
				if (propertyManager == null)
					propertyManager = new PropertyManager();
			}
		}
		return propertyManager;
	}

	/**
	 * @param key for the property value to be returned
	 * @return the value associated with the given key
	 */
	public String getProperty(String key) {
		return (String) applicationProperties.get(key);
	}

	/**
	 * @param key for the boolean property value to be returned
	 * @return the value associated with the given key
	 */
	public boolean getBooleanProperty(String key) {
		return Boolean.parseBoolean((String)applicationProperties.get(key));
	}
	
}