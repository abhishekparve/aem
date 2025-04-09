package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.UpdateProductDocumentService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.model.WorkflowModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.LoginException;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import com.bdb.aem.core.services.solr.SolrSearchService;
import org.osgi.service.component.annotations.Reference;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.jcr.Session;

@Component(service = UpdateProductDocumentService.class, immediate = true)
public class UpdateProductDocumentServiceImpl implements UpdateProductDocumentService {

    /**
     * The Constant LOG
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProductDocumentServiceImpl.class);
    
    /** The Resource Resolver Factory. */
    @Reference
    private ResourceResolverFactory resolverFactory;

    @Reference
    Replicator replicator;
    
    /** The solr search service. */
    @Reference
    private SolrSearchService solrSearchService;

    /**
     * Creates a Map from the input Excel sheet.
     * <p>
     * DOC path is stored as a key.
     * </p>
     * <p>
     * Regions are stored as a value.
     * </p>
     *
     * @param filePath         the path of Excel sheet in DAM
     * @param resourceResolver the ResourceResolver
     * @param workflowSession  the Workflow Session
     */
    @Override
    public void updateDocumentRegionList(String filePath, ResourceResolver resourceResolver, WorkflowSession workflowSession) {
        LOGGER.debug("Entry in updateDocumentRegionList method to get Excel Data");

        TreeMap<String, String> dataMap = new TreeMap<String, String>();
        try {
            Resource res = resourceResolver.getResource(filePath);
            Asset asset = res.adaptTo(Asset.class);
            Rendition rendition = asset.getOriginal();
            InputStream inputStream = rendition.adaptTo(InputStream.class);
            Workbook excelWorkBook = new XSSFWorkbook(inputStream);

            // Get sheet from the workbook
            Sheet sheet = excelWorkBook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Get the first and last sheet row number
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();

            for (int i = firstRowNum + 1; i < lastRowNum + 1; i++) {
                Row row = sheet.getRow(i);
                int lastCell = row.getLastCellNum();
                
                if(lastCell == -1) {
                	break;
                }

                // Get document path from cell
                Cell cellDamPath = row.getCell(--lastCell);
                String path = cellDamPath.getStringCellValue();

                // Get regions to extend from cell
                Cell cellRegion = row.getCell(--lastCell);
                String region = cellRegion.getStringCellValue();

                // Put document path and regions to extend in Map
                dataMap.put(path, region);
            }
            excelWorkBook.close();

            // Invoking getRegionList method
            getRegionList(dataMap, resourceResolver, workflowSession);
        } catch (Exception e) {
            LOGGER.error("Error Occurred in retrieving excel data : {}", e.getMessage());
        }
        LOGGER.debug("Exit from updateDocumentRegionList method");
    }

    /**
     * Creates region list from the Map to update DOC region.
     *
     * @param dataMap          the Map
     * @param resourceResolver the ResourceResolver
     * @param workflowSession  the workflowSession
     */
    public void getRegionList(Map dataMap, ResourceResolver resourceResolver, WorkflowSession workflowSession) {
        try {
            Set set = dataMap.entrySet();

            for (Object data : set) {
                Map.Entry entry = (Map.Entry) data;

                // Get the document path
                String docPath = entry.getKey() + CommonConstants.METADATAPATH;
                Resource resource = resourceResolver.resolve(docPath);
                ValueMap vm = resource.adaptTo(ValueMap.class);
                if (null != vm && vm.containsKey(CommonConstants.PDF_DOC_REGION)) {

                    // Get the list of region from dataMap value
                    String excelRegions = entry.getValue().toString();

                    // Converting excelRegions string into list
                    String[] excelRegionsList = excelRegions.replaceAll("\\s*,\\s*", ",").split(",");

                    // Creating empty addRegions list to collect regions required to be updated in JCR
                    List<String> addRegions = new ArrayList<String>();

                    if (excelRegions.equalsIgnoreCase("Global")) {
                        String globalRegion = CommonConstants.REGION_TAG + CommonConstants.GLOBAL;
                        addRegions.add(globalRegion);
                    } else {
                        for (String newRegion : excelRegionsList) {
                            newRegion = CommonConstants.REGION_TAG + newRegion;
                            addRegions.add(newRegion);
                        }
                    }

                    if (addRegions.size() > 0) {
                        // Invoking regionUpdate method
                        regionUpdate(docPath, addRegions, resourceResolver, workflowSession);
                    }
                } else {
                    LOGGER.error("Error Occurred Doc path or DocRegion property not exist for path : {}", docPath);
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error Occurred in getRegionList method : {}", e.getMessage());
        } finally {
            if (null != resourceResolver) {
                CommonHelper.closeResourceResolver(resourceResolver);
            }
        }
    }

    /**
     * Re-index the document in Solr for the correct regions.
     *
     * @param docPath          the DOC path
     * @param region           the region list to be updated for the DOC
     * @param resourceResolver the ResourceResolver
     * @param workflowSession  the Session
     */
    public void regionUpdate(String docPath, List<String> region, ResourceResolver resourceResolver, WorkflowSession workflowSession) {
        try {
            String pdfPath = docPath.replace(CommonConstants.METADATAPATH, "");
            deleteAssetFromSolr(pdfPath);
            Resource resource = resourceResolver.resolve(docPath);
            ModifiableValueMap updateRegion = resource.adaptTo(ModifiableValueMap.class);
            int listSize = region.size();
            Object[] updatedRegions = region.toArray(new String[listSize]);
            updateRegion.put(CommonConstants.PDF_DOC_REGION, updatedRegions);
            resource.getResourceResolver().commit();
            docPath = docPath.replace(CommonConstants.METADATAPATH, "");
            Session session = resourceResolver.adaptTo(Session.class);
            replicator.replicate(session,ReplicationActionType.ACTIVATE,docPath);
        } catch (IOException e ) {
            LOGGER.error("Error occurred in updating region : {} for path {}", e.getMessage(), docPath);
        } catch (ReplicationException e) {
        	LOGGER.error("Error occurred in Replication : {} for path {}", e.getMessage(), docPath);
        }
    }
    public void deleteAssetFromSolr(String pdfPath) {
		try {
			ArrayList<HttpSolrClient> solrClients = (ArrayList<HttpSolrClient>) solrSearchService.getAllSolrClients();
			for (HttpSolrClient server : solrClients) {
				server.deleteById(pdfPath);
				server.commit();
				server.close();
			}
		} catch (SolrServerException | LoginException | IOException e) {
			LOGGER.error("Exception due to", e);
		}

	}
}