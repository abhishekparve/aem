package com.bdb.aem.core.pojo;

import com.bdb.aem.core.bean.BeadlotsCategoryBean;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BeadlotsConfig {

    /**
     * The beadlotsJson.
     */
    @SerializedName("beadlots")
    private List<BeadlotsCategoryBean> beadlotsJson;

    /**
     * Constructor to set beadlotsJson
     */
    public BeadlotsConfig(List<BeadlotsCategoryBean> beadlotsJson) {
        this.beadlotsJson = new ArrayList<>(beadlotsJson);
    }

    /**
     * get the beadlotsJson.
     */
    public List<BeadlotsCategoryBean> getBeadlotsJson() {
        return new ArrayList<>(beadlotsJson);
    }
}
