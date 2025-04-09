package com.bdb.aem.core.pdmigration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jcr.RepositoryException;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.distribution.DistributionRequest;
import org.apache.sling.distribution.DistributionRequestType;
import org.apache.sling.distribution.Distributor;
import org.apache.sling.distribution.SimpleDistributionRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.CheckboxComponent;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.PathfieldComponent;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.form.TextfieldComponent;
import com.adobe.acs.commons.mcp.model.GenericReport;
import com.adobe.acs.commons.mcp.model.ManagedProcess;
import com.bdb.aem.core.services.impl.ProcessProductDocumentServiceImpl;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;

public class ProductActivationProcessDefinition extends ProcessDefinition {

	/**
	 * The Constant LOG.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ProcessProductDocumentServiceImpl.class);
	private static final String INDEXED = "indexed";
	private static final String TRUE = "true";
	private static final String TARGET = "target";
	private static final String SOURCE = "source";
	private static final String ACTIVATED = "activated";
	private static final String TAGS = "tags";
	private static final String ASSETS = "assets";
	private static final String COMMERCE = "commerce";
	private static final String ALL = "ALL";
	private static final String SINGLE_PRD_REGEX = "^\\d{6}$";
	private static final String REGEX = "(,?(?:\\d{6}:\\d{6}|\\d{6}))+";

	private static final String EXTRACT_REGEX = "(\\d{6}:\\d{6}|\\d{6})+";
	private static final String PRODUCT_COMMERCE_BASE_PATH = "/content/commerce/products/bdb/products/";
	private static final String PRODUCT_TAGS_BASE_PATH = "/content/cq:tags/bdb/products/";
	private static final String PRODUCT_ASSETS_BASE_PATH = "/content/dam/bdb/products/global/";
	private static final String INDEX = "Index";
	private static final String REPLICATE = "Replicate";

	@FormField(name = "Mode", component = RadioComponent.EnumerationSelector.class, required = true, description = "MCP Execution Mode.", options = {
			"horizontal", "default=DRY_RUN_MODE" })
	private Mode mode;

	@FormField(name = "Material Number", component = TextfieldComponent.class, required = true, description = "Enter ALL, single or range of material numbers. Example: 557666 or 557666:558047 or ALL or 557666,557669:557721,557756")
	private String materialNumber;

	@FormField(name = "Category Path", component = PathfieldComponent.NodeSelectComponent.class, required = true, description = "A category path in Commerce to run the script")
	private String categoryPath;

	@FormField(name = "Force", component = CheckboxComponent.class, options = { "default=false" })
	private boolean forceMode;

	@FormField(name = "Skip indexing", component = CheckboxComponent.class, options = { "default=false" })
	private boolean skipIndexing;

	@FormField(name = "Skip activation", component = CheckboxComponent.class, options = { "default=false" })
	private boolean skipActivation;

	@FormField(name = "Update indexes", component = CheckboxComponent.class, options = { "default=false" })
	private boolean updateIndexes;
	
	@FormField(name = "Alphanumeric Material Number", component = CheckboxComponent.class, options = { "default=false" })
	private boolean alphanumericMaterialNumber;


	private final Distributor distributor;
	private final SolrSearchService solrSearchService;

	ManagedProcess processInfo;
	Resource trackingNodePDRes;
	ResourceResolver resolver;

	private final ArrayList<String> productPaths = new ArrayList<>();
	private final ArrayList<String> assetPaths = new ArrayList<>();
	private final ArrayList<String> tagPaths = new ArrayList<>();
	private final Map<String, String> pdfPaths = new HashMap<>();
	private final List<String> baseProductList = new ArrayList<>();

	private final transient GenericReport report = new GenericReport();
	private final List<EnumMap<ReportColumns, String>> reportData = Collections.synchronizedList(new ArrayList<>());

	public ProductActivationProcessDefinition(Distributor distributor, SolrSearchService solrSearchService) {
		this.distributor = distributor;
		this.solrSearchService = solrSearchService;
	}

	@Override
	public void buildProcess(ProcessInstance instance, ResourceResolver resolver)
			throws LoginException, RepositoryException {
		logger.debug("Entry buildProcess method of ProductActivationProcessDefinition");
		logger.debug("Processing category {}", categoryPath);
		processInfo = instance.getInfo();
		processInfo.setDescription("Running Product Activation & Indexing Process Definition.");

		switch (mode) {
		case LIVE_MODE:
			instance.defineCriticalAction("Validate Product Activation & Indexing Process Request Input", resolver,
					this::startValidateProductActivationIndexingRequest);
			instance.defineCriticalAction("Filter Valid Products & Assets", resolver,
					this::startFetchValidProductPaths);
			instance.defineCriticalAction("Activate & Index Products", resolver,
					this::startActivateAndIndexingProducts);
			break;
		case DRY_RUN_MODE:
			instance.defineCriticalAction("Validate Product Activation & Indexing Process Request Input", resolver,
					this::startValidateProductActivationIndexingRequest);
			instance.defineCriticalAction("Filter Valid Products & Assets", resolver,
					this::startFetchValidProductPaths);
			instance.defineCriticalAction("Activate & Index Products Dryrun Mode", resolver,
					this::startActivateAndIndexingProductsDryrunMode);
			break;
		}
		logger.debug("Exit buildProcess method of ProductActivationProcessDefinition");
	}

	private void startValidateProductActivationIndexingRequest(ActionManager actionManager) {
		actionManager.deferredWithResolver(this::validateProductActivationIndexingRequest);
	}

	private void startFetchValidProductPaths(ActionManager actionManager) {
		actionManager.deferredWithResolver(this::fetchValidPaths);
	}

	private void startActivateAndIndexingProducts(ActionManager actionManager) {
		actionManager.deferredWithResolver(this::activateAndIndexPaths);
	}

	private void startActivateAndIndexingProductsDryrunMode(ActionManager actionManager) {
		actionManager.deferredWithResolver(this::activateAndIndexPathsDryRunMode);
	}

	private void validateProductActivationIndexingRequest(ResourceResolver resolver) {
		logger.info("Entry validateProductActivationIndexingRequest method of ProductActivationProcessDefinition");

		Resource categoryRes = resolver.getResource(categoryPath);
		// verify the target category path exist and it is a category in Commerce
		if (null == categoryRes || ResourceUtil.isNonExistingResource(categoryRes)
				|| !StringUtils.startsWith(categoryPath, PRODUCT_COMMERCE_BASE_PATH)) {
			recordAction("Validate Category Path",
					"Either the target category path doesn't exists or it is not a valid PD category in Commerce",
					"FAILURE", categoryPath);
			throw new IllegalArgumentException(String
					.format("Either category path %s doesn't exist or not a valid commerce category.", categoryPath));
		} else {
			logger.info("Category path {} input is a valid category in commerce.", categoryPath);
			recordAction("Validate Category Path", "The target category path exists and it is a category in Commerce",
					"SUCCESS", categoryPath);
		}

		// Check for tracking info exist
		String trackingNodePDPath = "/var/bdb" + categoryPath + "/products/";
		trackingNodePDRes = resolver.getResource(trackingNodePDPath);

		if (null == trackingNodePDRes || ResourceUtil.isNonExistingResource(trackingNodePDRes)) {
			recordAction("Validate Tracking Path", "Tracking information does not exist for category.", "FAILURE",
					trackingNodePDPath);
			throw new IllegalArgumentException(
					String.format("Tracking node %s doesn't exist for category %s", trackingNodePDPath, categoryPath));
		} else {
			logger.info("Tracking information exist for category {}", categoryPath);
			recordAction("Validate Tracking Path", "Tracking information exist for category.", "SUCCESS",
					trackingNodePDPath);
		}

		if (StringUtils.equalsIgnoreCase(materialNumber, ALL)) {
			Iterator<Resource> firstLevelChilds = trackingNodePDRes.listChildren();

			while (firstLevelChilds.hasNext()) {
				Resource firstLevelChildRes = firstLevelChilds.next();
				Iterator<Resource> secondLevelChilds = firstLevelChildRes.listChildren();

				while (secondLevelChilds.hasNext()) {
					Resource secondLevelChildRes = secondLevelChilds.next();
					Iterator<Resource> basePDChilds = secondLevelChildRes.listChildren();

					while (basePDChilds.hasNext()) {
						Resource basePDRes = basePDChilds.next();
						String productMaterialNum = basePDRes.getName();
						String basePDPath = fetchTargetBasePDPath(resolver, productMaterialNum);
						if (StringUtils.isNotBlank(basePDPath)) {
							baseProductList.add(basePDPath);
						}
					}
				}
			}
		} else if (Pattern.matches(REGEX, materialNumber)) {

			recordAction("Validating Range Material Numbers", "Start", "INFO",
					materialNumber);

			baseProductList.addAll(getBaseProductPaths(resolver, materialNumber));

			recordAction("Validating Range Material Numbers", "End", "INFO",
					materialNumber);
		} else if (Boolean.TRUE.equals(alphanumericMaterialNumber)) {

			recordAction("Validating Range Material Numbers", "Start", "INFO",
					materialNumber);

			baseProductList.addAll(getAlphaNumericBaseProductPaths(resolver, materialNumber));

			recordAction("Validating Range Material Numbers", "End", "INFO",
					materialNumber);
		} else {
			recordAction("Validate Material Number Format", "Not a valid material number format.", "FAILURE",
					materialNumber);
			throw new IllegalArgumentException(
					String.format("Material number %s is not in valid format.", materialNumber));
		}

		logger.info("Exit validateProductActivationIndexingRequest method of ProductActivationProcessDefinition");
	}

	private List<String> getBaseProductPaths(ResourceResolver resolver, String inputMaterialNumbers) {
		Matcher matcher = Pattern.compile(EXTRACT_REGEX).matcher(inputMaterialNumbers);
		List<String> result = new ArrayList<>();
		while (matcher.find()) {
			String group = matcher.group();
			if (StringUtils.isNotBlank(group)) {
				if (group.contains(":")) {
					String[] arr = group.split(":");
					Integer startMN = Integer.valueOf(arr[0]);
					Integer endMN = Integer.valueOf(arr[1]);
					if (endMN < startMN) {
						recordAction("Validate Material Number Format", "Not a valid material number format.",
								"FAILURE", materialNumber);
						throw new IllegalArgumentException(String.format(
								"Range Start: %s is greater than Range End: %s", startMN, endMN));
					}
					while (endMN >= startMN) {
						String bpPath = fetchTargetBasePDPath(resolver, String.valueOf(startMN));
						if (StringUtils.isNotBlank(bpPath)) {
							result.add(bpPath);
						}
						startMN++;
					}
				} else {
					String bpPath = fetchTargetBasePDPath(resolver, group);
					if (StringUtils.isNotBlank(bpPath)) {
						result.add(bpPath);
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param resolver the resolver
	 * @param inputMaterialNumbers the input material number
	 * @return list of material paths.
	 */
	private List<String> getAlphaNumericBaseProductPaths(ResourceResolver resolver, String inputMaterialNumbers) {
		List<String> result = new ArrayList<>();
		if(inputMaterialNumbers.contains(",")) {
			List<String> materialNumberList = Arrays.asList(inputMaterialNumbers.split(","));
			materialNumberList.forEach(materialNumber->{
				String bpPath = fetchTargetBasePDPath(resolver, String.valueOf(materialNumber.trim()));
				if (StringUtils.isNotBlank(bpPath)) {
					result.add(bpPath);
				}
	        });
		} else {
			String bpPath = fetchTargetBasePDPath(resolver, inputMaterialNumbers);
			if (StringUtils.isNotBlank(bpPath)) {
				result.add(bpPath);
			}
		}
		return result;
	}

	private String fetchTargetBasePDPath(ResourceResolver resolver, String materialNum) {
		String basePDTrackingPath = null;
		Resource basePDTrackingRes = null;
		if(Boolean.TRUE.equals(alphanumericMaterialNumber)) {
			basePDTrackingPath = getAlphaNumericMaterialTrackingPath(materialNum);
		} else {
			basePDTrackingPath = getTrackingPath(materialNum);
		}
		if(null != basePDTrackingPath) {
			basePDTrackingRes = resolver.getResource(basePDTrackingPath);
		}
		if (null != basePDTrackingRes) {
			Resource commerce = basePDTrackingRes.getChild(COMMERCE);
			if (null != commerce) {
				ValueMap commerceProps = commerce.getValueMap();
				boolean migrationStatus = commerceProps.get("MigrationFailed", false);
				String baseProductPath = commerceProps.get(TARGET, String.class);
				if (migrationStatus) {
					recordAction("Validate Material Number", "Migration failed", "WARNING", baseProductPath);
					return null;
				}
				Resource targetBPResource = null;
				if (baseProductPath != null) {
					targetBPResource = resolver.getResource(baseProductPath);
				}

				if (null != targetBPResource) {
					recordAction("Validate Material Number", "Target base product exists.", "SUCCESS", baseProductPath);
					return targetBPResource.getPath();
				} else {
					recordAction("Validate Material Number", "Target base product doesn't exist.", "FAILURE", baseProductPath);
				}
			}
		}

		return null;
	}

	private void fetchValidPaths(ResourceResolver resolver) {
		logger.info("Entry fetchValidProductPaths method of ProductActivationProcessDefinition");

		// Get the valid products based on the range input configured by material number
		// property.

		for (String baseProduct : baseProductList) {
			String basePDMaterialNum = getBaseNumber(baseProduct);
			String basePDTrackingPath = null;
			Resource basePDTrackingRes = null;
			if(Boolean.TRUE.equals(alphanumericMaterialNumber)) {
				basePDTrackingPath = getAlphaNumericMaterialTrackingPath(basePDMaterialNum);
			} else {
				basePDTrackingPath = getTrackingPath(basePDMaterialNum);
			}
			if(null != basePDTrackingPath) {
				basePDTrackingRes = resolver.getResource(basePDTrackingPath);
			}
			if (null != basePDTrackingRes) {
				Resource commerceRes = basePDTrackingRes.getChild(COMMERCE);
				if (null != commerceRes) {
					ValueMap commerceProps = commerceRes.getValueMap();
					Boolean activatedFlag = commerceProps.get(ACTIVATED, false);
					String targetPath = commerceProps.get(TARGET, String.class);
					if ((!activatedFlag || forceMode)
							&& StringUtils.isNotBlank(targetPath)
							&& resolver.getResource(targetPath) != null) {
						productPaths.add(targetPath);
					}
					logger.debug("Target path: {}", targetPath);
				}

				Resource assetsRes = basePDTrackingRes.getChild(ASSETS);
				if (null != assetsRes) {
					ValueMap assetProps = assetsRes.getValueMap();
					Boolean activatedFlag = assetProps.get(ACTIVATED, false);
					String targetPath = assetProps.get(TARGET, String.class);
					String sourcePath = assetProps.get(SOURCE, String.class);
					Map<String, String> allPdfs = getAllPdfs(targetPath, sourcePath, resolver);
					if ((!activatedFlag || forceMode)
							&& StringUtils.isNotBlank(targetPath)
							&& resolver.getResource(targetPath) != null) {
						assetPaths.add(targetPath);
						pdfPaths.putAll(allPdfs);
					}
					logger.debug("Pdfs paths: {}", allPdfs);
				}

				Resource tagsRes = basePDTrackingRes.getChild(TAGS);
				if (null != tagsRes) {
					ValueMap tagProps = tagsRes.getValueMap();
					Boolean activatedFlag = tagProps.get(ACTIVATED, false);
					String targetPath = tagProps.get(TARGET, String.class);
					if ((!activatedFlag || forceMode)
							&& StringUtils.isNotBlank(targetPath)
							&& resolver.getResource(targetPath) != null) {
						tagPaths.add(targetPath);
					}
				}
			}
		}

		logger.info("Exit activateandIndexCatalogProducts method of ProductActivationProcessDefinition");
	}

	private String getBaseNumber(String baseProduct) {
		return baseProduct.substring(baseProduct.lastIndexOf("/") + 1).replaceAll("_base",
				StringUtils.EMPTY);
	}

	private String getTrackingPath(String materialNum) {
		String basePath = trackingNodePDRes.getPath();
		StringBuilder builder = new StringBuilder(basePath).append("/");
		String finalPath;
		String temp = materialNum;
		while (temp.length() > 0) {
			if (temp.length() > 2 && temp.length() != 3) {
				builder.append(temp, 0, 3).append("xxx").append("/");
				builder.append(temp, 0, 4).append("xx").append("/");
				temp = temp.substring(3);
			} else {
				temp = "";
			}
		}
		builder.append(materialNum);
		finalPath = builder.toString();
		return finalPath;
	}
	
	/**
	 * 
	 * @param materialNum the materialNum
	 * @return materialPath
	 */
	private String getAlphaNumericMaterialTrackingPath(String materialNum) {
		String basePath = trackingNodePDRes.getPath();
		StringBuilder builder = new StringBuilder(basePath).append("/");
		String finalPath;
		if (materialNum.length() > 6) {
			builder.append(materialNum, 0, materialNum.length()-3).append("xxx").append("/");
			builder.append(materialNum, 0, materialNum.length()-2).append("xx").append("/");
		}
		builder.append(materialNum);
		finalPath = builder.toString();
		return finalPath;
	}

	private void activateAndIndexPathsDryRunMode(ResourceResolver resolver) {
		if (!productPaths.isEmpty()) {
			for (String product : productPaths) {
				recordAction("Replicate & Index Product Path", "dry run", "SUCCESS", product);
			}
		}
		if (!assetPaths.isEmpty()) {
			for (String asset : assetPaths) {
				recordAction("Replicate Asset Path", "dry run", "SUCCESS", asset);
			}
		}
		if (!pdfPaths.isEmpty()) {
			for (Map.Entry<String, String> pdf : pdfPaths.entrySet()) {
				recordAction("Index PDF Asset Path", "dry run", "SUCCESS", pdf.getKey());
			}
		}
		if (!tagPaths.isEmpty()) {
			for (String tag : tagPaths) {
				recordAction("Replicate Tag Path", "dry run", "SUCCESS", tag);
			}
		}
	}

	private void activateAndIndexPaths(ResourceResolver resolver) {
		recordAction("[MAIN] Activating and indexing", "START", "SUCCESS", materialNumber);
		activateAndIndexProductPaths(resolver);
		activateAndIndexAssetPaths(resolver);
		activateTagPaths(resolver);
		recordAction("[MAIN] Activating and indexing", "END", "SUCCESS", materialNumber);
	}

	private void activateAndIndexProductPaths(ResourceResolver resolver) {
		recordAction("[MAIN] [PRODUCTS] Activating and indexing", "START", "SUCCESS", materialNumber);
		if (!productPaths.isEmpty()) {

			for (String productPath : productPaths) {
				recordAction("Activating and indexing product", "START", "SUCCESS", productPath);
				Resource baseProductRes = resolver.getResource(productPath);
				logger.debug("BP path: {}", productPath);
				Iterable<Resource> children = Collections.emptyList();
				if (baseProductRes != null) {
					children = baseProductRes.getChildren();
				}

				List<String> variants = new ArrayList<>();
				String materialNum = getBaseNumber(productPath);
				String bpLookupPath = CommonHelper.getLookUpPathFromProductId(materialNum, CommonConstants.BASE_MATERIAL_NUMBER);
				List<String> paths = new ArrayList<>();
				paths.add(productPath);
				paths.add(bpLookupPath);
				for (Resource child: children) {
					ValueMap valueMap = child.getValueMap();
					logger.debug("Child name: {}", child.getName());
					if (valueMap.containsKey("isVariant")) {
						variants.add(child.getPath());
						String vLookupPath = CommonHelper.getLookUpPathFromProductId(materialNum, CommonConstants.MATERIAL_NUMBER);
						paths.add(vLookupPath);
					}
				}

				if (!skipActivation) {
					DistributionRequest request = new SimpleDistributionRequest(DistributionRequestType.ADD, true,
							paths.toArray(new String[0]));
					distributor.distribute("publish", resolver, request);
					updateTrackingInfo(resolver, materialNum, COMMERCE, REPLICATE);
					recordAction("Replicate Product Path", null, "SUCCESS", productPath);
				} else {
					recordAction("Replicate Product Path", "Skipping replication", "INFO", productPath);
				}

				try {
					if (!skipIndexing) {
						if (updateIndexes) {
							recordAction("Index Product Path", "Update indexing", "INFO", productPath);
							solrSearchService.updateProductDataLocation(resolver, variants);
						} else {
							recordAction("Index Product Path", "Full indexing", "INFO", productPath);
							solrSearchService.indexProductDataToSolr(resolver, variants);
						}
						updateTrackingInfo(resolver, materialNum, COMMERCE, INDEX);
						recordAction("Index Product Path", null, "SUCCESS", productPath);
					} else {
						recordAction("Index Product Path", "Skipping indexing", "INFO", productPath);
					}
				} catch (LoginException | SolrServerException | IOException | RepositoryException e) {
					logger.error("Failed to index products in Solr {}", variants);
					recordAction("Index Product Path", null, "FAILURE", productPath);
				}
				recordAction("Activating and indexing product", "END", "SUCCESS", productPath);
			}
		}
		recordAction("[MAIN] [PRODUCTS] Activating and indexing", "END", "SUCCESS", materialNumber);
	}

	private void updateTrackingInfo(ResourceResolver resolver, String materialNum, String child, String type) {
		String basePDTrackingPath = null;
		Resource basePDTrackingRes = null;
		if(Boolean.TRUE.equals(alphanumericMaterialNumber)) {
			basePDTrackingPath = getAlphaNumericMaterialTrackingPath(materialNum);
		} else {
			basePDTrackingPath = getTrackingPath(materialNum);
		}
		if(null != basePDTrackingPath) {
			basePDTrackingRes = resolver.getResource(basePDTrackingPath);
		}
		try {
			if (null != basePDTrackingRes && StringUtils.isNotBlank(child) && StringUtils.isNotBlank(type)) {
				Resource res = basePDTrackingRes.getChild(child);

				if (null != res) {
					ModifiableValueMap props = res.adaptTo(ModifiableValueMap.class);
					if (props != null) {
						if (StringUtils.equalsIgnoreCase(type, REPLICATE)) {
							props.put(ACTIVATED, true);
						} else if (StringUtils.equalsIgnoreCase(type, INDEX)) {
							props.put(INDEXED, true);
						}
						resolver.commit();
					}
				}
			}
		} catch (PersistenceException e) {
			logger.error("Error occured while persisting the track information.", e);
		}
	}

	private void activateAndIndexAssetPaths(ResourceResolver resolver) {
		recordAction("[MAIN] [ASSETS] Activating and indexing assets", "START", "INFO", materialNumber);
		if (!assetPaths.isEmpty()) {

			for (String assetPath : assetPaths) {
				recordAction("Activating asset", "START", "INFO", assetPath);
				if (!skipActivation) {
					DistributionRequest request = new SimpleDistributionRequest(DistributionRequestType.ADD, true,
							assetPath);
					distributor.distribute("publish", resolver, request);

					String materialNum = getBaseNumber(assetPath);
					updateTrackingInfo(resolver, materialNum, ASSETS, REPLICATE);
				} else {
					recordAction("Activating asset", "Skipping activation", "INFO", assetPath);
				}
				recordAction("Activating asset", "END", "INFO", assetPath);
			}
		}

		if (!pdfPaths.isEmpty()) {

			pdfPaths.forEach((newPath, oldPath) -> {
				recordAction("Activating and indexing pdf", "START", "INFO", newPath);
				recordAction("Removing index pdf", "START", "INFO", oldPath);
				try {
					String assetBasePath = newPath.split("_base")[0];
					String materialNum = newPath.substring(assetBasePath.lastIndexOf("/") + 1, assetBasePath.length());
					Resource resource = resolver.getResource(newPath);
					if (!skipIndexing) {
						if (resource != null) {
							Asset asset = resource.adaptTo(Asset.class);
							if (updateIndexes) {
								solrSearchService.updatePdfDataLocation(resolver, newPath, oldPath);
							} else {
								solrSearchService.unIndexContent(oldPath, CommonConstants.TYPE_PDF);
								solrSearchService.indexAssetsToSolr(asset, resolver);
							}
							updateTrackingInfo(resolver, materialNum, ASSETS, INDEX);
							recordAction("Index PDF Asset Path", null, "SUCCESS", newPath);
						} else {
							recordAction("Index PDF Asset Path", "Resource is missing", "FAILURE", newPath);
						}
					} else {
						recordAction("Index PDF Asset Path", "Skipping indexing", "INFO", newPath);
					}
				} catch (LoginException | IOException | SAXException | TikaException e) {
					logger.error("Failed to index pdf in Solr: {}", newPath);
					recordAction("Index PDF Path", null, "FAILURE", newPath);
				}

				recordAction("Activating and indexing pdf", "END", "INFO", newPath);
			});

		}
		recordAction("[MAIN] [ASSETS] Activating and indexing assets", "END", "INFO", materialNumber);
	}

	private void activateTagPaths(ResourceResolver resolver) {
		recordAction("[MAIN] [TAGS] Activating and indexing tags", "START", "INFO", materialNumber);
		if (!tagPaths.isEmpty()) {

			for (String tagPath : tagPaths) {
				if (!skipActivation) {
					DistributionRequest request = new SimpleDistributionRequest(DistributionRequestType.ADD, true,
							tagPath);
					distributor.distribute("publish", resolver, request);
					String assetBasePath = tagPath.split("_base")[0];
					String materialNum = tagPath.substring(assetBasePath.lastIndexOf("/") + 1, assetBasePath.length());
					updateTrackingInfo(resolver, materialNum, TAGS, REPLICATE);
					recordAction("Replicate Tag Path", null, "SUCCESS", tagPath);
				} else {
					recordAction("Replicate Tag Path", "Skip activation", "INFO", tagPath);
				}
			}
		}
		recordAction("[MAIN] [TAGS] Activating and indexing tags", "END", "INFO", materialNumber);
	}

	private Map<String, String> getAllPdfs(String resourcePath, String oldPath, ResourceResolver resolver) {
		Map<String, String> assetsMap = new HashMap<>();
		Resource pdfFolder = resolver.getResource(resourcePath + "/pdf");

		if (pdfFolder != null) {
			Iterator<Resource> resourceIterator = pdfFolder.listChildren();
			while (resourceIterator.hasNext()) {
				Resource next = resourceIterator.next();
				Asset asset = next.adaptTo(Asset.class);
				if ( asset != null && "application/pdf".equalsIgnoreCase(asset.getMetadataValue("dc:format"))) {
					assetsMap.put(asset.getPath(), oldPath + "/pdf/" + next.getName());
				}
			}
		}

		return assetsMap;
	}

	private String getTagPath(String baseProductPath) {
		return baseProductPath.replace(PRODUCT_COMMERCE_BASE_PATH, PRODUCT_TAGS_BASE_PATH);
	}

	private String getAssetPath(String baseProductPath) {
		return baseProductPath.replace(PRODUCT_COMMERCE_BASE_PATH, PRODUCT_ASSETS_BASE_PATH);
	}

	@Override
	public void storeReport(ProcessInstance instance, ResourceResolver rr)
			throws RepositoryException, PersistenceException {
		logger.info("Inside storeReport Method");
		report.setRows(reportData, ReportColumns.class);
		report.persist(rr, instance.getPath() + "/jcr:content/report");
	}

	private void recordAction(String action, String description, String status, String path) {
		EnumMap<ReportColumns, String> row = new EnumMap<>(ReportColumns.class);
		row.put(ReportColumns.ACTION, action);
		row.put(ReportColumns.DESCRIPTION, description);
		row.put(ReportColumns.STATUS, status);
		row.put(ReportColumns.PATH, path);
		reportData.add(row);
		logger.debug("Action: {}, Description: {}, Status: {}, Path: {}", action, description, status, path);
	}

	private enum Mode {
		LIVE_MODE, DRY_RUN_MODE
	}

	public enum ReportColumns {
		ACTION, DESCRIPTION, STATUS, PATH
	}

	@Override
	public void init() throws RepositoryException {

	}

}
