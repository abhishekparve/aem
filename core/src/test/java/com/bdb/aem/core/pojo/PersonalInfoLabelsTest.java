package com.bdb.aem.core.pojo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import junitx.framework.Assert;


/**
 * The Class AccountPreferenceLabelsTest.
 */
public class PersonalInfoLabelsTest {


    /** The test personalInfoLabels. */
    PersonalInfoLabels personalInfoLabels;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        personalInfoLabels = new PersonalInfoLabels(
                "title",
                "firstnamelabel",
                "lastnamelabel",
                "companylabel",
                "phonenumberlabel",
                "eancodelabel",
                "catnumberlabel",
                "updateinformationlabel",
                "mobilenumberlabel",
                "cancelctalabel",
                "updatectalabel",
                "successmessage",
                "updateinformationmodalheader",
                "closeIcon",
                "closeIconAltText",
                "firstNameError",
                "lastNameError",
                "phoneNumberValidationError",
                "phoneNumberPatternError");

    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(personalInfoLabels);

    }
}


