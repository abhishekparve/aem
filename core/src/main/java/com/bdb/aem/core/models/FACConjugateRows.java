package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * The FAC Conjugate Rows.
 * 
 * @author ronbanerjee
 *
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FACConjugateRows extends BaseSlingModel {

	/**  The constant Serial Version UUID */
	private static final long serialVersionUID = -2097326043162460554L;

	@ValueMapValue
	private String rowHeading;
	
	@ChildResource
	private List<FACConjugateColumns> columns;
	
	/**
	 * Gets the list of columns
	 * 
	 * @return the list of columns
	 */
	public List<FACConjugateColumns> getColumns(){
		return Optional.ofNullable(columns).filter(c -> !c.isEmpty()).orElseGet(ArrayList::new);
	}
	
	/**
	 * Gets the row heading.
	 * 
	 * @return the row heading
	 */
	public String getRowHeading() {
		return rowHeading;
	}
}
