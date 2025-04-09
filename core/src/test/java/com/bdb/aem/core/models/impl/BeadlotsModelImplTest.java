package com.bdb.aem.core.models.impl;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.bdb.aem.core.bean.BeadlotsGroupBean;
import com.bdb.aem.core.models.BeadlotsGroupModel;
import com.bdb.aem.core.models.BeadlotsPartNumbersModel;
import com.bdb.aem.core.services.ExternalizerService;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;
import junitx.util.PrivateAccessor;
import org.apache.sling.api.resource.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * The Class BeadlotsModelImplTest.
 */
@ExtendWith({ MockitoExtension.class })
class BeadlotsModelImplTest {

	/** The beadlots model impl. */
	@InjectMocks
	BeadlotsModelImpl beadlotsModelImpl;

	/** The resolver factory. */
	@Mock
	ResourceResolverFactory resolverFactory;

	/** The resource resolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The externalizer service. */
	@Mock
	ExternalizerService externalizerService;

	/** The query builder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The session. */
	@Mock
	Session session;

	/** The query. */
	@Mock
	Query query;

	/** The result. */
	@Mock
	SearchResult result;

	/** The beadlots category bean. */
	@Mock
	BeadlotsCategoryBean beadlotsCategoryBean;

	/** The test value. */
	private String TEST_VALUE = "test";

	/**
	 * Sets the up.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@BeforeEach
	void setUp() throws NoSuchFieldException {
		PrivateAccessor.setField(beadlotsModelImpl, "categoryLabel", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "beadlotsCategoryTitle", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "beadlotsCategorySubTitle", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "statusLabel", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "partNumberLabel", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "beadlotsFilesTitle", TEST_VALUE);
		PrivateAccessor.setField(beadlotsModelImpl, "beadlotsCategoryBean", beadlotsCategoryBean);
	}

	/**
	 * Test init.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 * @throws LoginException       the login exception
	 * @throws RepositoryException  the repository exception
	 */
	@Test
	void testInit() throws NoSuchFieldException, LoginException, RepositoryException {
		List<Resource> partNumberMultiField = new ArrayList<>();
		Resource resource = Mockito.mock(Resource.class);
		Resource partNumberResource = Mockito.mock(Resource.class);
		ValueMap partNumberValueMap = Mockito.mock(ValueMap.class);
		partNumberMultiField.add(resource);
		BeadlotsGroupModel partNumberGroup = Mockito.mock(BeadlotsGroupModel.class);
		BeadlotsPartNumbersModel partNumberModel = Mockito.mock(BeadlotsPartNumbersModel.class);
		Hit hit = Mockito.mock(Hit.class);
		Map<String, Object> writeServiceAuth = Collections.singletonMap(ResourceResolverFactory.SUBSERVICE,
				"writeService");
		List<BeadlotsGroupModel> authoredBeadlotsGroupList = new ArrayList<>();
		authoredBeadlotsGroupList.add(partNumberGroup);
		List<BeadlotsPartNumbersModel> listBeadlotsPartNumbersModel = new ArrayList<>();
		listBeadlotsPartNumbersModel.add(partNumberModel);
		List<Hit> listHit = new ArrayList<>();
		listHit.add(hit);

		PrivateAccessor.setField(beadlotsModelImpl, "partNumberMultiField", partNumberMultiField);

		when(resource.adaptTo(BeadlotsGroupModel.class)).thenReturn(partNumberGroup);
		when(partNumberGroup.getAuthoredPartNumbersList()).thenReturn(listBeadlotsPartNumbersModel);
		when(partNumberModel.getPartNumber()).thenReturn("partNumber");
		when(partNumberModel.getBadge()).thenReturn("badge");
		when(resourceResolver.adaptTo(QueryBuilder.class)).thenReturn(queryBuilder);
		when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		when(queryBuilder.createQuery(Mockito.any(), Mockito.any())).thenReturn(query);
		when(query.getResult()).thenReturn(result);
		when(result.getTotalMatches()).thenReturn(1l);
		when(result.getHits()).thenReturn(listHit);
		when(hit.getPath()).thenReturn("content/bdb/hit/path");
		when(resourceResolver.getResource("content/bdb/hit/path")).thenReturn(partNumberResource);
		when(partNumberResource.hasChildren()).thenReturn(true);
		when(partNumberResource.getValueMap()).thenReturn(partNumberValueMap);
		when(partNumberValueMap.get("materialNumber")).thenReturn("10");
		when(partNumberValueMap.get("materialDescription")).thenReturn("documentDescription");
		when(partNumberResource.getChildren()).thenReturn(partNumberMultiField);
		when(resource.getValueMap()).thenReturn(partNumberValueMap);
		when(partNumberValueMap.get("documentNumber")).thenReturn("documentNumber");
		when(partNumberValueMap.get("regStatus")).thenReturn("regStatus");
		when(partNumberValueMap.get("releaseDate")).thenReturn("releaseDate");
		when(partNumberValueMap.get("documentPart")).thenReturn("documentPart");
		when(partNumberValueMap.get("documentType")).thenReturn("documentType");
		when(partNumberValueMap.get("documentVersion")).thenReturn("documentVersion");
		when(partNumberValueMap.get("softwareVersion")).thenReturn("softwareVersion");
		when(partNumberValueMap.get("status")).thenReturn("status");
		when(partNumberValueMap.get("batchExpiryDate")).thenReturn("20210605");

		beadlotsModelImpl.init();
	}

	/**
	 * Testformat date.
	 */
	@Test
	void testformatDate() {

		assertNotNull(beadlotsModelImpl.formatDate("20201208"));
	}

	/**
	 * Testformat date parse exception.
	 */
	@Test
	void testformatDateParseException() {

		assertNull(beadlotsModelImpl.formatDate("28"));
	}

	/**
	 * Test get methods.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testGetMethods() throws NoSuchFieldException {
		/*
		 * List<BeadlotsGroupBean> beadlotsGroupBeanList=new ArrayList<>();
		 * PrivateAccessor.setField(beadlotsModelImpl, "beadlotsGroupBeanList",
		 * beadlotsGroupBeanList);
		 */
		assertEquals(TEST_VALUE, beadlotsModelImpl.getBeadlotsCategoryTitle());
		assertEquals(TEST_VALUE, beadlotsModelImpl.getBeadlotsCategorySubTitle());
		assertEquals(TEST_VALUE, beadlotsModelImpl.getStatusLabel());
		assertEquals(TEST_VALUE, beadlotsModelImpl.getPartNumberLabel());
		assertEquals(TEST_VALUE, beadlotsModelImpl.getBeadlotsFilesTitle());
		assertEquals(Collections.emptyList(), beadlotsModelImpl.getBeadlotsGroupBeanList());
		assertEquals(beadlotsCategoryBean, beadlotsModelImpl.getBeadlotsCategoryBean());

	}

	/**
	 * Test get methods not null.
	 *
	 * @throws NoSuchFieldException the no such field exception
	 */
	@Test
	void testGetMethodsNotNull() throws NoSuchFieldException {
		List<BeadlotsGroupBean> beadlotsGroupBeanList = new ArrayList<>();
		PrivateAccessor.setField(beadlotsModelImpl, "beadlotsGroupBeanList", beadlotsGroupBeanList);

		assertNotNull(beadlotsModelImpl.getBeadlotsCategoryTitle());
		assertNotNull(beadlotsModelImpl.getBeadlotsCategorySubTitle());
		assertNotNull(beadlotsModelImpl.getStatusLabel());
		assertNotNull(beadlotsModelImpl.getPartNumberLabel());
		assertNotNull(beadlotsModelImpl.getBeadlotsFilesTitle());
		assertNotNull(beadlotsModelImpl.getBeadlotsGroupBeanList());
		assertNotNull(beadlotsModelImpl.getBeadlotsCategoryBean());

	}

}
