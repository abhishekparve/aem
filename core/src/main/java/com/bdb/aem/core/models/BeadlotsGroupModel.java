package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

import com.bdb.aem.core.services.ExternalizerService;
import com.google.gson.annotations.SerializedName;

/**
 * The Class BeadlotsGroupModel.
 */
@Model(adaptables = {Resource.class}, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BeadlotsGroupModel {
	
	/** The resource resolver. */
	@SlingObject
	ResourceResolver resourceResolver;
	
	/** The externalizer service. */
	@Inject
    ExternalizerService externalizerService;

    /** The multiple part numbers. */
	//in the new implementation material number will be authored in place of partNumber
    @Inject
    @Named("multiplePartNumbers")
    private List<Resource> multiplePartNumbers;

    /** The information. */
    @Inject
    @Named("information")
    @SerializedName("information")
    private String information;

    /** The installation instructions label. */
    @Inject
    @Named("installationInstructionsLabel")
    @SerializedName("installationInstructionsLabel")
    private String installationInstructionsLabel;

    /** The installation instructions link. */
    @Inject
    @Named("installationInstructionsLink")
    @SerializedName("installationInstructionsLink")
    private String installationInstructionsLink;

    /** The updater file label. */
    @Inject
    @Named("updaterFileLabel")
    @SerializedName("updaterFileLabel")
    private String updaterFileLabel;

    /** The updater file link. */
    @Inject
    @Named("updaterFileLink")
    @SerializedName("updaterFileLink")
    private String updaterFileLink;

    /** The authored part numbers list. */
  //in the new implementation material number will be authored in place of partNumber
    private List<BeadlotsPartNumbersModel> authoredPartNumbersList = new ArrayList<>();

    
    /**
     * Inits the.
     */
    @PostConstruct
    protected void init() {
        if (multiplePartNumbers != null && !multiplePartNumbers.isEmpty()) {

            for (Resource partNumberResource : multiplePartNumbers) {
                BeadlotsPartNumbersModel partNumberModel = partNumberResource.adaptTo(BeadlotsPartNumbersModel.class);
                authoredPartNumbersList.add(partNumberModel);
            }
        }
        installationInstructionsLink = externalizerService.getFormattedUrl(installationInstructionsLink, resourceResolver);
    }
    

    /**
     * Gets the information.
     *
     * @return the information
     */
    public String getInformation() {
        return information;
    }

    /**
     * Gets the installation instructions label.
     *
     * @return the installation instructions label
     */
    public String getInstallationInstructionsLabel() {
        return installationInstructionsLabel;
    }

    /**
     * Gets the installation instructions link.
     *
     * @return the installation instructions link
     */
    public String getInstallationInstructionsLink() {
        return installationInstructionsLink;
    }

    /**
     * Gets the updater file label.
     *
     * @return the updater file label
     */
    public String getUpdaterFileLabel() {
        return updaterFileLabel;
    }

    /**
     * Gets the updater file link.
     *
     * @return the updater file link
     */
    public String getUpdaterFileLink() {
        return updaterFileLink;
    }

    /**
     * Gets the authored part numbers list.
     *
     * @return the authored part numbers list
     */
    public List<BeadlotsPartNumbersModel> getAuthoredPartNumbersList() {
        return new ArrayList<>(authoredPartNumbersList);
    }


}
