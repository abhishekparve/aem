package com.bdb.aem.core.pdmigration;


import com.adobe.acs.commons.fam.ActionManager;
import com.adobe.acs.commons.mcp.ProcessDefinition;
import com.adobe.acs.commons.mcp.ProcessInstance;
import com.adobe.acs.commons.mcp.form.FormField;
import com.adobe.acs.commons.mcp.form.RadioComponent;
import com.adobe.acs.commons.mcp.model.GenericReport;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.runtime.ServiceComponentRuntime;
import org.osgi.service.component.runtime.dto.ComponentDescriptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Arrays;

public class ManageOsgiServices extends ProcessDefinition {

    public static final String COMMA = ",";
    public static final String ENABLE = "enable";
    public static final String MCP_EXECUTION_REPORT = "MCP Execution Report";
    private static final Logger logger = LoggerFactory.getLogger(ManageOsgiServices.class);
    private static final GenericReport report = new GenericReport();
    public static final String INSTANCE_DESCRIPTION = "Manage OSGI Components/Services MCP process";
    public static final String MANAGE_OSGI_SERVICES = "Manage OSGI Services";
    @FormField(name = "OSGI Services to be Managed during Migration",
            description = "Comma separated list OSGI component/services names that need to be managed during the migration. " +
                    "Each of the values should be the component.name property of the OSGI component", required = true)
    private String osgiServices = "com.bdb.aem.core.services.impl.BDBIndexingJob," +
            "com.bdb.aem.core.schedulers.SimpleScheduledTask," +
            "com.bdb.aem.core.services.impl.BDBProcessBulkFutureStateDocJob";


    @FormField(
            name = "Enable / Disable OSGI Component Names (comma separated)",
            component = RadioComponent.EnumerationSelector.class,
            required = true,
            description = "Comma separated list of OSGI Components that need to be Enabled or Disabled during the PF migration process",
            options = {"horizontal", "default=ENABLE"}
    )
    private Mode mode;


    @Override
    public void buildProcess(ProcessInstance instance, ResourceResolver rr) throws LoginException {

        logger.info("Inside buildProcess Method");
        instance.getInfo().setDescription(INSTANCE_DESCRIPTION);
        instance.defineCriticalAction(MANAGE_OSGI_SERVICES, rr, this::manageOSGIServices);
        report.setName(MCP_EXECUTION_REPORT);

    }


    protected void manageOSGIServices(ActionManager manager) {

        Bundle osgiBundle = FrameworkUtil.getBundle(this.getClass());
        ServiceComponentRuntime scr = (ServiceComponentRuntime) osgiBundle.
                getBundleContext().
                getService(osgiBundle.
                        getBundleContext().
                        getServiceReference(ServiceComponentRuntime.class.getName()));
        if (mode.toString().equalsIgnoreCase(ENABLE)) {
            logger.info("Enabling OSGI Components/Services");
        } else {
            logger.info("Disabling OSGI Components/Services");
        }

        if (osgiServices != null) {
            if (!osgiServices.isEmpty()) {

                if (osgiServices.contains(COMMA)) {
                    Arrays.stream(osgiServices.split(COMMA)).forEach(item -> processOSGIComponent(scr, osgiBundle, item, mode.toString().equalsIgnoreCase(ENABLE)));
                } else {
                    processOSGIComponent(scr, osgiBundle, osgiServices, mode.toString().equalsIgnoreCase(ENABLE));

                }
            }
        }

    }

    protected void processOSGIComponent(ServiceComponentRuntime scr, Bundle osgiBundle, String osgiServiceComponent, boolean enable) {

        ComponentDescriptionDTO dto = scr.getComponentDescriptionDTO(osgiBundle, osgiServiceComponent.trim());
        if (dto != null) {
            if (enable) {
                scr.enableComponent(dto);
                logger.info("Component {} enabled by configuration.", dto.implementationClass);
            } else {
                scr.disableComponent(dto);
                logger.info("Component {} disabled by configuration.", dto.implementationClass);
            }
        }
    }

    @Override
    public void storeReport(ProcessInstance instance, ResourceResolver rr) {
        logger.info("Inside storeReport Method");

    }

    @Override
    public void init() throws RepositoryException {
        logger.info("Inside init Method");
    }

    private enum Mode {
        /**
         * Enable OSGI Component
         */
        ENABLE,
        /**
         * Disable OSGI Component
         */
        DISABLE
    }

}
