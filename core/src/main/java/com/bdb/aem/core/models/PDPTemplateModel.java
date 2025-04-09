package com.bdb.aem.core.models;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * Model to derive Template Id from HP data.
 */
@Model(adaptables = { SlingHttpServletRequest.class,
        Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class PDPTemplateModel {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(PDPTemplateModel.class);
    
    /** The request. */
    @Inject
    SlingHttpServletRequest request;
    
    /** The resource resolver. */
    @SlingObject
    ResourceResolver resourceResolver;
   
    @Inject
    Page currentPage;
    
    /** Template Id of the Material Number. */
    private String templateId;

    /** The sfa template path. */
    @Inject
    @Via("resource")
    String sfa;

    /** The scm template path. */
    @Inject
    @Via("resource")
    String scm;


    /** The Others template path. */
    @Inject
    @Via("resource")
    String otherProducts;


    /** The Kits and Sets template path. */
    @Inject
    @Via("resource")
    String kitsAndSets;


    /** The Instruments template path. */
    @Inject
    @Via("resource")
    String instruments;


    /**
     * Gives Template Id of the PDP Page product.
     *
     * @throws LoginException the login exception
     */
    @PostConstruct
    protected void init() throws LoginException {

        logger.debug("Inside PDPTemplateModel");
        
        Resource hpBaseResource = null;
		if (null != request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)) {
			String productVarHPPath = request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH).toString();
			hpBaseResource = resourceResolver.getResource(productVarHPPath);
		}
        if(null !=hpBaseResource)
        {
            ValueMap hpValueMap=hpBaseResource.getValueMap();
            templateId= hpValueMap.get("pdpTemplate", StringUtils.EMPTY);
        }
        String urlFormate = CommonHelper.getRegion(currentPage)+"/"+CommonHelper.getCountry(currentPage)+"/"+CommonHelper.getLanguage(currentPage)+"-"+CommonHelper.getCountry(currentPage);
        logger.debug("urlFormate {} " , urlFormate);
        if(StringUtils.contains(sfa, "language-masters/en"))
            sfa = sfa.replace("language-masters/en",urlFormate);
        logger.debug("sfa {} " , sfa);

        if(StringUtils.contains(scm, "language-masters/en"))
            scm = scm.replace("language-masters/en",urlFormate);
        logger.debug("scm {} ", scm);

        if(StringUtils.contains(otherProducts, "language-masters/en"))
            otherProducts = otherProducts.replace("language-masters/en",urlFormate);
        logger.debug("otherProducts {} ", otherProducts);

        if(StringUtils.contains(kitsAndSets, "language-masters/en"))
            kitsAndSets = kitsAndSets.replace("language-masters/en",urlFormate);
        logger.debug("kitsAndSets {}", kitsAndSets);

        if(StringUtils.contains(instruments, "language-masters/en"))
            instruments = instruments.replace("language-masters/en",urlFormate);
        logger.debug("instruments {} ", instruments);


        logger.debug("End of PDPTemplateModel");
    }

    /**
     * Gets the template id.
     *
     * @return Template Id
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * Gets the SFA template
     * @return
     */
    public String getSfa() {
        return sfa;
    }

    /**
     * Gets the SCM template
     * @return
     */

    public String getScm() {
        return scm;
    }

    /**
     * Gets the Others template
     * @return
     */
    public String getOtherProducts() {
        return otherProducts;
    }

    /**
     * Gets the Kits template
     * @return
     */
    public String getKitsAndSets() {
        return kitsAndSets;
    }

    /**
     * Gets the Instruments template
     * @return
     */
    public String getInstruments() {
        return instruments;
    }
}
