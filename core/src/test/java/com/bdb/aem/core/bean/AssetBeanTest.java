package com.bdb.aem.core.bean;

import static org.mockito.Mockito.lenient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import junitx.framework.Assert;

public class AssetBeanTest {
	AssetBean assetBean;

	@BeforeEach
	void setUp() throws Exception {
		assetBean = new AssetBean();
		assetBean.setAssetPaths("/content/dam");
		assetBean.setAssetName("assetname");
		assetBean.setMaterialNumber("materialnumber");
		// this;

	}

	@Test
	void test() {
		Assert.assertEquals(assetBean.getAssetPaths(), "/content/dam");
		Assert.assertEquals(assetBean.getAssetName(), "assetname");
		// Assert.assertNotNull(assetBean.getMaterialNumber());
		Assert.assertEquals(assetBean.getMaterialNumber(), "materialnumber");
		Assert.assertEquals(assetBean.toString(), assetBean.toString());
		Assert.assertEquals(assetBean.hashCode(), assetBean.hashCode());
		Assert.assertEquals(assetBean.equals(assetBean), true);

	}

	@Test
	void testNull() {
		assetBean = new AssetBean();
		Assert.assertEquals(assetBean.equals(null), false);
	}

	@Test
	void testHashcode() {
		assetBean = new AssetBean();
		assetBean.setAssetPaths(null);
		assetBean.setAssetName(null);
		assetBean.setMaterialNumber(null);
		Assert.assertEquals(assetBean.hashCode(), assetBean.hashCode());

	}

	@Test
	void testEquals() {
		AssetBean other = new AssetBean();
		other.setAssetPaths("/content/dam/bdb");
		other.setMaterialNumber("materialnumberequals");
		Assert.assertEquals(assetBean.equals(other), false);
	}

	@Test
	void testAssetEquals() {
		assetBean.setAssetPaths(null);
		assetBean.setMaterialNumber(null);
		AssetBean other = new AssetBean();
		other.setAssetPaths("/content/dam/bdb");
		other.setMaterialNumber("materialnumberequals");
		Assert.assertEquals(assetBean.equals(other), false);
	}

}
