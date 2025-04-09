package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.ExporterOption;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

import com.bdb.aem.core.services.tools.SlingModelService;
import com.bdb.aem.core.services.tools.impl.FACSelectListServiceImpl;
import com.day.cq.wcm.api.Page;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The FAC Select List Model
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class, Resource.class }, resourceType = {
		"bdb-aem/components/content/tools/facselect/reportsList" }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = "jackson", extensions = "json", options = {
		@ExporterOption(name = "SerializationFeature.WRITE_DATES_AS_TIMESTAMPS", value = "true") })
public class FACSelectListModel extends BaseSlingModel {

	/** The constant Serial Version UUID */
	private static final long serialVersionUID = 2177127213481069747L;

	private List<FACReportsModel> reportsList;

	@OSGiService
	private transient FACSelectListServiceImpl facSelectService;

	@ChildResource
	private List<FACListNavigation> navigation;

	@Override
	public SlingModelService getDataService() {
		return facSelectService;
	}

	/**
	 * Gets the FAC Reports List
	 * 
	 * @return the FAC Reports List
	 */
	public List<FACReportsModel> getReportsList() {
		return Optional.ofNullable(reportsList).orElseGet(ArrayList::new);
	}

	/**
	 * Sets the FAC Reports list
	 * 
	 * @param reportsList
	 */
	public void setReportsList(List<FACReportsModel> reportsList) {
		this.reportsList = Collections.unmodifiableList(reportsList);
	}
	
	/**
	 * Gets the navigation items.
	 * 
	 * @return the navigation items
	 */
	@JsonIgnore
	public List<FACListNavigation> getNavigation() {
		return Optional.ofNullable(navigation).orElseGet(ArrayList::new);
	}

	/**
	 * Gets the FAC Data Endpoint
	 * 
	 * @return
	 */
	@JsonIgnore
	public String getFacDataEndpoint() {
		return Optional.ofNullable(page).map(Page::getPath)
				.map(p -> p.concat("/jcr:content/root/reportslist.model.tidy.json")).orElse(StringUtils.EMPTY);
	}

}
