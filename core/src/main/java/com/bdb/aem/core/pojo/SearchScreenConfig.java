package com.bdb.aem.core.pojo;

import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

/**
 * The Class SearchScreenConfig.
 */
public class SearchScreenConfig {

	/** The get synonym results. */
	@SerializedName("getSynonymResults")
	private JsonElement getSynonymResults;

	/** The delete synonym. */
	@SerializedName("deleteSynonym")
	private JsonElement deleteSynonym;

	/** The create synonym. */
	@SerializedName("createSynonym")
	private JsonElement createSynonym;

	/** The get stop word results. */
	@SerializedName("getStopWordResults")
	private JsonElement getStopWordResults;

	/** The create stop word. */
	@SerializedName("createStopWord")
	private JsonElement createStopWord;

	/** The remove selected stop word. */
	@SerializedName("removeSelectedStopWord")
	private JsonElement removeSelectedStopWord;

	/**
	 * Instantiates a new synonym screen config.
	 *
	 * @param getSynonymResults the get synonym results
	 * @param deleteSynonym the delete synonym
	 * @param createSynonym the create synonym
	 * @param getStopWordResults the get stop word results
	 * @param createStopWord the create stop word
	 * @param removeSelectedStopWord the remove selected stop word
	 */
	public SearchScreenConfig(JsonElement getSynonymResults, JsonElement deleteSynonym,
							  JsonElement createSynonym, JsonElement getStopWordResults,
							  JsonElement createStopWord, JsonElement removeSelectedStopWord) {

		this.getSynonymResults = getSynonymResults;
		this.deleteSynonym = deleteSynonym;
		this.createSynonym = createSynonym;
		this.getStopWordResults = getStopWordResults;
		this.createStopWord = createStopWord;
		this.removeSelectedStopWord = removeSelectedStopWord;
	}

	/**
	 * Gets the gets the synonym results.
	 *
	 * @return the gets the synonym results
	 */
	public JsonElement getGetSynonymResults() {
		return getSynonymResults;
	}

	/**
	 * Gets the delete synonym.
	 *
	 * @return the delete synonym
	 */
	public JsonElement getDeleteSynonym() {
		return deleteSynonym;
	}

	/**
	 * Gets the creates the synonym.
	 *
	 * @return the creates the synonym
	 */
	public JsonElement getCreateSynonym() {
		return createSynonym;
	}

	/**
	 * Gets the gets the stop word results.
	 *
	 * @return the gets the stop word results
	 */
	public JsonElement getGetStopWordResults() {
		return getStopWordResults;
	}

	/**
	 * Gets the creates the stop word.
	 *
	 * @return the creates the stop word
	 */
	public JsonElement getCreateStopWord() {
		return createStopWord;
	}

	/**
	 * Gets the removes the selected stop word.
	 *
	 * @return the removes the selected stop word
	 */
	public JsonElement getRemoveSelectedStopWord() {
		return removeSelectedStopWord;
	}

}