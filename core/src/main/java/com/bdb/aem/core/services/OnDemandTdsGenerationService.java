package com.bdb.aem.core.services;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.xml.sax.SAXException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface OnDemandTdsGenerationService {
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
     * @throws ParserConfigurationException
     */
    ByteArrayOutputStream getPdfStream(Resource variant, ResourceResolver resourceResolver, String skuName, String locale) throws IOException, TransformerException, SAXException, RepositoryException, ParserConfigurationException;
}