package com.bdb.aem.core.models;

public interface BDBBrightcoveVideo {

	String getVideoId();

	String getBrightcoveAccountId();

	String getBrightcovePlayerId();
	
	String getResolution();
	
	String getTitle();
	
	String getTitleSize();
	
	String getSubTitle();
	
	String getDescription();
	
	String getCaption();
	
	String getTogglePaddingBottom();
	
	String getTogglePaddingTop();

	String getBorder();
	
	String getTextAlignment();

	String getVideoAlignment();
	
	/**
	 * 
	 * @return backgroundcolor
	 */
	String getBackgroundColor();
}
