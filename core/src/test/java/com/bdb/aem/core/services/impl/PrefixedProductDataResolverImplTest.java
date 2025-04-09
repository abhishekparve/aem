package com.bdb.aem.core.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PrefixedProductDataResolverImplTest {

    //@InjectMocks
    private PrefixedProductDataResolverImpl resolver = new PrefixedProductDataResolverImpl();

    @Mock
    PrefixedProductDataResolverImpl.Configuration configuration;


    @BeforeEach
    void setUp() throws Exception {
        when(configuration.mapping()).thenReturn(new String[] {
                "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-]+)->/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1",
                "/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-]+)->/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1",
                "/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-]+)->/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1"
        });
        resolver.activate(configuration);
    }

    @Test
    void isPathPrefixed() {
        assertFalse(resolver.isPathPrefixed("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"));
        assertFalse(resolver.isPathPrefixed("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"));
        assertFalse(resolver.isPathPrefixed("/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"));
        assertTrue(resolver.isPathPrefixed("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"));
        assertTrue(resolver.isPathPrefixed("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"));
        assertTrue(resolver.isPathPrefixed("/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"));
    }

    @Test
    void resolve() {
        assertEquals(resolver.resolve("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"), "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base");
        assertEquals(resolver.resolve("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/5550"), "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/5550");
        assertEquals(resolver.resolve("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"), "/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base");
        assertEquals(resolver.resolve("/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base"), "/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base");
    }

    @Test
    void map() {
        assertEquals(resolver.map("/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"), "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base");
        assertEquals(resolver.map("/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"), "/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base");
        assertEquals(resolver.map("/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555xxx/5550xx/555000_base"), "/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/555000_base");
    }
}