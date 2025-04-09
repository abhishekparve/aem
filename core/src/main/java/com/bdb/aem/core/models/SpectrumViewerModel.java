package com.bdb.aem.core.models;

import com.adobe.acs.commons.models.injectors.annotation.ChildResourceFromRequest;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.SVUtils;
import com.bdb.aem.core.util.SpectrumViewerConstants;
import com.day.cq.i18n.I18n;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.models.annotations.*;
import org.apache.sling.models.annotations.injectorspecific.InjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Class Spectrum Viewer Model.
 */
@Model(adaptables = {SlingHttpServletRequest.class, Resource.class}, resourceType = {
        SpectrumViewerModel.BDB_AEM_COMPONENTS_SPECTRUM_VIEWER}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json", options = {
        @ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true")})
public class SpectrumViewerModel {

    /**
     * The logger.
     */
    protected static final Logger logger = LoggerFactory.getLogger(SpectrumViewerModel.class);
    public static final String BDB_AEM_COMPONENTS_SPECTRUM_VIEWER = "bdb-aem/components/content/spectrumViewer/v1/spectrumViewer";
    private static final String UUID = "{{uuid}}";

    /**
     * The search path url
     */
    @Inject
    @Via("resource")
    public String searchPathUrl;

    @Inject
    @Via("resource")
    private String sortingOrder;

    /** The resolver factory. */
    @Inject
    @Via("resource")
    ResourceResolverFactory resolverFactory;
    @Self
    private SlingHttpServletRequest request;

    /** The Externalizer */
    @Inject
    protected ExternalizerService externalizer;

    @ScriptVariable
    private Page currentPage;
	/**
     * The SpectrumViewerConfigService.
     */
    @Inject
    SpectrumViewerConfigService spectrumViewerConfigService;

	/**
     * The BDBApiEndpointService
     */
    @Inject
    private BDBApiEndpointService bdbApiEndpointService;

    private String spectrumViewerConfig;

    private String spectrumViewerLables;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String title;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String description;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandWorkspaceLabel;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String collapseWorkspaceLabel;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportIconGray;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandWorkspaceIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String collapseWorkspaceIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String faqIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String downIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportIconGrayAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String expandWorkspaceIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String collapseWorkspaceIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String faqIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String downIconAlt;

    @ChildResourceFromRequest(name = "graphs")
    private Graph graph;
    @ChildResourceFromRequest(name = "instrument-config")
    private InstrumentConfiguration instrumentConfiguration;
    @ChildResourceFromRequest(name = "fluorochromes")
    private Fluorochromes fluorochromes;


    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String loginModalHeading;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String loginPopupBodyMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String loginCta;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importFluoroCombination;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importFlurochromeAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importCytometerAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importInstrumentConfiguration;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importWrongFormatFluoroAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String importWrongFormatCytoAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String loadFluroChromeAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String loadCytometerAlert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupMessageText;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupCancelButton;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupOkButton;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupProceedButton;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupCytometerTitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupCytometerNamePlaceholder;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupConfigurationNamePlaceholder;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupValidationMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String fluorochromeSetNamePlaceholder;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String briefDescriptionOptional;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String alert;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String saveSuccessFlu;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String saveSuccessIns;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String overwrite;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String confirmation;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String confirmationMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String warningMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String delete;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String dontShowMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String errorIcon;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String errorIconAlt;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String outOfRange;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String saveWarningToast;
    
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupTitleMatrix;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String complexityScore;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String complexityScoreNotAvailable;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String similarityScores;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupMessageSubtitle;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupMessageContent;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportImgbutton;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String similarityAndcomplexity;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String exportCSV;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String failedMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    private String popupCytoAlertMessage;
    @ValueMapValue(injectionStrategy = InjectionStrategy.REQUIRED)
    @Default(values=SpectrumViewerConstants.SV_SYS_JSON_DEFAULT_FILES_PARENT_PATH)
    private String svSystemConfigPath;

    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonParser jsonParser = new JsonParser();
    private String systemCytometerConfigStr;
    private String systemFluorochromesStr;
    private String svFluorochromesSortOrder;
    private String encryptedJsonStr;
    private String decryptedJsonStr;
    private String secureKeyPart1;
    private String secureKeyPart2;

    public Fluorochromes getFluorochromes() {
        return fluorochromes;
    }

    public Graph getGraph() {
        return graph;
    }

    public InstrumentConfiguration getInstrumentConfiguration() {
        return instrumentConfiguration;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getExpandWorkspaceLabel() {
        return expandWorkspaceLabel;
    }

    public String getFaqIcon() {
        return faqIcon;
    }

    public String getExportIcon() {
        return exportIcon;
    }

    public String getExportIconGray() {
        return exportIconGray;
    }

    public String getExpandWorkspaceIcon() {
        return expandWorkspaceIcon;
    }

    public String getDownIcon() {
        return downIcon;
    }

    public String getFaqIconAlt() {
        return faqIconAlt;
    }

    public String getExportIconAlt() {
        return exportIconAlt;
    }

    public String getExportIconGrayAlt() {
        return exportIconGrayAlt;
    }

    public String getExpandWorkspaceIconAlt() {
        return expandWorkspaceIconAlt;
    }

    public String getDownIconAlt() {
        return downIconAlt;
    }

    public String getLoginModalHeading() {
        return loginModalHeading;
    }

    public String getLoginPopupBodyMessage() {
        return loginPopupBodyMessage;
    }

    public String getLoginCta() {
        return loginCta;
    }

    public String getImportFlurochromeAlert() {
        return importFlurochromeAlert;
    }

    public String getImportFluoroCombination() {
        return importFluoroCombination;
    }

    public String getImportInstrumentConfiguration() {
        return importInstrumentConfiguration;
    }

    public String getImportCytometerAlert() {
        return importCytometerAlert;
    }

    public String getImportWrongFormatFluoroAlert() {
        return importWrongFormatFluoroAlert;
    }

    public String getImportWrongFormatCytoAlert() {
        return importWrongFormatCytoAlert;
    }

    public String getLoadFluroChromeAlert() {
        return loadFluroChromeAlert;
    }

    public String getLoadCytometerAlert() {
        return loadCytometerAlert;
    }

    public String getPopupTitle() {
        return popupTitle;
    }

    public String getPopupMessageText() {
        return popupMessageText;
    }

    public String getPopupCancelButton() {
        return popupCancelButton;
    }
    
    public String getPopupOkButton() {
        return popupOkButton;
    }

    public String getPopupProceedButton() {
        return popupProceedButton;
    }

    public String getFluorochromeSetNamePlaceholder() {
        return fluorochromeSetNamePlaceholder;
    }

    public String getBriefDescriptionOptional() {
        return briefDescriptionOptional;
    }


    public String getAlert() {
        return alert;
    }

    public String getSaveSuccessFlu() {
        return saveSuccessFlu;
    }

    public String getSaveSuccessIns() {
        return saveSuccessIns;
    }

    public String getOverwrite() {
        return overwrite;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public String getDelete() {
        return delete;
    }

    public String getDontShowMessage() {
        return dontShowMessage;
    }
    
    public String getPopupCytometerTitle() {
        return popupCytometerTitle;
    }

    public String getPopupCytometerNamePlaceholder() {
        return popupCytometerNamePlaceholder;
    }

    public String getPopupConfigurationNamePlaceholder() {
        return popupConfigurationNamePlaceholder;
    }

    public String getPopupValidationMessage() {
        return popupValidationMessage;
    }

    public String getCollapseWorkspaceLabel() {
        return collapseWorkspaceLabel;
    }

    public String getCollapseWorkspaceIcon() {
        return collapseWorkspaceIcon;
    }

    public String getCollapseWorkspaceIconAlt() {
        return collapseWorkspaceIconAlt;
    }

    public String getSearchPathUrl() {
        return searchPathUrl;
    }

    public String getSortingOrder() {
        return sortingOrder;
    }

    public String getSpectrumViewerConfig() {
        return spectrumViewerConfig;
    }

    public String getSpectrumViewerLables() {
        return spectrumViewerLables;
    }

    public String getEncryptedJsonStr() {
        return encryptedJsonStr;
    }

    public String getDecryptedJsonStr() {
        return decryptedJsonStr;
    }

    public String getSecureKeyPart1() {
        return secureKeyPart1;
    }

    public String getSecureKeyPart2() {
        return secureKeyPart2;
    }

    public String getErrorIcon() {
        return errorIcon;
    }

    public String getErrorIconAlt() {
        return errorIconAlt;
    }

    public String getOutOfRange() {
        return outOfRange;
    }
    
    public String getSaveWarningToast() {
        return saveWarningToast;
    }

    public String getPopupTitleMatrix() {
        return popupTitleMatrix;
    }
    
    /**
	 * @return the complexityScoreNotAvailable
	 */
	public String getComplexityScoreNotAvailable() {
		return complexityScoreNotAvailable;
	}

	public String getComplexityScore() {
        return complexityScore;
    }
    public String getSimilarityScores() {
        return similarityScores;
    }
    public String getPopupMessageSubtitle() {
        return popupMessageSubtitle;
    }
    public String getPopupMessageContent() {
        return popupMessageContent;
    }
    public String getExportImgbutton() {
        return exportImgbutton;
    }
    public String getSimilarityAndcomplexity() {
        return similarityAndcomplexity;
    }
    public String getExportCSV() {
        return exportCSV;
    }
    public String getFailedMessage() {
        return failedMessage;
    }
    public String getPopupCytoAlertMessage() {
        return popupCytoAlertMessage;
    }
    public String getSvSystemConfigPath() {
        return svSystemConfigPath;
    }
    
    /** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;
	
    @PostConstruct
    protected void init() {
        logger.debug("SpectrumViewerModel - Init method starting");
        Locale pageLocale = currentPage.getLanguage(true);
		ResourceBundle bundle = request.getResourceBundle(pageLocale);
        I18n i18n = new I18n(bundle);

        expandWorkspaceIcon = SVUtils.replaceIconPath(expandWorkspaceIcon);
        collapseWorkspaceIcon = SVUtils.replaceIconPath(collapseWorkspaceIcon);
        exportIcon = SVUtils.replaceIconPath(exportIcon);
        exportIconGray = SVUtils.replaceIconPath(exportIconGray);
        faqIcon = SVUtils.replaceIconPath(faqIcon);
        downIcon = SVUtils.replaceIconPath(downIcon);
        errorIcon = SVUtils.replaceIconPath(errorIcon);

        String spectrumViewerConfigParentPath = svSystemConfigPath;

        String cytometerConfigFile = spectrumViewerConfigService.getCytometerConfigFile();
        systemCytometerConfigStr = readSystemFile(spectrumViewerConfigParentPath, cytometerConfigFile);

        String fluorochromeConfigFile = spectrumViewerConfigService.getFluorochromeConfigFile();
        systemFluorochromesStr = readSystemFile(spectrumViewerConfigParentPath,fluorochromeConfigFile);
        
        String damFilePath = "/content/dam/bdb/sv/sys/2023-03-08-11-22";
	       Resource damResource = resourceResolver.getResource(damFilePath);
	       if (null != damResource && damResource.hasChildren()) {
	    	   Resource svFluorochromesSortOrderJson = damResource.getChild("svFluorochromesSortOrder.json");
		        if (null != svFluorochromesSortOrderJson) {
		        	String svFluorochromesSortOrderConfigFile = spectrumViewerConfigService.getSvFluorochromesSortOrder();
		        	 svFluorochromesSortOrder = readSystemFile(spectrumViewerConfigParentPath,svFluorochromesSortOrderConfigFile);
		        }
	       }

        if (StringUtils.isNotEmpty(systemCytometerConfigStr) && StringUtils.isNotEmpty(systemFluorochromesStr)) {
            JsonObject combinedJson = getCombinedJsonObject(systemCytometerConfigStr, systemFluorochromesStr);
            String textToEncrypt = combinedJson.toString();
            logger.debug("Before Encryption value : {}",textToEncrypt);
            String encryptionKey = spectrumViewerConfigService.getSpectrumViewerEncryptionKey();
            if(StringUtils.isNotEmpty(encryptionKey)) {
                int encryptionKeyLength = encryptionKey.length();
                secureKeyPart1 = StringUtils.substring(encryptionKey, 0, encryptionKeyLength / 2);
                secureKeyPart2 = StringUtils.substring(encryptionKey, encryptionKeyLength / 2, encryptionKeyLength);
                encryptedJsonStr = SVUtils.encrypt(textToEncrypt, encryptionKey);
                logger.debug("Encrypted Value : {}", encryptedJsonStr);
/*  Commenting Below piece of code as decryption is happening at FE now
        //Below snippet of code can be Deleted, once decryption handled at react layer
                String formingKey = secureKeyPart1+secureKeyPart2;//((new StringBuilder(secureKeyPart1)).append(secureKeyPart2)).toString();
                decryptedJsonStr = SVUtils.decrypt(encryptedJsonStr, formingKey );
                logger.debug("Decrypted Value : {}", decryptedJsonStr);
        //Snippet ends here
*/
            }
        }else{
            logger.debug("System json files are not configured properly or not having well-formatted data");
        }

        spectrumViewerConfig = getConfigJson();
        spectrumViewerLables = getLabelJson(i18n);
        logger.debug("SpectrumViewerModel - Init method Ends");
    }

    private String readSystemFile(String parentFolderPath, String fileName) {
        if(StringUtils.isNotEmpty(parentFolderPath) && StringUtils.isNotEmpty(fileName)) {
            String fileContent = SVUtils.readAssetFile(resolverFactory, parentFolderPath + CommonConstants.SINGLE_SLASH + fileName);
            return fileContent;
        }
        return null;
    }

    private String getConfigJson() {
        JsonObject spectrumViewerJsonConfig = new JsonObject();
        String searchPagePath = StringUtils.isNotEmpty(getSearchPathUrl()) ? getSearchPathUrl() : CommonHelper.getSearchResultPagePath(currentPage);
        try {
            searchPagePath = externalizer.getFormattedUrl(searchPagePath, CommonHelper.getReadServiceResolver(resolverFactory));
            searchPagePath = searchPagePath.concat("?searchKey={{searchTerm}}&docType_facet_s::{{products}}={{products}}");
        } catch (LoginException e) {
            logger.error("Error in fetching search result page path URL: {}", e.getMessage());
        }
        spectrumViewerJsonConfig.addProperty(CommonConstants.SEARCH_PAGE_PATH, searchPagePath);
        spectrumViewerJsonConfig.addProperty(SpectrumViewerConstants.SV_SORTING_ORDER, getSortingOrder());
        spectrumViewerJsonConfig.addProperty(SpectrumViewerConstants.SECURE_KEY_PART_LAST,secureKeyPart2);
        String svResourcePath = request.getResource().getPath();
        logger.debug("svResourcePath is : {}", svResourcePath);
        String ssmEndPoint = buildSsmEndPoint(svResourcePath);
        spectrumViewerJsonConfig.addProperty(SpectrumViewerConstants.SSM_END_POINT, ssmEndPoint);
        logger.debug("Path to Get SSM Data from ssmEndPoint : {}",ssmEndPoint);
        String bdbHybrisDomain = bdbApiEndpointService.getBDBHybrisDomain();
        spectrumViewerJsonConfig.addProperty(SpectrumViewerConstants.BDB_HYBRIS_DOMAIN, bdbHybrisDomain);
        if (StringUtils.isNotEmpty(svFluorochromesSortOrder)) {
            JsonElement jsonElement = jsonParser.parse(svFluorochromesSortOrder);
            spectrumViewerJsonConfig.add(SpectrumViewerConstants.FLUOROCHROMES_SORT_ORDER,jsonElement);
        }else {
        	spectrumViewerJsonConfig.addProperty(SpectrumViewerConstants.FLUOROCHROMES_SORT_ORDER, StringUtils.EMPTY);
        }
        return spectrumViewerJsonConfig.toString();
    }

    private String buildSsmEndPoint(String svResourcePath) {
        StringBuilder sb = new StringBuilder(svResourcePath);
        sb.append(CommonConstants.SINGLE_DOT);
        sb.append(UUID);
        sb.append(CommonConstants.SINGLE_DOT);
        sb.append(CommonConstants.JSON);
        return sb.toString();
    }

    private JsonObject getCombinedJsonObject(String systemCytometerConfigStr, String systemFluorochromesStr) {
        JsonObject fluorochromesJobject = jsonParser.parse(systemFluorochromesStr).getAsJsonObject();
        JsonObject cytometerJobject = jsonParser.parse(systemCytometerConfigStr).getAsJsonObject();
        JsonObject combinedJson = new JsonObject();
        combinedJson.add(SpectrumViewerConstants.FLUOROCHROMES, fluorochromesJobject.getAsJsonArray(SpectrumViewerConstants.FLUOROCHROMES));
        combinedJson.add(SpectrumViewerConstants.CYTOMETERS, cytometerJobject.getAsJsonArray(SpectrumViewerConstants.CYTOMETERS));
        return combinedJson;
    }

    private String getLabelJson(I18n i18n) {
        JsonObject spectrumViewerLabel = new JsonObject();
        spectrumViewerLabel.addProperty(CommonConstants.HEADING, getTitle());

        JsonObject flurochomeJsonObject = getFluorochromesLabels(i18n);
        spectrumViewerLabel.add(SpectrumViewerConstants.FLUOROCHROMES_LABELS, flurochomeJsonObject);

        JsonObject cytometerJsonObject = getCytometerLabels(i18n);
        spectrumViewerLabel.add(SpectrumViewerConstants.CYTOMETER_LABELS, cytometerJsonObject);

        JsonObject popupJsonObject = getPopupLabels();
        spectrumViewerLabel.add(SpectrumViewerConstants.SV_POPUP_LABEL, popupJsonObject);
        
        JsonObject matrixJsonObject = getMatrixLabels();
        spectrumViewerLabel.add(SpectrumViewerConstants.SV_MATRIX_LABEL, matrixJsonObject);
        
        String svLaserListForSorting = spectrumViewerConfigService.getSvLaserListForSorting();
        if(StringUtils.isNotEmpty(svLaserListForSorting)) {
          addLaserListObject(spectrumViewerLabel, svLaserListForSorting,SpectrumViewerConstants.SV_LASER_LIST_FOR_SORTING);
        }
        
       String fluoroAltColorCode = spectrumViewerConfigService.getFluoroAltColorCode();
       if(StringUtils.isNotEmpty(fluoroAltColorCode)) {
          addLaserListObject(spectrumViewerLabel, fluoroAltColorCode,SpectrumViewerConstants.FLUORO_ALT_COLOR_CODE);
       }

        try {
            if(graph!=null) {
                String graphJsonStr = mapper.writeValueAsString(graph);
                spectrumViewerLabel.add(SpectrumViewerConstants.GRAPH, jsonParser.parse(graphJsonStr));
            }
            
         
            
            
            if (instrumentConfiguration != null){
                String insConfigjsonStr = mapper.writeValueAsString(instrumentConfiguration);
                spectrumViewerLabel.add(SpectrumViewerConstants.INSTRUMENT_CONFIGURATION, jsonParser.parse(insConfigjsonStr));
            }
        } catch (JsonProcessingException ex) {
            logger.error("Error in Parsing the Json {}", ex.getMessage());
        }
     

        if(StringUtils.isNotEmpty(description)) {
        	spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DESCRRIPTION, SVUtils.externalizeRteLinks(getDescription(),externalizer,resolverFactory));

        } 
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SINGLE_GRAPH_VIEW, i18n.get(SpectrumViewerConstants.I18N_KEY_SINGLE_GRAPH_VIEW));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.STACKED_GRAPH_VIEW, i18n.get(SpectrumViewerConstants.I18N_KEY_STACKED_GRAPH_VIEW));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SPECTRAL_GRAPH_VIEW, i18n.get(SpectrumViewerConstants.I18N_KEY_SPECTRAL_GRAPH_VIEW));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.HEAT_MAP_GRAPH_VIEW, i18n.get(SpectrumViewerConstants.I18N_KEY_HEAT_MAP_GRAPH_VIEW));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.HEAT_MAP_GRAPH_SHOW_DETAILS_ON_HOVER, i18n.get(SpectrumViewerConstants.I18N_KEY_HEAT_MAP_SHOW_DETAILS_ON_HOVER));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.HEAT_MAP, i18n.get(SpectrumViewerConstants.I18N_KEY_HEAT_MAP));

        spectrumViewerLabel.addProperty(SpectrumViewerConstants.FLUOROCHROME_INFO, i18n.get(SpectrumViewerConstants.I18N_KEY_FLUOROCHROME_INFO));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_FLUOROCHROME, i18n.get(SpectrumViewerConstants.I18N_KEY_FLUOROCHROME));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXCITATION_MAX, i18n.get(SpectrumViewerConstants.I18N_KEY_EXCITATION_MAX));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EMISSION_MAX, i18n.get(SpectrumViewerConstants.I18N_KEY_EMISSION_MAX));
        spectrumViewerLabel.addProperty(CommonConstants.NM, i18n.get(SpectrumViewerConstants.I18N_KEY_NM));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.PRIMARY_LASER, i18n.get(SpectrumViewerConstants.I18N_KEY_PRIMARY_LASER));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.COMMON_FILTERS, i18n.get(SpectrumViewerConstants.I18N_KEY_COMMON_FILTERS));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.AVAILABLE_CONJUGATES, i18n.get(SpectrumViewerConstants.I18N_KEY_AVAILABLE_CONJUGATES));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_COLON, i18n.get(SpectrumViewerConstants.I18N_KEY_COLON));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.EXPORT, i18n.get(SpectrumViewerConstants.I18N_KEY_EXPORT));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.NORMALIZE_EM_TO_LASER_EX, i18n.get(SpectrumViewerConstants.I18N_KEY_NORMALIZE_EM_TO_LASER_EX));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.ALT_COLOR_SCHEME_LABEL, i18n.get(SpectrumViewerConstants.I18N_KEY_ALT_COLOR_SCHEME));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SHOW_FILLED_FILTERS, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_FILLED_FILTERS));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.USE_FILLED_EM_CURVES, i18n.get(SpectrumViewerConstants.I18N_KEY_USE_FILLED_EM_CURVES));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SHOW_GRID, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_GRID));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SHOW_LABELS, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_LABELS));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SHOW_EX, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_EX));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_SHOW_LASER_FILTER, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_LASER_FILTER));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_HIGHLIGHT_SPECTRA, i18n.get(SpectrumViewerConstants.I18N_KEY_HIGHLIGHT_SPECTRA));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_RESOLVE_FILTER_OVERLAP, i18n.get(SpectrumViewerConstants.I18N_KEY_RESOVE_FILTER_OVERLAP));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_SHOW_FLUOROCHROME_NAME, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOW_FLUOROCHROME_NAME));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.INVALID_ENTRY, i18n.get(SpectrumViewerConstants.I18N_KEY_INVALID_ENTRY));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.NO_FILTER_DISPLAY_ERROR, i18n.get(SpectrumViewerConstants.I18N_NO_FILTER_DISPLAY_ERROR));

        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPAND_WORKSPACE_LABEL, getExpandWorkspaceLabel());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPAND_WORKSPACE_ICON,getExpandWorkspaceIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPAND_WORKSPACE_ICON_ALT,getExpandWorkspaceIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_COLLAPSE_WORKSPACE_LABEL, getCollapseWorkspaceLabel());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_COLLAPSE_WORKSPACE_ICON, getCollapseWorkspaceIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_COLLAPSE_WORKSPACE_ICON_ALT, getCollapseWorkspaceIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_ICON, getExportIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_ICON_ALT, getExportIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_ICON_GRAY, getExportIconGray());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_ICON_GRAY_ALT, getExportIconGrayAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_FAQ_ICON, getFaqIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_FAQ_ICON_ALT, getFaqIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.DOWN_ICON,getDownIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.DOWN_ICON_ALT,getDownIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.ERROR, i18n.get(SpectrumViewerConstants.I18N_KEY_ERROR));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.REQUIRED, i18n.get(SpectrumViewerConstants.I18N_KEY_REQUIRED));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.ERROR_ICON, getErrorIcon());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.ERROR_ICON_ALT, getErrorIconAlt());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.OUT_OF_RANGE, getOutOfRange());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SAVE_WARNING_TOAST, getSaveWarningToast());
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPAND_GRAPH_MENU, i18n.get(SpectrumViewerConstants.I18N_KEY_EXPAND_GRAPH_MENU));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_FLUOROCHROME, i18n.get(SpectrumViewerConstants.I18N_KEY_EXPORT_FLUOROCHROME));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_INSTRUMENT_CONFIGURATION, i18n.get(SpectrumViewerConstants.I18N_KEY_EXPORT_INSTRUMENT_CONFIGURATION));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_IMPORT, i18n.get(SpectrumViewerConstants.I18N_KEY_IMPORT));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_EXPORT_AND_IMPORT, i18n.get(SpectrumViewerConstants.I18N_KEY_EXPORT_AND_IMPORT));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_LOAD_INS_AND_FLU, i18n.get(SpectrumViewerConstants.I18N_KEY_LOAD_INSTRUMENT_AND_FLUOROCHROME));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DELETE_INS_AND_FLU, i18n.get(SpectrumViewerConstants.I18N_KEY_DELETE_INSTRUMENT_AND_FLUOROCHROME));
        spectrumViewerLabel.addProperty(CommonConstants.OKAY_LABEL, i18n.get(CommonConstants.OKAY_LABEL));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_OPTIONAL, i18n.get(SpectrumViewerConstants.I18N_KEY_OPTIONAL));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_CHOOSE_FILE, i18n.get(SpectrumViewerConstants.I18N_KEY_CHOOSE_FILE));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_IMPORT_CSV, i18n.get(SpectrumViewerConstants.I18N_KEY_IMPORT_CSV));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DOWNLOAD_JPG, i18n.get(SpectrumViewerConstants.I18N_KEY_DOWNLOAD_JPG));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DOWNLOAD_PNG, i18n.get(SpectrumViewerConstants.I18N_KEY_DOWNLOAD_PNG));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DOWNLOAD_JPEG, i18n.get(SpectrumViewerConstants.I18N_KEY_DOWNLOAD_JPEG));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_DOWNLOAD_TIFF, i18n.get(SpectrumViewerConstants.I18N_KEY_DOWNLOAD_TIFF));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_MOBILE_SHOW_ALL_DETECTORS, i18n.get(SpectrumViewerConstants.I18N_KEY_MOBILE_SHOW_ALL_DETECTORS));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_MOBILE_CLEAR_ALL, i18n.get(SpectrumViewerConstants.I18N_KEY_MOBILE_CLEAR_ALL));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_MOBILE_SHOW_HIDE, i18n.get(SpectrumViewerConstants.I18N_KEY_MOBILE_SHOW_HIDE));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_MOBILE_SHOW_MORE, i18n.get(SpectrumViewerConstants.I18N_KEY_MOBILE_SHOW_MORE));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_MOBILE_SHOW_LESS, i18n.get(SpectrumViewerConstants.I18N_KEY_MOBILE_SHOW_LESS));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_SE_S6_INST_DATA_MSG, i18n.get(SpectrumViewerConstants.I18N_KEY_SE_S6_INST_DATA_MSG));
        spectrumViewerLabel.addProperty(SpectrumViewerConstants.SV_A8_DATA_MSG, i18n.get(SpectrumViewerConstants.I18N_KEY_SV_A8_DATA_MSG));

        return spectrumViewerLabel.toString();
    }
    
    private JsonObject getPopupLabels() {
        JsonObject popupJsonObject = new JsonObject();
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_LOGIN_HEADING, getLoginModalHeading());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_LOGIN_SUB_HEADING, getLoginPopupBodyMessage());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_LOGIN_CTA_TEXT, getLoginCta());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_FLU_COBINATION, getImportFluoroCombination());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_FLU_ALERT, getImportFlurochromeAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_INS_CONFIG, getImportInstrumentConfiguration());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_CYTO_ALERT, getImportCytometerAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_FORMAT_FLU_ALERT, getImportWrongFormatFluoroAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_IMPORT_FORMAT_CYTO_ALERT, getImportWrongFormatCytoAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_LOAD_FLU_ALERT, getLoadFluroChromeAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_LOAD_CYTO_ALERT, getLoadCytometerAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_TITLE, getPopupTitle());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_MESSAGE_TEXT, getPopupMessageText());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_CANCEL_BUTTON, getPopupCancelButton());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_OK_BUTTON, getPopupOkButton());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_PROCEED_BUTTON, getPopupProceedButton());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_CYTOMETER_TITLE, getPopupCytometerTitle());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_CYTOMETER_NAME_PLACEHOLDER, getPopupCytometerNamePlaceholder());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_CONFIGURATION_NAME_PLACEHOLDER, getPopupConfigurationNamePlaceholder());
        popupJsonObject.addProperty(SpectrumViewerConstants.SV_POPUP_VALIDATION_MESSAGE, getPopupValidationMessage());

        popupJsonObject.addProperty(SpectrumViewerConstants.FLUOROCHROME_SET_NAME_PLACEHOLDER, getFluorochromeSetNamePlaceholder());
        popupJsonObject.addProperty(SpectrumViewerConstants.BRIEF_DESCRIPTION_OPTIONAL, getBriefDescriptionOptional());
        popupJsonObject.addProperty(SpectrumViewerConstants.ALERT, getAlert());
        popupJsonObject.addProperty(SpectrumViewerConstants.SAVE_SUCCESS_FLU, getSaveSuccessFlu());
        popupJsonObject.addProperty(SpectrumViewerConstants.SAVE_SUCCESS_INS, getSaveSuccessIns());
        popupJsonObject.addProperty(SpectrumViewerConstants.OVERWRITE, getOverwrite());
        popupJsonObject.addProperty(SpectrumViewerConstants.CONFIRMATION, getConfirmation());
        popupJsonObject.addProperty(SpectrumViewerConstants.CONFIRMATION_MESSAGE, getConfirmationMessage());
        popupJsonObject.addProperty(SpectrumViewerConstants.WARNING_MESSAGE, getWarningMessage());
        popupJsonObject.addProperty(SpectrumViewerConstants.DELETE, getDelete());
        popupJsonObject.addProperty(SpectrumViewerConstants.DONT_SHOW_MESSAGE, getDontShowMessage());
        popupJsonObject.addProperty(SpectrumViewerConstants.FAILED_MESSAGE, getFailedMessage());
        popupJsonObject.addProperty(SpectrumViewerConstants.POPUP_CYTO_ALERT_MESSAGE, getPopupCytoAlertMessage());
        
        return popupJsonObject;
     }
    
    private JsonObject getMatrixLabels() {
    	JsonObject matrixJsonObject = new JsonObject();

    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_POPUP_TITLE, getPopupTitleMatrix());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_COMPLEXITY_SCORE, getComplexityScore());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_SIMILARITY_SCORES, getSimilarityScores());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_POPUP_MESSAGE_SUBTITLE, getPopupMessageSubtitle());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_POPUP_MESSAGE_CONTENT, getPopupMessageContent());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_SIMILARITY_AND_COMPLEXITY, getSimilarityAndcomplexity());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_EXPORT_IMG_BUTTON, getExportImgbutton());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_EXPORT_CSV, getExportCSV());
    	matrixJsonObject.addProperty(SpectrumViewerConstants.SV_MATRIX_COMPLEXITY_SCORE_NOT_AVAILABLE, getComplexityScoreNotAvailable());
    	
    	return matrixJsonObject;
    }

    private JsonObject getCytometerLabels(I18n i18n) {
        JsonObject cytometerJsonObject = new JsonObject();
        String spectrumViewerLaserList = spectrumViewerConfigService.getSpectrumViewerLaserList();
        if(StringUtils.isNotEmpty(spectrumViewerLaserList)) {
            addLaserListObject(cytometerJsonObject, spectrumViewerLaserList, SpectrumViewerConstants.LASER_LIST);
        }
        String svLaserList = spectrumViewerConfigService.getSvLaserList();
        if(StringUtils.isNotEmpty(svLaserList)) {
            addLaserListObject(cytometerJsonObject, svLaserList,SpectrumViewerConstants.SV_LASER_LIST);
        }

        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_WAVELENGTH, i18n.get(SpectrumViewerConstants.I18N_KEY_WAVELENGTH));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_RANGE_MESSAGE, i18n.get(SpectrumViewerConstants.I18N_KEY_RANGE_MESSAGE));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_POWER, i18n.get(SpectrumViewerConstants.I18N_KEY_POWER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_PRIMARY_FILTER, i18n.get(SpectrumViewerConstants.I18N_KEY_PRIMARY_FILTER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_TYPE, i18n.get(SpectrumViewerConstants.I18N_KEY_TYPE));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_WAVELENGTH_COL, i18n.get(SpectrumViewerConstants.I18N_KEY_WAVELENGTH_COL));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_POWER_COL, i18n.get(SpectrumViewerConstants.I18N_KEY_POWER_COL));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_SECONDARY_FILTER, i18n.get(SpectrumViewerConstants.I18N_KEY_SECONDARY_FILTER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_SHOWALL, i18n.get(SpectrumViewerConstants.I18N_KEY_SHOWALL));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_ACTIONS, i18n.get(SpectrumViewerConstants.I18N_KEY_ACTIONS));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_ADD_DETECTOR, i18n.get(SpectrumViewerConstants.I18N_KEY_ADD_DETECTOR));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_ADD_LASER, i18n.get(SpectrumViewerConstants.I18N_KEY_ADD_LASER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_SAVECONFIGURATION, i18n.get(SpectrumViewerConstants.I18N_KEY_SAVE_CONFIGURATION));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_OR, i18n.get(SpectrumViewerConstants.I18N_KEY_OR));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_ADDLASERFILTER, i18n.get(SpectrumViewerConstants.I18N_KEY_ADD_LASER_FILTER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_CLEARALL, i18n.get(SpectrumViewerConstants.I18N_KEY_CONF_CLEAR_ALL));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_LASER, i18n.get(SpectrumViewerConstants.I18N_KEY_LASER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_DELETELASER, i18n.get(SpectrumViewerConstants.I18N_KEY_DELETE_LASER));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_BP, i18n.get(SpectrumViewerConstants.I18N_KEY_BP));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_LP, i18n.get(SpectrumViewerConstants.I18N_KEY_LP));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_SP, i18n.get(SpectrumViewerConstants.I18N_KEY_SP));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_NONE, i18n.get(SpectrumViewerConstants.I18N_KEY_NONE));
        cytometerJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_BANDWIDTH, i18n.get(SpectrumViewerConstants.I18N_KEY_BANDWIDTH));
        return cytometerJsonObject;
    }

    private void addLaserListObject(JsonObject cytometerJsonObject, String spectrumViewerLaserList, String listName) {
        try {
            JsonElement laserListArray = jsonParser.parse(spectrumViewerLaserList);
            JsonArray cytometerLaserList = (JsonArray) laserListArray;
            logger.info("cytometerLaserList: {}",cytometerLaserList);
            cytometerJsonObject.add(listName, cytometerLaserList);
        } catch (JsonSyntaxException e) {
            logger.error("Error in Parsing the Laser list object");
            cytometerJsonObject.add(SpectrumViewerConstants.LASER_LIST, jsonParser.parse(SpectrumViewerConstants.LASER_LIST_DEFAULT));
        }
    }

    private JsonObject getFluorochromesLabels(I18n i18n) {
        JsonObject flurochomeJsonObject = new JsonObject();
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.EX, i18n.get(SpectrumViewerConstants.I18N_KEY_EX));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_NAME, i18n.get(SpectrumViewerConstants.I18N_KEY_NAME));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.SHORT_NAME, i18n.get(SpectrumViewerConstants.I18N_KEY_SHORT_NAME));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.FLUORO_ADDED, i18n.get(SpectrumViewerConstants.I18N_KEY_FLUORO_ADDED));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_EM, i18n.get(SpectrumViewerConstants.I18N_KEY_EM));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.CYTOMETER_ACTION, i18n.get(SpectrumViewerConstants.I18N_KEY_ACTION));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.FLU_SAVE_PANEL, i18n.get(SpectrumViewerConstants.I18N_KEY_SAVE_PANEL));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.FLU_CLEAR_ALL, i18n.get(SpectrumViewerConstants.I18N_KEY_FLU_CLEAR_ALL));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.FLU_SELECT_CRITERIA, i18n.get(SpectrumViewerConstants.I18N_KEY_SELECT_CRITERIA));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.EX_MAX, i18n.get(SpectrumViewerConstants.I18N_KEY_EX_MAX));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.EM_MAX, i18n.get(SpectrumViewerConstants.I18N_KEY_EM_MAX));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.ADD, i18n.get(SpectrumViewerConstants.I18N_KEY_ADD));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.SPECTRAL_SIGNATURE, i18n.get(SpectrumViewerConstants.I18N_KEY_SPECTRAL_SIGNATURE));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.LIST_ROW_HOVER_HIGH, i18n.get(SpectrumViewerConstants.I18N_KEY_LIST_ROW_HOVER_HIGH));
        flurochomeJsonObject.addProperty(SpectrumViewerConstants.LIST_ROW_HOVER_UNHIGH, i18n.get(SpectrumViewerConstants.I18N_KEY_LIST_ROW_HOVER_UNHIGH));
        try {
            if (fluorochromes != null){
                String fluorochromesJsonStr = mapper.writeValueAsString(fluorochromes);
                JsonElement jsonElement = jsonParser.parse(fluorochromesJsonStr);
                flurochomeJsonObject.add(SpectrumViewerConstants.FLUOROCHROMES_CONFIG,jsonElement);
            }
        } catch (JsonProcessingException ex) {
            logger.error("Error in Parsing the Json {}", ex.getMessage());
        }
        return flurochomeJsonObject;
    }

}
