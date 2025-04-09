package com.bdb.aem.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ProductCarouselModel.
 */
@Model(adaptables = { Resource.class }, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ProductCarouselModel {
    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ProductCarouselModel.class);

    
    /** The title. */
    @Inject
    private String title;
    
    
    /** The products. */
    @Inject
    private List<ProductCarouselDetailsModel> products;

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public List<ProductCarouselDetailsModel> getProducts() {
		return new ArrayList<>(products);
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
