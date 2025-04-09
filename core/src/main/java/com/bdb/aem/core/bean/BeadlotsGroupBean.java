package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The Class BeadlotsGroupBean.
 */
public class BeadlotsGroupBean {

    /** The id. */
    @SerializedName("id")
    private String id;

    /** The description. */
    @SerializedName("description")
    private String description;

    /** The information. */
    @SerializedName("information")
    private String information;

    /** The reg status. */
    @SerializedName("regStatus")
    private String regStatus;

    /** The installation instructions label. */
    @SerializedName("installationInstructionsLabel")
    private String installationInstructionsLabel;

    /** The installation instructions link. */
    @SerializedName("installationInstructionsLink")
    private String installationInstructionsLink;
    
    /** The updater file label. */
    @SerializedName("updaterFileLabel")
    private String updaterFileLabel;

    /** The updater file link. */
    @SerializedName("updaterFileLink")
    private String updaterFileLink;

    /** The files. */
    @SerializedName("files")
    private List<BeadlotFileBean> files;

    /** The material number beans. */
    @SerializedName("materialNumbers")
    private List<BeadlotsMaterialNumberBean> materialNumberBeans = new ArrayList<>();

    /**
     * Gets the material number.
     *
     * @return the material number
     */
    public List<BeadlotsMaterialNumberBean> getMaterialNumber() {
        return new ArrayList<>(materialNumberBeans);
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the reg status.
     *
     * @return the reg status
     */
    public String getRegStatus() {
        return regStatus;
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
     * Gets the files.
     *
     * @return the files
     */
    public List<BeadlotFileBean> getFiles() {
    	if (null != files) {
			return new ArrayList<>(files);
		} else {
			return Collections.emptyList();
		}
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Sets the information.
     *
     * @param information the new information
     */
    public void setInformation(String information) {
        this.information = information;
    }

    /**
     * Sets the reg status.
     *
     * @param regStatus the new reg status
     */
    public void setRegStatus(String regStatus) {
        this.regStatus = regStatus;
    }
    
    /**
     * Sets the installation instructions label.
     *
     * @param installationInstructionsLabel the new installation instructions label
     */
    public void setInstallationInstructionsLabel(String installationInstructionsLabel) {
        this.installationInstructionsLabel = installationInstructionsLabel;
    }

    /**
     * Sets the installation instructions link.
     *
     * @param installationInstructionsLink the new installation instructions link
     */
    public void setInstallationInstructionsLink(String installationInstructionsLink) {
        this.installationInstructionsLink = installationInstructionsLink;
    }
    
    /**
     * Sets the updater file label.
     *
     * @param updaterFileLabel the new updater file label
     */
    public void setUpdaterFileLabel(String updaterFileLabel) {
        this.updaterFileLabel = updaterFileLabel;
    }

    /**
     * Sets the updater file link.
     *
     * @param updaterFileLink the new updater file link
     */
    public void setUpdaterFileLink(String updaterFileLink) {
        this.updaterFileLink = updaterFileLink;
    }

    /**
     * Sets the files.
     *
     * @param files the new files
     */
    public void setFiles(List<BeadlotFileBean> files) {
    	if (files != null) {
			files = new ArrayList<>(files);
			this.files = Collections.unmodifiableList(files);
		}
    }

    /**
     * Sets the material number beans.
     *
     * @param materialNumberBeans the new material number beans
     */
    public void setMaterialNumberBeans(List<BeadlotsMaterialNumberBean> materialNumberBeans) {
    	if (materialNumberBeans == null)
	        throw new IllegalArgumentException("Parameter materialNumberBeans is null");
	    this.materialNumberBeans.clear();
	    this.materialNumberBeans.addAll(materialNumberBeans);
    }
}
