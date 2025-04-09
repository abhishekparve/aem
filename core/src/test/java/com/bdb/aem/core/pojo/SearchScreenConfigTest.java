package com.bdb.aem.core.pojo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * The Class SearchScreenConfigTest.
 */
class SearchScreenConfigTest {

	/** The search screen config. */
	SearchScreenConfig searchScreenConfig;
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		JsonElement getSynonymResults = new JsonObject();
		JsonElement deleteSynonym = new JsonObject();
		JsonElement createSynonym = new JsonObject();
		JsonElement getStopWordResults = new JsonObject();
		JsonElement createStopWord = new JsonObject();
		JsonElement removeSelectedStopWord = new JsonObject();
		searchScreenConfig = new SearchScreenConfig(
				getSynonymResults,
				deleteSynonym,
				createSynonym,
				getStopWordResults,
				createStopWord,
				removeSelectedStopWord);
	}

	/**
	 * Test.
	 */
	@Test
	void test() {
		assertNotNull(searchScreenConfig.getGetSynonymResults());
		assertNotNull(searchScreenConfig.getDeleteSynonym());
		assertNotNull(searchScreenConfig.getCreateSynonym());
		assertNotNull(searchScreenConfig.getGetStopWordResults());
		assertNotNull(searchScreenConfig.getCreateStopWord());
		assertNotNull(searchScreenConfig.getRemoveSelectedStopWord());
	}
}
