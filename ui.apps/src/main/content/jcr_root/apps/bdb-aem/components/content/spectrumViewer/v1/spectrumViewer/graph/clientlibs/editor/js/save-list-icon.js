(function ($, document, ns) {
    $(document).on("dialog-ready", function() {
    	// In dialog ready lets request the JSON and store it
        let iconList;
		$.ajax({
            url: "/apps/bdb-aem/components/content/spectrumViewer/v1/dialog/svgDataPath.json", //Update the path
            async: false,
            success: function (data) {
                iconList = data;
            }
        });

        const setDropdownOptions = function () {
            let svgField = $(".cq-dialog").find("#savedIconList")[0];
            let options = iconList;
            for (var i = 0; i < options.length; i++) {

                $item = $(svgField).find("coral-select-item[value='" + options[i].value + "']");
            if(_.isEmpty($item)){
                return;
            }
            $item.prepend("<img src='" + options[i].value + "' align='middle' width='30px' height='30px' style='margin-right: 5px; '/>" );

            }

        };

        setDropdownOptions();
    });
})(Granite.$, document, Granite.author);