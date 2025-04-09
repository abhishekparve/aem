package com.bdb.aem.core.bean;

import com.google.gson.annotations.SerializedName;

public class BeadlotFileBean {

    @SerializedName("documentNumber")
    private String documentNumber;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("batchExpiryDate")
    private String batchExpiryDate;

    @SerializedName("documentPart")
    private String documentPart;

    @SerializedName("documentVersion")
    private String documentVersion;

    @SerializedName("documentType")
    private String documentType;

    @SerializedName("status")
    private String status;

    @SerializedName("softwareVersion")
    private String softwareVersion;

    @SerializedName("regStatus")
    private String regStatus;

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getBatchExpiryDate() {
        return batchExpiryDate;
    }

    public String getDocumentPart() {
        return documentPart;
    }

    public String getDocumentVersion() {
        return documentVersion;
    }

    public String getDocumentType() {
        return documentType;
    }

    public String getStatus() {
        return status;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public String getRegStatus() {
        return regStatus;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentNumber == null) ? 0 : documentNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeadlotFileBean other = (BeadlotFileBean) obj;
		if (documentNumber == null) {
			if (other.documentNumber != null)
				return false;
		} else if (!documentNumber.equals(other.documentNumber)) {
			return false;
		}
		return true;
	}
    
}
