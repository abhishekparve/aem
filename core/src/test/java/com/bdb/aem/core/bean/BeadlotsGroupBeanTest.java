package com.bdb.aem.core.bean;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import junitx.framework.Assert;

public class BeadlotsGroupBeanTest {
	BeadlotsGroupBean beadlotsGroupBean;

	@Mock
	private List<BeadlotFileBean> files = new ArrayList<>();

	/**
	 * The partNumbers.
	 */
	@Mock
	private List<BeadlotsMaterialNumberBean> partNumberBeans = new ArrayList<BeadlotsMaterialNumberBean>();

	@BeforeEach
	void setUp() throws Exception {
		beadlotsGroupBean = new BeadlotsGroupBean();
		beadlotsGroupBean.setId("id");
		beadlotsGroupBean.setDescription("description");
		beadlotsGroupBean.setInformation("information");
		beadlotsGroupBean.setRegStatus("regstatus");
		beadlotsGroupBean.setInstallationInstructionsLabel("installationinstructionslabel");
		beadlotsGroupBean.setInstallationInstructionsLink("installationInstructionsLink");
		beadlotsGroupBean.setUpdaterFileLabel("updaterfilelabel");
		beadlotsGroupBean.setUpdaterFileLink("updaterfilelink");
		beadlotsGroupBean.setFiles(files);

	}

	@Test
	void test() {
		Assert.assertEquals(beadlotsGroupBean.getId(), "id");
		Assert.assertEquals(beadlotsGroupBean.getDescription(), "description");
		Assert.assertEquals(beadlotsGroupBean.getInformation(), "information");
		Assert.assertEquals(beadlotsGroupBean.getRegStatus(), "regstatus");
		Assert.assertEquals(beadlotsGroupBean.getInstallationInstructionsLabel(), "installationinstructionslabel");
		Assert.assertEquals(beadlotsGroupBean.getInstallationInstructionsLink(), "installationInstructionsLink");
		Assert.assertEquals(beadlotsGroupBean.getUpdaterFileLabel(), "updaterfilelabel");
		Assert.assertEquals(beadlotsGroupBean.getUpdaterFileLink(), "updaterfilelink");
	}
}
