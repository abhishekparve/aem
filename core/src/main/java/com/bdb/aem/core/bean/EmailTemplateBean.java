package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EmailTemplateBean {
	
	private List<String> participants;

    private String templateSelector;

    private String emailSubject;

    private String recipientUser;
    
    private String payload;
    
    private Map<String, Object> inputParams;
    
    
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public List<String> getParticipants() {
		if (null != participants) {
			return new ArrayList<>(participants);
		} else {
			return Collections.emptyList();
		}
	}

	public String getTemplateSelector() {
		return templateSelector;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public String getRecipientUser() {
		return recipientUser;
	}

	public Map<String, Object> getInputParams() {
		return inputParams;
	}

	public void setParticipants(List<String> participants) {
		if (participants != null) {
			participants = new ArrayList<>(participants);
			this.participants = Collections.unmodifiableList(participants);
		}
	}

	public void setTemplateSelector(String templateSelector) {
		this.templateSelector = templateSelector;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public void setRecipientUser(String recipientUser) {
		this.recipientUser = recipientUser;
	}

	public void setInputParams(Map<String, Object> inputParams) {
		this.inputParams = inputParams;
	}
    
    

}
