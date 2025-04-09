package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.ImageMetadataImportService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.tagging.InvalidTagFormatException;
import com.day.cq.tagging.Tag;
import com.day.cq.tagging.TagConstants;
import com.day.cq.tagging.TagManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * This class is used to add metadata to product images moved to product dam base folder
 */

@Component(service = ImageMetadataImportService.class, name = "Image Metadata Import Service", immediate = true)
public class ImageMetadataImportServiceImpl implements ImageMetadataImportService {
    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageMetadataImportServiceImpl.class);
    /**
     * The Constant used to retrieve data from given provided asset.
     */
    private static final String INVERTED = "\"";
    /**
     * The Constant DOT.
     */
    private static final String DOT = ".";

    /**
     * The Constant SLASH.
     */
    private static final String SLASH = "/";
    /**
     * Pipe symbol
     */
    private static final String PIPE = "\\|";
    /**
     * mime type of an asset
     */
    private static final String MIME_TYPE = "image/png";
    /**
     * name of medias property in hp node
     */
    private static final String MEDIAS_PROP = "medias";
    /**
     * relative path to product node
     */
    private static final String VAR_PRODUCT_FOLDER = "products/";
    /**
     * label description property name
     */
    private static final String LABEL_DESC = "labelDescription";
    /**
     * Image caption property name
     */
    private static final String CAPTION = "caption";
    /**
     * relative base path of the cq tags folder
     */
    private static final String CQ_TAG_BASE_PATH = "bdb:assets/images/";
    /**
     * relative base path of the cq tags folder
     */
    private static final String CQ_TAG_VIAL = "bdb:assets/images/clinical-vial";
    /**
     * relative base path of the cq tags folder
     */
    private static final String CQ_TAG_GALLERY = "bdb:assets/images/gallery";

    /**
     * The Constant COMA.
     */
    private static final String COMA = ",";
    /**
     * Image type property name
     */
    private static final String IMAGE_TYPE = "imageType";
    /**
     * empty string
     */
    private static final String EMPTY_STRING = "";
    /**
     * Image name value
     */
    private static final String IMAGE_NAME = "imageName";
    /**
     * Image metadata property name
     */
    private static final String IMAGE_META = "imageMetadata";
    /**
     * Approximate symbol used inside the give metadata
     */
    private static final String APPROX = "~";
    /**
     * imageTitle property name
     */
    private static final String IMAGE_TITLE = "ImageTitle";
    /**
     * dam:MIMEtype property name
     */
    private static final String DAM_MIME_TYPE = "dam:MIMEtype";
    /**
     * dc:caption property name
     */
    private static final String DC_CAPTION = "dc:caption";
    /**
     * dc:imageRegion property name
     */
    private static final String DC_IMG_REG = "dc:imageRegion";
    /**
     * image language property name
     */
    private static final String IMG_LANG = "imageLang";
    /**
     * image Region property name
     */
    private static final String IMG_REG = "imageRegion";
    /**
     * image description property name
     */
    private static final String IMAGE_DESC = "imageDesc";
    /**
     * dc:imageKeywords property name
     */
    private static final String DC_KEYWORDS = "dc:imageKeywords";
    /**
     * image keywords property name
     */
    private static final String IMG_KEYWORDS = "ImageKeywords";
    /**
     * dc:imageLang property name
     */
    private static final String DC_IMG_LAG = "dc:imageLang";
    /**
     * dc:imageAltText property name
     */
    private static final String DC_IMG_ALT_TXT = "dc:imageAltText";
    /**
     * image-alt text property name
     */
    private static final String IMG_ALT_TXT = "imageAltText";

    /**
     * image-language property name
     */
    private static final String IMG_LAG = "EN";

    /**
     * regex to compare clinical-vial images
     * regex will return true in cases where image names are like materialNumber_01/02.. to materialNumber_99.
     * materialNumber can be Numeric, Alphanumeric or Alphabets/Characters.
     */
    private static final String REGEX = ".*_\\d{2}";

    /**
     * function to process image and it's respective metadata
     *
     * @param assetPath        the asset path
     * @param resourceResolver the resource resolver
     * @param session          the session
     * @throws RepositoryException
     * @throws InvalidTagFormatException
     */
    @Override
    public void processProductImage(String assetPath, ResourceResolver resourceResolver, Session session) throws RepositoryException, InvalidTagFormatException {
        LOGGER.debug("Entry processProductImage method of ImageMetadataImportWorkflowServiceImpl");
        /* dam base path for Image */
        String damPath = assetPath.substring(0, assetPath.lastIndexOf(SLASH));
        /* product path for Image */
        String productPath = damPath.replace(CommonConstants.DAM_PRODUCT_PATH, CommonConstants.COMMERCE_PATH + VAR_PRODUCT_FOLDER);
        Resource hpResource = resourceResolver.getResource(productPath + "/hp");

        /** name of an asset */
        String fileName = assetPath.substring(assetPath.lastIndexOf(SLASH) + 1);
        /* name of an asset without the extension for comparison */
        String propValue = fileName.substring(0, fileName.lastIndexOf(DOT));

        /* getting image resource */
        Resource imageRes = resourceResolver.getResource(assetPath + CommonConstants.METADATAPATH);
        LOGGER.debug("image resource :: {}", imageRes);

        if (null != hpResource && null != imageRes) {
            ValueMap productMap = hpResource.adaptTo(ValueMap.class);
            Node currentNode = imageRes.adaptTo(Node.class);
            placeMetadata(resourceResolver, productMap, propValue, currentNode);
        }
        session.save();
        LOGGER.debug("Exit processProductImage method of ImageMetadataImportWorkflowServiceImpl");
    }

    /**
     * function to place image metadata
     *
     * @param resourceResolver
     * @param propValue
     * @throws InvalidTagFormatException
     */
    private void placeMetadata(ResourceResolver resourceResolver, ValueMap productMap, String propValue, Node currentNode) throws RepositoryException, InvalidTagFormatException {
        LOGGER.debug("Place Metadata started : {}", currentNode.getPath());
        TagManager tagMan = resourceResolver.adaptTo(TagManager.class);
        String labelDescription = productMap.containsKey(LABEL_DESC) ? productMap.get(LABEL_DESC).toString() : StringUtils.EMPTY;

        /** Initializing variables **/
        StringBuilder imageType = new StringBuilder();
        StringBuilder caption = new StringBuilder();
        Map<String, String> mediaMap = new HashMap<>();

        /** function to get medias data from HP Node and get image type (vial/non-vial)
         * the method will return true if the image type is clinical-vial otherwise it will return false.
         */
        Boolean isClinicalVial = getMediaAndImageType(productMap, mediaMap, propValue, imageType, caption);

        /** processing non-vial images */
        if (Boolean.FALSE.equals(isClinicalVial)) {
            placingMetadataOnNonVialProductImages(tagMan, currentNode, mediaMap, labelDescription, imageType, caption);
        }
        /** processing clinical-vial images */
        else {
            placingMetadataOnVialImages(productMap, currentNode);
        }
        LOGGER.debug("Place Metadata ended : {}", currentNode.getPath());
    }

    /**
     * This function will read all the custom metadata for Images based on the type from Media attribute of HP Node.
     * It will also check and return whether an Image is clinical-vial or not.
     *
     * @param productMap
     * @param mediaMap
     * @param propValue
     * @param imageType
     * @param caption
     * @return
     */
    private Boolean getMediaAndImageType(ValueMap productMap, Map<String, String> mediaMap, String propValue, StringBuilder imageType, StringBuilder caption) {
    	/** getting media attribute data from product hp node */
        String media = productMap.getOrDefault(MEDIAS_PROP, StringUtils.EMPTY).toString();

        /** converting media data type to JSON */
        Gson gsonInstance = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gsonInstance.fromJson(media, JsonArray.class);

        /** clinical-vial image type check
         *  Pattern matches return true for propValue like materialNumber_01/02 ...till materialNumber_99
         *  materialNumber can be Numeric, Alphabets or Alphanumeric.
         */
        boolean isClinicalVial = isImageClinicalVial(propValue);

        for (JsonElement jsonElement : jsonArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            LOGGER.debug("Json Object : {}", jsonObject);

            if (jsonObject.get(IMAGE_NAME).toString().replace(INVERTED, StringUtils.EMPTY).equals(propValue)) {
                isClinicalVial = false;
                appendCaptionAndImageType(jsonObject, caption, imageType);
                updateMediaMap(jsonObject, mediaMap);
                LOGGER.debug("mediaMap : {}", mediaMap);
            }
        }
        return isClinicalVial;
    }

    
    /**
     * Method to append the caption and imageType in the stringBuilder.
     *
     * @param jsonObject
     * @param caption
     * @param imageType
     * @return
     */
    private void appendCaptionAndImageType(JsonObject jsonObject, StringBuilder caption, StringBuilder imageType) {
        caption.append(jsonObject.has(CAPTION) ? jsonObject.get(CAPTION).toString() : StringUtils.EMPTY);
        imageType.append(jsonObject.has(IMAGE_TYPE) ? jsonObject.get(IMAGE_TYPE).toString().replace(INVERTED, EMPTY_STRING) : StringUtils.EMPTY);
    }
    
    /**
     * Method to update the mediaMap.
     *
     * @param jsonObject
     * @param mediaMap
     * @return
     */
    private void updateMediaMap(JsonObject jsonObject, Map<String, String> mediaMap) {
        String imageMetadata = jsonObject.has(IMAGE_META) ? jsonObject.get(IMAGE_META).toString() : StringUtils.EMPTY;
        String[] imageList = imageMetadata.replace(INVERTED, EMPTY_STRING).split(PIPE);
        
        for (String s : imageList) {
            if (s.contains(APPROX)) {
                String[] parts = s.split(APPROX);
                mediaMap.put(parts[0].trim(), parts[1]);
            }
        }
    }
    
    
    /**
     * This function will check whether an Image is Clinical-vial or not based on its name.
     *
     * @param imageName
     * @return
     */
    private boolean isImageClinicalVial(String imageName) {
        /* Pattern matches return true for propValue like materialNumber_01/02 ...till materialNumber_99*/
        return Pattern.matches(REGEX, imageName);
    }

    /**
     * This function will place custom metadata for the clinical-vial Image on the currentNode present under product dam base folder.
     *
     * @param productMap
     * @param currentNode
     * @throws RepositoryException
     */
    private void placingMetadataOnVialImages(ValueMap productMap, Node currentNode) throws RepositoryException {
        LOGGER.debug("Placing metadata for Clinical-vial image {}", currentNode.getName());
        String[] imageTypes = {CQ_TAG_VIAL, CQ_TAG_GALLERY};
        if (productMap.containsKey(LABEL_DESC))
            currentNode.setProperty(DamConstants.DC_TITLE, productMap.get(LABEL_DESC).toString());
        if (productMap.containsKey(LABEL_DESC))
            currentNode.setProperty(DC_IMG_ALT_TXT, productMap.get(LABEL_DESC).toString());
        if (!productMap.containsKey(DamConstants.DC_DESCRIPTION) && productMap.containsKey(LABEL_DESC))
            currentNode.setProperty(DamConstants.DC_DESCRIPTION, productMap.get(LABEL_DESC).toString());
        currentNode.setProperty(DC_IMG_REG, CommonConstants.GLOBAL);
        currentNode.setProperty(DC_IMG_LAG, IMG_LAG);
        currentNode.setProperty(TagConstants.PN_TAGS, imageTypes);
    }

    /**
     * This function will place custom metadata for the Product Image on the currentNode present under product dam base folder.
     *
     * @param tagMan
     * @param currentNode
     * @param mediaMap
     * @param labelDescription
     * @param imageType
     * @param caption
     * @throws RepositoryException
     * @throws InvalidTagFormatException
     */
    private void placingMetadataOnNonVialProductImages(TagManager tagMan, Node currentNode, Map<String, String> mediaMap, String labelDescription, StringBuilder imageType, StringBuilder caption) throws RepositoryException, InvalidTagFormatException {
        LOGGER.debug("Placing metadata for product image {}", currentNode.getName());
        if (StringUtils.isNotBlank(imageType)) {
            String[] tagsList = String.valueOf(imageType).split(COMA);
            for (int i = 0; i < tagsList.length; i++) {
                String tag = tagsList[i];
                TagManager.FindResults fr = tagMan.findByTitle(tag);
                Tag[] tagArray = fr.tags;
                if (tagArray.length < 1) {
                    tagMan.createTag(CQ_TAG_BASE_PATH + tag.toLowerCase(), tag, "");
                }
                tagsList[i] = CQ_TAG_BASE_PATH + tag.toLowerCase();
            }
            currentNode.setProperty(TagConstants.PN_TAGS, tagsList);
        }
        currentNode.setProperty(DC_CAPTION, caption.toString());
        currentNode.setProperty(DAM_MIME_TYPE, MIME_TYPE);

        /* Adding Meta data properties */
        if (mediaMap.containsKey(IMAGE_TITLE))
            currentNode.setProperty(DamConstants.DC_TITLE, mediaMap.get(IMAGE_TITLE));
        if (mediaMap.containsKey(IMG_KEYWORDS)) currentNode.setProperty(DC_KEYWORDS, mediaMap.get(IMG_KEYWORDS));
        if (mediaMap.containsKey(IMAGE_DESC))
            currentNode.setProperty(DamConstants.DC_DESCRIPTION, mediaMap.get(IMAGE_DESC));
        if (mediaMap.containsKey(IMG_REG)) currentNode.setProperty(DC_IMG_REG, mediaMap.get(IMG_REG).toLowerCase());
        if (mediaMap.containsKey(IMG_LANG))
            currentNode.setProperty(DC_IMG_LAG, mediaMap.get(IMG_LANG).toLowerCase());
        currentNode.setProperty(DC_IMG_ALT_TXT, mediaMap.containsKey(IMG_ALT_TXT) && (null != mediaMap.get(IMG_ALT_TXT)) && !mediaMap.get(IMG_ALT_TXT).isEmpty() ? mediaMap.get(IMG_ALT_TXT) : labelDescription);
    }
}