package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.SpectrumViewerConstants;
import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

/**
 * The Class SpectrumViewerConfigServiceImpl.
 */
@Component(immediate = true, service = SpectrumViewerConfigService.class)
@Designate(ocd = SpectrumViewerConfigServiceImpl.Configuration.class)
public class SpectrumViewerConfigServiceImpl implements SpectrumViewerConfigService {

	/** The SpectrumViewer secure key to encrypt/decrypt */
	private String spectrumViewerEncryptionKey;

	/** The SpectrumViewer Laser List. */
	private String	spectrumViewerLaserList;

	/** The SpectrumViewer Laser List. */
	private String	svLaserList;
	
	/** The SpectrumViewer Laser List For Sorting. */
	private String	svLaserListForSorting;
	
	/** The SpectrumViewer Fluoro Alt Color Code. */
	private String	fluoroAltColorCode;

	/** The SpectrumViewer Fluorochrome Sorting List. */
	private String	svFluorochromesSortOrder;

	/** The cytometer config file endpoint. */
	private String cytometerConfigFile;
	
	/** The fluorochrome config file endpoint. */
	private String fluorochromeConfigFile;

	/** The SV Icons config file endpoint. */
	private String	spectrumViewerIconsPath;

	/** The system Cytometer API endpoint. */
	private String systemCytometerEndpoint;

	/** The system Fluorochrome API endpoint. */
	private String systemFluorochromeEndpoint;

	/** The system SSM data API endpoint. */
	private String systemSsmdataEndpoint;

	/**
	 * Activate.
	 *
	 * @param config the config
	 */
	@Activate
	@Modified
	protected final void activate(Configuration config) {
		this.spectrumViewerIconsPath = config.spectrumViewerIconsPath();
		this.spectrumViewerEncryptionKey = config.spectrumViewerEncryptionKey();
		this.spectrumViewerLaserList = config.spectrumViewerLaserList();
		this.svLaserList = config.svLaserList();
		this.svLaserListForSorting = config.svLaserListForSorting();
		this.fluoroAltColorCode = config.fluoroAltColorCode();
		this.svFluorochromesSortOrder = config.svFluorochromesSortOrder();
		this.cytometerConfigFile= config.cytometerConfigFile();
		this.fluorochromeConfigFile= config.fluorochromeConfigFile();
		this.systemCytometerEndpoint = config.systemCytometerEndpoint();
		this.systemFluorochromeEndpoint = config.systemFluorochromeEndpoint();
		this.systemSsmdataEndpoint = config.systemSsmdataEndpoint();
	}

	/**
	 * Deactivate.
	 */
	@Deactivate
	protected void deactivate() {
		// DoNothing
	}

	/**
	 * Gets the spectrumViewerEncryptionKey.
	 *
	 * @return the spectrumViewerEncryptionKey
	 */
	@Override
	public String getSpectrumViewerEncryptionKey() {
		return spectrumViewerEncryptionKey;
	}

	/**
	 * Gets the cytometerConfigFile.
	 *
	 * @return the cytometerConfigFile
	 */
	@Override
	public String getCytometerConfigFile() {

		return cytometerConfigFile;
	}
	
	/**
	 * Gets the fluorochromeConfigFile.
	 *
	 * @return the fluorochromeConfigFile
	 */
	@Override
	public String getFluorochromeConfigFile() {

		return fluorochromeConfigFile;
	}

	@Override
	public String getSystemCytometerEndpoint() {
		return systemCytometerEndpoint;
	}

	@Override
	public String getSystemSsmdataEndpoint() {
		return systemSsmdataEndpoint;
	}

	@Override
	public String getSystemFluorochromeEndpoint() {
		return systemFluorochromeEndpoint;
	}

	/**
	 * Gets the svLaserList.
	 *
	 * @return the svLaserList
	 */
	@Override
	public String getSvLaserList() {

		return svLaserList;
	}

	/**
	 * Gets the spectrumViewerLaserList.
	 *
	 * @return the spectrumViewerLaserList
	 */
	@Override
	public String getSpectrumViewerLaserList() {

		return spectrumViewerLaserList;
	}

	/**
	 * @return the svLaserListForSorting
	 */
	@Override
	public String getSvLaserListForSorting() {
		return svLaserListForSorting;
	}

	/**
	 * @return the fluoroAltColorCode
	 */
	@Override
	public String getFluoroAltColorCode() {
		return fluoroAltColorCode;
	}

	/**
	 * Gets the svFluorochromesSortOrder.
	 *
	 * @return the svFluorochromesSortOrder
	 */
	@Override
	public String getSvFluorochromesSortOrder() {
		return svFluorochromesSortOrder;
	}

	/**
	 * Gets the spectrumViewerIconsPath.
	 *
	 * @return the spectrumViewerIconsPath
	 */
	@Override
	public String getSpectrumViewerIconsPath() {
		return spectrumViewerIconsPath;
	}

	/**
	 * The Interface Configuration.
	 */
	@ObjectClassDefinition(name = "Spectrum Viewer Configurations")
	public @interface Configuration {

		/**
		 * Base Folder path for all Icons of SV Config to display in Icon-Picker.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "spectrumViewerIconsPath", description = "Parent folder Path for Icons to display in IconPicker in all dropdown", type = AttributeType.STRING)
		public String spectrumViewerIconsPath() default (SpectrumViewerConstants.BASE_RESOURCES_ICONS_PATH);

		/**
		 * File name of Cytometer System Config.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "cytometerConfigFile", description = "The file name of System cytometer config json (relative path to spectrumViewerConfigFile)", type = AttributeType.STRING)
		public String cytometerConfigFile() default SpectrumViewerConstants.SV_SYS_CYTOMETER_JSON_FILE_PATH;

		/**
		 * File name of fluorochrome System Config .
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fluorochromeConfigFile", description = "The file name for System fluorochrome json (relative path to spectrumViewerConfigFile)", type = AttributeType.STRING)
		public String fluorochromeConfigFile() default SpectrumViewerConstants.SV_SYS_FLUOROCHROME_JSON_FILE_PATH;
		
		/**
		 * Default SV Laser List.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "svLaserList", description = "The List of Lasers with their ID, label , min and max value for SpectrumViewer (in json format)", type = AttributeType.STRING)
		public String svLaserList() default SpectrumViewerConstants.LASER_LIST_DEFAULT;
		
		/**
		 * Default SV Laser List For Sorting.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "svLaserListForSorting", description = "The List of Lasers with their ID, label , min and max value for SpectrumViewer (in json format)", type = AttributeType.STRING)
		public String svLaserListForSorting() default SpectrumViewerConstants.LASER_LIST_DEFAULT;
		
		/**
		 * Default Fluoro Alt Color Code.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "fluoroAltColorCode", description = "The List of Lasers with their ID, label , min and max value for SpectrumViewer (in json format)", type = AttributeType.STRING)
		public String fluoroAltColorCode() default SpectrumViewerConstants.LASER_LIST_DEFAULT;

		/**
		 * Default Laser List.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "spectrumViewerLaserList", description = "The List of Lasers with their ID, label , min and max value for SpectrumViewer (in json format)", type = AttributeType.STRING)
		public String spectrumViewerLaserList() default SpectrumViewerConstants.LASER_LIST_DEFAULT;

		/**
		 * Fluorochromes Sort Order List.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "svFluorochromesSortOrder", description = "The List of Fluorochromes with their name, short_name, full_name, common_name and sort_order value for SpectrumViewer (in json format)", type = AttributeType.STRING)
		public String svFluorochromesSortOrder() default "/content/dam/bdb/sv/sys/2023-03-08-11-22/svFluorochromesSortOrder.json";

		/**
		 * SV Encryption Key.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "spectrumViewerEncryptionKey", description = "Secure key for Encryption/Decryption of SV System data (Please enter 16 bytes key only and avoid whitespace)", type = AttributeType.STRING)
		public String spectrumViewerEncryptionKey() default StringUtils.EMPTY;

		/**
		 * SV System Cytometer API EndPoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "systemCytometerEndpoint", description = "End point to get the SV system Cytometer data", type = AttributeType.STRING)
		public String systemCytometerEndpoint() default SpectrumViewerConstants.SV_SYS_DEFAULT_CYTOMETER_ENDPOINT;

		/**
		 * SV System fluorochromes API EndPoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "systemFluorochromeEndpoint", description = "End point to get the SV system Fluorochromes data", type = AttributeType.STRING)
		public String systemFluorochromeEndpoint() default SpectrumViewerConstants.SV_SYS_DEFAULT_FLUOROCHROME_ENDPOINT;

		/**
		 * SV System SSM data API EndPoint.
		 *
		 * @return the string
		 */
		@AttributeDefinition(name = "systemSsmdataEndpoint", description = "End point to get the SV system SSM data for instrument Config", type = AttributeType.STRING)
		public String systemSsmdataEndpoint() default SpectrumViewerConstants.SV_SYS_DEFAULT_SSM_ENDPOINT;
	}

}
