package com.bdb.aem.core.services.impl;

import com.adobe.granite.asset.api.AssetManager;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.ExternalizerService;
import com.bdb.aem.core.services.OnDemandTdsGenerationService;
import com.bdb.aem.core.services.TDSService;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.Externalizer;
import com.day.cq.dam.api.Asset;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.sf.saxon.TransformerFactoryImpl;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.*;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.jcr.RepositoryException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component(service = OnDemandTdsGenerationService.class, immediate = true)
@Designate(ocd = OnDemandTdsGenerationServiceImpl.Configuration.class)
public class OnDemandTdsGenerationServiceImpl implements OnDemandTdsGenerationService {

    /**
     * The Constant LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OnDemandTdsGenerationServiceImpl.class);
    
    /**
     * IMAGE_TYPE
     */
    private static final String IMAGE_TYPE = "imageType";
    /**
     * GALLERY_IMAGE
     */
    private static final String GALLERY_IMAGE = "galleryImage";
    /**
     * CAPTION2
     */
    private static final String CAPTION2 = "caption";
    /**
     * GALLERY_IMAGE_COUNT
     */
    private static final String GALLERY_IMAGE_COUNT = "galleryImageCount";
    /**
     * GALLERY_IMAGE_LIST
     */
    private static final String GALLERY_IMAGE_LIST = "galleryImageList";
    /**
     * PRODUCT_REFERENCE_DATA_LIST
     */
    private static final String PRODUCT_REFERENCE_DATA_LIST = "productReferenceDataList";
    /**
     * PRODUCT_APPLICATION_TEST
     */
    private static final String PRODUCT_APPLICATION_TEST = "productApplicationTest";
    /**
     * ISO_TYPE
     */
    private static final String ISO_TYPE = "isoType";
    /**
     * SPECIES_REACTIVITY
     */
    private static final String SPECIES_REACTIVITY = "speciesReactivity";
    /**
     * REFERENCE_DETAILS
     */
    private static final String REFERENCE_DETAILS = "referenceDetails";
    /**
     * MANUFACTURING_COMPONENT
     */
    private static final String MANUFACTURING_COMPONENT = "manufacturingComponent";
    /**
     * WORK_SHOP_NUMBER
     */
    private static final String WORK_SHOP_NUMBER = "workshopNumber";
    /**
     * PRODUCT_NOTICES
     */
    private static final String PRODUCT_NOTICES = "productNotices";
    /**
     * RECOM_ASSAY_PROCEDURE
     */
    private static final String RECOM_ASSAY_PROCEDURE = "recomAssayProcedure";
    /**
     * ADD_PREPARATION_STORAGE
     */
    private static final String ADD_PREPARATION_STORAGE = "addPreparationStorage";
    /**
     * PREPARATION_STORAGE
     */
    private static final String PREPARATION_STORAGE = "preparationStorage";
    /**
     * OTHER_TDS_DESCRIPTION
     */
    private static final String OTHER_TDS_DESCRIPTION_XML = "otherTDSDescription";
    /**
     * OTHER_TDS_DESCRIPTION
     */
    private static final String OTHER_TDS_DESCRIPTION = "otherTdsDescription";
    private static final String BD_FORMAT = "bdFormat";
    /**
     * TDS_DESCRIPTION
     */
    private static final String TDS_DESCRIPTION = "tdsDescription";
    /**
     * REGULATORY_STATUS
     */
    private static final String REGULATORY_STATUS = "regulatoryStatus";
    /**
     * CLONE
     */
    private static final String CLONE = "clone";
    /**
     * ALTERNATIVE_NAME
     */
    private static final String ALTERNATIVE_NAME = "alternativeName";
    /**
     * STORAGE_BUFFER
     */
    private static final String STORAGE_BUFFER = "storageBuffer";
    /**
     * ENTREZ_GENE_ID
     */
    private static final String ENTREZ_GENE_ID = "entrezGeneId tdsCloneName";
    /**
     * ENTREZ_GENE_ID
     */
    private static final String TDS_CLONE_NAME = "tdsCloneName";
    /**
     * VOL_PER_TEST
     */
    private static final String VOL_PER_TEST = "volPerTest";
    /**
     * CONCENTRATION
     */
    private static final String CONCENTRATION = "concentration";
    /**
     * SEQ_ID
     */
    private static final String SEQ_ID = "seqId";
    /**
     * SIZE_UOM
     */
    private static final String SIZE_UOM = "sizeUOM";
    /**
     * BARCODE_SEQUENCE
     */
    private static final String BARCODE_SEQUENCE = "barcodeSequence";
    /**
     * BEAD_POSITION
     */
    private static final String BEAD_POSITION = "beadPosition";
    /**
     * SIZE_QTY
     */
    private static final String SIZE_QTY = "sizeQty";
    /**
     * TDS_REVISION
     */
    private static final String TDS_REVISION = "tdsRevision";
    /**
     * LABEL_DESCRIPTION
     */
    private static final String LABEL_DESCRIPTION = "labelDescription";
    /**
     * BRAND
     */
    private static final String BRAND = "brand";
    /**
     * relative path to product node
     */
    private static final String VAR_PRODUCT_FOLDER = "products/";

    /**
     * TDS XSLT Template Base name
     */
    private static final String TDS_XSLT_TEMPLATE_BASE_NAME = "TDS_Template_";

    /**
     * TDS XSLT Externsion
     */
    private static final String XSLT_EXTENSION = ".xslt";
    /**
     * TDS XML Absolute path
     */
    private static final String TDS_XML_ABS_PATH = "TDS_Template_XML.xml";

    /**
     * Type of the Document : ON_DEMAND_TDS
     */
    private static final String ON_DEMAND_TDS_PLAN = "ON_DEMAND_TDS";
    /**
     * Type of the Document : PUBLISHED_TDS
     */
    private static final String PUBLISHED_TDS = "PUBLISHED_TDS";
    /**
     * Hp property for document type
     */
    private static final String TDS_DOC_TYPE = "tdsDocumentType";
    /**
     * Hp property for document Layout type
     */
    private static final String TDS_DOC_LAYOUT = "tdsDocumentLayout";
    /**
     * Hp property for companion Product
     */
    private static final String COMPANION_PRODUCTS = "companionProduct";
    /**
     * DEFAULT_IMAGE
     */
    private static final String DEFAULT_IMAGE = "<galleryImage></galleryImage>";
    /**
     * Hp property for medias attribute
     */
    private static final String MEDIAS = "medias";

    /**
     * xmlns path
     */
    private static final String XMLNS_PATH = " xmlns=\"http://www.w3.org/1999/xhtml\"";

    /**
     * Sku base extension
     */
    private static final String BASE_PRODUCT = "_base";
    /**
     * materialNumber of the sku
     */
    private static final String MATERIAL_NO = "materialNumber";
    /**
     * Hp property for companion MaterialNumber
     */
    private static final String COMPANION_MATERIAL = "companionMaterialNumber";
    /**
     * Hp property for catalog Number
     */
    private static final String CATALOG_NUMBER = "catalogNumber";

    /**
     * Production Author Domain
     */
    private static final String PROD_AUTHOR_DOMAIN = "https://author-p14102-e70270.adobeaemcloud.com";
    /**
     * REFERENCES_LIST
     */
    private static final String REFERENCES_LIST = "citationDataList";

    private static final String AEM_FORMAT_STATEMENT = "formatStatement";
    /**
     * AEM_REFERENCES_ATTRIBUTE
     */
    private static final String AEM_REFERENCES_ATTRIBUTE = "citationdata";
    /**
     * REFERENCES
     */
    private static final String REFERENCES = "citationData";
    /**
     * TDS_SINGLEVIAL2022_TEMPLATE
     */
    private static final String TDS_SINGLEVIAL2022_TEMPLATE = "SingleVial2022";
    
    /**
     * TDS_SINGLEVIAL2022WIDE_TEMPLATE
     */
    private static final String TDS_SINGLEVIAL2022WIDE_TEMPLATE = "SingleVial2022Wide";

    /**
     * The externalizer service.
     */
    @Reference
    ExternalizerService externalizerService;
    /**
     * GetPublishedTDSpdf
     */
    @Reference
    TDSService tDSService;

    /**
     * SolrSearchService
     */
    @Reference
    SolrSearchService solrSearchService;
    /**
     * The externalizer.
     */
    @Reference
    Externalizer externalizer;
    /**
     * The BDBApiEndpointService
     */
    @Reference
    private BDBApiEndpointService bdbApiEndpointService;
    /**
     * The AuthenticationEnabled Value.
     */
    private String authenticationEnabled;


    @Override
    public ByteArrayOutputStream getPdfStream(Resource variantRes, ResourceResolver resourceResolver, String skuName, String locale) throws IOException, TransformerException, SAXException, RepositoryException, ParserConfigurationException {
        ByteArrayOutputStream pdfStream;
        String xmlString = "";
        Resource currentTdsBase = resourceResolver.getResource(CommonConstants.TDS_TEMPLATE_BASE_PATH);
        Asset tdsXmlTemplate = currentTdsBase.getChild(TDS_XML_ABS_PATH).adaptTo(Asset.class);
        Resource baseHpResource = CommonHelper.getBaseHpResourceFromLookUp(variantRes);
        /**Handling the null exception for Query result */
        if (null != variantRes && null != baseHpResource) {
            Resource variantHpResource = variantRes.getChild(CommonConstants.HP);
            ValueMap baseHpMap = baseHpResource.getValueMap();
            ValueMap varianHpMap = variantHpResource.getValueMap();
            LOGGER.debug("destination path :: {}", baseHpResource);
            if (baseHpMap.containsKey(TDS_DOC_TYPE)) {
                if (baseHpMap.get(TDS_DOC_TYPE).equals(ON_DEMAND_TDS_PLAN) && baseHpMap.containsKey(TDS_DOC_LAYOUT)) {
                    String xsltType = baseHpMap.get(TDS_DOC_LAYOUT).toString().replace(" ", "");
                    final String xslt_name = TDS_XSLT_TEMPLATE_BASE_NAME + xsltType + XSLT_EXTENSION;
                    LOGGER.debug("XSLT NAME : {}", xslt_name);
                    Resource tdsXsltTemplateRes = currentTdsBase.getChild(xslt_name);
                    if (null != tdsXsltTemplateRes) {
                        Asset tdsXsltTemplate = tdsXsltTemplateRes.adaptTo(Asset.class);
                        // XSLT Input Stream
                        InputStream tdsXsltAssetStream = tdsXsltTemplate.getOriginal().getStream();
                        // XML Input Stream
                        InputStream tdsXmlAssetStream = tdsXmlTemplate.getOriginal().getStream();

                        // single values
                        InputStream updatedXmlAssetSteam = addSingleValuesToUpdateXML(tdsXmlAssetStream, baseHpMap, varianHpMap);

                        xmlString = extractingStrFromIS(updatedXmlAssetSteam);
                        LOGGER.debug("Single values : {}", xmlString);


                        // List Items in the Xml
                        xmlString = addMultiValuesToUpdateXML(xmlString, baseHpMap);

                        if (baseHpMap.containsKey(COMPANION_PRODUCTS)) {
                            xmlString = companionProduct(baseHpMap.get(COMPANION_PRODUCTS).toString(), resourceResolver, xmlString);
                        }

                        if (baseHpMap.containsKey(MEDIAS)) {
                            xmlString = images(baseHpMap.get(MEDIAS).toString(), xmlString, baseHpResource.getPath(), xsltType, resourceResolver);
                        }

                        xmlString = stringValidation(xmlString);

                        xmlString = addHeader(xmlString);

                        pdfStream = convertingXMltoPDF(tdsXsltAssetStream, xmlString, resourceResolver);

                        if (pdfStream.size() == 0) {
                            LOGGER.error("Xml content is wrong");
                            pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
                        }
                        LOGGER.debug("final xml string : {}", xmlString);
                    } else {
                        // NO_TDS with template not found.
                        LOGGER.error("TDS Template not found : {}", xslt_name);
                        pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
                    }

                } else if (baseHpMap.get(TDS_DOC_TYPE).equals(PUBLISHED_TDS)) {
                    pdfStream = getPublishPdf(resourceResolver, baseHpResource, baseHpMap, currentTdsBase, tdsXmlTemplate, locale, skuName);
                } else {
                    // NO_TDS Template.
                    pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
                }
            } else {
                // NO_TDS Template.
                pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
            }
        } else {
            // ERROR Template with material number not found.
            final String xslt_name = TDS_XSLT_TEMPLATE_BASE_NAME + "Error" + XSLT_EXTENSION;
            Asset tdsXsltTemplate = currentTdsBase.getChild(xslt_name).adaptTo(Asset.class);
            InputStream tdsXsltAssetStream = tdsXsltTemplate.getOriginal().getStream();
            xmlString = extractingStrFromIS(tdsXmlTemplate.getOriginal().getStream());
            pdfStream = convertingXMltoPDF(tdsXsltAssetStream, xmlString, resourceResolver);
        }
        return pdfStream;
    }
    
    
    /**
     * @param tdsXmlAssetStream
     * @param skuName
     * @return Document Input Stream
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws TransformerException
     */
    private InputStream addSingleValuesToUpdateXML(InputStream tdsXmlAssetStream, ValueMap baseMap, ValueMap variantMap) throws SAXException, IOException, ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        // Disable access to external entities to enhance security
        // This is crucial in preventing XML External Entity (XXE) attacks, which can lead to sensitive data exposure or denial of service.
        docFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        docFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        docFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(tdsXmlAssetStream);
        if (doc != null) {
            updateBaseMapValues(doc, baseMap);
            updateVariantMapValues(doc, variantMap);
            updateRegulatoryStatus(doc, baseMap);
            updateTDSDescriptions(doc, baseMap);
            updateAdditionalHpNodeValues(doc, baseMap);
        }
        return transformXMLtoStream(doc);
    }
    
    /**
     * 
     * Updating the base product hp node properties 
     * such as brand, labelDescription, beadPosition etc into the Document
     * @param Document doc
     * @param ValueMap baseMap
     */
    private Document updateBaseMapValues(Document doc, ValueMap baseMap) {
        String[] baseKeys = {BRAND, LABEL_DESCRIPTION, BEAD_POSITION, BARCODE_SEQUENCE, SEQ_ID, CONCENTRATION, VOL_PER_TEST, ENTREZ_GENE_ID, STORAGE_BUFFER, ALTERNATIVE_NAME, TDS_DESCRIPTION, OTHER_TDS_DESCRIPTION};
        
        for (String key : baseKeys) {
            if (baseMap.containsKey(key)) {
                updateXMLNodeData(key, doc, baseMap.get(key).toString());
            }
        }
		return doc;
    }
    
    /**
     * Updating the product variant node properties 
     * such as materialNumber, tdsRevision, and sizeQty into the Document
     * @param Document doc
     * @param ValueMap variantMap
     */
    private Document updateVariantMapValues(Document doc, ValueMap variantMap) {
        if (variantMap != null) {
            if (variantMap.containsKey(MATERIAL_NO)) {
                updateXMLNodeData("code", doc, variantMap.get(MATERIAL_NO).toString());
            }
            if (variantMap.containsKey(TDS_REVISION)) {
                updateXMLNodeData(TDS_REVISION, doc, variantMap.get(TDS_REVISION).toString());
            }
            if (variantMap.containsKey(SIZE_QTY) && variantMap.containsKey(SIZE_UOM)) {
                updateXMLNodeData("productSize", doc, variantMap.get(SIZE_QTY).toString() + " " + variantMap.get(SIZE_UOM).toString());
            }
        }
		return doc;
    }
    
    /**
     * Updating the base product hp node regulatoryStatus   
     * property into the Document
     * @param Document doc
     * @param ValueMap baseMap
     */
    private Document updateRegulatoryStatus(Document doc, ValueMap baseMap) {
        if (baseMap.containsKey(REGULATORY_STATUS)) {
            updateXMLNodeData(REGULATORY_STATUS, doc, baseMap.get(REGULATORY_STATUS).toString());
        }
		return doc;
    }
    
    /**
     * Updating the base product hp node properties   
     * such as tdsDescription and otherTdsDescription into the Document
     * @param Document doc
     * @param ValueMap baseMap
     */
    private Document updateTDSDescriptions(Document doc, ValueMap baseMap) {
        if (baseMap.containsKey(TDS_DESCRIPTION)) {
            updateXMLNodeData(TDS_DESCRIPTION, doc, baseMap.get(TDS_DESCRIPTION).toString());
        }
        if (baseMap.containsKey(OTHER_TDS_DESCRIPTION)) {
            updateXMLNodeData(OTHER_TDS_DESCRIPTION_XML, doc, baseMap.get(OTHER_TDS_DESCRIPTION).toString());
        }
		return doc;
    }
    
    /**
     * Updating the additional base product hp node properties   
     * such as bdFormat, preparationStorage, addPreparationStorage, recomAssayProcedure
     * productNotices and workshopNumber into the Document
     * @param Document doc
     * @param ValueMap baseMap
     */
    private Document updateAdditionalHpNodeValues(Document doc, ValueMap baseMap) {
        if (TDS_SINGLEVIAL2022_TEMPLATE.equalsIgnoreCase(baseMap.get(TDS_DOC_LAYOUT).toString()) ||
            TDS_SINGLEVIAL2022WIDE_TEMPLATE.equalsIgnoreCase(baseMap.get(TDS_DOC_LAYOUT).toString())) {
            if (baseMap.containsKey(BD_FORMAT))
                updateXMLNodeData(BD_FORMAT, doc, formatDetails(baseMap.get(BD_FORMAT).toString()));
        }
        
        String[] additionalKeys = {PREPARATION_STORAGE, ADD_PREPARATION_STORAGE, RECOM_ASSAY_PROCEDURE, PRODUCT_NOTICES};
        
        for (String key : additionalKeys) {
            if (baseMap.containsKey(key)) {
                updateXMLNodeData(key, doc, baseMap.get(key).toString());
            }
        }
        
        return updateXMLNodeData(WORK_SHOP_NUMBER, doc, extractValueFromArray(CLONE, baseMap, WORK_SHOP_NUMBER));
    }

    /**
     * @param xmlString
     * @param hpVM
     * @return xmlString
     */
    private String addMultiValuesToUpdateXML(String xmlString, ValueMap hpVM) {
        if (hpVM.containsKey(MANUFACTURING_COMPONENT))
            xmlString = multiValueToMapList(hpVM.get(MANUFACTURING_COMPONENT).toString(), xmlString, "mfgCompData");
        if (hpVM.containsKey(REFERENCE_DETAILS))
            xmlString = references(hpVM.get(REFERENCE_DETAILS).toString(), xmlString);

        /**single value list.*/
        if (hpVM.containsKey(SPECIES_REACTIVITY))
            xmlString = singleValuesToMapList(hpVM.get(SPECIES_REACTIVITY).toString(), xmlString, "reactivity", "reactivityStatusDesc", "speciesDescription", ":");
        if (hpVM.containsKey(CLONE))
            xmlString = singleValuesToMapList(hpVM.get(CLONE).toString(), xmlString, ENTREZ_GENE_ID, ENTREZ_GENE_ID, null, "");
        if (hpVM.containsKey(CLONE))
            xmlString = singleValuesToMapList(hpVM.get(CLONE).toString(), xmlString, CLONE, TDS_CLONE_NAME, null, "");
        if (hpVM.containsKey(CLONE))
            xmlString = singleValuesToMapList(hpVM.get(CLONE).toString(), xmlString, ISO_TYPE, "hostSpecies", "hostStrain", " ");
        if (hpVM.containsKey(PRODUCT_APPLICATION_TEST))
            xmlString = singleValuesToMapList(hpVM.get(PRODUCT_APPLICATION_TEST).toString(), xmlString, "application", "applicationDesc", "applicationStatusDesc", "(");
        return xmlString;

    }

    private String formatDetails(String bdFormat) {
        String formatStatementValue = "";
        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(bdFormat, JsonArray.class);
        if (jsonArray != null) {
            for (JsonElement jele : jsonArray) {
                JsonObject jobj = jele.getAsJsonObject();
                if (null != jobj && jobj.has(AEM_FORMAT_STATEMENT)) {
                    formatStatementValue = jobj.get(AEM_FORMAT_STATEMENT).getAsString();
                }
                LOGGER.debug("formatStatementValue : {} ", formatStatementValue);
            }
        }
        return formatStatementValue;
    }

    /**
     * For Companion Products
     *
     * @param updatedValue
     * @param resourceResolver
     * @param xmlString
     * @return
     */
    private String companionProduct(String updatedValue, ResourceResolver resourceResolver, String xmlString) {
        StringBuilder existingXMLStringBuilder = new StringBuilder();
        String showClone = "false";
        String clone = StringUtils.EMPTY;
        existingXMLStringBuilder.append("<");
        existingXMLStringBuilder.append(PRODUCT_REFERENCE_DATA_LIST);
        existingXMLStringBuilder.append("/>");
        String existingXMLString = existingXMLStringBuilder.toString();
        String productReferenceShowClone = "<productReferenceShowClone xmlns=\"http://www.w3.org/1999/xhtml\"/>";

        StringBuilder updatedProductReferenceShowClone = new StringBuilder();
        updatedProductReferenceShowClone.append("<");
        updatedProductReferenceShowClone.append("productReferenceShowClone");
        updatedProductReferenceShowClone.append(XMLNS_PATH);
        updatedProductReferenceShowClone.append(">");

        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(updatedValue, JsonArray.class);

        if (xmlString.contains(existingXMLString)) {
            StringBuilder updateXMLStringBuilder = new StringBuilder();
            updateXMLStringBuilder.append("<");
            updateXMLStringBuilder.append(PRODUCT_REFERENCE_DATA_LIST);
            updateXMLStringBuilder.append(">");
            updateXMLStringBuilder.append("\n");

            for (JsonElement jele : jsonArray) {
            	updateXMLStringBuilder = updateProductReferenceData(updateXMLStringBuilder, jele, gs, resourceResolver);
            	JsonObject jobj = jele.getAsJsonObject();
            	String skuName = jobj.has(COMPANION_MATERIAL) ? jobj.get(COMPANION_MATERIAL).getAsString() : StringUtils.EMPTY;
            	Resource hitRes = CommonHelper.getProductFromLookUp(resourceResolver, skuName, CommonConstants.MATERIAL_NUMBER);
                if (null != hitRes) {
                    ValueMap hitVM = hitRes.getParent().getChild(CommonConstants.HP).adaptTo(ValueMap.class);
                    clone = getClone(hitVM, clone, gs);
                    if (!StringUtils.isEmpty(clone)) {
                    	showClone = "True";
                    }
                }
            }
            updateXMLStringBuilder.append("</");
            updateXMLStringBuilder.append(PRODUCT_REFERENCE_DATA_LIST);
            updateXMLStringBuilder.append(">");

            xmlString = xmlString.replaceAll(existingXMLString, updateXMLStringBuilder.toString());

            updatedProductReferenceShowClone.append(showClone);
            updatedProductReferenceShowClone.append("</");
            updatedProductReferenceShowClone.append("productReferenceShowClone");
            updatedProductReferenceShowClone.append(">");
            xmlString = xmlString.replaceAll(productReferenceShowClone, updatedProductReferenceShowClone.toString());
        }

        LOGGER.debug("Companion multi value : inside {}", xmlString);
        return xmlString;
    }
    
    /**
     * Updating product reference data, label description, size and clone
     *
     * @param updateXMLStringBuilder
     * @param showClone
     * @param jele
     * @param gs
     * @param resourceResolver
     * 
     */
    private StringBuilder updateProductReferenceData(StringBuilder updateXMLStringBuilder, JsonElement jele, Gson gs, ResourceResolver resourceResolver) {
    	updateXMLStringBuilder.append("<");
        updateXMLStringBuilder.append("productReferenceData");
        updateXMLStringBuilder.append(">");
        updateXMLStringBuilder.append("\n");

        LOGGER.debug("inside the for loop :: {}", jele);
        JsonObject jobj = jele.getAsJsonObject();

        String skuName = jobj.has(COMPANION_MATERIAL) ? jobj.get(COMPANION_MATERIAL).getAsString() : StringUtils.EMPTY;
        String description = StringUtils.EMPTY;
        String size = StringUtils.EMPTY;
        String clone = StringUtils.EMPTY;
        updateValueInNode(updateXMLStringBuilder, skuName, CATALOG_NUMBER);
        /**
         * Dumping companion products retrieval logic based upon regions
         Resource hitRes = solrSearchService.getHpNodeResource(skuName.replace("\"", ""), country, resourceResolver);
         */
        Resource hitRes = CommonHelper.getProductFromLookUp(resourceResolver, skuName, CommonConstants.MATERIAL_NUMBER);
        if (null != hitRes) {
            ValueMap hitVM = hitRes.getParent().getChild(CommonConstants.HP).adaptTo(ValueMap.class);
            description = hitVM.containsKey(LABEL_DESCRIPTION) ? hitVM.get(LABEL_DESCRIPTION).toString() : description;
            size = getSize(hitVM);
            clone = getClone(hitVM, clone, gs);
        }

        updateValueInNode(updateXMLStringBuilder, description, LABEL_DESCRIPTION);
        updateValueInNode(updateXMLStringBuilder, size, "size");
        updateValueInNode(updateXMLStringBuilder, clone, "cloneName");
        updateXMLStringBuilder.append("</");
        updateXMLStringBuilder.append("productReferenceData");
        updateXMLStringBuilder.append(">");
        return updateXMLStringBuilder.append("\n");
    }
    
    /**
     * getting the clone
     *
     * @param hitVM
     * @param clone
     * @param gs
     * @return clone
     */
    private String getClone(ValueMap hitVM, String clone, Gson gs) {
    	if (null != hitVM && hitVM.containsKey(CLONE)) {
            JsonArray jAry = gs.fromJson(hitVM.get(CLONE).toString(), JsonArray.class);
            if (null != jAry && jAry.size() > 0) {
                JsonObject ele = jAry.get(0).getAsJsonObject();
                if (ele.has(TDS_CLONE_NAME) && !ele.get(TDS_CLONE_NAME).toString().isEmpty()) {
                    clone = ele.get(TDS_CLONE_NAME).getAsString();
                }
            }
        }
    	return clone;
    }
    
    /**
     * getting the size
     *
     * @param hitVM
     * @return size
     * 
     */
    private String getSize(ValueMap hitVM) {
        if (null != hitVM && hitVM.containsKey(SIZE_QTY)) {
            return hitVM.containsKey(SIZE_UOM) ? 
                hitVM.get(SIZE_QTY).toString() + " " + hitVM.get(SIZE_UOM).toString() : 
                hitVM.get(SIZE_QTY).toString();
        }
        return StringUtils.EMPTY;
    }

    /**
     * @param resourceResolver
     * @param skuRes
     * @param hitVM
     * @param currentTdsBase
     * @param tdsXmlTemplate
     * @param locale
     * @param skuName
     * @return PDF Stream
     * @throws IOException
     */
    private ByteArrayOutputStream getPublishPdf(ResourceResolver resourceResolver, Resource skuRes, ValueMap hitVM, Resource currentTdsBase, Asset tdsXmlTemplate, String locale, String skuName) throws IOException {
        Resource pdfRes = tDSService.getPublishedTdsPdf(skuRes, hitVM, resourceResolver, locale, skuName);
        ByteArrayOutputStream pdfStream;
        if (null != pdfRes) {
            Asset pdfIs = pdfRes.adaptTo(Asset.class);
            InputStream pdfIS = pdfIs.getOriginal().getStream();
            pdfStream = new ByteArrayOutputStream();
            IOUtils.copy(pdfIS, pdfStream);
            if (pdfStream.size() == 0) {
                LOGGER.debug("PDF content is Empty");
                pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
            }
        } else {
            // NO_TDS with template not found.
            LOGGER.debug("PDF not found in Base SKU folder for PUBLISHED_TDS : {}", skuRes.getPath());
            pdfStream = noTDS(currentTdsBase, resourceResolver, tdsXmlTemplate.getOriginal().getStream());
        }
        return pdfStream;
    }

    /**
     * @param updatedValue
     * @param xmlString
     * @param skuPath
     * @param xsltType
     * @param resourceResolver
     * @return xmlString
     */
    private String images(String updatedValue, String xmlString, String skuPath, String xsltType, ResourceResolver resourceResolver) {
        String runmodes = bdbApiEndpointService.environmentType();
        LOGGER.debug("runmodes : {}", runmodes);

        StringBuilder galleryExistingXMLStringBuilder = new StringBuilder();
        StringBuilder galleryCountStringBuilder = new StringBuilder();

        galleryExistingXMLStringBuilder.append("<").append(GALLERY_IMAGE_LIST).append("/>");

        galleryCountStringBuilder.append("<").append(GALLERY_IMAGE_COUNT).append(XMLNS_PATH).append("/>");

        String galleryexistingXMLString = galleryExistingXMLStringBuilder.toString();
        String galleryCountString = galleryCountStringBuilder.toString();

        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(updatedValue, JsonArray.class);

        if (xmlString.contains(galleryexistingXMLString)) {
            LOGGER.debug(" Gallery Images Method ");
            StringBuilder updateXMLStringBuilderGallery = new StringBuilder();
            updateXMLStringBuilderGallery.append("<").append(GALLERY_IMAGE_LIST).append(">").append("\n");

            int galleryCount = 0;
            int thumbnailCount = 0;
            String primaryImageStr = StringUtils.EMPTY;
            Boolean hasPrimaryImage = false;
            for (JsonElement jele : jsonArray) {
                JsonObject jobj = jele.getAsJsonObject();
                String imageName = jobj.has("imageName") ? jobj.get("imageName").getAsString() : StringUtils.EMPTY;
                String caption = jobj.has(CAPTION2) ? jobj.get(CAPTION2).getAsString() : StringUtils.EMPTY;
                String url = skuPath.replace(CommonConstants.COMMERCE_PATH + VAR_PRODUCT_FOLDER, CommonConstants.DAM_PRODUCT_PATH);
                url = url.contains(BASE_PRODUCT) && !imageName.isEmpty() ? url.substring(0, skuPath.indexOf(BASE_PRODUCT) - 1).concat(imageName).concat(".png") : StringUtils.EMPTY;
                AssetManager assetManager = resourceResolver.adaptTo(AssetManager.class);
                com.adobe.granite.asset.api.Asset repStatusAsset =  assetManager.getAsset(url);
                if(repStatusAsset != null && replicationStatusCheck(repStatusAsset.adaptTo(ValueMap.class))) {
                	 continue;
                }
                url = getDomainUrl(url, resourceResolver); 
                LOGGER.debug("image url:{}", url);
                
                if (jobj.has(IMAGE_TYPE)) {
                    if ((xsltType.equals("OptEIASet") && galleryCount == 2) || galleryCount == 0) {
                        updateXMLStringBuilderGallery.append(defaultImage()).append("\n");
                    }
                    if (jobj.get(IMAGE_TYPE).toString().contains("THUMBNAIL") || jobj.get(IMAGE_TYPE).toString().contains("COMPARE")) {
                        thumbnailCount++;
                    }
                    if ((jobj.get(IMAGE_TYPE).toString().contains("PRIMARY"))) {
                    	hasPrimaryImage = true;
                    	primaryImageStr = updatePrimaryImage(primaryImageStr, url, caption, repStatusAsset, jobj);
                    }
                    
                    updateXMLStringBuilderGallery = updateGalleryImages(updateXMLStringBuilderGallery, url, caption, repStatusAsset, jobj);
                    galleryCount++;
                }
            }
            updateXMLStringBuilderGallery.append("</");
            updateXMLStringBuilderGallery.append(GALLERY_IMAGE_LIST);
            updateXMLStringBuilderGallery.append(">");
            String updateXMLStringGallery = updateXMLStringBuilderGallery.toString();
            if (Boolean.TRUE.equals(hasPrimaryImage)) {
                updateXMLStringGallery = updateXMLStringGallery.replace(defaultImage(), primaryImageStr);
            }
            xmlString = xmlString.replaceAll(galleryexistingXMLString, updateXMLStringGallery);
            xmlString = updateGalleryCount(galleryCountString, xmlString, galleryCount, thumbnailCount);
        }

        return xmlString;
    }
    
    
    /**
     * updating gallery images
     * 
     * @param updateXMLStringBuilderGallery
     * @param url
     * @param caption
     * @param repStatusAsset
     * @param jobj
     * 
     */
    private StringBuilder updateGalleryImages(StringBuilder updateXMLStringBuilderGallery, String url, String caption, com.adobe.granite.asset.api.Asset repStatusAsset, JsonObject jobj) {
    	if(repStatusAsset != null && jobj.get(IMAGE_TYPE).toString().contains("GALLERY")) {
    		updateXMLStringBuilderGallery.append("<");
            updateXMLStringBuilderGallery.append(GALLERY_IMAGE);
            updateXMLStringBuilderGallery.append(">");
            updateXMLStringBuilderGallery.append("\n");
            updateValueInNode(updateXMLStringBuilderGallery, url, "url");
            updateValueInNode(updateXMLStringBuilderGallery, caption, CAPTION2);
            updateXMLStringBuilderGallery.append("</");
            updateXMLStringBuilderGallery.append(GALLERY_IMAGE);
            updateXMLStringBuilderGallery.append(">");
            updateXMLStringBuilderGallery.append("\n");
    	}
	return updateXMLStringBuilderGallery;
    }
    
    /**
     * updating primary images
     * 
     * @param primaryImageStr
     * @param url
     * @param caption
     * @param repStatusAsset
     * @param jobj
     * 
     */
    private String updatePrimaryImage(String primaryImageStr, String url, String caption, com.adobe.granite.asset.api.Asset repStatusAsset, JsonObject jobj) {
    	if (jobj.get(IMAGE_TYPE).toString().contains("PRIMARY") && primaryImageStr.isEmpty() && repStatusAsset != null) {
    		StringBuilder primaryImage = new StringBuilder();
        	primaryImage.append("<");
            primaryImage.append(GALLERY_IMAGE);
            primaryImage.append(">");
            primaryImage.append("\n");
            updateValueInNode(primaryImage, url, "url");
            updateValueInNode(primaryImage, caption, CAPTION2);
            primaryImage.append("</");
            primaryImage.append(GALLERY_IMAGE);
            primaryImage.append(">");
            primaryImage.append("\n");
            primaryImageStr = primaryImage.toString();
        }
		return primaryImageStr;
    }
    /**
     * getting the domain url
     * 
     * @param url
     * @param resourceResolver
     * 
     * @return url 
     */
    private String getDomainUrl(String url, ResourceResolver resourceResolver) {
    	if (authenticationEnabled.equalsIgnoreCase("true")) {
            LOGGER.debug("TDSDocumentGenerationServlet: {}", authenticationEnabled);
            String servletPath = PROD_AUTHOR_DOMAIN.concat(CommonConstants.IMAGE_SERVLETPATH);
            url = servletPath.concat("?imagePath=").concat(url);
        } else {
            url = externalizer.publishLink(resourceResolver, resourceResolver.map(url));
        }
    	return url;
    }
    
    /**
     * checking the replication status of the ON_DEMAND_TDS
     * 
     * @param map
     * @return boolean
     */
    private boolean replicationStatusCheck(ValueMap map) {
    	if (null != map) {
    		String customReplicationStatus = map.get(CommonConstants.CUSTOM_REPLICATION_STATUS, String.class);
			String lastReplicationAction = map.get(CommonConstants.CQ_LAST_REPICATION_ACTION, String.class);
			if (null != customReplicationStatus || null != lastReplicationAction) {
				return (CommonConstants.UNPUBLISHED.equals(customReplicationStatus) || CommonConstants.DEACTIVATE.equals(lastReplicationAction));
			}
    	}
    	return false;
    }
    
    /**
     * updating gallery count
     * 
     * @param galleryCountString
     * @param xmlString
     * @param galleryCount
     * @param thumbnailCount
     * 
     */
    private String updateGalleryCount(String galleryCountString, String xmlString, int galleryCount, int thumbnailCount) {
    	if (xmlString.contains(galleryCountString)) {
        	LOGGER.debug("Thumbnail/Compare Images Count: {}", thumbnailCount);
        	galleryCount = galleryCount - thumbnailCount;
            LOGGER.debug("Gallery Images Count: {}", galleryCount);
            StringBuilder galleryCountUpdated = new StringBuilder();
            galleryCountUpdated.append("<");
            galleryCountUpdated.append(GALLERY_IMAGE_COUNT);
            galleryCountUpdated.append(XMLNS_PATH);
            galleryCountUpdated.append(">");
            galleryCountUpdated.append(galleryCount);
            galleryCountUpdated.append("</");
            galleryCountUpdated.append(GALLERY_IMAGE_COUNT);
            galleryCountUpdated.append(">");
            xmlString = xmlString.replaceAll(galleryCountString, galleryCountUpdated.toString());
        }
		return xmlString;
    }
    
    /**
     * default image
     *
     * @return defaultString
     */
    private String defaultImage() {
        return DEFAULT_IMAGE;
    }

    /**
     * @param doc
     * @return XMl InputSteam
     * @throws TransformerException
     */
    private InputStream transformXMLtoStream(Document doc) throws TransformerException {
        LOGGER.debug(" Converting XMl to InputStream ");
        TransformerFactoryImpl transformerFactoryImpl = new TransformerFactoryImpl();
        Transformer transformer = transformerFactoryImpl.newTransformer();
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        StreamResult resultXml = new StreamResult(outputStream);
        transformer.transform(source, resultXml);
        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    /**
     * @param tdsXsltassetStream
     * @param xmlString
     * @return Output Stream
     * @throws TransformerException
     * @throws IOException
     * @throws SAXException
     */
    private ByteArrayOutputStream convertingXMltoPDF(InputStream tdsXsltassetStream, String xmlString, ResourceResolver resourceResolver) throws TransformerException, SAXException, IOException {
        LOGGER.debug(" Converting XMl to PDF ");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Resource configRes = resourceResolver.getResource(CommonConstants.TDS_TEMPLATE_BASE_PATH + "/fop.xconf");
        if (null != configRes) {
            InputStream is = configRes.adaptTo(Asset.class).getOriginal().getStream();
            File tempFile = File.createTempFile("fop", "xconf");
            tempFile.deleteOnExit();
            try(FileOutputStream neout = new FileOutputStream(tempFile)) {   
           	  IOUtils.copy(is, neout);
               neout.close();
               }
            catch(IOException ex) {
                LOGGER.error("Exception occurred",ex);
            }
            FopConfParser parser = new FopConfParser(tempFile);
            FopFactoryBuilder builder = parser.getFopFactoryBuilder();
            builder.setStrictFOValidation(false);
            FopFactory fopFactory = builder.build();
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
            TransformerFactoryImpl transformerFactoryImpl = new TransformerFactoryImpl();
            Transformer transformer = transformerFactoryImpl.newTransformer(new StreamSource(tdsXsltassetStream));
            if (transformer != null) {
                LOGGER.debug("Transforming Started");
                transformer.setParameter("versionParam", "2.0");
                Result res = new SAXResult(fop.getDefaultHandler());
                final StreamSource xmlSource = new StreamSource(new StringReader(xmlString));
                transformer.transform(xmlSource, res);
                LOGGER.debug("Transforming Ended");
            }
        }
        return out;
    }

    /**
     * @return XMl String.
     * @throws IOException
     */
    private String extractingStrFromIS(InputStream tdsXmlassetStreamStr) throws IOException {
        StringWriter stringWriter = new StringWriter();
        if (tdsXmlassetStreamStr.available() > 0)
            IOUtils.copy(tdsXmlassetStreamStr, stringWriter, StandardCharsets.UTF_8.toString());
        return stringWriter.toString();
    }

    /**
     * @param multifield
     * @param hpVM
     * @param attribute
     * @return xmlString
     */
    private String extractValueFromArray(String multifield, ValueMap hpVM, String attribute) {
        String xmlString = StringUtils.EMPTY;
        if (hpVM.containsKey(multifield)) {
            String multiFieldData = hpVM.get(multifield).toString();
            if (!multiFieldData.isEmpty()) {
                Gson gs = CommonHelper.getGsonInstance();
                JsonArray jsonArray = gs.fromJson(multiFieldData, JsonArray.class);
                for (JsonElement jele : jsonArray) {
                    JsonObject jobj = jele.getAsJsonObject();
                    if (jobj.has(attribute)) {
                    	StringBuilder xmlStringBuilder = new StringBuilder(xmlString);
                    	xmlStringBuilder.append(",").append(jobj.get(attribute).getAsString());
                    	xmlString = xmlStringBuilder.toString();
                    }
                }
                if (!xmlString.isEmpty()) {
                    xmlString = xmlString.replaceFirst(",", "");
                }
            }
        }
        LOGGER.debug("extracting the vaues as string: {}", xmlString);
        return xmlString;
    }

    /**
     * @param updatedValue
     * @param xmlString
     * @param nodeName
     * @param attribute1
     * @param attribute2
     * @param separater
     * @return xmlString
     */
    private String singleValuesToMapList(String updatedValue, String xmlString, String nodeName, String attribute1, String attribute2, String separater) {
        StringBuilder existingXMLStringBuilder = new StringBuilder();
        existingXMLStringBuilder.append("<");
        existingXMLStringBuilder.append(nodeName + "List");
        existingXMLStringBuilder.append("/>");
        String existingXMLString = existingXMLStringBuilder.toString();
        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(updatedValue, JsonArray.class);
        if (xmlString.contains(existingXMLString)) {
            StringBuilder updateXMLStringBuilder = new StringBuilder();
            updateXMLStringBuilder.append("<");
            updateXMLStringBuilder.append(nodeName + "List");
            updateXMLStringBuilder.append(">");
            updateXMLStringBuilder.append("\n");

            for (JsonElement jele : jsonArray) {
                JsonObject jobj = jele.getAsJsonObject();
                if (null != jobj) {
                    String nodeValue = buildNodeValue(jobj, attribute1, attribute2, separater, nodeName);
                    if (StringUtils.isNotEmpty(nodeValue)) {
                        updateXMLStringBuilder.append("<");
                        updateXMLStringBuilder.append(nodeName);
                        updateXMLStringBuilder.append(">");
                        updateXMLStringBuilder.append("\n");
                        updateValueInNode(updateXMLStringBuilder, nodeValue, "value");
                        updateXMLStringBuilder.append("</");
                        updateXMLStringBuilder.append(nodeName);
                        updateXMLStringBuilder.append(">");
                        updateXMLStringBuilder.append("\n");
                        LOGGER.debug("inside the INNER FOR loop for attributes {}", nodeName);
                    }
                }
            }
            updateXMLStringBuilder.append("</");
            updateXMLStringBuilder.append(nodeName + "List");
            updateXMLStringBuilder.append(">");

            xmlString = xmlString.replaceAll(existingXMLString, updateXMLStringBuilder.toString());
        }

        return xmlString;

    }

    private String buildNodeValue(JsonObject jobj, String attribute1, String attribute2, String separater, String nodeName) {
        String nodeValue = StringUtils.EMPTY;
        if (jobj.has(attribute1)) {
            nodeValue = jobj.get(attribute1).getAsString();
            if (attribute2 != null && jobj.has(attribute2)) {
                nodeValue += separater + jobj.get(attribute2).getAsString();
            }
        }
        if (nodeName.equals(ISO_TYPE) && jobj.has(ISO_TYPE)) {
            nodeValue = nodeValue + separater + jobj.get(ISO_TYPE).getAsString();
        }
        if (separater.equals("(")) {
            nodeValue = nodeValue.concat(")");
        }
        return nodeValue;
    }

    /**
     * @param updatedValue
     * @param xmlString
     * @return xmlString
     */
    private String references(String updatedValue, String xmlString) {
        StringBuilder existingXMLStringBuilder = new StringBuilder();
        existingXMLStringBuilder.append("<");
        existingXMLStringBuilder.append(REFERENCES_LIST);
        existingXMLStringBuilder.append("/>");
        String existingXMLString = existingXMLStringBuilder.toString();
        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(updatedValue, JsonArray.class);
        if (xmlString.contains(existingXMLString)) {
            StringBuilder updateXMLStringBuilder = new StringBuilder();
            updateXMLStringBuilder.append("<");
            updateXMLStringBuilder.append(REFERENCES_LIST);
            updateXMLStringBuilder.append(">");
            updateXMLStringBuilder.append("\n");

            for (JsonElement jele : jsonArray) {
                JsonObject jobj = jele.getAsJsonObject();
                if (null != jobj && jobj.has(AEM_REFERENCES_ATTRIBUTE)) {
                    String nodeValue = jobj.get(AEM_REFERENCES_ATTRIBUTE).getAsString();
                    updateXMLStringBuilder.append("<");
                    updateXMLStringBuilder.append(REFERENCES);
                    updateXMLStringBuilder.append(">");
                    updateXMLStringBuilder.append("\n");
                    updateValueInNode(updateXMLStringBuilder, nodeValue, "data");
                    updateXMLStringBuilder.append("</");
                    updateXMLStringBuilder.append(REFERENCES);
                    updateXMLStringBuilder.append(">");
                    updateXMLStringBuilder.append("\n");
                }
            }

            updateXMLStringBuilder.append("</");
            updateXMLStringBuilder.append(REFERENCES_LIST);
            updateXMLStringBuilder.append(">");
            LOGGER.debug("References : {}", updateXMLStringBuilder);
            xmlString = xmlString.replaceAll(existingXMLString, updateXMLStringBuilder.toString());
        }

        return xmlString;

    }

    /**
     * @param updatedValue
     * @param nodeName
     */
    @SuppressWarnings("unchecked")
    private String multiValueToMapList(String updatedValue, String xmlString, String nodeName) {
        StringBuilder existingXMLStringBuilder = new StringBuilder();

        existingXMLStringBuilder.append("<");
        existingXMLStringBuilder.append(nodeName + "List");
        existingXMLStringBuilder.append("/>");
        String existingXMLString = existingXMLStringBuilder.toString();

        Gson gs = CommonHelper.getGsonInstance();
        JsonArray jsonArray = gs.fromJson(updatedValue, JsonArray.class);

        if (xmlString.contains(existingXMLString)) {
            StringBuilder updateXMLStringBuilder = new StringBuilder();
            updateXMLStringBuilder.append("<");
            updateXMLStringBuilder.append(nodeName + "List");
            updateXMLStringBuilder.append(">");
            updateXMLStringBuilder.append("\n");


            for (JsonElement jele : jsonArray) {
                updateXMLStringBuilder.append("<");
                updateXMLStringBuilder.append(nodeName);
                updateXMLStringBuilder.append(">");
                updateXMLStringBuilder.append("\n");

                JsonObject jobj = jele.getAsJsonObject();
                HashMap<String, String> multiValueMap = gs.fromJson(jobj.toString(), HashMap.class);
                for (Map.Entry<String, String> map : multiValueMap.entrySet()) {
                    updateValueInNode(updateXMLStringBuilder, map.getValue(), map.getKey());
                    LOGGER.debug("inside the INNER FOR loop for attributes {}", nodeName);
                }
                updateXMLStringBuilder.append("</");
                updateXMLStringBuilder.append(nodeName);
                updateXMLStringBuilder.append(">");
                updateXMLStringBuilder.append("\n");
            }
            updateXMLStringBuilder.append("</");
            updateXMLStringBuilder.append(nodeName + "List");
            updateXMLStringBuilder.append(">");
            String replaceAttributesToMatchXml = replaceAttributesToMatchXml(updateXMLStringBuilder.toString());
            LOGGER.debug("manufacturing Components : {}", replaceAttributesToMatchXml);
            xmlString = xmlString.replaceAll(existingXMLString, replaceAttributesToMatchXml);
        }

        return xmlString;
    }

    /**
     * @param replaceAttributesToMatchXml
     * @return updated replaceAttributesToMatchXml
     */
    private String replaceAttributesToMatchXml(String replaceAttributesToMatchXml) {

        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("<componentMaterialNumber", "<materialNumber");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("<tdsCloneName", "<cloneId");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("<isoType", "<isoTypeCode");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("<sizeUOM", "<unit");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("<entrezGeneID", "<entrezGeneId");

        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("</componentMaterialNumber", "</materialNumber");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("</tdsCloneName", "</cloneId");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("</isoType", "</isoTypeCode");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("</sizeUOM", "</unit");
        replaceAttributesToMatchXml = replaceAttributesToMatchXml.replace("</entrezGeneID", "</entrezGeneId");
        return replaceAttributesToMatchXml;
    }

    /**
     * @param updatedXMLStringBuilder
     * @param value
     * @param nodeName
     * @return updatedXMLStringBuilder
     */
    private StringBuilder updateValueInNode(StringBuilder updatedXMLStringBuilder, String value, String nodeName) {
        updatedXMLStringBuilder.append("<");
        updatedXMLStringBuilder.append(nodeName);
        updatedXMLStringBuilder.append(" xmlns=\"http://www.w3.org/1999/xhtml\">");
        updatedXMLStringBuilder.append(value);
        updatedXMLStringBuilder.append("</");
        updatedXMLStringBuilder.append(nodeName);
        updatedXMLStringBuilder.append(">");
        updatedXMLStringBuilder.append("\n");

        return updatedXMLStringBuilder;

    }

    /**
     * @param attributeName
     * @param doc
     * @param value
     * @return doc
     */
    private Document updateXMLNodeData(String attributeName, Document doc, String value) {
        Node node = doc.getElementsByTagName(attributeName).item(0);
        if (null != node) {
            node.setTextContent(value);
        }
        return doc;
    }

    /**
     * Adding headers to the xml
     *
     * @param xmlString
     * @return xmlString
     */
    private String addHeader(String xmlString) {
        String entities = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r\n" +
                "\r\n" +
                "<!DOCTYPE xsl:stylesheet [\r\n" +
                "<!ENTITY nbsp \"&#160;\" >\r\n" +
                "<!ENTITY trade \"&#8482;\">\r\n" +
                "<!ENTITY deg \"&#176;\">\r\n" +
                "<!ENTITY lt \"&#60;\">\r\n" +
                "<!ENTITY le \"&#8804;\">\r\n" +
                "<!ENTITY amp \"&#38;\">\r\n" +
                "<!ENTITY epsilon \"&#949;\">\r\n" +
                "<!ENTITY Kappa \"&#922;\">\r\n" +
                "<!ENTITY kappa \"&#954;\">\r\n" +
                "<!ENTITY aacute \"&#225;\">\r\n" +
                "<!ENTITY eacute \"&#233;\">\r\n" +
                "<!ENTITY iacute \"&#237;\">\r\n" +
                "<!ENTITY iuml \"&#239;\">\r\n" +
                "<!ENTITY ntilde \"&#241;\">\r\n" +
                "<!ENTITY oacute \"&#243;\">\r\n" +
                "<!ENTITY ouml \"&#246;\">\r\n" +
                "<!ENTITY uacute \"&#250;\">\r\n" +
                "<!ENTITY uuml \"&#252;\">\r\n" +
                "<!ENTITY alpha \"&#945;\">\r\n" +
                "<!ENTITY beta \"&#946;\">\r\n" +
                "<!ENTITY gamma \"&#947;\">\r\n" +
                "<!ENTITY delta \"&#948;\">\r\n" +
                "<!ENTITY zeta \"&#950;\">\r\n" +
                "<!ENTITY eta \"&#951;\">\r\n" +
                "<!ENTITY lambda \"&#955;\">\r\n" +
                "<!ENTITY mu \"&#956;\">\r\n" +
                "<!ENTITY nu \"&#957;\">\r\n" +
                "<!ENTITY xi \"&#958;\">\r\n" +
                "<!ENTITY omicron \"&#959;\">\r\n" +
                "<!ENTITY pi \"&#960;\">\r\n" +
                "<!ENTITY rho \"&#961;\">\r\n" +
                "<!ENTITY sigma \"&#963;\">\r\n" +
                "<!ENTITY tau \"&#964;\">\r\n" +
                "<!ENTITY upsilon \"&#965;\">\r\n" +
                "<!ENTITY phi \"&#966;\">\r\n" +
                "<!ENTITY chi \"&#967;\">\r\n" +
                "<!ENTITY psi \"&#968;\">\r\n" +
                "<!ENTITY omega \"&#969;\">\r\n" +
                "<!ENTITY micro \"&#181;\">\r\n" +
                "<!ENTITY theta \"&#952;\">\r\n" +
                "<!ENTITY Phi \"&#934;\">\r\n" +
                "<!ENTITY reg \"&#174;\">\r\n" +
                "<!ENTITY plusmn \"&#177;\">\r\n" +
                "<!ENTITY ge \"&#8805;\">\r\n" +
                "<!ENTITY ndash \"&#8211;\">\r\n" +
                "<!ENTITY mdash \"&#8212;\">\r\n" +
                "<!ENTITY bull \"&#8226;\">\r\n" +
                "<!ENTITY times \"&#215;\">\r\n" +
                "<!ENTITY Uuml \"&#220;\">\r\n" +
                "]>\r\n" +
                "\r\n";
        xmlString = xmlString.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", entities);
        return xmlString;
    }

    /**
     * No TDS PDF
     *
     * @param currentTdsBase
     * @param resourceResolver
     * @return
     */
    private ByteArrayOutputStream noTDS(Resource currentTdsBase, ResourceResolver resourceResolver, InputStream tdsXmlTemplate) {
        String xsltName = TDS_XSLT_TEMPLATE_BASE_NAME + "No_TDS_Type" + XSLT_EXTENSION;
        Asset tdsXsltTemplate = currentTdsBase.getChild(xsltName).adaptTo(Asset.class);
        InputStream noTDSInputStream = tdsXsltTemplate.getOriginal().getStream();
        ByteArrayOutputStream pdfStream = new ByteArrayOutputStream();
        try {
            String xmlString = extractingStrFromIS(tdsXmlTemplate);
            pdfStream = convertingXMltoPDF(noTDSInputStream, xmlString, resourceResolver);
        } catch (TransformerException | SAXException | IOException e) {
            LOGGER.error("Exception occured due to:", e);
        }
        return pdfStream;
    }

    /**
     * @param attr
     * @return String after handled
     */
    private String stringValidation(String attr) {
        /**
         * HTML validations
         */
        attr = StringEscapeUtils.unescapeHtml(attr);
        attr = StringEscapeUtils.unescapeJava(attr);
        /**
         * Handling & as it is special character in xml
         */
        attr = attr.replace("&", "&amp;");
        /**
         * Handling br tags
         */
        attr = attr.replace("<br>", "<br />");
        attr = attr.replace("<area>", "<area />");
        attr = attr.replace("<base>", "<base />");

        attr = attr.replace("<col>", "<col />");
        attr = attr.replace("<embed>", "<embed />");
        attr = attr.replace("<hr>", "<hr />");

        attr = attr.replace("<ol>", "<ol xmlns=\"http://www.w3.org/1999/xhtml\">");
        attr = attr.replace("<li>", "<li xmlns=\"http://www.w3.org/1999/xhtml\">");
        return attr;
    }

    /**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(Configuration config) {
        this.authenticationEnabled = config.authenticationEnabled();
    }

    /**
     * Get the Authentication Enabled Value
     *
     * @return
     */
    protected String getAuthenticationEnabled() {
        return authenticationEnabled;
    }

    /**
     * The Interface Configuration.
     */
    @ObjectClassDefinition(name = "BDB TDS Authentication Config")
    public @interface Configuration {
        /**
         * Get the BDB hybris domain.
         *
         * @return
         * @return the bdb hybris domain
         */
        @AttributeDefinition(name = "authenticationEnabled", description = "Enable check-box if the prod domain is Authentication", type = AttributeType.STRING) String authenticationEnabled() default "false";
    }
}
