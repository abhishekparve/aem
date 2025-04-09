package com.bdb.aem.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.jcr.Session;


/**
 * Model to fetch technical tools details.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TechnicalToolsModel {

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(TechnicalToolsModel.class);

	/** The icon 1. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String icon1;

	/** The icon 2. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String icon2;

	/** The icon 3. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String icon3;

	/** The link 1. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String link1;

	/** The link 2. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String link2;

	/** The link 3. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String link3;

	/** The text 1. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String text1;

	/** The text 2. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String text2;

	/** The text 3. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String text3;

	/** The dye name excel path. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String dyeNameExcelPath;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

	/** The request. */
	@Inject
	SlingHttpServletRequest request;

	/** The solr search service. */
	@Inject
	SolrSearchService solrSearchService;

	/** The display spectrum viewer tool. */
	private Boolean displaySpectrumViewerTool;

	@Inject
	Page currentPage;

	String country;

	/** The Constant XLSX_MIME_TYPE. */
	private static final String XLSX_MIME_TYPE = "application/vnd.ms-excel";

	/** The Constant XLS_MIME_TYPE. */
	private static final String XLS_MIME_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/**
	 * Inits the.
	 */
	@PostConstruct
	protected void init() {
		logger.debug("TechnicalToolsModel - Init method started");
		country = CommonHelper.getCountry(currentPage);
		String urlFormate = CommonHelper.getRegion(currentPage) + "/" + CommonHelper.getCountry(currentPage) + "/"
				+ CommonHelper.getLanguage(currentPage) + "-" + CommonHelper.getCountry(currentPage);
		logger.debug("urlFormate {} ", urlFormate);
		if (StringUtils.contains(link1, "language-masters/en"))
			link1 = link1.replace("language-masters/en", urlFormate);
		if (StringUtils.contains(link2, "language-masters/en"))
			link2 = link2.replace("language-masters/en", urlFormate);
		if (StringUtils.contains(link3, "language-masters/en"))
			link3 = link3.replace("language-masters/en", urlFormate);
		displaySpectrumViewerTool = getDisplaySpectrumViewer();
	}

	/**
	 * Gets the display spectrum viewer.
	 *
	 * @return the display spectrum viewer
	 */
	public Boolean getDisplaySpectrumViewer() {
		String catalogNo = CommonHelper.getSelectorDetails(request);
		if (null != catalogNo) {

			Resource hpBaseResource = null;
			if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
				String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
				hpBaseResource = resourceResolver.getResource(productVarHPPath);
				
			}
			if (null != hpBaseResource) {
				ValueMap hpProperty = hpBaseResource.adaptTo(ValueMap.class);
				return checkHPPropertyWithExcelData(hpProperty);
			}
		}
		return false;
	}

	/**
	 * Check HP property with excel data.
	 *
	 * @param hpProperty the hp property
	 * @return the boolean
	 */
	public Boolean checkHPPropertyWithExcelData(ValueMap hpProperty) {
		List<String> dyeNameExcelList = getDyeNameExcelList();
		String dyeName = StringUtils.EMPTY;
		if (hpProperty.containsKey(CommonConstants.BD_FORMAT) && !dyeNameExcelList.isEmpty()) {
			JsonArray formatDetailsJsonArray = new JsonParser()
					.parse(hpProperty.get(CommonConstants.BD_FORMAT).toString()).getAsJsonArray();
			for (JsonElement formatDetailsElement : formatDetailsJsonArray)
				dyeName = getFormatDetailJson(formatDetailsElement);
			if (null != dyeName && dyeNameExcelList.contains(dyeName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the format detail json.
	 *
	 * @param formatDetailsElement the format details element
	 * @return the format detail json
	 */
	public String getFormatDetailJson(JsonElement formatDetailsElement) {
		String formatname = StringUtils.EMPTY;
		JsonObject formatDetailsJson = formatDetailsElement.getAsJsonObject();
		if (formatDetailsJson.has(CommonConstants.DYE_NAME) && null != formatDetailsJson.get(CommonConstants.DYE_NAME)
				&& !formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString().isEmpty()) {
			formatname = formatDetailsJson.get(CommonConstants.DYE_NAME).getAsString();
		}
		return formatname;
	}

	/**
	 * Gets the dye name excel list.
	 *
	 * @return the dye name excel list
	 */
	public List<String> getDyeNameExcelList() {
		List<String> dyeNameExcelList = new ArrayList<>();
		try {
			if (null != dyeNameExcelPath) {
				Resource dyeNameExceResource = resourceResolver.getResource(dyeNameExcelPath);
				if (null != dyeNameExceResource) {
					Asset asset = dyeNameExceResource.adaptTo(Asset.class);
					if (null != asset && (asset.getMimeType().equals(XLSX_MIME_TYPE)
							|| asset.getMimeType().equals(XLS_MIME_TYPE))) {
						dyeNameExcelList = convertXLSXToList(asset.getOriginal().getStream());
					}
				}
			}
		} catch (IOException e) {

			logger.error("Error in getting Dyne Name Excel List {0}", e);
		}

		return dyeNameExcelList;
	}

	/**
	 * Convert XLSX to list.
	 *
	 * @param inputStream the input stream
	 * @return the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public List<String> convertXLSXToList(InputStream inputStream) throws IOException {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		Iterator<Row> itr = sheet.iterator();
		List<String> dyeNameExcelList = new ArrayList<>();
		while (itr.hasNext()) {
			Row row = itr.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				dyeNameExcelList.add(cell.getStringCellValue());
			}
		}
		return dyeNameExcelList;
	}

	/**
	 * Gets the icon 1.
	 *
	 * @return externalized icon1
	 */
	public String getIcon1() {
		return externalizerService.getFormattedUrl(icon1, resourceResolver);
	}

	/**
	 * Gets the icon 2.
	 *
	 * @return externalized icon2
	 */
	public String getIcon2() {
		return externalizerService.getFormattedUrl(icon2, resourceResolver);
	}

	/**
	 * Gets the icon 3.
	 *
	 * @return externalized icon3
	 */
	public String getIcon3() {
		return externalizerService.getFormattedUrl(icon3, resourceResolver);
	}

	/**
	 * Gets the link 1.
	 *
	 * @return externalized link1
	 */
	public String getLink1() {
		return externalizerService.getFormattedUrl(link1, resourceResolver);
	}

	/**
	 * Gets the link 2.
	 *
	 * @return externalized link2
	 */
	public String getLink2() {
		return externalizerService.getFormattedUrl(link2, resourceResolver);
	}

	/**
	 * Gets the link 3.
	 *
	 * @return externalized link3
	 */
	public String getLink3() {
		return externalizerService.getFormattedUrl(link3, resourceResolver);
	}

	/**
	 * Gets the text 1.
	 *
	 * @return text1
	 */
	public String getText1() {
		return text1;
	}

	/**
	 * Gets the text 2.
	 *
	 * @return text2
	 */
	public String getText2() {
		return text2;
	}

	/**
	 * Gets the text 3.
	 *
	 * @return text3
	 */
	public String getText3() {
		return text3;
	}

	/**
	 * Gets the display spectrum viewer tool.
	 *
	 * @return the display spectrum viewer tool
	 */
	public Boolean getDisplaySpectrumViewerTool() {
		return displaySpectrumViewerTool;
	}

}