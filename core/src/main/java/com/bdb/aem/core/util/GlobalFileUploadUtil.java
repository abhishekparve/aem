package com.bdb.aem.core.util;

import com.bdb.aem.core.models.GlobalFileUploadModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

public class GlobalFileUploadUtil {

    /**
     * Creates and returns an instance of file Upload Model from the
     * file Upload Model.
     *
     * @param fileUploadModel - file Upload Model
     * @return - Instance of IndustryRoleInterestModelConfig
     */
    public static String createGlobalFileUploadLabels(GlobalFileUploadModel fileUploadModel, String fileUploadNote) {

        if(StringUtils.isNotBlank(fileUploadNote)){
            fileUploadModel.setNote(fileUploadNote);
        }
        ExcludeUtil excludeUtilObject = new ExcludeUtil();
        Gson fileUploadLabelsGson = new GsonBuilder().addDeserializationExclusionStrategy(excludeUtilObject)
                .addSerializationExclusionStrategy(excludeUtilObject).create();

        String fileUploadLabels = fileUploadLabelsGson.toJson(fileUploadModel);
        return fileUploadLabels;
    }

}
