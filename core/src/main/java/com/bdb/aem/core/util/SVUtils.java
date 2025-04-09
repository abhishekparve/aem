package com.bdb.aem.core.util;

import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.dam.api.Asset;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Utility Class for
 * Spectrum Viewer
 */
public class SVUtils {


    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory.getLogger(SVUtils.class);
    
    
    /**
     * Instantiates a new common helper.
     */
    private SVUtils() {
    }

    /**
     * replace the APPS path from icon.
     *
     * @param iconPath String
     * @return client_lib iconPath String
     */
    public static String replaceIconPath(String iconPath) {
        return StringUtils.isNotEmpty(iconPath) ? StringUtils.replace(iconPath, SpectrumViewerConstants.APPS_PATH, SpectrumViewerConstants.ETC_CLIENTLIBS_PATH) : iconPath;
    }

    /**
     * sort the Json ArrayResources
     *
     * @param sortBy    String
     * @param jsonArray
     * @return JsonArray
     */
    public static JsonArray sortJsonObject(final JsonArray jsonArray, final String sortBy) {
        final JsonArray sortedArray = new JsonArray();
        final ArrayList<JsonObject> listJsonObj = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            listJsonObj.add((JsonObject) jsonArray.get(i));
        }

        Collections.sort(listJsonObj,
                (o1, o2) -> o1.get(sortBy).getAsString().compareToIgnoreCase(o2.get(sortBy).getAsString()));

        for (int i = 0; i < jsonArray.size(); i++) {
            sortedArray.add(listJsonObj.get(i));
        }
        return sortedArray;
    }

    /**
     * Get the SV System config files from repository
     *
     * @param request
     * @param resolverFactory
     * @return String
     */
    public static String getSvSystemConfigFolderPath(SlingHttpServletRequest request, ResourceResolverFactory resolverFactory) {
        String suffixPath = request.getRequestPathInfo().getSuffix();
        LOG.debug("Suffix Resource Path is {}", suffixPath);
        String svResourcePath = StringUtils.substringBeforeLast(suffixPath, CommonConstants.SINGLE_SLASH);
        LOG.debug("Spectrum Viewer resource path is {}", svResourcePath);
        String svSystemConfigPath = getSvSystemConfigPath(resolverFactory, svResourcePath);
        return svSystemConfigPath;
    }

    /**
     * Get the SV System config files path from resource properties
     *
     * @param resolverFactory
     * @param svResourcePath
     * @return String
     */
    public static String getSvSystemConfigPath(ResourceResolverFactory resolverFactory, String svResourcePath) {
        try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)) {
            Resource svResource = resourceResolver.getResource(svResourcePath);
            ValueMap properties = ResourceUtil.getValueMap(svResource);
            return null != properties ? properties.get("svSystemConfigPath", SpectrumViewerConstants.SV_SYS_JSON_DEFAULT_FILES_PARENT_PATH) : SpectrumViewerConstants.SV_SYS_JSON_DEFAULT_FILES_PARENT_PATH;
        } catch (LoginException e) {
            LOG.error("Exception Occured in getSvSystemConfigFolderPath : {}", e.getMessage());
        }
        return StringUtils.EMPTY;
    }

    /**
     * Read the content of files from repository
     *
     * @param resolverFactory ResourceResolverFactory
     * @param filePath        String
     * @return String
     */
    public static String readAssetFile(ResourceResolverFactory resolverFactory, String filePath) {
        String fileContent = StringUtils.EMPTY;
        try (ResourceResolver resourceResolver = CommonHelper.getReadServiceResolver(resolverFactory)) {
            LOG.debug("File reading starting with resolver: {} and file path is {}", resourceResolver, filePath);
           
            	fileContent = readAsset(resourceResolver, filePath);
            	
           
        } catch (IOException | LoginException ex) {
            LOG.error("EXCEPTION occured in getting the system configs: {}", ex);
        }
        return fileContent;
    }

    /**
     * Encrypt the String using  Standard java Security API
     *
     * @param unsecuredText String
     * @param key           String
     * @return String
     */
    public static String encrypt(final String unsecuredText, final String key) {
        final byte[] iv = new byte[SpectrumViewerConstants.IV_SIZE];
        new SecureRandom().nextBytes(iv);
        final Key skeySpec = new SecretKeySpec(new Base64().decode(key), SpectrumViewerConstants.ALGORITHM);
        final Cipher cipher;
        final ByteBuffer byteBuffer;
        try {
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(SpectrumViewerConstants.TAG_LENGTH_BIT, iv);
            cipher = Cipher.getInstance(SpectrumViewerConstants.FULL_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,skeySpec, gcmParameterSpec);
            LOG.debug("Crypto Initiated");
            final byte[] encryptedValue = cipher.doFinal(unsecuredText.getBytes(StandardCharsets.UTF_8));
            byteBuffer = ByteBuffer.allocate(iv.length + encryptedValue.length);
            byteBuffer.put(iv);
            byteBuffer.put(encryptedValue);
            return new Base64().encodeAsString(byteBuffer.array());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            LOG.error("Exception occured in encryption {}", e.getMessage());
        }
        return null;
    }

    /**
     * Decrypt the String using  Standard java Security API
     *
     * @param encryptedText String
     * @param key           String
     * @return String
     */
    public static String decrypt(final String encryptedText, final String key) {
        try {
            final ByteBuffer byteBuffer = ByteBuffer.wrap(new Base64().decode(encryptedText));
            final byte[] iv = new byte[SpectrumViewerConstants.IV_SIZE];
            byteBuffer.get(iv);
            final byte[] encryptedValue = new byte[byteBuffer.remaining()];
            byteBuffer.get(encryptedValue);

            final Key skeySpec = new SecretKeySpec(new Base64().decode(key), SpectrumViewerConstants.ALGORITHM);
            final Cipher cipher = Cipher.getInstance(SpectrumViewerConstants.FULL_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));

            final byte[] decryptedValue = cipher.doFinal(encryptedValue);
            return new String(decryptedValue, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            LOG.error("Exception occured in encryption {}", e.getMessage());
        }
        return null;
    }

    public static Iterator<JsonElement> getSortedIterator(Iterator<JsonElement> it, Comparator<JsonElement> comparator) {
        List<JsonElement> list = new ArrayList<JsonElement>();
        while (it.hasNext()) {
            list.add(it.next());
        }
        Collections.sort(list, comparator);
        return list.iterator();
    }

    /**
     * Method to get the current timestamp
     *
     * @param dateFormat
     * @return String
     */
    public static String timeNow(String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    /**
     * Method to read the Dam asset
     *
     * @param resourceResolver
     * @param filePath
     * @return String
     * @throws IOException
     */
    public static String readAsset(ResourceResolver resourceResolver, String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (null != resourceResolver && StringUtils.isNotEmpty(filePath)) {
            Resource resource = resourceResolver.getResource(filePath);
            if(null!=resource) {
            Asset asset = resource.adaptTo(Asset.class); 
            if(null!=asset) {
            	Resource original = asset.getOriginal();
            	if(null!=original) {
            InputStream content = original.adaptTo(InputStream.class);
            	 String line;
                 BufferedReader br = new BufferedReader(new InputStreamReader(
                         content, StandardCharsets.UTF_8));
                 while ((line = br.readLine()) != null) {
                     sb.append(line);
                 }
            	}
            }   
            }
        }
        return sb.toString();
    }
    
    /**
     * Method to read the Dam asset
     *
     * @param resourceResolver
     * @param filePath
     * @return String
     * @throws IOException
     */
    public static String externalizeRteLinks(String rteContent,ExternalizerService externalizer,ResourceResolverFactory resolverFactory) {
    	try {
    	 Document doc = Jsoup.parse(rteContent);
         
         Elements links = doc.select("a[href]");

         for (Element link : links) {
             String href = link.attr("href");
             String newHref = externalizer.getFormattedUrl(href, CommonHelper.getReadServiceResolver(resolverFactory));
             link.attr("href",newHref);
            
         }
         
         return doc.select("body").html();
    	}catch (LoginException e) {
    		LOG.error("Exception occured in encryption {}", e.getMessage());
    		return "";
    	}
         
     }
    
}
