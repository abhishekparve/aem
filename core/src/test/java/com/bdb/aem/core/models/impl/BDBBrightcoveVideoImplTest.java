package com.bdb.aem.core.models.impl;


import com.bdb.aem.core.services.BDBApiEndpointService;
import com.bdb.aem.core.services.impl.BDBApiEndpointServiceImpl;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
public class BDBBrightcoveVideoImplTest {

    @InjectMocks
    BDBApiEndpointService serviceConfig = new BDBApiEndpointServiceImpl();

    private AemContext context;

    @InjectMocks
    private BDBBrightcoveVideoImpl BrightcoveVideoImpl;

    @BeforeEach
    void setUp() throws Exception {

        //serviceConfig = null;
  }

    @Test
    void test() {

        assertNotNull(BrightcoveVideoImpl.getBrightcovePlayerId());
        assertNotNull(BrightcoveVideoImpl.getBrightcoveAccountId());


    }


    @Test
    void negativeTest() {

        serviceConfig = null;
        assertNotNull(BrightcoveVideoImpl.getBrightcovePlayerId());
        assertNotNull(BrightcoveVideoImpl.getBrightcoveAccountId());


    }
}
