package com.bdb.aem.core.servlets;

import com.bdb.aem.core.util.CommonHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The Class BaseServlet.
 */
public class BaseServlet extends SlingAllMethodsServlet {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(BaseServlet.class);

    /**
     * Gets the request object.
     *
     * @param request the request
     * @return the request object
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected JsonObject getRequestObject(SlingHttpServletRequest request)
            throws IOException, JsonSyntaxException, JsonProcessingException { 

        final BufferedReader reader = request.getReader();
        final StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        String data = sb.toString();
        LOG.debug("getRequestObject() Request JSON String : {}", data);
        JsonObject object = new JsonObject();
        if (StringUtils.isNotEmpty(data)) {
            JsonElement element = CommonHelper.getGsonInstance().fromJson(data, JsonElement.class);
            if (element != null) {
                object = element.getAsJsonObject();
                return object;
            }
        }
        return object;
    }

    /**
     * Gets the request object Array.
     *
     * @param request the request
     * @return the request object Array
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected JsonArray getRequestObjectArray(SlingHttpServletRequest request)
            throws IOException {

        final BufferedReader reader = request.getReader();
        final StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        String data = sb.toString();
        LOG.debug("getRequestObjectArray() Request JSON String : {}", data);
        JsonArray object = new JsonArray();
        if (StringUtils.isNotEmpty(data)) {
            JsonElement element = CommonHelper.getGsonInstance().fromJson(data, JsonElement.class);
            if (element != null) {
                object = element.getAsJsonArray();
                return object;
            }
        }
        return object;
    }
    
    protected String getRequestObjectString(SlingHttpServletRequest request)
            throws IOException {

        final BufferedReader reader = request.getReader();
        String line = reader.readLine();
        reader.close();
      
        return line;
    }


}