package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class AccountSettingConfigTest.
 */
class AccountSettingConfigTest {

    /**
     * The accountSettingConfig.
     */
    AccountSettingConfig accountSettingConfig;

    @Mock
    PayloadConfig getUserSettingsConfig;

    @Mock
    PayloadConfig updateUserDetailsConfig;

    @Mock
    PayloadConfig updateUserPwdConfig;



    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        accountSettingConfig = new AccountSettingConfig(
                getUserSettingsConfig,
                updateUserDetailsConfig,
                updateUserPwdConfig
                );
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(accountSettingConfig);
    }
    }