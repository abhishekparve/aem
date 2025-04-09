package com.bdb.aem.core.models.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.models.SearchAdminPageModel;
import com.bdb.aem.core.pojo.Payload;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.BDBSearchEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.bdb.aem.core.util.ExcludeUtil;
import com.day.cq.wcm.api.Page;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.Getter;


/**
 * The Class SearchAdminPageModelImpl.
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class },
		adapters = { SearchAdminPageModel.class },
		resourceType = { SearchAdminPageModelImpl.RESOURCE_TYPE },
		defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class SearchAdminPageModelImpl implements SearchAdminPageModel {

	/** The Constant RESOURCE_TYPE. */
	protected static final String RESOURCE_TYPE = "bdb-aem/components/content/searchadmin/v1/searchadmin";

	/** The Constant log. */
	protected static final Logger log = LoggerFactory.getLogger(SearchAdminPageModelImpl.class);


	/** The bdb search endpoint service. */
	@Inject
	private BDBSearchEndpointService bdbSearchEndpointService;
	
	/** The bdb api endpoint service. */
    @Inject
    BDBApiEndpointService bdbApiEndpointService;
    
    /** The resolver factory. */
    @Inject
    ResourceResolverFactory resolverFactory;

	/** The request. */
	@Self
	private SlingHttpServletRequest request;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;

	/** The current page. */
	@Inject
	private Page currentPage;


	/** The language. */
	@Inject
	@Via("resource")
	@Default(values = StringUtils.EMPTY)
	private String language;

	/** The synonym label. */
	@Inject
	@Via("resource")
	@Named("synonymLabel")
	@Default(values = StringUtils.EMPTY)
	private String synonymLabel;

	/** The stop word label. */
	@Inject
	@Via("resource")
	@Named("stopWordLabel")
	@Default(values = StringUtils.EMPTY)
	private String stopWordLabel;

	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String stopWord;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String search;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String existing;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String addNewStopWord;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String save;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String reloadSolr;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String reloadSolrSuccessMsg;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String managedMappings;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String addNewSynonym;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String from;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String to;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String delete;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String deleteQuestion;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String deleteSuccessMsg;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String addedSynonym;
	
	@Inject
	@Via("resource")
	@Getter
	@Default(values = StringUtils.EMPTY)
	private String cancel;

	/** The search admin screen config. */
	private String searchAdminScreenConfig;
	
	/** The get synonym results. */
	private String getSynonymResults;
	
	/** The delete synonym. */
	private String deleteSynonym;
	
	/** The create synonym. */
	private String createSynonym;
	
	/** The reload solr server. */
	private String reloadSolrServer;
	
	/** The get stop word results. */
	private String getStopWordResults;
	
	/** The create stop word. */
	private String createStopWord;
	
	/** The remove selected stop word. */
	private String removeSelectedStopWord;
	
	/** The refresh solr collection. */
	private String refreshSolrCollection;
	
	/** The language list. */
	private String languageList;
	
	/** The Synonym Screen Labels */
	@Getter
	private String synonymScreenLabels;



	/**
	 * Inits the.
	 */
	@PostConstruct
	void init() {
		log.debug("Inside Synonym Screen Init Method");

		ExcludeUtil excludeUtilObject = new ExcludeUtil();
		if (bdbSearchEndpointService != null) {
			setSynonymScreenConfig(excludeUtilObject);
		}
		synonymScreenLabelsLangList();
		synonymScreenLabels = setSynonymLabels();
	}


	/**
	 * set synonym labels
	 * @return string
	 */
	private String setSynonymLabels() {
		JsonObject synonymLabels = new JsonObject();
		synonymLabels.addProperty(CommonConstants.SYNONYM_LABEL, synonymLabel);
		synonymLabels.addProperty(CommonConstants.STOP_WORDS_LABEL, stopWordLabel);
		synonymLabels.addProperty(CommonConstants.STOP_WORD_LABEL, stopWord);
		synonymLabels.addProperty(CommonConstants.SEARCH_LABEL, search);
		synonymLabels.addProperty(CommonConstants.EXISTING_LABEL, existing);
		synonymLabels.addProperty(CommonConstants.ADD_NEW_STOP_WORD_LABEL, addNewStopWord);
		synonymLabels.addProperty(CommonConstants.SAVE, save);
		synonymLabels.addProperty(CommonConstants.RELOAD_SOLR, reloadSolr);
		synonymLabels.addProperty(CommonConstants.RELOAD_SUCCESS_MSG, reloadSolrSuccessMsg);
		synonymLabels.addProperty(CommonConstants.MANAGED_MAPPINGS, managedMappings);
		synonymLabels.addProperty(CommonConstants.ADD_NEW_SYNONYM_LABEL, addNewSynonym);
		synonymLabels.addProperty(CommonConstants.FROM_LABEL, from);
		synonymLabels.addProperty(CommonConstants.TO_LABEL, to);
		synonymLabels.addProperty(CommonConstants.LANGUAGE, language);
		synonymLabels.addProperty(CommonConstants.DELETE_LABEL, delete);
		synonymLabels.addProperty(CommonConstants.DELETE_QUESTION_LABEL, deleteQuestion);
		synonymLabels.addProperty(CommonConstants.DELETE_SUCCESS_MSG, deleteSuccessMsg);
		synonymLabels.addProperty(CommonConstants.CANCEL, cancel);
		synonymLabels.addProperty(CommonConstants.ADDED_SYNONYM, addedSynonym);
		return synonymLabels.toString();
		
	}



	/**
	 * Sets the synonym screen config.
	 *
	 * @param excludeUtilObject the new synonym screen config
	 */
	private void setSynonymScreenConfig(ExcludeUtil excludeUtilObject) {
		log.debug("Inside Synonym Screen Config Method");
		Gson json = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
				.addSerializationExclusionStrategy(excludeUtilObject).create();

		//set get Synonym Results
		Payload getSynonymResultsPayload = new Payload(
				bdbSearchEndpointService.getSynonymResultsServlet(),
				HttpConstants.METHOD_GET);
		this.setGetSynonymResults(json.toJson(getSynonymResultsPayload));


		//set delete Synonym
		Payload deleteSynonymPayload = new Payload(
				bdbSearchEndpointService.getDeleteSynonymServlet(),
				HttpConstants.METHOD_POST);

		this.setDeleteSynonym(json.toJson(deleteSynonymPayload));

		// set create Synonym
		Payload createSynonymPayload = new Payload(
				bdbSearchEndpointService.getCreateSynonymServlet(),
				HttpConstants.METHOD_POST);

		this.setCreateSynonym(json.toJson(createSynonymPayload));
		
		// set reload solr server
		Payload reloadSolrServerPayload = new Payload(
				bdbSearchEndpointService.getReloadSolrServerServlet(),
				HttpConstants.METHOD_POST);

		this.setReloadSolrServer(json.toJson(reloadSolrServerPayload));
		
		//set get StopWord Results
		Payload getStopWordResultsPayload = new Payload(
				bdbSearchEndpointService.getGetStopWordResultsServlet(),
				HttpConstants.METHOD_GET);

		this.setGetStopWordResults(json.toJson(getStopWordResultsPayload));

		// set create StopWord
		Payload createStopWordPayload = new Payload(
				bdbSearchEndpointService.getCreateStopWordServlet(),
				HttpConstants.METHOD_POST);

		this.setCreateStopWord(json.toJson(createStopWordPayload));

		//set remove Selected StopWord
		Payload removeSelectedStopWordPayload = new Payload(
				bdbSearchEndpointService.getRemoveSelectedStopWordServlet(),
				HttpConstants.METHOD_POST);
		this.setRemoveSelectedStopWord(json.toJson(removeSelectedStopWordPayload));
		
		//set Refresh Solr collection
		Payload refreshSolrCollectionPayload = new Payload(
				bdbSearchEndpointService.getSolrUrl() + bdbSearchEndpointService.getRefreshSolrCollection() + bdbSearchEndpointService.getContentPageCollectionName(),
				HttpConstants.METHOD_GET);
		this.setRefreshSolrCollection(json.toJson(refreshSolrCollectionPayload));

	}

	
	/**
	 * Synonym screen labels language list.
	 */
	private void synonymScreenLabelsLangList() {
		try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)){
			if (null != bdbApiEndpointService && null != resourceResolver) {
				Resource genericListResource = resourceResolver.getResource(bdbApiEndpointService.getLanguageSearchDropdownEndpoint());
				languageList = CommonHelper.getGenericList (genericListResource, resourceResolver);
        	}
        } catch (LoginException e) {
            log.error("LoginException {}", e.getMessage());
        }
	}
	
	/**
	 * Gets the language list.
	 *
	 * @return the language list
	 */
	public String getLanguageList() {
		return languageList;
	}
	
	/**
	 * Gets the search admin screen config.
	 *
	 * @return the search admin screen config
	 */
	public String getSearchAdminScreenConfig() {
		return searchAdminScreenConfig;
	}


	/**
	 * Gets the gets the synonym results.
	 *
	 * @return the gets the synonym results
	 */
	public String getGetSynonymResults() {
		return getSynonymResults;
	}

	/**
	 * Sets the gets the synonym results.
	 *
	 * @param getSynonymResults the new gets the synonym results
	 */
	public void setGetSynonymResults(String getSynonymResults) {
		this.getSynonymResults = getSynonymResults;
	}

	/**
	 * Gets the delete synonym.
	 *
	 * @return the delete synonym
	 */
	public String getDeleteSynonym() {
		return deleteSynonym;
	}

	/**
	 * Sets the delete synonym.
	 *
	 * @param deleteSynonym the new delete synonym
	 */

	public void setDeleteSynonym(String deleteSynonym) {
		this.deleteSynonym = deleteSynonym;
	}

	/**
	 * Gets the creates the synonym.
	 *
	 * @return the creates the synonym
	 */
	public String getCreateSynonym() {
		return createSynonym;
	}

	/**
	 * Sets the creates the synonym.
	 *
	 * @param createSynonym the new creates the synonym
	 */
	public void setCreateSynonym(String createSynonym) {
		this.createSynonym = createSynonym;
	}
	
	/**
	 * Gets the reload solr server.
	 *
	 * @return the reload solr server
	 */
	public String getReloadSolrServer() {
		return reloadSolrServer;
	}

	/**
	 * Sets the reloads solr server.
	 *
	 * @param reloadSolrServer the reloads all solr server
	 */
	public void setReloadSolrServer(String reloadSolrServer) {
		this.reloadSolrServer = reloadSolrServer;
	}

	/**
	 * Gets the gets the stop word results.
	 *
	 * @return the gets the stop word results
	 */
	public String getGetStopWordResults() {
		return getStopWordResults;
	}

	/**
	 * Sets the gets the stop word results.
	 *
	 * @param getStopWordResults the new gets the stop word results
	 */
	public void setGetStopWordResults(String getStopWordResults) {
		this.getStopWordResults = getStopWordResults;
	}

	/**
	 * Gets the creates the stop word.
	 *
	 * @return the creates the stop word
	 */
	public String getCreateStopWord() {
		return createStopWord;
	}

	/**
	 * Sets the creates the stop word.
	 *
	 * @param createStopWord the new creates the stop word
	 */
	public void setCreateStopWord(String createStopWord) {
		this.createStopWord = createStopWord;
	}

	/**
	 * Gets the removes the selected stop word.
	 *
	 * @return the removes the selected stop word
	 */
	public String getRemoveSelectedStopWord() {
		return removeSelectedStopWord;
	}

	/**
	 * Sets the removes the selected stop word.
	 *
	 * @param removeSelectedStopWord the new removes the selected stop word
	 */
	public void setRemoveSelectedStopWord(String removeSelectedStopWord) {
		this.removeSelectedStopWord = removeSelectedStopWord;
	}

	/**
	 * Gets the synonym label.
	 *
	 * @return the synonym label
	 */
	public String getSynonymLabel() {
		return synonymLabel;
	}

	/**
	 * Gets the stop word label.
	 *
	 * @return the stop word label
	 */

	public String getStopWordLabel() {
		return stopWordLabel;
	}

	/**
	 * Gets the refresh solr collection.
	 *
	 * @return the refresh solr collection
	 */

	public String getRefreshSolrCollection() {
		return refreshSolrCollection;
	}

	/**
	 * Sets the refresh solr collection.
	 *
	 * @param refreshSolrCollection the new refresh solr collection
	 */
	public void setRefreshSolrCollection(String refreshSolrCollection) {
		this.refreshSolrCollection = refreshSolrCollection;
	}

}