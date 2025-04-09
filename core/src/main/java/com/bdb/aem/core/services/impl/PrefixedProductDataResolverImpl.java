package com.bdb.aem.core.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bdb.aem.core.services.PrefixedProductDataResolver;

@Component(service = PrefixedProductDataResolver.class, immediate = true)
@Designate(ocd = PrefixedProductDataResolverImpl.Configuration.class)
public class PrefixedProductDataResolverImpl implements PrefixedProductDataResolver {

    /**
     * The mapping looks like this: ["/content/dam/cat1/cat11/\\w+xxx/\\w+xx/(\\w+)->/content/dam/cat1/cat11/$1",..]
     */

    private Map<String, Pattern> mapping;

    private Map<String, Pattern> inverseMapping;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrefixedProductDataResolver.class);

    @Override
    public Boolean isPathPrefixed(String absPath) {
        if (StringUtils.isBlank(absPath)) {
            return false;
        }
        for (String basePath : mapping.keySet()) {
            
            if (absPath.startsWith(basePath) 
                && mapping.get(basePath).matcher(absPath).matches()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String resolve(String absPath) {
        if (StringUtils.isBlank(absPath)) {
            return null;
        }
        LOGGER.debug("Resolve method start");
        LOGGER.debug("Absolute path: " + absPath);
        for (String basePath : inverseMapping.keySet()) {
            LOGGER.debug("Base path: " + basePath);
            if (absPath.startsWith(basePath)) {
                Pattern pattern = inverseMapping.get(basePath);
                LOGGER.debug("Regex: " + pattern.pattern());
                Matcher matcher = pattern.matcher(absPath);
                if (matcher.matches()) {
                    LOGGER.debug("Is match: " + matcher.matches());
                    String baseProductNodeName = matcher.group(1);
                    LOGGER.debug("Group: " + baseProductNodeName);
                    if (baseProductNodeName.length() > 3) {
                        return basePath + baseProductNodeName.substring(0, 3) + "xxx/" + baseProductNodeName.substring(0, 4)
                                + "xx/" + baseProductNodeName;
                    }
                }
                return absPath;
            }
        }
        LOGGER.debug("Resolve method end");
        return absPath;
    }

    @Override
    public String map(String absPath) {
        if (StringUtils.isBlank(absPath)) {
            return null;
        }
        LOGGER.debug("Map method start");
        LOGGER.debug("Absolute path: " + absPath);
        for (String basePath : mapping.keySet()) {
            LOGGER.debug("Base path: " + basePath);
            if (absPath.startsWith(basePath)) {
                Pattern pattern = mapping.get(basePath);
                LOGGER.debug("Regex: " + pattern.pattern());
                Matcher matcher = pattern.matcher(absPath);
                LOGGER.debug("Is match: " + matcher.matches());
                String baseProductNodeName = matcher.group(1);
                LOGGER.debug("Group: " + baseProductNodeName);
                return basePath + baseProductNodeName;
            }
        }
        LOGGER.debug("Map method end");
        return null;
    }

    /**
     * The Interface Configuration.
     */
    @ObjectClassDefinition(name = "Prefix based JCR PD Resolver Configuration")
    public @interface Configuration {

        /**
         * mapping
         *
         * @return the string
         */
        @AttributeDefinition(name = "mapping", description = "Resource Mapping", type = AttributeType.STRING)
        public String[] mapping() default  {
                "/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-\\.\\/]+)->/content/dam/bdb/products/global/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1",
                "/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-\\.\\/]+)->/content/commerce/products/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1",
                "/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/\\w+xxx/\\w+xx/([\\w\\-\\.\\/]+)->/content/cq:tags/bdb/products/reagents/flow-cytometry-reagents/research-reagents/single-color-antibodies-ruo/$1"
        };
    }
    
    /**
     * Activate.
     *
     * @param config the config
     */
    @Activate
    @Modified
    protected final void activate(PrefixedProductDataResolverImpl.Configuration config) {
        this.mapping = new HashMap<>();
        this.inverseMapping = new HashMap<>();
        for (String entry : config.mapping()) {
            String[] item = entry.split("->");
            LOGGER.debug("Left:" + item[0]);
            LOGGER.debug("Right:" + item[1]);
            if (item.length == 2) {
                String value = item[1];
                String[] segments = value.split("\\$");
                if (segments.length < 1) {
                    LOGGER.error("Incorrect entry, use $ symbol: {}", entry);
                    continue;
                }
                String basePath = segments[0];
                mapping.put(basePath, Pattern.compile(item[0]));
                String regex = item[1].replaceAll("\\$\\d", "([\\\\w\\\\-\\\\.\\\\/]+)");
                inverseMapping.put(basePath, Pattern.compile(regex));
            } else {
                LOGGER.error("Incorrect entry: {}", entry);
            }
        }
    }
}
