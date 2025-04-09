package com.bdb.aem.core.services;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

public interface GenerateFailedImageReportService {

    public void generateFailedImageReport(ResourceResolver serviceResolver, Resource failedRecordsResource, Session session, String failedImagesReportPath);

}