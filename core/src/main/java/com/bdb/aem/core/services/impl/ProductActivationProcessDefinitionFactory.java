package com.bdb.aem.core.services.impl;

import com.bdb.aem.core.services.PrefixedProductDataResolver;
import org.apache.sling.distribution.Distributor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.adobe.acs.commons.mcp.ProcessDefinitionFactory;
import com.bdb.aem.core.pdmigration.ProductActivationProcessDefinition;
import com.bdb.aem.core.services.solr.SolrSearchService;
import com.day.cq.search.QueryBuilder;

@Component(service = ProcessDefinitionFactory.class, immediate = true)
public class ProductActivationProcessDefinitionFactory
		extends ProcessDefinitionFactory<ProductActivationProcessDefinition> {

	@Reference
	Distributor distributor;

	@Reference
	SolrSearchService solrSearchService;

	@Override
	public String getName() {
		return "PD Activation & Indexing Process";
	}

	@Override
	protected ProductActivationProcessDefinition createProcessDefinitionInstance() {
		return new ProductActivationProcessDefinition(distributor, solrSearchService);
	}

}
