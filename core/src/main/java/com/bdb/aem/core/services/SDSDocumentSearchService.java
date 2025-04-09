package com.bdb.aem.core.services;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;

import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.google.gson.JsonArray;

/**
 * The Interface SDSDocumentSearchService.
 */
public interface SDSDocumentSearchService {

   
    /**
     * Search SDS document.
     *
     * @param country the country
     * @param materialNumber the material number
     * @return the json array
     */
    public JsonArray searchSDSDocument(String country, String materialNumber);
    
    /**
     * Download SDS document.
     *
     * @param fileID the file ID
     * @param requestConfig the request config
     * @return the http response
     * @throws AemInternalServerErrorException 
     */
    public HttpResponse downloadSDSDocument(String fileID, RequestConfig requestConfig) throws AemInternalServerErrorException;


}
