package com.ltse.orders.test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.ltse.orders.data.LTSEDataRepository;
import com.ltse.orders.exceptions.PropertiesInitializatonException;

/**
 * Tests that the applications reference data can be properly loaded
 * 
 * @author Alex De Leon
 *
 */
class LTSEDataRepositoryTest {

	@Test
	void testLoadApplicationReferenceData() {
		try {
			new LTSEDataRepository();
		} catch (PropertiesInitializatonException e) {
			fail("Application reference data could not be loaded.");
		}
	}

}
