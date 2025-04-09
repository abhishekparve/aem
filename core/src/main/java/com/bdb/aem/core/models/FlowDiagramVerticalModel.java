package com.bdb.aem.core.models;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;

/**
 * The Class FlowDiagramVerticalModel.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
		Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class FlowDiagramVerticalModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(FlowDiagramVerticalModel.class);
     
	/** The title. */
    @Inject
	@Via("resource")
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String componentTitle;
    
	/** The title. */
    @Inject
	@Via("resource")
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String subTitle;
    
	/** The title. */
    @Inject
	@Via("resource")
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String contentIcon;
    
    /** The title. */
    @Inject
	@Via("resource")
    @Getter
    @Default(values = StringUtils.EMPTY)
    private String description;
    
    @Inject
	@Via("resource")
    @Getter
	@Default(values = StringUtils.EMPTY)
	private String backgroundColor;
       
	/** Tabs - Title, Description, Image and Image Caption. */
	@Inject
	@Via("resource")
	@Getter
	private List<FlowDiagramVerticalMutlifieldModel> tabSection;

    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        logger.debug("CategoryCaseStudyModel Initialized :: ");
        
    }
}
