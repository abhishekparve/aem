package com.bdb.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * The Class RteTableModel.
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class RteTableModel {
    /**
     * The logger.
     */
    Logger logger = LoggerFactory.getLogger(RteTableModel.class);
    /**
     * The current resource.
     */
    @Inject
    Resource currentResource;
    /**
     * The sectionName.
     */
    @Inject
    private String sectionTitle;
    /**
     * The title.
     */
    @Inject
    private String title;

    @PostConstruct
    protected void init() {

    }

    /**
     * Gets the article id.
     *
     * @return the article id
     */
    public String getArticleId() {
        return currentResource.getParent().getName() + "-" + currentResource.getName();
    }

    /**
     * Gets the section name.
     *
     * @return the section name
     */
    public String getSectionTitle() {
        return sectionTitle;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }
}
