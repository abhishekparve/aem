package com.bdb.aem.core.models;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.jcr.resource.api.JcrResourceConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.services.solr.SolrSearchService;
import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.SolrUtils;
import com.day.cq.commons.inherit.InheritanceValueMap;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class PdpTestimonialLookUpModelTest {

	@InjectMocks
	PdpTestimonialLookUpModel pdpTestimonialLookUpModel;

	@Mock
	SlingHttpServletRequest request;

	@Mock
	RequestPathInfo requestPathInfo;

	@Mock
	InheritanceValueMap pageProperties;

	@Mock
	SolrSearchService solrSearchService;

	@Mock
	ResourceResolver resourceResolver;

	@Mock
	Resource resource, nextResource, nextResource1;

	@Mock
	Resource markResource, finalResource;

	@Mock
	Iterator<Resource> itrResource;

	@Mock
	ValueMap valueMap, newValueMap;
	
	String testimonialId;
	private String productVarHPPath = "var/commerce/products/bdb/product/variant/hp";


	@BeforeEach
	void setUp() throws Exception {

		PrivateAccessor.setField(pdpTestimonialLookUpModel, "testimonialId", "mock");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "testimonial", "testimonialId");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "bgColor", "bgColor");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "ctaBgColor", "ctaBgColor");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "ctaLabel", "ctaLabel");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "ctaTextColor", "ctaTextColor");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "ctaUrl", "ctaUrl");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "department", "department");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "description", "description");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "fontColor", "fontColor");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "fontStyle", "fontStyle");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "imagePath", "imagePath");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "imageAlt", "imageAlt");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "name", "name");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "university", "university");
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "videoEnabled", true);
		PrivateAccessor.setField(pdpTestimonialLookUpModel, "brightcoveVideoId", "brightcoveVideoId");
		String[] selectors = new String[] { "junit", "mock" };

		lenient().when(pageProperties.getInherited(CommonConstants.TESTIMONIAL_URL, StringUtils.EMPTY))
				.thenReturn("/path/to/testimonial");
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		lenient().when(request.getAttribute(CommonConstants.PRODUCT_VAR_HP_PATH)).thenReturn(resource);		
		lenient().when(solrSearchService.getHpNodeResource("mock", "us", resourceResolver)).thenReturn(resource);
		lenient().when(resource.getPath()).thenReturn("path/to/hp");
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(markResource);
		lenient().when(markResource.hasChildren()).thenReturn(true);
		lenient().when(markResource.getParent()).thenReturn(finalResource);
		lenient().when(finalResource.getChild("mock")).thenReturn(nextResource);
		lenient().when(nextResource.getChild(CommonConstants.HP_NODE)).thenReturn(finalResource);
		lenient().when(finalResource.getPath()).thenReturn("path/to/hp");
	}

	@Test
	void testInit() throws LoginException {
		lenient().when(markResource.getChild("us")).thenReturn(nextResource);
		lenient().when(nextResource.getValueMap()).thenReturn(newValueMap);
		pdpTestimonialLookUpModel.init();
	}
	@Test
	void testElseInit() throws LoginException {
		lenient().when(markResource.getChild(CommonConstants.GLOBAL)).thenReturn(nextResource);
		lenient().when(nextResource.getValueMap()).thenReturn(newValueMap);
		pdpTestimonialLookUpModel.init();
	}

	@Test
	void testGetters() {
		assertEquals(pdpTestimonialLookUpModel.getTestimonial(), "testimonialId");
		assertEquals(pdpTestimonialLookUpModel.getBgColor(), "bgColor");
		assertEquals(pdpTestimonialLookUpModel.getCtaBgColor(), "ctaBgColor");
		assertEquals(pdpTestimonialLookUpModel.getCtaLabel(), "ctaLabel");
		assertEquals(pdpTestimonialLookUpModel.getCtaTextColor(), "ctaTextColor");
		assertEquals(pdpTestimonialLookUpModel.getCtaUrl(), "ctaUrl");
		assertEquals(pdpTestimonialLookUpModel.getDepartment(), "department");
		assertEquals(pdpTestimonialLookUpModel.getDescription(), "description");
		assertEquals(pdpTestimonialLookUpModel.getFontColor(), "fontColor");
		assertEquals(pdpTestimonialLookUpModel.getFontStyle(), "fontStyle");
		assertEquals(pdpTestimonialLookUpModel.getImagePath(), "imagePath");
		assertEquals(pdpTestimonialLookUpModel.getImageAlt(), "imageAlt");
		assertEquals(pdpTestimonialLookUpModel.getName(), "name");
		assertEquals(pdpTestimonialLookUpModel.getUniversity(), "university");
		assertEquals(pdpTestimonialLookUpModel.getVideoEnabled(), true);
		assertEquals(pdpTestimonialLookUpModel.getBrightcoveVideoId(), "brightcoveVideoId");

	}

	@Test
	void testFetchTestimonialId() {
		lenient().when(resource.getChild("global")).thenReturn(markResource);
		lenient().when(markResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get("testimoniaId", String.class)).thenReturn("12345");
		//pdpTestimonialLookUpModel.fetchTestimonialId(resource);
	}

	@Test
	void testGetLookupPage() throws LoginException {
		lenient().when(resourceResolver.getResource("path" + CommonConstants.JCR_CONTENT)).thenReturn(resource);
		lenient().when(resource.isResourceType("bdb-aem/proxy/components/structure/home-page")).thenReturn(true);
		lenient().when(resourceResolver.getResource("path" + CommonConstants.JCR_CONTENT_PATH))
				.thenReturn(markResource);
		lenient().when(markResource.hasChildren()).thenReturn(true);
		List<Resource> resourceList = new ArrayList<>();
		resourceList.add(nextResource);
		Iterator<Resource> itr = resourceList.iterator();
		lenient().when(markResource.listChildren()).thenReturn(itr);
		lenient().when(nextResource.getValueMap()).thenReturn(valueMap);
		lenient().when(valueMap.get(JcrResourceConstants.SLING_RESOURCE_TYPE_PROPERTY, StringUtils.EMPTY))
				.thenReturn(CommonConstants.TESTIMONIAL_DATATYPE);
		lenient().when(nextResource.getChild("slides")).thenReturn(finalResource);
		
		List<Resource> resourceList1 = new ArrayList<>();
		resourceList1.add(nextResource1);
		Iterator<Resource> itr1 = resourceList1.iterator();
		lenient().when(finalResource.listChildren()).thenReturn(itr1);
		lenient().when(nextResource1.getValueMap()).thenReturn(newValueMap);
		lenient().when(newValueMap.get("testimonialId", "")).thenReturn("mock");
		pdpTestimonialLookUpModel.getLookUpPageContent("path");
	}

}
