package com.bdb.aem.core.servlets;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.request.RequestPathInfo;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bdb.aem.core.models.PromoDetailsModel;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.Hit;
import com.day.cq.search.result.SearchResult;

/**
 * @author knarayansingh
 *
 */
@ExtendWith(MockitoExtension.class)
public class PromotionDetailsServletTest {

	/** The PromotionIdDetailsServlet servlet object. */
	@InjectMocks
	PromotionIdDetailsServlet promotionDetailsServlet;

	/** The ResourceResolverFactory. */
	@Mock
	ResourceResolverFactory resourceResolverFactory;

	/** The BDBApiEndpointService. */
	@Mock
	BDBApiEndpointService bdbApiEndpointService;

	/** The QueryBuilder. */
	@Mock
	QueryBuilder queryBuilder;

	/** The SlingHttpServletRequest. */
	@Mock
	SlingHttpServletRequest request;

	/** The SlingHttpServletResponse. */
	@Mock
	SlingHttpServletResponse response;

	/** The RequestPathInfo. */
	@Mock
	RequestPathInfo requestPathInfo;

	/** The ResourceResolver. */
	@Mock
	ResourceResolver resourceResolver;

	/** The Resource. */
	@Mock
	Resource promotionsResource;

	/** The Session. */
	@Mock
	Session session;

	/** The Query. */
	@Mock
	Query query;

	/** The SearchResult. */
	@Mock
	SearchResult result;

	/** The hitList. */
	@Mock
	List<Hit> hitList;

	/** The Hit. */
	@Mock
	Hit hit;

	/** The Resource. */
	@Mock
	Resource componentResource;

	/** The PrPromoDetailsModel. */
	@Mock
	PromoDetailsModel promoDetailsModel;

	/** The PrintWriter. */
	@Mock
	PrintWriter printWriter;

	@Mock 
	RequestParameter param;

	/**
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		lenient().when(request.getRequestPathInfo()).thenReturn(requestPathInfo);
		String[] selectors = new String[1];
		selectors[0] = "promotionID";
		lenient().when(requestPathInfo.getSelectors()).thenReturn(selectors);
		lenient().when(request.getRequestParameter("promotionUrl")).thenReturn(param);
		lenient().when(request.getResourceResolver()).thenReturn(resourceResolver);
		lenient()
				.when(resourceResolver
						.getResource("/content/bdb/regionvalue/countryvalue/languagevalue-countryvalue/promotions"))
				.thenReturn(promotionsResource);
		lenient().when(resourceResolver.getResource(Mockito.anyString())).thenReturn(componentResource);
		lenient().when(resourceResolver.adaptTo(Session.class)).thenReturn(session);
		Map<String, String> params = new HashMap<>();
		params.put("path", "param");
        params.put("property", "promoId");
        params.put("property.value", "promotionID");
        params.put("p.limit", "1");
		params.put("type", JcrConstants.NT_UNSTRUCTURED);
		lenient().when(queryBuilder.createQuery(PredicateGroup.create(params), session)).thenReturn(query);
		lenient().when(query.getResult()).thenReturn(result);
		lenient().when(result.getTotalMatches()).thenReturn(1L);
		lenient().when(result.getHits()).thenReturn(hitList);
		lenient().when(hitList.get(0)).thenReturn(hit);
		lenient().when(hit.getPath()).thenReturn("hitPath");
		lenient().when(resourceResolver.getResource("hitPath")).thenReturn(componentResource);
		lenient().when(componentResource.adaptTo(PromoDetailsModel.class)).thenReturn(promoDetailsModel);
		lenient().when(response.getWriter()).thenReturn(printWriter);
	}

	/**
	 * @throws IOException
	 */
	@Test
	public void doPostTest() throws IOException {
		promotionDetailsServlet.doPost(request, response);
	}

	/**
	 * @throws IOException
	 * @throws RepositoryException
	 */
	@Test
	public void repositoryExceptionTest() throws IOException, RepositoryException {
		lenient().when(hit.getPath()).thenThrow(RepositoryException.class);
		promotionDetailsServlet.doPost(request, response);
	}

}
