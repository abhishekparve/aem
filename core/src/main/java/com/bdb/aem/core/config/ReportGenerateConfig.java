package com.bdb.aem.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "Report Generation Service Configuration", description = "Configure Report Generation Service parameters")
public @interface ReportGenerateConfig {
	
	 @AttributeDefinition(name = "DAM asset path", description = "Asset Path for Report Generation Service", type = AttributeType.STRING)
	 public String damAssetPath() default "/content/dam/bdb/products/global";
	 
	 @AttributeDefinition(name = "Commerce products path", description = "Commerce Path for Report Generation Service", type = AttributeType.STRING)
	 public String commerceContentPath() default	"/content/commerce/products/bdb/products";

	 @AttributeDefinition(name = "Report from email", description = "Resource Sending from email address", type = AttributeType.STRING)
	 public String fromEmail() default "noreply@bd.com";
	 
	 @AttributeDefinition(name = "Report email subject", description = "Resource Sending from email address", type = AttributeType.STRING)
	 public String emailSubject() default "Attached are the list of resources that are missing in the DAM asset in comparision to commerce media data.";
	 
	 @AttributeDefinition(name = "Report email message", description = "Resource Sending from email address", type = AttributeType.STRING)
	 public String emailMessage() default "Attached is the list of resources which are missing from the dam asset.";
	 
	 @AttributeDefinition(name = "Report Recipient User Group Name", description = "Report Recipient User Group Name", type = AttributeType.STRING)
	 public String reportRecipientUserGroup() default "bdlegal";
	 
	 @AttributeDefinition(name = "Resource Missing email template", description = "Resource Missing Email Template", type = AttributeType.STRING)
	 public String resourceMissingEmailTemplate() default "/etc/email-templates/missing-reports/email-missing-assets-report.html";
	 
	
}
