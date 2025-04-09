package com.bdb.aem.core.models;

/**
 * The Interface AccountManagementCertificationsModel.
 */
public interface AccountManagementCertificationsModel {
   
    /**
     * Gets the certifications labels.
     *
     * @return the certifications labels
     */
    String getCertificationsLabels();
    
    /**
     * Gets the certificates config.
     *
     * @return the certificates config
     */
    String getCertificatesConfig();
    
    /**
     * Gets the certifications upload labels.
     *
     * @return the certifications upload labels
     */
    String getUploadCertificationLabels();
    
    /**
     * Gets the certifications upload config.
     *
     * @return the certifications upload config
     */
    String getUploadCertificationConfig();


    /**
     * Gets the getFileUploadLabels.
     *
     * @return the getFileUploadLabels
     */
   String getFileUploadLabels();
}