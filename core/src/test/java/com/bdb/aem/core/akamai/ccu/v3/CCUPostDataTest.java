package com.bdb.aem.core.akamai.ccu.v3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Class CCUPostDataTest.
 *
 *
 */
public class CCUPostDataTest {

	/**
	 * Test testGetCCUPostData.
	 *
	 *
	 */
	@Test
	public void testGetCCUPostData() {
		CCUPostData ccuPostData = new CCUPostData();
		ccuPostData.setHostname("junitHost");
		ccuPostData.setObjects(new ArrayList<String>());
		ccuPostData.toJSON();
		ccuPostData.getHostname();
		ccuPostData.getObjects();
	}

}
