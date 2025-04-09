(function ($, $document) {
    "use strict";
    const contentTypeSelector = "#default_instrument";
    const contentSubTypeSelector = "#default_instrument_configuration";
    const contentSubTypeHiddenSelector = "#default_instrument_configuration-hidden";
    const contentSubTypeDataSourceUri =
        "/apps/bdb-aem/components/content/spectrumViewer/v1/spectrumViewer/instrument-configuration/dialog/cytometerConfigDsPath.json";

    $document.on("foundation-contentloaded", function (e) {
        setSubTypeDropdown(true);
    });

    $document.on("change", contentTypeSelector, function (e) {
        setSubTypeDropdown(false);
    });

    function setSubTypeDropdown(preSelect) {
        const contentType = document.querySelector(contentTypeSelector);
        const contentSubType = document.querySelector(contentSubTypeSelector);

        if (contentType && contentSubType) {
            var url =contentSubTypeDataSourceUri +"?type=" +contentType.value +"&ck=" +Math.random();
            $.get(url, function (data) {
                updateSubTypeDropdownField(preSelect, data);
            });
        }
    }
 
    function updateSubTypeDropdownField(preSelect, data) {
        const contentSubTypeDropdown = document.querySelector(contentSubTypeSelector);
        const contentSubTypeDropdownValue = document.querySelector(contentSubTypeHiddenSelector);

        //  Remove existing items from dropdown
        contentSubTypeDropdown.items.clear();

        for (var i in data) {
            contentSubTypeDropdown.items.add({
                value: data[i],
                content: { innerHTML: data[i] },
                selected:
                    preSelect && contentSubTypeDropdownValue === data[i] ? true : false,
            });
        }
    }
})($, $(document));
