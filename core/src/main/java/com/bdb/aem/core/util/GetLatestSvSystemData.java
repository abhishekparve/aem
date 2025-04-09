package com.bdb.aem.core.util;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.model.GenericReport;
import com.bdb.aem.core.api.client.RestClient;
import com.bdb.aem.core.api.request.util.HttpMethodType;
import com.bdb.aem.core.api.response.BaseResponse;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SpectrumViewerConfigService;
import com.day.cq.commons.jcr.JcrUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.management.openmbean.OpenDataException;
import java.io.IOException;
import java.util.*;

/**
 * GetLatestSvSystemData to fetch
 * Latest System data from BDRC
 * Via CCV2 Rest APIs
 */
public class GetLatestSvSystemData extends ProcessDefinition {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetLatestSvSystemData.class);

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd-HH-mm";
    private static final String REPORT_NAME = "ProcessReport";
    private static final String SINGLE_DASH = "-";
    private final List<EnumMap<ReportColumns, Object>> reportRows = new ArrayList<>();

    @FormField(name = "Parent folder Paths",
            description = "Parent folder/path to store the latest system data",
            required = true,
            component = PathfieldComponent.FolderSelectComponent.class,
            options = {CommonConstants.DEFAULT + CommonConstants.EQUAL + SpectrumViewerConstants.SV_SYS_JSON_DEFAULT_FILES_PARENT_PATH})
    private String parentPaths;
    private BDBApiEndpointService bdbApiEndpointService;
    private RestClient restClient;
    private SpectrumViewerConfigService spectrumViewerConfigService;


    public GetLatestSvSystemData(BDBApiEndpointService bdbApiEndpointService, RestClient restClient, SpectrumViewerConfigService spectrumViewerConfigService) {
        this.bdbApiEndpointService = bdbApiEndpointService;
        this.restClient = restClient;
        this.spectrumViewerConfigService = spectrumViewerConfigService;
    }

    @Override
    public void buildProcess(ProcessInstance processInstance, ResourceResolver resourceResolver) throws LoginException, RepositoryException {
        try {
            LOGGER.info("Starting the buildProcess of Get latest System data Managed Process");
            processInstance.defineCriticalAction("Starting the base folder creation process : Critical", resourceResolver, this::recordAction);

            //Creating Folder in Dam with current timestamp
            String damFolderPath = createBaseFolderDam(resourceResolver);
            LOGGER.info("Base Folder created in DAM at: {}", damFolderPath);
            processInstance.getInfo().setDescription(String.format("Storing latest system data at [ %s ]", damFolderPath));

            //Getting Auth Token through Rest API
            processInstance.defineCriticalAction("Getting the Auth Token", resourceResolver, this::recordAction);
            String authToken = ManagedProcessUtils.fetchAuthToken(bdbApiEndpointService, restClient);
            LOGGER.debug("AuthToken is : {}", authToken);
            processInstance.defineAction("Getting the Cytometer data", resourceResolver, this::recordAction);

            //Getting Latest System Cytometer Data through Rest API
            String cytometerFilePath = processSystemData(resourceResolver, authToken, SpectrumViewerConstants.CYTOMETERS, damFolderPath);
            processInstance.defineAction("Cytometer data recieved ", resourceResolver, this::recordAction);
            LOGGER.debug("Latest Cytometer Data has been placed at : {}", cytometerFilePath);

            //Getting Latest System fluorochrome Data through Rest API
            String fluorochromeFilePath = processSystemData(resourceResolver, authToken, SpectrumViewerConstants.FLUOROCHROMES, damFolderPath);
            processInstance.defineAction("fluorochrome data recieved ", resourceResolver, this::recordAction);
            LOGGER.debug("Latest fluorochrome Data has been placed at : {}", fluorochromeFilePath);

            //Collecting All configurations from Cytometer file
            LOGGER.info("Getting all Instrument Configurations from : {} file", cytometerFilePath);
            processInstance.defineAction("Process to collect all Instruments Configurations to fetch SSM data ", resourceResolver, this::recordAction);
            Map<String, JsonObject> configurationProfilesMap = getConfigurationProfiles(resourceResolver, cytometerFilePath);

            //Getting SSM data for each config profile
            LOGGER.info("Starting to get SSM data from CCV2");
            processInstance.defineAction("Process to hit CCV2 Api to fetch SSM data ", resourceResolver, this::recordAction);
            processSsmData(resourceResolver, configurationProfilesMap, authToken, damFolderPath, processInstance);
            LOGGER.info("End of the buildProcess of Get latest System data Managed Process, Json files created at : {} and {}", cytometerFilePath, fluorochromeFilePath);

        } catch (AemInternalServerErrorException | IOException ex) {
            LOGGER.error("Error while loading of managed process : {}", ex.getMessage());
            throw new LoginException("Couldn't connect to CCV2");
        }

    }

    private void recordAction(ActionManager actionManager) {
        String actionManagerName = actionManager.getName();
        int completedCount = actionManager.getSuccessCount();
        try {
            LOGGER.debug("Here is the action Manager Name : {} and completed count : {}, remaining count : {}, Added Count : {} ", actionManagerName, completedCount, actionManager.getRemainingCount(), actionManager.getAddedCount());
            LOGGER.debug("Action Manager Statistics are {},  ", actionManager.getStatistics());
            recordIt(String.valueOf(completedCount), actionManagerName);
        } catch (OpenDataException e) {
            LOGGER.error("Error in getting Statistics for {} is : {}", actionManagerName, e.getMessage());
        }
    }

    private void recordIt(String path, String description) {
        final EnumMap<ReportColumns, Object> row = new EnumMap<>(ReportColumns.class);
        row.put(ReportColumns.SNO, path);
        row.put(ReportColumns.MESSAGE, description);
        reportRows.add(row);
    }

    @Override
    public void storeReport(ProcessInstance processInstance, ResourceResolver resourceResolver) throws RepositoryException {
        GenericReport genericReport = new GenericReport();
        genericReport.setName(REPORT_NAME);
        try {
            genericReport.setRows(reportRows, ReportColumns.class);
            List<String> columnNames = genericReport.getColumnNames();
            List<ValueMap> reportRows = genericReport.getRows();
            LOGGER.debug("List of columnNames {}", columnNames);
            LOGGER.debug("List of reportRows {}", reportRows);
            LOGGER.debug("ProcessInstance Details are store at Path {} ", processInstance.getPath());
//            genericReport.persist(resourceResolver, processInstance.getPath() + "/jcr:content/report");
        } catch (PersistenceException e) {
            LOGGER.error(" Exception in resource while Saving the Report  :  {}", e);
        }
    }

    @Override
    public void init() throws RepositoryException {

    }

    public enum ReportColumns {
        SNO, MESSAGE
    }

    private String createBaseFolderDam(ResourceResolver resourceResolver) throws RepositoryException {
        String currentDateTime = SVUtils.timeNow(DATE_FORMAT_NOW);
        LOGGER.debug("Current Date is : {}", currentDateTime);
        Session session = resourceResolver.adaptTo(Session.class);
        LOGGER.debug("Resource resolver adapted to Session with user {}", session.getUserID());
        if (StringUtils.isNotEmpty(parentPaths) && StringUtils.isNotEmpty(currentDateTime)) {
            String damFolderPath = parentPaths + CommonConstants.SINGLE_SLASH + currentDateTime;
            Node node = JcrUtil.createPath(damFolderPath, JcrResourceConstants.NT_SLING_FOLDER, session);
            damFolderPath = node.getPath();
            LOGGER.debug("New folder Created in DAM : {}   ", damFolderPath);
            session.save();
            return damFolderPath;
        }

        return null;
    }

    private String processSystemData(ResourceResolver resourceResolver, String authToken, String systemDataType, String damFolderPath) throws AemInternalServerErrorException {
        LOGGER.info("Starting the processSystemData for :{} ", systemDataType);

        String responseString = getSystemDataApiResponse(authToken, systemDataType);
        LOGGER.debug("Response from Get Call is : {}", responseString);

        // Saving the data to DAM .json file which we got from API call.
        String damAssetPath = "";
        if (StringUtils.isNotEmpty(responseString)) {
            try {
                damAssetPath = writeSystemDataJson(resourceResolver, damFolderPath, systemDataType, responseString);
            } catch (RepositoryException e) {
                LOGGER.error("Exception in writing data to DAM file {}", e.getMessage());
            }
        }
        LOGGER.info("End processSystemData for :{} ", systemDataType);
        return damAssetPath;
    }

    private String getSystemDataApiResponse(String authToken, String systemDataType) throws AemInternalServerErrorException {
        String responseString;
        String endPoint = getSystemDataEndpoint(systemDataType);
        if (StringUtils.isNotEmpty(authToken) && StringUtils.isNotEmpty(endPoint)) {
            responseString = ManagedProcessUtils.getResponseStr(authToken, endPoint, restClient, HttpMethodType.GET);
        } else {
            responseString = CommonConstants.ACCESS_TOKEN_RETRIEVAL_ERROR;
            LOGGER.error("Access token : {}, End point {}", authToken, endPoint);
        }
        return responseString;
    }

    private String getSystemDataEndpoint(String systemDataType) {
        String endpoint = StringUtils.EMPTY;
        StringBuilder endPointUrl = new StringBuilder();
        if (null != bdbApiEndpointService) {
            endPointUrl.append(bdbApiEndpointService.getBDBHybrisDomain().trim());
            String systemDataPoint = StringUtils.EMPTY;
            if (StringUtils.equalsIgnoreCase(systemDataType, SpectrumViewerConstants.FLUOROCHROMES)) {
                systemDataPoint = spectrumViewerConfigService.getSystemFluorochromeEndpoint();
            } else if (StringUtils.equalsIgnoreCase(systemDataType, SpectrumViewerConstants.CYTOMETERS)) {
                systemDataPoint = spectrumViewerConfigService.getSystemCytometerEndpoint();
            } else if (StringUtils.equalsIgnoreCase(systemDataType, SpectrumViewerConstants.SSM)) {
                systemDataPoint = spectrumViewerConfigService.getSystemSsmdataEndpoint();
            }
            endPointUrl.append(systemDataPoint.trim());
            endpoint = endPointUrl.toString();
        }
        return endpoint;
    }

    private void processSsmData(ResourceResolver resourceResolver, Map<String, JsonObject> configurationProfilesMap, String authToken, String damFolderPath, ProcessInstance processInstance) {
        String ssmEndpoint = getSystemDataEndpoint(SpectrumViewerConstants.SSM);
        LOGGER.debug("Final ssmEndpoint to hit : {} ", ssmEndpoint);

        for (Map.Entry<String, JsonObject> entry : configurationProfilesMap.entrySet()) {
            String uuid = entry.getKey();
            JsonObject jsonObject = entry.getValue();
            LOGGER.debug("Traversing the map for : {}", uuid);
            try {
                LOGGER.debug("Entering to fetch SSM data for : {}", uuid);
                processInstance.defineAction(String.format("Calling CCV2 to get SSM data for [ %s ]", uuid), resourceResolver, this::recordAction);
                BaseResponse ssmApiResponse = ManagedProcessUtils.getApiResponse(authToken, ssmEndpoint, jsonObject, restClient, HttpMethodType.POST);
                if (ssmApiResponse.hasError()) {
                    LOGGER.error(" Error in getting the SSM data for {}  and error message is {}", uuid, ssmApiResponse.getError().getMessage());
                    recordIt("Failed", "Failed to get SSM data for " + uuid);
                } else {
                    String responseString = ssmApiResponse.getResponseData().getData();
                    String ssmAssetPath = writeSystemDataJson(resourceResolver, damFolderPath, uuid, responseString);
                    LOGGER.debug("SSM Data has been posted at {}", ssmAssetPath);
                }
            } catch (AemInternalServerErrorException | RepositoryException | LoginException e) {
                LOGGER.debug("Exception in fetching the SSM data for this Uuid : {} and request: {}", uuid, jsonObject);
                LOGGER.error("Exception reported in fetching SSM data  : {} ", e.getMessage());
            }
            processInstance.updateProgress();
        }
    }


    public Map<String, JsonObject> getConfigurationProfiles(ResourceResolver resourceResolver, String cytometerFilePath) throws IOException {
        String cytometerJsonStr = SVUtils.readAsset(resourceResolver, cytometerFilePath);
        Map<String, JsonObject> instrumentConfigurtaiosMap = new HashMap<>();
        JsonObject jsonObject = new Gson().fromJson(cytometerJsonStr, JsonObject.class);
        JsonArray cytoJsonObjArray = jsonObject.getAsJsonArray("cytometers");
        for (JsonElement cytometerObj : cytoJsonObjArray) {
            String name = cytometerObj.getAsJsonObject().get("name").getAsString();
            LOGGER.debug("Instrument Cytometer Config Name is : {}", name);
            JsonArray configJsonoArray = cytometerObj.getAsJsonObject().get("cytometer_profiles").getAsJsonArray();
            Iterator<JsonElement> itc = configJsonoArray.iterator();
            while (itc.hasNext()) {
                JsonObject cjobj = (JsonObject) itc.next();
                String configurationName = cjobj.get("configuration_name").getAsString();
                String uuidConfiguration = cjobj.get("uuid").getAsString();
                if (StringUtils.isNotEmpty(configurationName) && StringUtils.isNotEmpty(uuidConfiguration)) {
                    String uniqueFileName = uuidConfiguration + SINGLE_DASH + JcrUtil.createValidName(name.trim()) + SINGLE_DASH + JcrUtil.createValidName(configurationName.trim());
                    instrumentConfigurtaiosMap.put(uniqueFileName, cjobj);
                    LOGGER.debug("Cytometer Configuration Added to Map : {}", cjobj);
                }
                LOGGER.debug("Cytometer Config Name is : {}", configurationName);
                LOGGER.debug("Cytometer Config UUID is : {}", uuidConfiguration);
            }
        }
        return instrumentConfigurtaiosMap;
    }

    private String writeSystemDataJson(ResourceResolver resourceResolver, String damFolderPath, String systemDataType, String responseString) throws RepositoryException {
        String configFileName = StringUtils.EMPTY;
        if (StringUtils.equalsIgnoreCase(systemDataType, SpectrumViewerConstants.FLUOROCHROMES)) {
            configFileName = spectrumViewerConfigService.getFluorochromeConfigFile();
        } else if (StringUtils.equalsIgnoreCase(systemDataType, SpectrumViewerConstants.CYTOMETERS)) {
            configFileName = spectrumViewerConfigService.getCytometerConfigFile();
        } else {
            configFileName = (JcrUtil.isValidName(systemDataType) ? systemDataType : JcrUtil.createValidName(systemDataType)) + ".json";
        }
        String filePath = damFolderPath + CommonConstants.SINGLE_SLASH + configFileName;
        return ManagedProcessUtils.createDamAsset(resourceResolver, filePath, responseString);
    }

}
