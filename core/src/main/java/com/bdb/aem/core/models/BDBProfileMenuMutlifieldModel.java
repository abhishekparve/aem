package com.bdb.aem.core.models;

import javax.inject.Inject;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.Getter;
import lombok.ToString;

/**
 * The Class BDBProfileMenuMutlifieldModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@ToString( exclude = {"resolverFactory","resourceResolver","logger"} )
public class BDBProfileMenuMutlifieldModel {
	/** The logger. */
	protected Logger logger = LoggerFactory.getLogger(BDBProfileMenuMutlifieldModel.class);

	/** The label. */
	@Inject
	@Getter
	private String label;

	/** The alt text. */
	@Inject
	@Getter
	private String altText;
	
	/** The image source. */
	@Inject
	@Getter
	private String imgSrc;
	
	/** The image source mobile. */
	@Inject
	@Getter
	private String imgSrcM;
	
	/** The id. */
	@Inject
	@Getter
	private String id;
	
	/** The redirectURL. */
	@Inject
	private String redirectURL;
	
	/**
	 * @return redirectURL
	 */
	public String getRedirectURL() {
		return redirectURL;
	}

}
