package com.bdb.aem.core.models;

public interface BDBDatalayerDataModel {

	/**
	 * Returns Page Path
	 * 
	 * @return Page path
	 */

	String getPath();
	
	/**
	 * Returns Page Path with query string
	 * 
	 * @return Page pathQueryString
	 */
	String getPathQueryString();

	/**
	 * Returns Page Name
	 * 
	 * @return Page Name
	 */

	String getName();

	/**
	 * Returns Page Title
	 * 
	 * @return Page title
	 */

	String getTitle();
	
	/**
	 * Returns the web/punchOut platform value
	 * 
	 * @return the platform
	 */
	String getPlatform();

	/**
	 * Returns Page Template
	 * 
	 * @return Page template
	 */

	String getTemplate();

	/**
	 * Returns Site section
	 * 
	 * @return Site section
	 */

	String getSiteSection();

	/**
	 * Returns Error Page Type
	 *
	 * @return pageType
	 */
	String getPageType();
	
	/**
	 * Returns the page replication date
	 * 
	 * @return replicationDate
	 */
	String getReplicationDate();
	
	/**
	 * Returns the page created date
	 * 
	 * @return createdDate
	 */
	public String getOriginalPublishDate();
	
	/**
	 * Returns Current time stamp
	 * 
	 * @return timeStamp
	 */
	String getTimestamp();
	
	/**
	 * Returns AEM run modes
	 * 
	 * @return runModes
	 */
	String getRunModes();
	
	/**
	 * Returns the previous referrer page
	 * 
	 * @return referrer
	 */
	String getPreviousPage();

	/**
	 * Returns AEM version
	 * 
	 * @return version
	 */
	String getVersion();

	/**
	 * Returns Author ID
	 * 
	 * @return authorId
	 */
	String getAuthorId();

	/**
	 * Returns if the AEM instance is author or publish
	 * 
	 * @return author/publish
	 */
	String checkAuthorVsPublish();
	
	/**
	 * Returns the internal campaign value from the URL
	 * 
	 * @return intCampaign
	 */
	String getIntCampaign();
	
	/**
	 * Returns the currentPage country code.
	 * 
	 * @return region
	 */
	String getRegion();
	
	/**
	 * check if the page is a blog or not.
	 * 
	 * @return isBlogPage
	 */
	Boolean isBlogPage();
	
	/**
	 * Returns the external campaign value from the URL
	 * 
	 * @return extCampaign
	 */
	String getExtCampaign();

	/**
	 * Returns the topic for the blog.
	 * 
	 * @return blogTopicFilterUsed
	 */
	String getBlogTopicFilterUsed();

	/**
	 * Returns the date for the blog.
	 * 
	 * @return blogDateFilterUsed
	 */
	String getBlogDateFilterUsed();
	
	/**
	 * Returns the publish date for the blog.
	 * 
	 * @return blogArticlePublishDate
	 */
	String getBlogArticlePublishDate();
	
	/**
	 * Returns the title for the blog.
	 * 
	 * @return blogArticleTitle
	 */
	String getBlogArticleTitle();

	/**
	 * Returns the login status for user.
	 * 
	 * @return userLoginStatus
	 */
	String getUserloginStatus();

	/**
	 * Returns the scroll depth for last page.
	 * 
	 * @return scrollDepth
	 */
	String getScrollDepth();

	/**
	 * Returns the CCV2 user ID for logged in user.
	 * 
	 * @return ccv2UserId
	 */
	String getCcv2UserID();
	
	/**
	 * Returns Content Page Type
	 *
	 * @return contentPageType
	 */
	String getContentPageType();

}