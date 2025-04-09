package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.akamai.ccu.v3.*;
import com.bdb.aem.core.services.AkamaiPurgeService;
import com.bdb.aem.core.services.PageCollectionService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;
import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.ReplicationResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.BoundedInputStream;
import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;


/**
 * The Class AkamaiPurgeServiceImpl.
 */
@Component(service = AkamaiPurgeService.class, immediate = true)
@Designate(ocd = AkamaiPurgeServiceImpl.Configuration.class)
public class AkamaiPurgeServiceImpl implements AkamaiPurgeService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AkamaiPurgeServiceImpl.class);

    /**
     * Replication agent default CCUP API type value.
     */
    private static final String PROPERTY_CCU_API_V2 = "V2";

    /**
     * Replication agent default type value.
     */
    private static final String PROPERTY_TYPE_DEFAULT = "arl";

    /**
     * Replication agent default domain value.
     */
    private static final String PROPERTY_DOMAIN_DEFAULT = "production";

    /**
     * Replication agent default action value.
     */
    private static final String PROPERTY_ACTION_DEFAULT = "invalidate";

    /**
     * the constant ccu_v3
     */
    private static final String CCU_V3 = "/ccu/v3/";

    /**
     * the constant URL
     */
    private static final String URL = "/url/";

    /**
     * Default timeout value
     */
    private static final String DEFAULT_TIMEOUT = "5000";

    /**
     * Constant Empty String
     */
    private static final String EMPTY_STRING = "";

    private String accessTokenProperty;
    private String akamaiActionProperty;
    private String akamaiDomainProperty;
    private String akamaiTypeProperty;
    private String ccuapitypeProperty;
    private String clientSecretProperty;
    private String clientTokenProperty;
    private String connectionTimeoutProperty;
    private String enableDispatcherInvalidationProperty;
    private String hostHeaderProperty;
    private String openAuthServiceURLProperty;
    private String socketTimeoutProperty;

    /**
     * The resolver factory.
     */
    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    PageCollectionService pageCollectionService;

    @Override
    public ReplicationResult purgePage(final ReplicationAction replicationAction) {

        LOGGER.debug("Entry purgePage of AkamaiPurgeServiceImpl");

        LOGGER.debug("ccuapitypeProperty {}" , ccuapitypeProperty);
        final String type = PropertiesUtil.toString(ccuapitypeProperty, PROPERTY_CCU_API_V2);

        if (type.equals(PROPERTY_CCU_API_V2)) {
            LOGGER.error("CCU API Type V2 Selected Cannot Handle Replication");
        } else {
            try {
                postCCUV3Call(replicationAction);
            } catch (ReplicationException re) {
                LOGGER.error("Exception in Akamai Agent Failing Replication Result", re);
                return new ReplicationResult(false, 0, "Replication failed");
            }
        }
        return ReplicationResult.OK;

    }

    /**
     * Method to invalidate or delete akamai's cache
     * @param resource - list of resource
     * @return
     */
    @Override
    public ReplicationResult purgeAssets(List<String> resource) {
        LOGGER.info("Entry purgeAssets method of AkamaiPurgeServiceImpl");

        /** Initializing all variables as per the env configuration */
        final String ccuAPIType = PropertiesUtil.toString(ccuapitypeProperty, PROPERTY_CCU_API_V2);
        final String openAuthServiceURL = PropertiesUtil.toString(openAuthServiceURLProperty, EMPTY_STRING);
        final String type = PropertiesUtil.toString(akamaiTypeProperty, PROPERTY_TYPE_DEFAULT);
        final String domain = PropertiesUtil.toString(akamaiDomainProperty, PROPERTY_DOMAIN_DEFAULT);
        final String action = PropertiesUtil.toString(akamaiActionProperty, PROPERTY_ACTION_DEFAULT);

        if (ccuAPIType.equals(PROPERTY_CCU_API_V2)) {
            LOGGER.error("CCU API Type V2 Selected Cannot Handle Replication");
        } else {

            /** Resource resolver */
            ResourceResolver resolver = null;

            /** Response of HTTP request */
            HttpResponse response;



            try {
                resolver = CommonHelper.getReadServiceResolver(resolverFactory);

                if (type.equals(PROPERTY_TYPE_DEFAULT) && !resource.isEmpty()) {

                    StringBuilder sb = new StringBuilder();
                    sb.append(CCU_V3).append(action).append(URL).append(domain);

                    String invalidateDomain = sb.toString();

                    LOGGER.debug("Invalidate Domain : {} ", invalidateDomain);

                    /** Getting the body of post request */
                    String entity = getPurgeJSON(resource,resolver);
                    LOGGER.debug("Entity Details : {} ", entity);

                    /** Calling Akamai API with required details */
                    response = callAkamaiAPI(openAuthServiceURL + invalidateDomain, entity);
                    /** function to read the response */
                    readAkamaiResponse(response);

                }else {
                    LOGGER.debug("\n **************** Nothing to Purge In Akamai *********************** \n");
                }

            } catch (LoginException | ReplicationException | IOException e){
                LOGGER.error("Exception occurred while purging akamai's cache {}", e.getMessage());
            }finally {
                if(null != resolver && resolver.isLive()){
                    resolver.close();
                }
            }
        }
        LOGGER.info("Exit purgeAssets method of AkamaiPurgeServiceImpl");
        return ReplicationResult.OK;
    }

    /**
     * function to get domain of publish environment as per the request
     * @param resolver
     * @param path
     * @return
     */
    private String getDomain(ResourceResolver resolver, String path) {
        Externalizer externalizer = resolver.adaptTo(Externalizer.class);
        return externalizer.externalLink(resolver, Externalizer.PUBLISH, path);
    }

    /**
     * function to return request body JSON as string
     * @param resource - list of resource path
     * @param resolver
     * @return
     */
    private String getPurgeJSON(List<String> resource, ResourceResolver resolver) {
        StringBuilder jsonBuff = new StringBuilder();
        jsonBuff.append("{\"objects\":[");
        for(int i=0;i<resource.size();i++){
            String fileURI = getDomain(resolver,resource.get(i));
            LOGGER.debug("JSON fileURI Path", fileURI);
            jsonBuff.append("\"");
            jsonBuff.append(fileURI);
            jsonBuff.append("\"");
            if(i < resource.size()-1) jsonBuff.append(",");
        }
        jsonBuff.append("]}");
        return jsonBuff.toString();
    }

    /**
     * Post ccu v3 call.
     *
     * @param tx the tx
     * @return the http response
     * @throws ReplicationException the replication exception
     */
    private void postCCUV3Call(final ReplicationAction tx) throws ReplicationException {

        LOGGER.debug("start method postCCUV3Call ");
        final String type = PropertiesUtil.toString(akamaiTypeProperty, PROPERTY_TYPE_DEFAULT);
        final String openAuthServiceURL = PropertiesUtil.toString(openAuthServiceURLProperty, EMPTY_STRING);
        final String enableDispatcherInvalidation = PropertiesUtil.toString(enableDispatcherInvalidationProperty, EMPTY_STRING);
        final String domain = PropertiesUtil.toString(akamaiDomainProperty, PROPERTY_DOMAIN_DEFAULT);
        final String action = PropertiesUtil.toString(akamaiActionProperty, PROPERTY_ACTION_DEFAULT);

        ResourceResolver resolver = null;

        String postDataJSON = null;
        CCUPostData postData = new CCUPostData();
        List<String> replicationPages = new ArrayList<>();
        String invalidateDomain = null;
        HttpResponse response = null;
        try {
            resolver = CommonHelper.getReadServiceResolver(resolverFactory);

            if (type.equals(PROPERTY_TYPE_DEFAULT)) {
                final String link = tx.getPath();
                if (StringUtils.isNotBlank(link)) {

                    if (StringUtils.equals(domain, PROPERTY_DOMAIN_DEFAULT) && StringUtils.equals(action, PROPERTY_ACTION_DEFAULT)) {
                        invalidateDomain = CCUV3Constants.ARL_INVALIDATE_URL_PROD_PURGING;
                    } else if (StringUtils.equals(domain, PROPERTY_DOMAIN_DEFAULT) && !StringUtils.equals(action, PROPERTY_ACTION_DEFAULT)) {
                        invalidateDomain = CCUV3Constants.ARL_REMOVE_URL_PROD_PURGING;
                    } else if (!StringUtils.equals(domain, PROPERTY_DOMAIN_DEFAULT) && StringUtils.equals(action, PROPERTY_ACTION_DEFAULT)) {
                        invalidateDomain = CCUV3Constants.ARL_INVALIDATE_URL_STAGE_PURGING;
                    } else {
                        invalidateDomain = CCUV3Constants.ARL_REMOVE_URL_STAGE_PURGING;
                    }

                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("invalidateDomain :: {}", invalidateDomain);
                        LOGGER.debug("enableDispatcherInvalidation :: {}", enableDispatcherInvalidation);
                    }



                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("[RAW LINK] link to activate: {}", link);
                    }
                    replicationPages = pageCollectionService.getReplicationPages(link, resolver);
                    replicationPages = pageCollectionService.getShortUrls(replicationPages, resolver, true, true);

                }
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("FINAL PURGE LIST FOR AKAMAI {} coming from replicationPages ", replicationPages);

            }
            LOGGER.debug("FINAL PURGE LIST FOR AKAMAI {} ", replicationPages);
            if (!replicationPages.isEmpty()) {
                int length = 0;
                boolean isAkamaiCallDone = false;
                List<String> postList = new ArrayList<>();
                for (int k = 0; k < replicationPages.size(); k++) {
                    length = length + replicationPages.get(k).length();
                    if (length < 1500) {
                        postList.add(replicationPages.get(k));
                    } else {
                        postList.add(replicationPages.get(k));
                        postData.setObjects(postList);
                        postDataJSON = postData.toJSON();

                        if (LOGGER.isDebugEnabled()) {
                            LOGGER.debug("PurgeObject JSON:: {}" , postDataJSON);
                            LOGGER.debug("URI:: {} {}" , openAuthServiceURL , invalidateDomain);
                        }

                        response = callAkamaiAPI(openAuthServiceURL + invalidateDomain, postDataJSON);
                        readAkamaiResponse(response);
                        if (k == replicationPages.size() - 1) {
                            isAkamaiCallDone = true;
                            break;
                        }
                        length = 0;
                        postList = new ArrayList<>();
                        postData = new CCUPostData();
                        postDataJSON = StringUtils.EMPTY;
                    }
                }
                if (!isAkamaiCallDone) {
                    postData.setObjects(postList);
                    postDataJSON = postData.toJSON();
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("PurgeObject JSON:: {}", postDataJSON);
                        LOGGER.debug("URI:: {}{}", openAuthServiceURL , invalidateDomain);
                    }

                    response = callAkamaiAPI(openAuthServiceURL + invalidateDomain, postDataJSON);
                    readAkamaiResponse(response);
                }
            } else {
                LOGGER.debug("\n **************** Nothing to Purge In Akamai *********************** \n");
            }
        }
        catch (IOException | LoginException e) {
            LOGGER.error("Exception, Could not retrieve content from content builder", e);
        } finally {
            if (resolver != null && resolver.isLive()) {
                resolver.close();
            }
        }
    }

    private HttpResponse callAkamaiAPI(String uriString, String postDataJSON) throws ReplicationException {

        final String clientToken = PropertiesUtil.toString(clientTokenProperty, EMPTY_STRING);
        final String accessToken = PropertiesUtil.toString(accessTokenProperty, EMPTY_STRING);
        final String clientSecret = PropertiesUtil.toString(clientSecretProperty, EMPTY_STRING);
        final String hostHeader = PropertiesUtil.toString(hostHeaderProperty, EMPTY_STRING);
        final String socketTimeout = PropertiesUtil.toString(socketTimeoutProperty, DEFAULT_TIMEOUT);
        final String connectionTimeout = PropertiesUtil.toString(connectionTimeoutProperty, DEFAULT_TIMEOUT);

        HttpResponse response = null;
        HttpPost httpPost = null;
        try {
            final URI uri = new URI(uriString);
            httpPost = new HttpPost(uri);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Host", hostHeader);

            final HttpClient httpclient = HttpClientBuilder.create().build();
            final StringEntity jsonEntity = new StringEntity(postDataJSON);
            httpPost.setEntity(jsonEntity);

            final ClientCredential credential = new DefaultClientCredential(clientToken, accessToken, clientSecret);

            //Perform CCU V3 Call
            final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(Integer.parseInt(socketTimeout)).setConnectTimeout(Integer.parseInt(connectionTimeout)).build();
            httpPost.setConfig(requestConfig);

            final AkamaiEdgeGridSigner signer = new AkamaiEdgeGridSignerImpl(Collections.emptyList(), 131072);
            final HttpRequestBase signedRequest = signer.sign(httpPost, credential);

            LOGGER.debug("Akamai httpPost .getEntity() :: "+ httpPost.getEntity());

            response = httpclient.execute(signedRequest);
        } catch (AkamaiException e) {
            throw new ReplicationException("RequestSigningException, Could not retrieve content from content builder", e);
        } catch (IOException e) {
            throw new ReplicationException("IOException, Could not retrieve content from content builder", e);
        } catch (URISyntaxException e) {
            throw new ReplicationException("URISyntaxException, Could not retrieve content from content builder", e);
        } finally {
            if (null != httpPost) {
                httpPost.releaseConnection();
            }
        }
        return response;
    }

    private void readAkamaiResponse(HttpResponse response) throws IOException, ReplicationException  {
        int statusCode = 0;
        StringBuilder responseString = new StringBuilder();

        if (response != null) {
            statusCode = response.getStatusLine().getStatusCode();
            try   ( StringBuilderWriter writer = new StringBuilderWriter(responseString)) {
                BoundedInputStream boundedInput = new BoundedInputStream(response.getEntity().getContent(), CommonConstants.SIZE_4B);
                BufferedReader reader = new BufferedReader(new InputStreamReader(boundedInput), CommonConstants.SIZE_2B);
            
                IOUtils.copy(reader, writer);
                responseString = writer.getBuilder();
            } catch (IllegalStateException e) {
                throw new ReplicationException("Could not retrieve response from Akamai", e);
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("\n AKAMAI Response String :: {} \n", responseString);
            }
            LOGGER.debug("\n AKAMAI Response String :: {} \n", responseString);
            LOGGER.debug("AKAMAI RESPONSE STATUS CODE:: {}", statusCode);
        } else {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Response IS NULL  ");
            }
            LOGGER.debug("Response IS NULL  ");
        }
    }

    public void processDispatcherFlush(String contentPath, List<DispatcherDetailsBean> dispatcherDetails, String country) {
        if(LOGGER.isDebugEnabled()) {
            LOGGER.debug("contentPath - {}", contentPath);
        }
        contentPath = contentPath.replace(CommonConstants.CONST_BDB_ROOT_PATH + CommonConstants.SINGLE_SLASH + country, EMPTY_STRING);
        for(DispatcherDetailsBean bean : dispatcherDetails) {
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("dispatcherDomain - {}", bean.getDispatcherDomain());
                LOGGER.debug("dispatcherUser - {}", bean.getUser());
                LOGGER.debug("dispatcherPassword - {}", bean.getPassword());
            }
            callDispatcherToInvalidate(contentPath, bean);
        }
    }
    @SuppressWarnings("squid:S2647")
    public void callDispatcherToInvalidate(String contentPath, DispatcherDetailsBean bean) {
        LOGGER.debug("In callDispatcherToInvalidate()");

        //make the flush call here
        HttpResponse response = null;
        HttpPost httpPost = null;
        StringBuilder responseString = new StringBuilder();
        try {
            final URI uri = new URI(bean.getDispatcherDomain() + "/dispatcher/invalidate.cache");
            httpPost = new HttpPost(uri);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString((bean.getUser() + ":" + bean.getPassword()).getBytes()));
            httpPost.setHeader("CQ-Action", "Activate");
            httpPost.setHeader("CQ-Handle", contentPath);
            httpPost.setHeader("Host", "flush");
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("Sending Dispatcher Invalidation Request for {}", contentPath);
            }
            final HttpClient httpclient = HttpClientBuilder.create().build();
            response = httpclient.execute(httpPost);

        } catch (URISyntaxException |  IOException e ) {
            LOGGER.error("Exception", e);
        }
        finally {
            if(httpPost!=null) {
                httpPost.releaseConnection();
            }
        }
        if (response != null) {
            try ( StringBuilderWriter writer = new StringBuilderWriter(responseString)){
                BoundedInputStream boundedInput = new BoundedInputStream(response.getEntity().getContent(),CommonConstants.SIZE_4B);
                BufferedReader reader = new BufferedReader(new InputStreamReader(boundedInput), CommonConstants.SIZE_2B);
               
                IOUtils.copy(reader, writer);
                responseString = writer.getBuilder();

            } catch (IOException e) {
                LOGGER.error("IOException", e);
            }
            if(LOGGER.isDebugEnabled()) {
                LOGGER.debug("responseString.toString() : {}" , responseString);
            }
        }
        LOGGER.debug("Out callDispatcherToInvalidate()");
    }

    /**
     * The Interface Configuration.
     */
    @ObjectClassDefinition(name = "BDB Akamai Configuration")
    public @interface Configuration {

        /**
         * ccuapitype
         *
         * @return the string
         */
        @AttributeDefinition(name = "ccuapitype", description = "PROPERTY_CCU_API_TYPE", type = AttributeType.STRING)
        public String ccuapitype() default PROPERTY_CCU_API_V2;

        /**
         * akamaiType
         *
         * @return the string
         */
        @AttributeDefinition(name = "akamaiType", description = "PROPERTY_TYPE", type = AttributeType.STRING)
        public String akamaiType() default PROPERTY_TYPE_DEFAULT;

        /**
         * akamaiDomain
         *
         * @return the string
         */
        @AttributeDefinition(name = "akamaiDomain", description = "PROPERTY_DOMAIN", type = AttributeType.STRING)
        public String akamaiDomain() default PROPERTY_DOMAIN_DEFAULT;

        /**
         * akamaiAction
         *
         * @return the string
         */
        @AttributeDefinition(name = "akamaiAction", description = "PROPERTY_ACTION", type = AttributeType.STRING)
        public String akamaiAction() default PROPERTY_ACTION_DEFAULT;

        /**
         * clientToken
         *
         * @return the string
         */
        @AttributeDefinition(name = "clientToken", description = "PROPERTY_CLIENT_TOKEN", type = AttributeType.STRING)
        public String clientToken() default "akab-pbmam57ann6aev2a-ygp3xcngshrm6w47";


        /**
         * accessToken
         *
         * @return the string
         */
        @AttributeDefinition(name = "accessToken", description = "PROPERTY_ACCESS_TOKEN", type = AttributeType.STRING)
        public String accessToken() default "akab-ycorzwdmvak55qzj-zu2uu2jtcornosfp";

        /**
         * clientSecret
         *
         * @return the string
         */
        @AttributeDefinition(name = "clientSecret", description = "PROPERTY_CLIENT_SECRET", type = AttributeType.STRING)
        public String clientSecret() default "k0hQFVxJlSka0C2IT06ANLFLMewUW3KYfVsDLKwy5vE=";

        /**
         * hostHeader
         *
         * @return the string
         */
        @AttributeDefinition(name = "hostHeader", description = "PROPERTY_HOST_HEADER", type = AttributeType.STRING)
        public String hostHeader() default "akab-mgfmr35o6pmcrjca-hzlswuryissjauwo.luna.akamaiapis.net";


        /**
         * openAuthServiceURL
         *
         * @return the string
         */
        @AttributeDefinition(name = "openAuthServiceURL", description = "PROPERTY_OPEN_AUTH_SERVICE_URL", type = AttributeType.STRING)
        public String openAuthServiceURL() default "https://akab-mgfmr35o6pmcrjca-hzlswuryissjauwo.luna.akamaiapis.net";

        /**
         * socketTimeout
         *
         * @return the string
         */
        @AttributeDefinition(name = "socketTimeout", description = "PROPERTY_SOCKET_TIMEOUT", type = AttributeType.STRING)
        public String socketTimeout() default DEFAULT_TIMEOUT;

        /**
         * connectionTimeout
         *
         * @return the string
         */
        @AttributeDefinition(name = "connectionTimeout", description = "PROPERTY_CONNECTION_TIMEOUT", type = AttributeType.STRING)
        public String connectionTimeout() default DEFAULT_TIMEOUT;

        /**
         * enableDispatcherInvalidation
         *
         * @return the string
         */
        @AttributeDefinition(name = "enableDispatcherInvalidation", description = "PROPERTY_ENABLE_DISPATCHER_INVALIDATION", type = AttributeType.STRING)
        public String enableDispatcherInvalidation() default EMPTY_STRING;


    }

    /**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(AkamaiPurgeServiceImpl.Configuration config) {
        this.accessTokenProperty = config.accessToken();
        this.akamaiActionProperty = config.akamaiAction();
        this.akamaiDomainProperty = config.akamaiDomain();
        this.akamaiTypeProperty = config.akamaiType();
        this.ccuapitypeProperty = config.ccuapitype();
        this.clientSecretProperty = config.clientSecret();
        this.clientTokenProperty = config.clientToken();
        this.connectionTimeoutProperty = config.connectionTimeout();
        this.enableDispatcherInvalidationProperty = config.enableDispatcherInvalidation();
        this.hostHeaderProperty = config.hostHeader();
        this.openAuthServiceURLProperty = config.openAuthServiceURL();
        this.socketTimeoutProperty = config.socketTimeout();
    }

}
