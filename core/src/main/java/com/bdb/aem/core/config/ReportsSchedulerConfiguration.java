package com.bdb.aem.core.config;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "ReportsSchedulerConfiguration", description = "Reports scheduler configuration")
public @interface ReportsSchedulerConfiguration {

	/**
	 * This method will return the name of the Scheduler
	 * @return {@link String}
	 */
	@AttributeDefinition(
			name = "Scheduler name",
			description = "Generate Reports for all Resources that are missing.",
			type = AttributeType.STRING)
	public String schdulerName() default "Resource Missing notification reports scheduler";


	/**
	 * This method will check if the scheduler is concurrent or not
	 * @return {@link Boolean}
	 */
	@AttributeDefinition(
			name = "Enabled",
			description = "True, if scheduler service is enabled",
			type = AttributeType.BOOLEAN)
	public boolean enabled() default true;

	/**
	 * This method returns the Cron expression which will decide how the scheduler will run
	 * @return {@link String}
	 */
	@AttributeDefinition(
			name = "Cron Expression",
			description = "Cron expression used by the scheduler, default is every day at 12 AM.",
			type = AttributeType.STRING)
	public String cronExpression() default "0 0 0 * * ?";
}
