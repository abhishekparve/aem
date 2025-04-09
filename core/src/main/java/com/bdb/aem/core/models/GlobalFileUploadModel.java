package com.bdb.aem.core.models;

import com.google.gson.annotations.SerializedName;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GlobalFileUploadModel.
 */
@Model(
        adaptables = {Resource.class},
        resourceType = {GlobalFileUploadModel.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class GlobalFileUploadModel {

    /**
     * the RESOURCE_TYPE.
     */
    protected static final String RESOURCE_TYPE = "bdb-aem/components/content/globalfileupload/v1/globalfileupload";


    /**
     * The Constant log.
     */
    protected static final Logger log = LoggerFactory.getLogger(GlobalFileUploadModel.class);

    /**
     * The file type multi field.
     */
    @Inject
    private List<Resource> fileTypeFieldTitleMultiField;

    /**
     * The file types list.
     */
    @SerializedName("fieldTitleList")
    private List<UploadCertificationFileTypeModel> fileTypesList = new ArrayList<>();

    /** The field title. */
    @Inject
    @SerializedName("fieldTitle")
    private String fieldTitle;
    
    /**
     * The info.
     */
    @Inject
    @SerializedName("info")
    private String info;

    /**
     * The iconSrc.
     */
    @Inject
    @SerializedName("iconSrc")
    private String iconSrc;

    /**
     * The altText.
     */
    @Inject
    @SerializedName("altText")
    private String altText;

    /**
     * The title.
     */
    @Inject
    @SerializedName("title")
    private String title;

    /**
     * The note.
     */
    @Inject
    @SerializedName("note")
    private String note;

    /**
     * The btnName.
     */
    @Inject
    @SerializedName("btnName")
    private String btnName;

    /**
     * The ariaLabel.
     */
    @Inject
    @SerializedName("ariaLabel")
    private String ariaLabel;

    /**
     * The fileIconSrc.
     */
    @Inject
    @SerializedName("fileIconSrc")
    private String fileIconSrc;

    /**
     * The fileIconAltText.
     */
    @Inject
    @SerializedName("fileIconAltText")
    private String fileIconAltText;

    /**
     * The closeIcon.
     */
    @Inject
    @SerializedName("closeIcon")
    private String closeIcon;

    /**
     * The ariaLabelClose.
     */
    @Inject
    @SerializedName("ariaLabelClose")
    private String ariaLabelClose;
    
    /**
     * The ariaLabelClose.
     */
    @Inject
    @SerializedName("error")
    @Default(values = "Your file can't be uploaded because the file format isn't supported (supported file type PDF / DOC / JPG / PNG) or your file exceeds the allowable limit of 2MB")
    private String error;


    @PostConstruct
    protected void init() {
        log.debug("Start Init method of class GlobalFileUploadModel");
        populateFilesTypesList();
    }

    /**
     * Populate files types list.
     */
    private void populateFilesTypesList() {
        if (fileTypeFieldTitleMultiField != null && !fileTypeFieldTitleMultiField.isEmpty()) {

            for (Resource resource : fileTypeFieldTitleMultiField) {
                UploadCertificationFileTypeModel fileTypeProperty = resource.adaptTo(UploadCertificationFileTypeModel.class);
                fileTypesList.add(fileTypeProperty);
            }
        }
    }

    public void setNote(String note){
        this.note = note;
    }
}
