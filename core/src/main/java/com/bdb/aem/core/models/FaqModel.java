package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.ExternalizerService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.Getter;

/**
 * The Class FaqModel.
 */

@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FaqModel {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FaqModel.class);

	/** The faq data. */
	@ChildResource
	private List<FaqSectionsModel> faqData;

	/** The expand all. */
	@ValueMapValue
	private String expandAll;

	/** The faq header. */
	@ValueMapValue
	private String faqHeader;
	
	/** The show search bar. */
	@ValueMapValue
	private Boolean showSearchBar;

	/** The search placeholder. */
	@ValueMapValue
	private String searchPlaceholder;

	/** The close product icon. */
	@ValueMapValue
	private String closeProductIcon;

	/** The showing label. */
	@ValueMapValue
	@JsonProperty("showing")
	private String showingLabel;

	/** The results for label. */
	@ValueMapValue
	@JsonProperty("resultsFor")
	private String resultsForLabel;

	/** The new search label. */
	@ValueMapValue
	@JsonProperty("emptySearchResultInfo")
	private String newSearchLabel;

	/** The no search result icon. */
	@ValueMapValue
	@JsonProperty("emptySearchResultIcon")
	private String noSearchResultIcon;

	/** The no search result icon alt text. */
	@ValueMapValue
	@JsonProperty("emptySearchResultAltText")
	private String noSearchResultIconAltText;
	
	/** The collapse all. */
	@ValueMapValue
	private String collapseAll;
	
	/** The accordion active icon. */
	@ValueMapValue
	@Getter
	private String accordionActiveIcon;
	
	/** The accordion in active icon. */
	@ValueMapValue
	@Getter
	private String accordionInActiveIcon;

	/** The json data. */
	@JsonIgnore
	private String jsonData;
	
	/** The resource resolver. */
	ResourceResolver resourceResolver;

	/** The resource. */
	@Inject
	private Resource resource;

	/** The externalizer service. */
    @Inject
    ExternalizerService externalizerService;
    
    /** The Faq question and answers. */
    private String faqSchemaData;
	

	/**
	 * Inits the.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	@PostConstruct
	protected void init() throws JsonProcessingException {
		this.updateSlingModel();
		LOGGER.debug("Into FAQ Model");
		
		resourceResolver = resource.getResourceResolver();
		noSearchResultIcon = externalizerService.getFormattedUrl(noSearchResultIcon, resourceResolver);
	}

	/**
	 * Update sling model.
	 *
	 * @throws JsonProcessingException the json processing exception
	 */
	private void updateSlingModel() throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(this);
		this.setJsonData(json);
		faqSchemaData = getFaqArray(json);
		
		
	}

	/**
	 * Gets the faq array.
	 *
	 * @param json the json
	 * @return the faq array
	 */
	public String getFaqArray(String json) {
		JsonArray mainEntityArray = new JsonArray();
		JsonObject questionJsonLabel = new JsonObject();
		JsonObject answerJsonLabel = new JsonObject();
		JsonParser parser = new JsonParser();
		if(StringUtils.isNotBlank(json)) {
		JsonObject faqJson = (JsonObject) parser.parse(json);
		if(null != faqJson.getAsJsonArray("faqData")) {
			JsonArray faqDataArray = faqJson.getAsJsonArray("faqData");
			for(JsonElement faqJsonObject :faqDataArray) {
				JsonObject faqItemObj = faqJsonObject.getAsJsonObject();
				if(null != faqItemObj.getAsJsonArray("questionsMap")) {
				getQuestionAndAnswers(mainEntityArray, questionJsonLabel, answerJsonLabel, faqItemObj);
				}
			}
		}
	 }
		return mainEntityArray.toString();
	}

	public void getQuestionAndAnswers(JsonArray mainEntityArray, JsonObject questionJsonLabel,
			JsonObject answerJsonLabel, JsonObject faqItemObj) {
		JsonArray questionsMapArray = faqItemObj.getAsJsonArray("questionsMap");
		for(JsonElement questionElement :questionsMapArray) {
			 
			 if(null != questionElement.getAsJsonObject().get("answer") ) {
				 answerJsonLabel.addProperty("@type", "Answer");
				 answerJsonLabel.addProperty("text", questionElement.getAsJsonObject().get("answer").getAsString());
			 }
			 if(null != questionElement.getAsJsonObject().get("question")) {
				 questionJsonLabel.addProperty("@type", "Question");
				 questionJsonLabel.addProperty("name", questionElement.getAsJsonObject().get("question").getAsString()); 
			 }
			 questionJsonLabel.add("acceptedAnswer", answerJsonLabel);
			 mainEntityArray.add(questionJsonLabel);
		}
	}

	/**
	 * Gets the faq header.
	 *
	 * @return the faq header
	 */
	public String getFaqHeader() {
		return faqHeader;
	}

	/**
	 * Gets the show search bar.
	 *
	 * @return the show search bar
	 */
	public Boolean getShowSearchBar() {
		return showSearchBar;
	}

	/**
	 * Gets the faq data.
	 *
	 * @return the faq data
	 */
	public List<FaqSectionsModel> getFaqData() {

		return Optional.ofNullable(faqData).filter(s -> !s.isEmpty()).orElseGet(ArrayList::new);

	}

	/**
	 * Gets the search placeholder.
	 *
	 * @return the search placeholder
	 */
	public String getSearchPlaceholder() {
		return searchPlaceholder;
	}

	/**
	 * Gets the close product icon.
	 *
	 * @return the close product icon
	 */
	public String getCloseProductIcon() {
		return closeProductIcon;
	}

	/**
	 * Gets the expand all.
	 *
	 * @return the expand all
	 */
	public String getExpandAll() {
		return expandAll;
	}

	/**
	 * Gets the collapse all.
	 *
	 * @return the collapse all
	 */
	public String getCollapseAll() {
		return collapseAll;
	}

	/**
	 * Gets the showing label.
	 *
	 * @return the showing label
	 */
	public String getShowingLabel() {
		return Optional.ofNullable(showingLabel).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the results for label.
	 *
	 * @return the results for label
	 */
	public String getResultsForLabel() {
		return Optional.ofNullable(resultsForLabel).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the new search label.
	 *
	 * @return the new search label
	 */
	public String getNewSearchLabel() {
		return Optional.ofNullable(newSearchLabel).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the no search result icon.
	 *
	 * @return the no search result icon
	 */
	public String getNoSearchResultIcon() {
		return Optional.ofNullable(noSearchResultIcon).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the no search result icon alt text.
	 *
	 * @return the no search result icon alt text
	 */
	public String getNoSearchResultIconAltText() {
		return Optional.ofNullable(noSearchResultIconAltText).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the json data.
	 *
	 * @return the json data
	 */
	public String getJsonData() {
		return jsonData;
	}

	/**
	 * Sets the json data.
	 *
	 * @param jsonData the new json data
	 */
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	/**
	 * Gets the faq schema data.
	 *
	 * @return the faq schema data
	 */
	public String getFaqSchemaData() {
		return faqSchemaData;
	}

}
