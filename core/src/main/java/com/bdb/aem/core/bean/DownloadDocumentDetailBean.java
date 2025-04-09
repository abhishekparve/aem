package com.bdb.aem.core.bean;

import lombok.*;

/**
 * The Class DownloadDocumentDetailBean.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DownloadDocumentDetailBean {

	/** The File name. */
	private String fileName;

	/** The path. */
	private String path;
	
	/** The Download Icon. */
	private String downloadIcon;

}
