package com.bdb.aem.core.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * The Class ShoppingListConfig.
 */
public class ShoppingListConfig {

    /** the createShoppingList */
    @SerializedName("createShoppingList")
    private PayloadConfig createShoppingList;

    /** the getShoppingListDetails */
    @SerializedName("getShoppingListDetails")
    private PayloadConfig getShoppingListDetails;

    /** the getShoppingList */
    @SerializedName("getShoppingList")
    private PayloadConfig getShoppingList;

    /** the fileUploadShoppingList */
    @SerializedName("fileUploadShoppingList")
    private PayloadConfig fileUploadShoppingList;

    /** the removeShoppingList */
    @SerializedName("removeShoppingList")
    private PayloadConfig removeShoppingList;

    /** the shareShoppingList */
    @SerializedName("shareShoppingList")
    private PayloadConfig shareShoppingList;
    
    /** the exportShoppingList */
    @SerializedName("exportShoppingList")
    private PayloadConfig exportShoppingList;

    /** the removeShoppingListEntries */
    @SerializedName("removeShoppingListEntries")
    private PayloadConfig removeShoppingListEntries;

    /** the updateShoppingListEntries */
    @SerializedName("updateShoppingListEntries")
    private PayloadConfig updateShoppingListEntries;

    /** the fileUploadShoppingListentries */
    @SerializedName("fileUploadShoppingListentries")
    private PayloadConfig fileUploadShoppingListentries;

    /** the getProductAnnouncements */
    @SerializedName("getProductAnnouncements")
    private PayloadConfig getProductAnnouncements;

    /** the addAllItemsToCart */
    @SerializedName("addAllItemsToCart")
    private PayloadConfig addAllItemsToCart;


    public ShoppingListConfig(PayloadConfig createShoppingList,
                              PayloadConfig getShoppingListDetails,
                              PayloadConfig getShoppingList,
                              PayloadConfig fileUploadShoppingList,
                              PayloadConfig removeShoppingList,
                              PayloadConfig shareShoppingList,
                              PayloadConfig exportShoppingList,
                              PayloadConfig removeShoppingListEntries,
                              PayloadConfig updateShoppingListEntries,
                              PayloadConfig fileUploadShoppingListentries,
                              PayloadConfig getProductAnnouncements,
                              PayloadConfig addAllItemsToCart) {
        this.createShoppingList = createShoppingList;
        this.getShoppingListDetails = getShoppingListDetails;
        this.getShoppingList = getShoppingList;
        this.fileUploadShoppingList = fileUploadShoppingList;
        this.removeShoppingList = removeShoppingList;
        this.shareShoppingList = shareShoppingList;
        this.exportShoppingList = exportShoppingList;
        this.removeShoppingListEntries = removeShoppingListEntries;
        this.updateShoppingListEntries = updateShoppingListEntries;
        this.fileUploadShoppingListentries = fileUploadShoppingListentries;
        this.getProductAnnouncements = getProductAnnouncements;
        this.addAllItemsToCart = addAllItemsToCart;
    }


}
