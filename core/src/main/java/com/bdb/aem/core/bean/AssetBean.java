package com.bdb.aem.core.bean;

public class AssetBean {

	private String assetPaths;
	
	private String materialNumber;
	
	private String assetName;

	public String getAssetPaths() {
		return assetPaths;
	}

	public void setAssetPaths(String assetPaths) {
		this.assetPaths = assetPaths;
	}

	public String getAssetName() {
		return assetName;
	}

	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	
	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	@Override
	public String toString() {
		return "AssetBean [assetPaths=" + assetPaths + ", assetName=" + assetName + "]";
	}
	
	
	 	@Override
	    public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((assetPaths == null) ? 0 : assetPaths.hashCode());
	        result = prime * result + ((assetName == null) ? 0 : assetName.hashCode());
	        result = prime * result + ((materialNumber == null) ? 0 : materialNumber.hashCode());
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
	        AssetBean other = (AssetBean) obj;
	        if (assetPaths == null) {
	            if (other.assetPaths != null)
	                return false;
	        } else if (!assetPaths.equals(other.assetPaths))
	            return false;
	        if (materialNumber == null) {
	            if (other.materialNumber != null)
	                return false;
	        } else if (!materialNumber.equals(other.materialNumber))
	            return false;
	        return true;
	    }
}
