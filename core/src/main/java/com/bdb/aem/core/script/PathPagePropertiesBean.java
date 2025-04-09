package com.bdb.aem.core.script;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PathPagePropertiesBean {

	private String path;
	private List<String> properties;
}
