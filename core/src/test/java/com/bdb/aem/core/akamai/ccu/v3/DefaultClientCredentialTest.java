
package com.bdb.aem.core.akamai.ccu.v3;


import junitx.framework.Assert;
import org.junit.jupiter.api.Test;

/**
 * Class DefaultClientCredentialTest.
 *
 *
 */
public class DefaultClientCredentialTest {

	/**
	 * Test Constructor.
	 *
	 *
	 */
	@Test
	public void testConstructor() {
		DefaultClientCredential clientCred = new DefaultClientCredential("clientToken", "accessToken", "clientSecret");
		Assert.assertEquals("clientToken", clientCred.getClientToken());
		Assert.assertEquals("accessToken", clientCred.getAccessToken());
		Assert.assertEquals("clientSecret", clientCred.getClientSecret());
	}
}
