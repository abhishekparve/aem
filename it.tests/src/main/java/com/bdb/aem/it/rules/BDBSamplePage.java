package com.bdb.aem.it.rules;

import org.apache.sling.testing.junit.rules.instance.Instance;

import com.adobe.cq.testing.junit.rules.Page;

/**
 * <p>
 * Rule to create a sample page with {@link #initialName}
 * </p>
 * 
 * 
 * @author ronbanerjee
 *
 */
public class BDBSamplePage extends Page {

	String initialName;

	/**
	 * Constructor to create a new page on the instance: {@code rule}, with page
	 * name {@code initialName}
	 * 
	 * @param rule        the instance object
	 * @param initialName the page name
	 */
	public BDBSamplePage(Instance rule, String initialName) {
		super(rule);
		this.initialName = initialName;
	}

	/**
	 * 
	 * @see Page#initialName()
	 */
	@Override
	protected String initialName() {
		return initialName;
	}

}
