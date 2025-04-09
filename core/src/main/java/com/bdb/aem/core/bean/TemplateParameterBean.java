package com.bdb.aem.core.bean;

import org.apache.sling.api.resource.Resource;

import com.adobe.granite.workflow.exec.HistoryItem;

public class TemplateParameterBean {
	
	private Resource resource;

    private HistoryItem lastItem;

    private String comment;
    
    private String recipient;

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public HistoryItem getLastItem() {
		return lastItem;
	}

	public void setLastItem(HistoryItem lastItem) {
		this.lastItem = lastItem;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
    
    

}
