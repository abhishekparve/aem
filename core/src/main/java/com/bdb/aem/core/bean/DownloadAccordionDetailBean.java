package com.bdb.aem.core.bean;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * The Class DownloadDocumentDetailBean.
 */

@Getter
@Setter
public class DownloadAccordionDetailBean {

	/** The title. */
	private String title;

	/** The Documents. */
	private List<DownloadDocumentDetailBean> documents;

}
