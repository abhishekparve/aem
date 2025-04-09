package com.bdb.aem.core.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.SerializedName;

public class ExcludeUtil implements ExclusionStrategy {

    /**
     *
     * @param arg0
     * @return
     */
    @Override
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    /**
     *
     * @param field
     * @return if the field should be skipped.
     */

    @Override
    public boolean shouldSkipField(FieldAttributes field) {
        SerializedName serializedName = getAnnotation(field);
        if(serializedName != null)
            return false;
        return true;
    }

    protected SerializedName getAnnotation(FieldAttributes field) {
        return field.getAnnotation(SerializedName.class);
    }
}