package com.bdb.aem.core.services.impl;


import com.bdb.aem.core.api.client.impl.RestClientImpl;
import com.bdb.aem.core.exceptions.AemInternalServerErrorException;
import com.bdb.aem.core.services.BDBApiEndpointService;
import com.google.gson.JsonObject;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import junitx.util.PrivateAccessor;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.Cookie;

import static org.mockito.Mockito.when;

/**
 * The Class SignOutServiceImpltest.
 */
@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class SignOutServiceImplTest {

    /** The sign out service impl. */
    @InjectMocks
    private SignOutServiceImpl signOutServiceImpl;

    /** The request. */
    @Mock
    SlingHttpServletRequest request;

    /** The response. */
    @Mock
    SlingHttpServletResponse response;

    /** The cookie. */
    @Mock
    Cookie cookie;

    /**
     * The bdb api endpoint service.
     */
    @InjectMocks
    BDBApiEndpointService bdbApiEndpointService = new BDBApiEndpointServiceImpl();

    /** The rest client impl. */
    @Mock
    RestClientImpl restClientImpl;

    /** The http client. */
    @Mock
    CloseableHttpClient httpClient;

    /**
     * Sets the up.
     *
     * @throws NoSuchFieldException the no such field exception
     */
    @BeforeEach
    void setUp() throws NoSuchFieldException {
        PrivateAccessor.setField(bdbApiEndpointService, "bdbHybrisDomain", "https://api.domain.com");
        PrivateAccessor.setField(bdbApiEndpointService, "getHybrisSignOutEndpoint", "/occ/v2/bdbUS/users/demo/logout");
        signOutServiceImpl = new SignOutServiceImpl();
    }

    /**
     * Test fetch sign out details.
     *
     * @throws AemInternalServerErrorException the aem internal server error exception
     * @throws NoSuchFieldException the no such field exception
     */
    @Test
    void testFetchSignOutDetails() throws AemInternalServerErrorException {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("name", "BDB");
        Cookie[] cookieArray = { cookie };
        when(request.getCookies()).thenReturn(cookieArray);
        when(cookie.getName()).thenReturn("auth_token");
        when(cookie.getValue()).thenReturn("cookie");
        signOutServiceImpl.fetchSignOutDetails(request, requestObject, response);
    }

    /**
     * Test fetch sign out null.
     *
     * @throws NoSuchFieldException the no such field exception
     * @throws AemInternalServerErrorException the aem internal server error exception
     */
    @Test
    void testFetchSignOutNull() throws NoSuchFieldException, AemInternalServerErrorException {
        JsonObject requestObject = new JsonObject();
        requestObject.addProperty("name", "BDB");
        signOutServiceImpl.fetchSignOutDetails(request, requestObject, response);
    }

}
