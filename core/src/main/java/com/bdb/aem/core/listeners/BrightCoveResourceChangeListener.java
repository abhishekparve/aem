package com.bdb.aem.core.listeners;

import com.bdb.aem.core.util.CommonConstants;
import com.bdb.aem.core.util.CommonHelper;
import com.day.cq.dam.api.DamEvent;
import org.apache.sling.api.resource.*;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(immediate = true, service = EventHandler.class, property = EventConstants.EVENT_TOPIC + "=" + DamEvent.EVENT_TOPIC)
public class BrightCoveResourceChangeListener implements EventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(BrightCoveResourceChangeListener.class);
    @Reference
    private ResourceResolverFactory resolverFactory;

    private boolean shouldBeProcessed(DamEvent damEvent) {
        // list of Asset related events
        return DamEvent.Type.METADATA_UPDATED.equals(damEvent.getType());
    }

    @Override
    public void handleEvent(org.osgi.service.event.Event event) {
        DamEvent damEvent = DamEvent.fromEvent(event);
        if (shouldBeProcessed(damEvent)) {
            String path = damEvent.getAdditionalInfo();
            if (path.contains(CommonConstants.METADATA_PATH_AS_CHILD + CommonConstants.SLASH + CommonConstants.DC_DESCRIPTION)) {
                ResourceResolver resolver = null;
                try {
                    resolver = CommonHelper.getServiceResolver(resolverFactory);
                } catch (LoginException e) {
                    throw new RuntimeException(e);
                }
                Resource res = resolver.getResource(damEvent.getAssetPath() + CommonConstants.METADATAPATH);
                ModifiableValueMap props = res.adaptTo(ModifiableValueMap.class);

                props.put(CommonConstants.BRC_DESCRIPTION, props.get(CommonConstants.DC_DESCRIPTION));

                try {
                    resolver.commit();
                } catch (PersistenceException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}