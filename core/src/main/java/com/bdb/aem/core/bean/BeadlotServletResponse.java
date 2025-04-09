package com.bdb.aem.core.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * The Class BeadlotServletResponse.
 */
public class BeadlotServletResponse {

	/** The readlot structure update response. */
	@SerializedName("response")
	@Expose
	private BeadlotStructureUpdateResponse readlotStructureUpdateResponse;

	/**
	 * Gets the readlot structure update response.
	 *
	 * @return the readlot structure update response
	 */
	public BeadlotStructureUpdateResponse getReadlotStructureUpdateResponse() {
		return readlotStructureUpdateResponse;
	}

	/**
	 * Sets the readlot structure update response.
	 *
	 * @param readlotStructureUpdateResponse the new readlot structure update response
	 */
	public void setReadlotStructureUpdateResponse(BeadlotStructureUpdateResponse readlotStructureUpdateResponse) {
		this.readlotStructureUpdateResponse = readlotStructureUpdateResponse;
	}
}