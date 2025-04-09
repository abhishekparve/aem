package com.bdb.aem.core.bean;

import com.google.gson.JsonElement;

/**
 * The Class CreateSynonymBean.
 */
public class CreateSynonymBean {

	private String from;

	private String to;
	
	private String language;
	
	private String synonym;

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSynonym() {
		return synonym;
	}

	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}

	public String[] getTo() {
		return to.split(",");
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

}
