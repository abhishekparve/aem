package com.bdb.aem.core.akamai.ccu.v3;

import java.util.Collections;
import java.util.List;

import javax.json.JsonException;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonObject;

/**
 * POJO for preparing Post Data JSON for Akamai Purge Call
 */
public class CCUPostData {

	private final Logger logger = LoggerFactory.getLogger(CCUPostData.class);

	/** The hostname. */
	private String hostname;

	/** The objects. */
	private List<String> objects;

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the hostname.
	 *
	 * @param hostname the new hostname
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Gets the objects.
	 *
	 * @return the objects
	 */
	public List<String> getObjects() {
		return Collections.unmodifiableList(objects);
	}

	/**
	 * Sets the objects.
	 *
	 * @param objects the new objects
	 */
	public void setObjects(List<String> objects) {
		this.objects = Collections.unmodifiableList(objects);
	}

	public String toJSON() {

		JsonObject obj = new JsonObject();

		try {
			obj.addProperty("hostname", hostname);
			if (CollectionUtils.isNotEmpty(objects)) {
				obj.addProperty("objects", objects.toString());
			}

		} catch (JsonException e) {
			logger.error("JSONException::", e);
		}

		return obj.toString();
	}

}
