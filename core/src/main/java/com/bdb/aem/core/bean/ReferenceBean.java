package com.bdb.aem.core.bean;

import lombok.Getter;

/**
 * The Class CitationBean.
 */
public class ReferenceBean implements Comparable {

	/** The HP reference ID. */
	private String HPReferenceID;
	
	/** The citation data. */
	private String citationdata ;
	
	/** The pub med id. */
	private String pubMedId;
	
	/** The pub med id value */
	@Getter
	private String pubMedIdValue;
	
	/**
	 * Gets the HP reference ID.
	 *
	 * @return the HP reference ID
	 */
	public String getHPReferenceID() {
		return HPReferenceID;
	}
	
	/**
	 * Gets the citation data.
	 *
	 * @return the citation data
	 */
	public String getCitationdata() {
		return citationdata;
	}
	
	/**
	 * Gets the pub med id.
	 *
	 * @return the pub med id
	 */
	public String getPubMedId() {
		return pubMedId;
	}

	@Override
	public int compareTo(Object o) {
		return this.getCitationdata().compareTo(((ReferenceBean) o).getCitationdata());
	}
	
}
