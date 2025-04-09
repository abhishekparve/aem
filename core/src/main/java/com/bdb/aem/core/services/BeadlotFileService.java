package com.bdb.aem.core.services;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.google.gson.JsonObject;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

/**
 * The Interface BeadlotFileService.
 */
public interface BeadlotFileService {


    /**
     * Fetch beadlot file details.
     *
     * @param request       the request
     * @param requestObject the request object
     * @param response      the response
     * @return the string
     * @throws AemInternalServerErrorException the aem internal server error exception
     */
    public String fetchBeadLotFileDetails(SlingHttpServletRequest request, JsonObject requestObject,
                                     SlingHttpServletResponse response) throws AemInternalServerErrorException;
}
