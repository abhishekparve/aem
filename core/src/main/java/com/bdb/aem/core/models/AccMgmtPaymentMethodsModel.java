package com.bdb.aem.core.models;

/**
 * The Interface AccMgmtPaymentMethodsModel.
 */
public interface AccMgmtPaymentMethodsModel {

	/**
	 * This method returns the labels related to payment Methods menu
	 * in account management page
	 * @return paymentsMethodsLabels
	 */
    String getPaymentsMethodsLabels();

	/**
	 * This method returns the configs related to payment Methods menu
	 * in account management page
	 * @return paymentsMethodsConfig
	 */
	String getPaymentsMethodsConfig();
}