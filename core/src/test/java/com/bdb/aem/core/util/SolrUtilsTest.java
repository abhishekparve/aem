package com.bdb.aem.core.util;

import static org.mockito.Mockito.lenient;

import java.util.Calendar;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.BDBApiEndpointService;
import com.day.cq.tagging.Tag;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonObject;

@ExtendWith({ MockitoExtension.class })
public class SolrUtilsTest {
	@InjectMocks
	SolrUtils solrUtils;
	/** The resource resolver. */
	@Mock
	ResourceResolver resolver;

	/** The resource. */
	@Mock
	Resource pageContent, baseProductHpResource;
	@Mock
	Page page;
	@Mock
	ValueMap valueMap;
	@Mock
	Object object;
	Tag tag;
	Tag tags[];
	@Mock
	Calendar cal;

	/** The bdb api endpoint service. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	@Test
	void testCheckNotNull() {
		JsonObject assetObj = new JsonObject();
		assetObj.addProperty("myParam", "myParam");
		lenient().when(pageContent.getParent()).thenReturn(pageContent);
		lenient().when(pageContent.adaptTo(Page.class)).thenReturn(page);
		lenient().when(page.getTags()).thenReturn(tags);
		lenient().when(valueMap.get("property")).thenReturn(object);
		lenient().when(baseProductHpResource.getParent()).thenReturn(baseProductHpResource);
		lenient().when(baseProductHpResource.getChild("catalogNo")).thenReturn(baseProductHpResource);
		solrUtils.checkNull("property");
		solrUtils.checkNullProperty(valueMap, "property");
		solrUtils.getJsonProperty(assetObj, "myParam");
		solrUtils.getVariantHpResource(baseProductHpResource, "catalogNo");

	}

	@Test
	void testCheckNull() {
		lenient().when(pageContent.getParent()).thenReturn(pageContent);
		lenient().when(pageContent.adaptTo(Page.class)).thenReturn(page);
		lenient().when(page.getTags()).thenReturn(tags);
		solrUtils.checkNull("");
		solrUtils.checkNullProperty(valueMap, "property");
		solrUtils.solrDate(cal);
		solrUtils.getVariantHpResource(null, "catalogNo");
	}
}
