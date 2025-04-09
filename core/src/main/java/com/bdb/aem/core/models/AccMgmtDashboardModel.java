package com.bdb.aem.core.models;

/**
 * The Interface AccMgmtDashboardModel.
 */
public interface AccMgmtDashboardModel {

	/**
	 * This method returns the labels related to dashboard menu
	 * in account management page
	 * @return dashboardLabels
	 */
    String getDashboardLabels();

    /**
			* This method returns the configs related to dashboard menu
	 * in account management page
	 * @return dashboardConfig
	 */
	String getDashboardConfig();

	/**
	 * This method returns the configs related to dashboard menu
	 * in account management page
	 * @return purchase account config
	 */
	String getPurchaseAccountConfig();
}