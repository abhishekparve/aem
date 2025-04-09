package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.bean.BeadlotFileBean;
import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.bdb.aem.core.bean.BeadlotsGroupBean;
import com.bdb.aem.core.bean.BeadlotsMaterialNumberBean;
import com.bdb.aem.core.models.BeadlotsGroupModel;
import com.bdb.aem.core.models.BeadlotsModel;
import com.bdb.aem.core.models.BeadlotsPartNumbersModel;
import com.bdb.aem.core.models.InstallationsInstructionsModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.search.Query;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.*;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.RepositoryException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The BeadLots Model. 
 * 
 * The implementation has been changed as per the ticket #
 * and now in place of part number "material number" is being used to fetch
 * documents available under the material number node. the field names are still
 * same at some places but values has been changed from part number to material
 * number. so the variable with part number are actually referring to material number
 */
@Model(adaptables = {Resource.class}, adapters = {BeadlotsModel.class}, resourceType = {
        BeadlotsModelImpl.RESOURCE_TYPE}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeadlotsModelImpl implements BeadlotsModel {

    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(com.bdb.aem.core.models.impl.BeadlotsModelImpl.class);

    /**
     * The Constant RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/beadlotscomponent/v1/beadlotscomponent";

    /** The part number. */
    private static final String PART_NUMBER = "partNumber";
    
    private static final String MATERIAL_NUMBER = "materialNumber";

    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;

    /**
     * The authoredBeadlotsGroupList list.
     */
    private List<BeadlotsGroupModel> authoredBeadlotsGroupList = new ArrayList<>();


    /**
     * The externalizer service.
     */
    @Inject
    ExternalizerService externalizerService;

    /**
     * The categoryLabel.
     */
    @Inject
    @SerializedName("categoryLabel")
    private String categoryLabel;


    /**
     * The beadlotsCategoryTitle.
     */
    @Inject
    @SerializedName("beadlotsCategoryTitle")
    private String beadlotsCategoryTitle;

    /**
     * The beadlotsCategorySubTitle.
     */
    @Inject
    @SerializedName("beadlotsCategorySubTitle")
    private String beadlotsCategorySubTitle;

    /**
     * The statusLabel.
     */
    @Inject
    @SerializedName("statusLabel")
    private String statusLabel;
    
    @Inject
    @SerializedName("showStatus")
    private boolean showStatus;

    /**
     * The partNumberLabel.
     */
    @Inject
    @SerializedName("partNumberLabel")
    private String partNumberLabel;

    /**
     * The beadlotsFilesTitle.
     */
    @Inject
    @SerializedName("beadlotsFilesTitle")
    private String beadlotsFilesTitle;

    /** The multiple installations instructions files per category. */
    @Inject
    public List<Resource> multipleInstallationsInstructions;

    /**
     * The multiple industry config.
     */
    @Inject
    public List<Resource> partNumberMultiField;

    /**
     * The beadlots group bean list.
     */
    @SerializedName("beadlotsGroupBeanList")
    private List<BeadlotsGroupBean> beadlotsGroupBeanList;


    /** The beadlots category bean. */
    private BeadlotsCategoryBean beadlotsCategoryBean;

    /**
     * The authoredInstallDocsList.
     */
    private List<InstallationsInstructionsModel> authoredInstallDocsList = new ArrayList<>();

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("Inside BeadLotsModelImpl Init");
        populateInstallationsInstructions(multipleInstallationsInstructions);
        populateAuthoredBeadlotsGroup(partNumberMultiField);
        beadlotsGroupBeanList = new ArrayList<>();

            if (null != CommonConstants.BEADLOT_BASE_PATH) {

                for (BeadlotsGroupModel beadlotsGroupModel : authoredBeadlotsGroupList) {
                    beadlotsGroupBeanList = setBeadlotsGroupBeanList(beadlotsGroupModel,categoryLabel);
                }

                beadlotsCategoryBean = new BeadlotsCategoryBean();
                beadlotsCategoryBean.setCategoryLabel(categoryLabel);
                beadlotsCategoryBean.setBeadlotsCategoryTitle(beadlotsCategoryTitle);
                if(CollectionUtils.isNotEmpty(beadlotsGroupBeanList) && CollectionUtils.isNotEmpty(beadlotsGroupBeanList.get(0).getFiles())) {
                	beadlotsCategoryBean.setBeadlotsCategoryTitle(getResolvedBeadlotsCategoryTitle(beadlotsGroupBeanList.get(0).getDescription(), beadlotsCategoryTitle));
                	//GE-17540 subtitle no need to show up
                    // beadlotsCategoryBean.setBeadlotsCategorySubTitle(CommonConstants.FOR + CommonConstants.SPACE + beadlotsGroupBeanList.get(0).getFiles().get(0).getSoftwareVersion());
                	beadlotsCategoryBean.setBeadlotsCategorySubTitle("");
                }
                beadlotsCategoryBean.setStatusLabel(statusLabel);
                beadlotsCategoryBean.setPartNumberLabel(partNumberLabel);
                beadlotsCategoryBean.setBeadlotsFilesTitle(beadlotsFilesTitle);
                beadlotsCategoryBean.setGetDocumentsBeanList(authoredInstallDocsList);
                beadlotsCategoryBean.setBeadlotsGroupBeanList(beadlotsGroupBeanList);
                beadlotsCategoryBean.setShowStatus(showStatus);

            }
    }

    /**
     * Gets the document json.
     *
     * @param filesJsonArray the files json array
     * @param beadlotFileResource the beadlot file resource
     * @return the document json
     * @throws ParseException the parse exception
     */
    private void getDocumentJson(JsonArray filesJsonArray, Resource beadlotFileResource) throws ParseException {
        JsonObject beadlotFileJson = new JsonObject();
        ValueMap beadlotFileValueMap = beadlotFileResource.getValueMap();
        beadlotFileJson.addProperty("documentNumber", beadlotFileValueMap.get("documentNumber").toString());
        beadlotFileJson.addProperty("regStatus", beadlotFileValueMap.get("regStatus").toString());
        beadlotFileJson.addProperty("releaseDate", beadlotFileValueMap.get("releaseDate").toString());
        beadlotFileJson.addProperty("documentPart", beadlotFileValueMap.get("documentPart").toString());
        beadlotFileJson.addProperty("documentType", beadlotFileValueMap.get("documentType").toString());
        beadlotFileJson.addProperty("documentVersion", beadlotFileValueMap.get("documentVersion").toString());
        beadlotFileJson.addProperty("softwareVersion", beadlotFileValueMap.get("softwareVersion").toString());
        beadlotFileJson.addProperty("status", beadlotFileValueMap.get("status").toString());
        beadlotFileJson.addProperty("batchExpiryDate", beadlotFileValueMap.get("batchExpiryDate").toString());
        if(null != beadlotFileValueMap.get("batchExpiryDate")) {
        	  String beadLotDate = beadlotFileValueMap.get("batchExpiryDate").toString();
              String regex = "\\d{4}(0[1-9]|1[012])(0[1-9]|[12][0-9]|[3][01])";        
              Pattern pattern = Pattern.compile(regex);
              Matcher matcher = pattern.matcher(beadLotDate);
              if(matcher.matches()) {
              	  SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMdd");
                    Date formatedBeadLotdate = formatter.parse(beadLotDate);
                    boolean dateMatched = beadlotExpiryDate(formatedBeadLotdate);
                    if(dateMatched) {
                    	 filesJsonArray.add(beadlotFileJson);	
                    }
              }  	
        }
    }
    
    /**
     * Beadlot expiry date.
     *
     * @param endDate the end date
     * @return true, if successful
     */
    public boolean beadlotExpiryDate(Date endDate) {
    	  boolean bool = false;
    	  Date curDate = new Date();
    	  if(curDate.before(endDate)) {
    	    bool = true;
    	  }
    	  return bool;
    	}
    
    /**
     * The Class CustomComparator.
     */
    public class CustomComparator implements Comparator<BeadlotFileBean> {
        
        /**
         * Compare.
         *
         * @param o1 the o 1
         * @param o2 the o 2
         * @return the int
         */
        @Override
        public int compare(BeadlotFileBean o1, BeadlotFileBean o2) {
            if (null != o1.getReleaseDate() && null != o2.getReleaseDate()) {
                Date o1Date = formatDate(o1.getReleaseDate());
                Date o2Date = formatDate(o2.getReleaseDate());

                if (null != o1Date && null != o2Date) {
                    return (o2Date).compareTo(o1Date);
                }
            }
            return -1;

        }
    }


    /**
     * Format date.
     *
     * @param date the date
     * @return the date
     */
    public Date formatDate(String date) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(date);
        } catch (ParseException pe) {
            logger.error("Parse exception in simple date format");
        }
        return null;
    }

    /**
     * Gets the path.
     *
     * @param beadlotsPath the beadlots path
     * @param materialNumber the Material number
     * @param resourceResolver the resource resolver
     * @return the path
     */
    private String getPath(String beadlotsPath, String materialNumber, ResourceResolver resourceResolver) {
        logger.debug("Inside function getPath");
        Map<String, String> params = new HashMap<>();
        params.put("path", beadlotsPath);
        params.put("type", JcrResourceConstants.NT_SLING_FOLDER);
        params.put("property", MATERIAL_NUMBER);
        params.put("property.value", materialNumber);
        params.put("p.limit", "1");

        Query query = CommonHelper.getQuery(resourceResolver, params);
        SearchResult result = query.getResult();
        try {
            if (null != result && result.getTotalMatches() > 0) {
                Hit hit = result.getHits().get(0);
                logger.debug("hit.getPath()- {}", hit.getPath());
                return hit.getPath();
            }
        } catch (RepositoryException e) {
            logger.error("RepositoryException", e);
        }
        return null;
    }

    /**
     * Populates the Industry List.
     *
     * @param resourceList the resource list
     */

    protected void populateAuthoredBeadlotsGroup(List<Resource> resourceList) {
        if (resourceList != null && !resourceList.isEmpty()) {

            for (Resource resource : resourceList) {
                BeadlotsGroupModel materialNumberGroup = resource.adaptTo(BeadlotsGroupModel.class);
                authoredBeadlotsGroupList.add(materialNumberGroup);
            }
        }
    }

    /**
     * Populates the Industry List.
     *
     * @param resourceList the resource list
     */

    protected void populateInstallationsInstructions(List<Resource> resourceList) {
        if (CollectionUtils.isNotEmpty(resourceList)) {

            for (Resource resource : resourceList) {
                InstallationsInstructionsModel installationsInstructionsModel = resource.adaptTo(InstallationsInstructionsModel.class);
                authoredInstallDocsList.add(installationsInstructionsModel);
            }

        }
    }

    /**
     * Sets the beadlots group bean list.
     *
     * @param beadlotsGroupModel the beadlots group model
     * @param categoryLabel the category label
     * @return the list
     */
    private List<BeadlotsGroupBean> setBeadlotsGroupBeanList(BeadlotsGroupModel beadlotsGroupModel, String categoryLabel) {

        String beadlotsContentHPPath;
        Resource materialNumberResource = null;

        beadlotsContentHPPath = CommonConstants.BEADLOT_BASE_PATH;

        List<String> materialNumberPathList = new ArrayList<>();

        //set the multiple Material numbers
        List<BeadlotsMaterialNumberBean> materialNumberBeans = new ArrayList<>();

        for (BeadlotsPartNumbersModel partNumberModel :
                beadlotsGroupModel.getAuthoredPartNumbersList()) {
            materialNumberBeans.add(new BeadlotsMaterialNumberBean(partNumberModel.getPartNumber(), partNumberModel.getBadge()));
            materialNumberPathList.add(getPath(beadlotsContentHPPath, partNumberModel.getPartNumber(), resourceResolver));

        }

        JsonArray filesJsonArray = new JsonArray();
        List<BeadlotFileBean> list;
        String description = StringUtils.EMPTY;

        for (String materialNumberPath : materialNumberPathList
                ) {
            if (StringUtils.isNotBlank(materialNumberPath)) {

                materialNumberResource = resourceResolver.getResource(materialNumberPath);

                JsonObject materialNumberJson = new JsonObject();

                if (null != materialNumberResource && materialNumberResource.hasChildren()) {

                    ValueMap materialNumberValueMap = materialNumberResource.getValueMap();
                    materialNumberJson.addProperty(PART_NUMBER, materialNumberValueMap.get(MATERIAL_NUMBER).toString());
                    materialNumberJson.addProperty("description", materialNumberValueMap.get("materialDescription").toString());
                    description = materialNumberValueMap.get("materialDescription").toString();
                    for (Resource beadlotFileResource : materialNumberResource.getChildren()) {
                        //for each document under a Material number.
                        try {
							getDocumentJson(filesJsonArray, beadlotFileResource);
						} catch (ParseException e) {
							logger.error("Date ParseException",e);
						}
                    }
                    materialNumberJson.add("beadlotFiles", filesJsonArray);
                }
            }
        }

        if(null != filesJsonArray) {
            Gson gson = new Gson();

            list = gson.fromJson(filesJsonArray, new TypeToken<List<BeadlotFileBean>>() {
            }.getType());
            
            if(!list.isEmpty()) {
            	
            	//Eliminating duplicate documents  
            	Set<BeadlotFileBean> filesSet = new HashSet <>(list);
                List<BeadlotFileBean> filesList = new ArrayList <>(filesSet);
                list = filesList;
            	
            	String regStatus = list.get(list.size()-1).getRegStatus();
            	Collections.sort(list, new CustomComparator());
                // Generate random integers in range 0 to 999
                String uniqueID = UUID.randomUUID().toString();
                BeadlotsGroupBean beadlotsGroupBean = new BeadlotsGroupBean();
                beadlotsGroupBean.setId(categoryLabel.replaceAll("\\s", "") +"_"+uniqueID);
                beadlotsGroupBean.setMaterialNumberBeans(materialNumberBeans);
                beadlotsGroupBean.setDescription(description);
                beadlotsGroupBean.setRegStatus(regStatus);
                beadlotsGroupBean.setInformation(beadlotsGroupModel.getInformation());
                beadlotsGroupBean.setInstallationInstructionsLabel(beadlotsGroupModel.getInstallationInstructionsLabel());
                beadlotsGroupBean.setInstallationInstructionsLink(beadlotsGroupModel.getInstallationInstructionsLink());
                beadlotsGroupBean.setUpdaterFileLabel(beadlotsGroupModel.getUpdaterFileLabel());
                beadlotsGroupBean.setUpdaterFileLink(beadlotsGroupModel.getUpdaterFileLink());
                beadlotsGroupBean.setFiles(list);

                beadlotsGroupBeanList.add(beadlotsGroupBean);

            }
        }

        return new ArrayList<>(beadlotsGroupBeanList);
    }

    /**
     * Gets the beadlots category title.
     *
     * @return the beadlots category title
     */
    @Override
    public String getBeadlotsCategoryTitle() {
        return beadlotsCategoryTitle;
    }

    /**
     * Gets the beadlots category sub title.
     *
     * @return the beadlots category sub title
     */
    @Override
    public String getBeadlotsCategorySubTitle() {
        return beadlotsCategorySubTitle;
    }

    /**
     * Gets the status label.
     *
     * @return the status label
     */
    @Override
    public String getStatusLabel() {
        return statusLabel;
    }

    /**
     * Gets the part number label.
     *
     * @return the part number label
     */
    @Override
    public String getPartNumberLabel() {
        return partNumberLabel;
    }

    /**
     * Gets the beadlots files title.
     *
     * @return the beadlots files title
     */
    @Override
    public String getBeadlotsFilesTitle() {
        return beadlotsFilesTitle;
    }

    /**
     * Gets the beadlots category bean.
     *
     * @return the beadlots category bean
     */
    @Override
    public BeadlotsCategoryBean getBeadlotsCategoryBean() {
        return beadlotsCategoryBean;

    }

    /**
     * gets the  BeadlotsCategoryBeanList.
     *
     * @return the beadlotsCategoryBeanList
     */
    @Override
    public List<BeadlotsGroupBean> getBeadlotsGroupBeanList() {
        if (null != beadlotsGroupBeanList) {
            return new ArrayList<>(beadlotsGroupBeanList);
        } else {
            return Collections.emptyList();
        }

    }
    
    public String getResolvedBeadlotsCategoryTitle(String fromBeadlotNode, String fromAuthoring) {
    	return StringUtils.isNotBlank(fromBeadlotNode) ? fromBeadlotNode : fromAuthoring;
    }
}
