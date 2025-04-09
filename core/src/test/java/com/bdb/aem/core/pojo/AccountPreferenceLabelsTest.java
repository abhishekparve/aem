package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The Class AccountPreferenceLabelsTest.
 */
public class AccountPreferenceLabelsTest {


    /** The test accountPreferenceLabels. */
    AccountPreferenceLabels accountPreferenceLabels;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        accountPreferenceLabels = new AccountPreferenceLabels(
                "title",
                "subtitle",
                "industrytitile",
                "roletitle",
                "interesttitle",
                "updatelabel",
                "successMessage",
                "closeIcon",
                "closeIconAltText",
                "logoIcon",
                "logoIconAltText");
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertEquals(accountPreferenceLabels.getTitle(),"title");
        Assert.assertEquals(accountPreferenceLabels.getSubTitle(),"subtitle");
        Assert.assertEquals(accountPreferenceLabels.getIndustryTitle(),"industrytitile");
        Assert.assertEquals(accountPreferenceLabels.getRoleTitle(),"roletitle");
        Assert.assertEquals(accountPreferenceLabels.getInterestTitle(),"interesttitle");
        Assert.assertEquals(accountPreferenceLabels.getUpdateLabel(),"updatelabel");
        Assert.assertEquals(accountPreferenceLabels.getSuccessMessage(),"successMessage");
        Assert.assertEquals(accountPreferenceLabels.getCloseIcon(),"closeIcon");
        Assert.assertEquals(accountPreferenceLabels.getCloseIconAltText(),"closeIconAltText");
        Assert.assertEquals(accountPreferenceLabels.getLogoIcon(),"logoIcon");
        Assert.assertEquals(accountPreferenceLabels.getLogoIconAltText(),"logoIconAltText");
    }
}

