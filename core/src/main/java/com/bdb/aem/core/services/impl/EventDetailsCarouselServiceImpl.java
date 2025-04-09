package com.bdb.aem.core.services.impl;

import java.util.Iterator;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;

import com.bdb.aem.core.models.EventBlogCarouselDetailsModel;

/**
 * The service EventDetailsCarouselServiceImpl.
 * 
 * @author ronbanerjee
 *
 */

@Component(service = EventDetailsCarouselServiceImpl.class)
public class EventDetailsCarouselServiceImpl {

	/**
	 * Updates the sling model.
	 * 
	 * @param slingModel sling model
	 * @param resolver resolver
	 */
	public void updateSlingModel(EventBlogCarouselDetailsModel slingModel, ResourceResolver resolver) {

		final String pagePath = Optional.ofNullable(slingModel).map(s -> s.getSearchField()).orElse(null);
		final ValueMap valueMap = Optional.ofNullable(pagePath).filter(StringUtils::isNotEmpty)
				.map(p -> resolver.getResource(p + "/jcr:content")).map(Resource::getValueMap).orElse(ValueMap.EMPTY);

		if(null!=slingModel) {
			slingModel.setCurrentLabel(getValue(valueMap, "current"));
			slingModel.setPastLabel(getValue(valueMap, "past"));
			slingModel.setUpcomingLabel(getValue(valueMap, "upcoming"));
		}

		final Resource eventResource = getEventResource(pagePath, resolver);

		if(null != slingModel && slingModel.getSelection().equals("event")) {
			final Resource childResource = Optional.ofNullable(eventResource).map(r -> r.getChild("eventDateAndTimeLabel"))
					.orElse(null);
			if (null != childResource && childResource.listChildren().hasNext()) {
				final Resource grandChildResource = childResource.listChildren().next();
					Optional.ofNullable(grandChildResource).map(e -> e.getValueMap()).map(v -> getValue(v, "multifieldEventStartDate"))
					.ifPresent(d -> slingModel.setEventStartDate(d));
			
					Optional.ofNullable(grandChildResource).map(e -> e.getValueMap()).map(v -> getValue(v, "multifieldEventEndDate"))
					.ifPresent(d -> slingModel.setEventEndDate(d));
			}
			Optional.ofNullable(eventResource).map(e -> e.getValueMap()).map(v -> getValue(v, "eventType"))
			.ifPresent(d -> slingModel.setEventType(d));
		}

	}

	/**
	 * Gets value from value map.
	 * 
	 * @param valueMap the value map.
	 * @param key key
	 * @return the value
	 */
	private String getValue(final ValueMap valueMap, String key) {

		return Optional.ofNullable(valueMap).map(v -> v.get(key, String.class)).orElse(StringUtils.EMPTY);
	}

	/**
	 * Gets the event resource.
	 * 
	 * @param pagePath the page path
	 * @param resolver the resolver
	 * @return event resource.
	 */
	private Resource getEventResource(String pagePath, ResourceResolver resolver) {

		final Resource resource = Optional.ofNullable(resolver).map(r -> r.getResource(pagePath + "/jcr:content/root"))
				.orElse(null);
		Iterator<Resource> it = ((resource==null)? null : resource.listChildren());
		if (it != null && it.hasNext()) {
			while (it.hasNext()) {
				final Resource childRes = it.next();
				if (childRes.getResourceType().equals("bdb-aem/proxy/components/content/eventblogDetails")) {
					return childRes;
				}
			}
		}
		return null;
	}
}
