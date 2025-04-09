package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class BeadlotsConfigTest.
 */
class BeadlotsConfigTest {

    /**
     * The test Beadlots Config.
     */
    BeadlotsConfig beadlotsTestConfig;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        List<BeadlotsCategoryBean> beadlotsJson = new ArrayList<>();

        beadlotsTestConfig = new BeadlotsConfig(beadlotsJson);
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(beadlotsTestConfig.getBeadlotsJson());
    }

}
