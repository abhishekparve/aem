package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.models.AdobeLaunchScriptsModel;
import com.bdb.aem.core.services.AdobeLaunchScriptsService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

@Model(adaptables = { SlingHttpServletRequest.class},adapters = AdobeLaunchScriptsModel.class)
public class AdobeLaunchScriptsModelImpl implements AdobeLaunchScriptsModel {


    protected static final Logger logger = LoggerFactory.getLogger(AdobeLaunchScriptsModelImpl.class);
    private String launchScriptUrl;

    @Inject
    AdobeLaunchScriptsService adobeLaunchScriptsService;


    @Override
    public String getLaunchScriptUrl() {
        return launchScriptUrl;
    }


    public String launchScriptUrl() {
    return adobeLaunchScriptsService.getLaunchScripts();

    }

    @PostConstruct
    protected void init() {
        this.launchScriptUrl = launchScriptUrl();

    }

}
