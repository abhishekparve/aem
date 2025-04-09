package com.bdb.aem.core.servlets;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
 import com.bdb.aem.core.services.BeadlotFileService;
 import com.bdb.aem.core.util.CommonHelper;
 import com.google.gson.Gson;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
 import io.wcm.testing.mock.aem.junit5.AemContextExtension;
 @ExtendWith({ AemContextExtension.class, MockitoExtension.class })
 class BeadlotsFileDownloadServletTest {
 
 	@InjectMocks
 	BeadlotsFileDownloadServlet beadLotsFileDownloadServlet;
 	
 	Logger LOGGER = LoggerFactory.getLogger(BeadlotsFileDownloadServlet.class);
 	
 	@Mock
 	SlingHttpServletRequest request;
 	
 	@Mock
 	BufferedReader reader;
 	
 	@Mock
 	SlingHttpServletResponse response;
 	
 	@Mock
 	BeadlotFileService beadlotFileService;
 	
 	@Mock
 	PrintWriter writer;
 	
	@Test
	void testDoPost() throws IOException {
		lenient().when(request.getReader()).thenReturn(reader);
		lenient().when(response.getWriter()).thenReturn(writer);
		
		beadLotsFileDownloadServlet.doPost(request, response);
	}
	
	@Test
	void testDoPostException() throws IOException, AemInternalServerErrorException {
		String content = "{"
				+ "}";
		JsonObject requestObject = new JsonParser().parse(content).getAsJsonObject();
		lenient().when(request.getReader()).thenReturn(reader);
		lenient().when(response.getWriter()).thenReturn(writer);
		lenient().when(beadlotFileService.fetchBeadLotFileDetails(request, requestObject, response)).thenThrow(AemInternalServerErrorException.class);
		beadLotsFileDownloadServlet.doPost(request, response);
	}
}
