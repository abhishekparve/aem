package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class AccountSettingLabelsTest.
 */
class AccountSettingLabelsTest {

    /**
     * The accountSettingLabels.
     */
    AccountSettingLabels accountSettingLabels;

    /** the personalInfoLabels */
    @Mock
    PersonalInfoLabels personalInfoLabels;


    /** the distributorInfoLabels */
    @Mock
    DistributorInfoLabels distributorInfoLabels;


    /** the loginInfoLabels */
    @Mock
    LoginInfoLabels loginInfoLabels;

    /** the accountPrefrenceLabels */
    @Mock
    AccountPreferenceLabels accountPreferenceLabels;
    

    /** the nextButtonLabel */
    @Mock
    String nextButtonLabel;


    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        accountSettingLabels = new AccountSettingLabels(
                "title",
                personalInfoLabels,
                distributorInfoLabels,
                loginInfoLabels,
                accountPreferenceLabels,
                nextButtonLabel);
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(accountSettingLabels);
    }
}
