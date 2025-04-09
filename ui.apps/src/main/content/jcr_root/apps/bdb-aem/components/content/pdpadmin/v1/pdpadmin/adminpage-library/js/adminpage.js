(function ($, $document) {
    "use strict";
    $(document).ready(function () {
        var tagValue = $('#tagValue').attr("tagValue");
        var regionValue = $('#regionValue').attr("regionValue");
	
        var currentPage = $(location).attr('pathname')
        var pageurl = currentPage.substring(0, currentPage.lastIndexOf("."));

        var faqLabel = $(this).find(".faqLabel");
        var description = $(this).find(".description");

        var startDate = $(this).find(".startDate");
        var endDate = $(this).find(".endDate");

        var moreInfo = $(this).find(".moreInfo");
        var disclaimer = $(this).find(".disclaimer");
        var turnOn = $(this).find(".turnOn");
        var turnOff = $(this).find(".turnOff");

        var moreInfoLink = $(this).find(".moreInfoLink");

        var openNewTab = $(this).find(".openNewTab");

        var disclaimerOn = $(this).find(".disclaimerOn");

        var disclaimeroff = $(this).find(".disclaimeroff");
		
		var bulkReagentQuoteLinkTurnOn = $(this).find(".bulkReagentQuoteLinkTurnOn");
        var bulkReagentQuoteLinkTurnOff = $(this).find(".bulkReagentQuoteLinkTurnOff");

        var viewMoreFaqLabel = $(this).find(".viewMoreFaqLabel");
        var productSku = $(this).find(".productSku"); 
        var productTitle = $(this).find(".productTitle");

        var smessageOn = $(this).find(".smessageOn");

        var smessageOff = $(this).find(".smessageOff");
        var specialMessage = $(this).find(".specialMessage");

        var altText = $(this).find(".altText");
		var replacedProductId = $(this).find(".replacedProductId");
		var neutralActivity = $(this).find(".neutralActivity");
        var target_MW = $(this).find(".target_MW");
        var assay_Range = $(this).find(".assay_Range");
        var testimonial_id=$(this).find(".testimonial_id");
        var videoId = $(this).find(".videoId");

        $.ajax({
            type: "GET",
            async: false,

            url: "/content/bdb/paths/admin/update-product-schema",
            contentType: 'application/json;charset=utf-8',
            data: {
                region:regionValue,
                currentPage: pageurl,
                productID: tagValue,
            },
            success: function (data) {
				const surchargeDisclaimer = data && data.surchargeDisclaimer ? data.surchargeDisclaimer : {};
                const msg = surchargeDisclaimer.specialMessage ? surchargeDisclaimer.specialMessage : '';
                const altTxt = surchargeDisclaimer.altText ? surchargeDisclaimer.altText : '';
                faqLabel.val(data["paFAQTitle"]);
                description.val(data["paDescription"]);
                startDate.val(data["startDate"]);
                endDate.val(data["endDate"]);
                moreInfo.val(data["paViewMoreCTA"]);
                disclaimer.val(data["regionalDisclaimers"]);
				replacedProductId.val(data["replacedProductId"]);

                if (data["productAnnouncement"] == "true") {
                    turnOn.attr('checked', data["productAnnouncement"]);
                } else if (data["productAnnouncement"] == "false") {
                    turnOff.attr('checked', data["productAnnouncement"]);
                }
                disclaimer.val(data["regionalDisclaimers"]);
                specialMessage.val(msg);
                altText.val(altTxt);
				neutralActivity.val(data["neutralizationActivity"]);
                target_MW.val(data["targetMW"]);
                assay_Range.val(data["assayRange"]);

                moreInfoLink.val(data["moreInfoLink"]);

                viewMoreFaqLabel.val(data["viewMoreFaqLabel"]);
                productSku.text(data["productSku"]);
                productTitle.text(data["productName"]);
                testimonial_id.val(data["testimonialId"]);
                videoId.val(data["videoId"]);
				if (data["displayBulkReagentQuote"] == "true") {
                    bulkReagentQuoteLinkTurnOn.attr('checked', data["displayBulkReagentQuote"]);
                } else if (data["displayBulkReagentQuote"] == "false") {
                    bulkReagentQuoteLinkTurnOff.attr('checked', data["displayBulkReagentQuote"]);
                }
                if (data["openNewTab"] == "true") {
                    openNewTab.attr('checked', data["openNewTab"]);
                }

                if (data["disclaimerStatus"] == "true") {
                    disclaimerOn.attr('checked', data["disclaimerStatus"]);
                } else if (data["disclaimerStatus"] == "false") {
                    disclaimeroff.attr('checked', data["disclaimerStatus"]);
                }


                if (data["smessageStatus"] == "true") {
                    smessageOn.attr('checked', data["smessageStatus"]);
                } else if (data["smessageStatus"] == "false") {
                    smessageOff.attr('checked', data["smessageStatus"]);
                }




                if (data.faqMap) {
                    data.faqMap.map((e, index) => {
                        e
                        if (e) {
                        	if(e.question && e.answer){	
	                            var fName = $("<div class=\"form-group\"><label>Question:</label><input type=\"text\" class=\"form-control faqQuestion\" name=\"faqQuestion" + index + "\" value=\"" + e.question + "\" data-index=\"" + index + "\" /></br></br><label>Answer:</label><textarea id=\"input1\" class=\"form-control faqDescription\" name=\"faqAnswer" + index + "\"  data-index=\"" + index + "\">" + e.answer + "</textarea></div>");
	                            var removeButton = $("<input type=\"button\" class=\"remove btn btn-secondary\" value=\"Remove\" />");

                                removeButton.click(function () {

                                console.log("remove button");
                                $(fName).remove();

                                $(removeButton).remove();

                            });

                            $('#buildyourform').append(fName);

                            $('#buildyourform').append(removeButton);

                         }
                        }
                    })

                }


            },
            error: function (data) {
                alert('error');
            }
        });
        $("#add").click(function () {
            var lastField = $("#buildyourform div:last");

            var faqLenth = $('#buildyourform').children().length;

            var newFaqLength = lastField.length;


            // var intId = (lastField && lastField.length && lastField.data("idx") + 1) || 1;

            //var intId = faqLenth - 1;

            if (newFaqLength == 0) {
                var intId = faqLenth + 0;
            } else {
                var intId = faqLenth + 1;
            }
            var fieldWrapper = $("<div class=\"fieldwrapper\" id=\"field" + intId + "\"/>");
            fieldWrapper.data("idx", intId);
            var fName = $("<div class=\"form-group\"><label>Question:</label><input type=\"text\" class=\"form-control faqQuestion\" name=\"faqQuestion" + intId + "\" data-index=\"" + intId + "\" /></br></br><label>Answer:</label><textarea id=\"input1\" class=\"form-control faqDescription\" name=\"faqAnswer" + intId + "\" data-index=\"" + intId + "\"></textarea></div>");

            var removeButton = $("<input type=\"button\" class=\"remove btn btn-secondary\" value=\"Remove\" />");
            removeButton.click(function () {

                console.log("remove button");
                $(this).parent().remove();
            });
            fieldWrapper.append(fName);

            fieldWrapper.append(removeButton);
            $("#buildyourform").append(fieldWrapper);
        });
        $("#activateToPublish").click(function () {
            $.ajax({
                type: "GET",
                async: false,

                url: "/content/bdb/paths/admin/update-product-schema",
                contentType: 'application/json;charset=utf-8',
                data: {
                    region:regionValue,
                    activate:"activatePage",
                    currentPage: pageurl
                },
                success: function (data) {
                    console.log("node published");
                },
                error: function (data) {
                   console.log('error');
                }
            });
        });

        $("#my_form").submit(function (event) {
            event.preventDefault(); //prevent default action 
            var currentPage = $(location).attr('pathname')
            var pageurl = currentPage.substring(0, currentPage.lastIndexOf("."));

            //var form_data = $('#my_form').serialize(); //Encode form elements for submission
            // console.log(form_data);


            var $questionList = $('#faqForm input.faqQuestion');

            var $AnswerList = $('#faqForm textarea.faqDescription');

            console.log($AnswerList);
            var data2 = [];
            $questionList.each(function (index, input) {
                var $q = $($questionList[index]);

                var $a = $($AnswerList[index]);

                console.log($AnswerList[index]);

                if ($q.val() != "Remove") {

                    console.log($a.val());
                    data2.push({
                        question: $q.val(),
                        answer: $a.val()


                    });
                }
            });


            var formdata = $('#my_form').serializeArray();
            var data = {};
            $(formdata).each(function (index, obj) {
                data[obj.name] = obj.value;
            });

            data.faqList = data2;
            var jsonFormData = JSON.stringify(data);
            console.log(jsonFormData);

            $.ajax({

                type: "POST",
                url: "/content/bdb/paths/admin/update-product-schema",
                dataType: "json",
                data: {
                    region:regionValue,
                    currentPage: pageurl,
                    form_data: jsonFormData
                },
            }).done(function (response) { //
                alert("Form Submitted Successfully");
            });
        });


    });

})($, $(document));