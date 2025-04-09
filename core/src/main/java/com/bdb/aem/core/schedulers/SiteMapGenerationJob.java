/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.bdb.aem.core.schedulers;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.SiteMapService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.xml.stream.XMLStreamException;


/**
 * A simple demo for cron-job like tasks that get executed regularly.
 * It also demonstrates how property values can be set. Users can
 * set the property values in /system/console/configMgr
 */
@Designate(ocd = SiteMapGenerationJob.Config.class)
@Component(service = Runnable.class, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class SiteMapGenerationJob implements Runnable {

    @ObjectClassDefinition(name = "SiteMap Generator Job",
            description = "Periodically generates sitemap.xml in author and replicates it")
    public static @interface Config {
    	
    	@AttributeDefinition(name = "SiteMap Generation Configuration", description = "SiteMap Generation Configuration")
	    public String schedulerName() default "SiteMap Generation Configuration";

        @AttributeDefinition(name = "Cron-job expression")
        String scheduler_expression() default "0 0 */12 ? * *";

        @AttributeDefinition(name = "Concurrent task",
                description = "Whether or not to schedule this task concurrently")
        boolean scheduler_concurrent() default false;

        @AttributeDefinition(name = "Content Path",
                description = "Available paths to traverse")
        String[] contentPaths() default {"/content/bdb/na/us/en-us", "/content/bdb/na/ca/en-ca",
                "/content/bdb/eu/at/en-at", "/content/bdb/eu/be/en-be", "/content/bdb/eu/dk/en-dk", "/content/bdb/eu/fi/en-fi",
                "/content/bdb/eu/fr/en-fr", "/content/bdb/eu/de/en-de", "/content/bdb/eu/ie/en-ie", "/content/bdb/eu/it/en-it",
                "/content/bdb/eu/lu/en-lu", "/content/bdb/eu/nl/en-nl", "/content/bdb/eu/no/en-no", "/content/bdb/eu/pl/en-pl",
                "/content/bdb/eu/pt/en-pt", "/content/bdb/eu/es/en-es", "/content/bdb/eu/se/en-se", "/content/bdb/eu/ch/en-ch",
                "/content/bdb/eu/gb/en-gb", "/content/bdb/eu/eu/en-eu", "/content/bdb/apac/au/en-au", "/content/bdb/apac/nz/en-nz",
                "/content/bdb/apac/tw/en-tw", "/content/bdb/apac/kr/ko-kr", "/content/bdb/apac/in/en-in", "/content/bdb/apac/sg/en-sg", "/content/bdb/apac/jp/ja-jp",
                "/content/bdb/cn/cn/zh-cn", "/content/bdb/latam/br/en-br"};
        
        @AttributeDefinition(name = "Country Level Path",
                description = "Available paths to traverse")
        String[] countryPaths() default {"/content/bdb/na/us", "/content/bdb/na/ca",
                "/content/bdb/eu/at", "/content/bdb/eu/be", "/content/bdb/eu/dk", "/content/bdb/eu/fi", "/content/bdb/eu/fr",
                "/content/bdb/eu/de", "/content/bdb/eu/ie", "/content/bdb/eu/it", "/content/bdb/eu/lu", "/content/bdb/eu/nl",
                "/content/bdb/eu/no", "/content/bdb/eu/pl", "/content/bdb/eu/pt", "/content/bdb/eu/es", "/content/bdb/eu/se",
                "/content/bdb/eu/ch", "/content/bdb/eu/gb","/content/bdb/eu/il", "/content/bdb/eu/eu", "/content/bdb/apac/au", "/content/bdb/apac/nz",
                "/content/bdb/apac/tw", "/content/bdb/apac/kr", "/content/bdb/apac/in", "/content/bdb/apac/sg", "/content/bdb/apac/jp",
                "/content/bdb/cn/cn", "/content/bdb/latam/br"};
        
        @AttributeDefinition(name = "Region Level Path",
                description = "Available paths to traverse")
        String[] regionPaths() default {"/content/bdb/na","/content/bdb/eu","/content/bdb/latam","/content/bdb/apac","/content/bdb/cn"};
        
        @AttributeDefinition(name = "Root Path",
                description = "Available paths to traverse")
        String[] rootPath() default "/content/bdb";

        @AttributeDefinition(name = "Enabled",
                description = "Check to enable the scheduler")
        boolean isEnabled() default false;
        
        @AttributeDefinition(name = "Default Limit",
                description = "Default Limit")
        int defaultLimit() default 50000;
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private SiteMapService siteMapService;

    /** The resolver factory. */
    @Reference
    ResourceResolverFactory resolverFactory;
    
    @Reference
	private Scheduler scheduler;
    
    private boolean isEnabled;

    private String[] paths;
    
    private String[] countryPaths;
    
    private String[] regionPaths;
    
    private String[] rootPath;
    
    private int schedulerID;
    
    private int defaultLimit;


    /** The bdb api endpoint service. */
    @Reference
    BDBApiEndpointService bdbApiEndpointService;

    @Override
    public void run() {
        ResourceResolver resourceResolver = null;
        logger.debug("SiteMapGenerationJob is now running, paths='{}'", paths);
        if(!isAuthor() || !isEnabled)
        {
            logger.debug("SiteMap Generation Job not activated, either run mode is not author or job is disabled");
            return;
        } 
        try {
        	resourceResolver = CommonHelper.getServiceResolverReplication(resolverFactory);
            siteMapService.generateSiteMap(paths, resourceResolver,defaultLimit);
            siteMapService.generateMasterIndexSiteMap(countryPaths, resourceResolver);
            siteMapService.generateMasterIndexSiteMap(regionPaths, resourceResolver);
            siteMapService.generateMasterIndexSiteMap(rootPath, resourceResolver);
        } catch (LoginException | RepositoryException | XMLStreamException e) {
            logger.error("Error occurred in generating SiteMap from scheduler", e);
        } finally {
			if (null != resourceResolver && resourceResolver.isLive()) {
				resourceResolver.close();
			}
		} 
        logger.debug("Stopped Run Method :: SiteMap Generation Job");
    }

    @Activate
    @Modified
    protected void activate(final Config config) {
        logger.debug(" SiteMap Generation OSGi Service ACTIVATED ");
        removeScheduler();
        this.schedulerID = config.schedulerName().hashCode();
        isEnabled = config.isEnabled();
        paths = config.contentPaths();
        countryPaths = config.countryPaths();
        regionPaths = config.regionPaths();
        rootPath = config.rootPath();
        defaultLimit = config.defaultLimit();
        addScheduler(config);
    }

    private boolean isAuthor() {
        return StringUtils.equalsIgnoreCase(this.bdbApiEndpointService.getCustomRunMode(), CommonConstants.AUTHOR);
    }
    
    @Deactivate
	protected void deactivate(final Config config) {
		removeScheduler();
	}
    
    /**
	  * Remove a scheduler based on the scheduler ID
	  */
	 private void removeScheduler() {
	  logger.debug("Removing Scheduler Job '{}'", schedulerID);
	  scheduler.unschedule(String.valueOf(schedulerID));
	 }
	 
	 /**
	  * Add a scheduler based on the scheduler ID
	  */
	 private void addScheduler(final Config config) {
	   ScheduleOptions sopts = scheduler.EXPR(config.scheduler_expression());
	   sopts.name(String.valueOf(schedulerID));
	   sopts.canRunConcurrently(false);
	   scheduler.schedule(this, sopts);
	   logger.debug("Scheduler added succesfully");
	 }
}
