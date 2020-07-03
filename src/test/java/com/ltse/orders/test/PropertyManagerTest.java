package com.ltse.orders.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ltse.orders.exceptions.PropertiesInitializatonException;
import com.ltse.orders.util.PropertyManager;
/**
 * Tests that the application properties can properly load
 * @author Alex De Leon
 *
 */
class PropertyManagerTest {

	@Test
	void testLoadApplicationProperties() {
		try {
			PropertyManager.getInstance();
		} catch (PropertiesInitializatonException e) {
			fail("Application properties could not be loaded.");
		}
	}

}
