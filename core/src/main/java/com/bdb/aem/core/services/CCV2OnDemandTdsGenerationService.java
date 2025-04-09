package com.bdb.aem.core.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.solr.client.solrj.SolrServerException;
import org.xml.sax.SAXException;

import com.day.cq.tagging.InvalidTagFormatException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;

public interface CCV2OnDemandTdsGenerationService {
	
	/**
     * function will generate PDF for sku having DocumentType as ON_DEMAND_TDS and return it's pdf Stream.
     *
     * @param variant
     * @param resourceResolver
     * @param skuName
     * @param locale
     * @return pdfStream
     * @throws IOException
     * @throws TransformerException
     * @throws SAXException
     * @throws RepositoryException
     * @throws JsonProcessingException
     * @throws ParserConfigurationException
     */
    public ByteArrayOutputStream getPdfStream(String jsonString, ResourceResolver resourceResolver, String skuName, String locale) throws IOException, TransformerException, SAXException, RepositoryException, ParserConfigurationException;

}
