package com.bdb.aem.core.services;

import org.apache.sling.api.resource.Resource;

public interface PrefixedProductDataResolver {

    /**
     * Checks whether the given path is prefix based or not
     *  
     * @param absPath
     * @return
     */
    Boolean isPathPrefixed(String absPath);

    /**
     * Find a resource based on the given non-prefixed path
     *  
     * @param absPath - non-prefixed path
     * @return null if not found
     */
    String resolve(String absPath);

    /**
     * Map the given prefixed path to a non-prefixed path
     * 
     * @param absPath prefixed path
     * @return
     */
    String map(String absPath);
    
}