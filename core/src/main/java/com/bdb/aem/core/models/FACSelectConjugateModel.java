package com.bdb.aem.core.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.FACSelectConjugateServiceImpl;
import com.bdb.aem.core.util.CommonConstants;

/**
 * The FAC Select Conjugate Model.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACSelectConjugateModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = -8309859890282636981L;

	@ValueMapValue
	private String conjugateName;
	
	@ChildResource
	private List<FACConjugateRows> rows;

	@OSGiService
	private transient FACSelectConjugateServiceImpl facSelectService;

	@ValueMapValue
	private String gatingHierarchyURL;

	@ValueMapValue
	private String barImageLinkUrl;
	
	@ValueMapValue
	private String viewProductURL;
	
	/** The resource resolver. */
	@SlingObject
    ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Inject
	ExternalizerService externalizerService;
	private Map<String, Serializable> statisticsImageTable = new HashMap<>();

	
	/**
	 * Gets the statistics image table.
	 * 
	 * @return the statistic image table
	 */
	public Map<String, Serializable> getStatisticsImageTable() {
		return statisticsImageTable;
	}

	/**
	 * Sets the statistics image table.
	 * 
	 * @param statisticsImageTable
	 */
	public void setStatisticsImageTable(Map<String, Serializable> statisticsImageTable) {
		this.statisticsImageTable = statisticsImageTable;
	}

	/**
	 * Gets the list of rows.
	 * 
	 * @return the list of rows
	 */
	public List<FACConjugateRows> getConjugateRows() {
		return Optional.ofNullable(rows).filter(r -> !r.isEmpty()).orElseGet(ArrayList::new);
	}

	/**
	 * @see BaseSlingModel#getDataService()
	 */
	@Override
	public SlingModelService getDataService() {
		return facSelectService;
	}

	/**
	 * Gets the gating hierarchy URL.
	 * 
	 * @return gating hierarchy URL
	 */
	public String getGatingHierarchyURL() {
		return Optional.ofNullable(gatingHierarchyURL).filter(StringUtils::isNotEmpty)
				.map(g -> g.contains(CommonConstants.HASH)
						? externalizer.getFormattedUrl(g.split(CommonConstants.HASH)[0], getResolver())
								.concat(CommonConstants.HASH).concat(g.split(CommonConstants.HASH)[1])
						: externalizer.getFormattedUrl(g, getResolver()))
				.orElse(StringUtils.EMPTY);

	}

	/**
	 * Gets the View Product URL
	 * 
	 * @return the View Product URL
	 */
	public String getViewProductURL() {
		return Optional.ofNullable(viewProductURL).filter(StringUtils::isNotEmpty)
				.map(v -> externalizer.getFormattedUrl(v, getResolver())).orElse(StringUtils.EMPTY);

	}
	
	/**
	 * Gets the conjuagte ID.
	 * 
	 * @return the conjagate ID
	 */
	public String getConjugateId() {
		return Optional.ofNullable(conjugateName).filter(StringUtils :: isNotEmpty).map( n -> n.toLowerCase().replaceAll(" ", "_")).orElse(StringUtils.EMPTY);
	}
	
	/**
	 * Gets the conjuagte ID.
	 * 
	 * @return the Combine columnNames
	 */
	public List<String> getCombineColumnName() {
		List <String> arry = new ArrayList<String>();
		for(FACConjugateRows r1 :rows) {
			arry.add(r1.getRowHeading());
		}
		return arry;
	}

	/**
	 * @return the barImageLinkUrl
	 */
	public String getBarImageLinkUrl() {
		return externalizerService.getFormattedUrl(barImageLinkUrl, resourceResolver);
	}
	
}
