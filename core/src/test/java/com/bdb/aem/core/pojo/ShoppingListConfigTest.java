package com.bdb.aem.core.pojo;

import junitx.framework.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ShoppingListConfigTest {

    /**
     * The ShoppingListConfig.
     */
    ShoppingListConfig shoppingListConfig;

    @Mock
    PayloadConfig createShoppingList;

    @Mock
    PayloadConfig getShoppingListDetails;

    @Mock
    PayloadConfig getShoppingList;

    @Mock
    PayloadConfig fileUploadShoppingList;

    @Mock
    PayloadConfig removeShoppingList;

    @Mock
    PayloadConfig shareShoppingList;
    
    @Mock
    PayloadConfig exportShoppingList;

    @Mock
    PayloadConfig removeShoppingListEntries;

    @Mock
    PayloadConfig updateShoppingListEntries;

    @Mock
    PayloadConfig fileUploadShoppingListentries;

    @Mock
    PayloadConfig getPromotionEndpoint;

    @Mock
    PayloadConfig addAllItemsToCart;

    /**
     * Sets the up.
     *
     * @throws Exception the exception
     */
    @BeforeEach
    void setUp() throws Exception {
        shoppingListConfig = new ShoppingListConfig(
                createShoppingList,
                getShoppingListDetails,
                getShoppingList,
                fileUploadShoppingList,
                removeShoppingList,
                shareShoppingList,
                exportShoppingList,
                removeShoppingListEntries,
                updateShoppingListEntries,
                fileUploadShoppingListentries,
                getPromotionEndpoint,
                addAllItemsToCart
        );
    }

    /**
     * Test.
     */
    @Test
    void test() {
        Assert.assertNotNull(shoppingListConfig);
    }
}

