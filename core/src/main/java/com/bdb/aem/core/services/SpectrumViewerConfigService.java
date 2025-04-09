package com.bdb.aem.core.services;

/**
 * The Interface SpectrumViewerConfigService.
 */
public interface SpectrumViewerConfigService {

	/**
	 * Gets the recommended config.
	 *
	 * @return the recommended configs
	 */
	public String getSpectrumViewerIconsPath();
	public String getSpectrumViewerLaserList();
	public String getSvLaserList();
	public String getSvLaserListForSorting();
	public String getFluoroAltColorCode();
	public String getSvFluorochromesSortOrder();
	public String getCytometerConfigFile();
	public String getFluorochromeConfigFile();
	public String getSystemCytometerEndpoint();
	public String getSystemSsmdataEndpoint();
	public String getSystemFluorochromeEndpoint();
	public String getSpectrumViewerEncryptionKey();

}
