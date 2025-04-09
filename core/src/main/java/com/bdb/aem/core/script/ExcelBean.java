package com.bdb.aem.core.script;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelBean {

	private String path;
	private String compModified;
	private String compNotModified;
	
}
