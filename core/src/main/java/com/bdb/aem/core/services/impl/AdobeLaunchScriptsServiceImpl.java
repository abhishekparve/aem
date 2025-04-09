package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.AdobeLaunchScriptsService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(immediate=true,service=AdobeLaunchScriptsService.class)
@Designate(ocd=AdobeLaunchScriptsServiceImpl.Configuration.class)
public class AdobeLaunchScriptsServiceImpl implements AdobeLaunchScriptsService {

private String  launchScripts;

@Activate
@Modified
protected final void activate(Configuration config){

    this.launchScripts = config.launchScripts();


}


    @Override
    public String getLaunchScripts() {
        return launchScripts;
    }

    @ObjectClassDefinition(name = "BDB Adobe Launch Configuration")
    public @interface Configuration {
        @AttributeDefinition(name = "launchScripts", description = "Adobe Launch Scripts", type = AttributeType.STRING)
        public String launchScripts() default "";

    }


}
