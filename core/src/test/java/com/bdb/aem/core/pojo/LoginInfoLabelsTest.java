package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

/**
 * The Class LoginInfoLabelsTest.
 */
public class LoginInfoLabelsTest {
    /**
     * The accountSettingLabels.
     */
    LoginInfoLabels loginInfoLabels;

    /** the personalInfoLabels */
    @Mock
    PersonalInfoLabels personalInfoLabels;



    /** the accountPrefrenceLabels */
    @Mock
    AccountPreferenceLabels accountPreferenceLabels;


    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        loginInfoLabels = new LoginInfoLabels("title",
                "emailAddressLabel",
                "passwordLabels",
                "updatePassword",
                "editPwdLabel",
                "enterNewPwdLabel",
                "confirmNewPwdLabel",
                "cancelCTALabel",
                "updateCTALabel",
                "successMessage",
                "updatePasswordModalHeader");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(loginInfoLabels);
    }
}


