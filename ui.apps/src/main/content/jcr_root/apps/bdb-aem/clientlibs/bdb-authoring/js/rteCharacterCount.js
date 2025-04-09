(function ($, $document) {
	"use strict";

	const ATTRIBUTE_RTE_CHAR_LIMIT = "data-char-limit";

	function char_count(htmlContent) {
		htmlContent = htmlContent.replace(/<[^>]*>/g, "");
		htmlContent = htmlContent.replace(/&nbsp;/g, ' ');
		var count = htmlContent.length;

		return count;
	}

	function getMessageTagId(lasttextarea) {
		var name = $(lasttextarea).attr('name').split('/');
		var char_count_id = "";
		if (name.length > 2) {
			char_count_id = "char-count-" + name[2];
		} else {
			char_count_id = "char-count-" + name[1];
		}
		return char_count_id;
	}

	function setListenerAndValidator(currenttextarea) {
		var charlimit = $(currenttextarea).attr(ATTRIBUTE_RTE_CHAR_LIMIT);
		if (typeof (charlimit) != 'undefined') {
			var char_count_id = getMessageTagId(currenttextarea);

			if ($('#' + char_count_id).length == 0) {
				var injecthtml = "<p class=\"char-count\" id=\"" + char_count_id + "\"style=\"color:blue\"></p>";
				$(injecthtml).insertAfter(currenttextarea);


				$(currenttextarea).keyup(function () {
					var htmlContent = $(this).html();
					var displayCount = char_count(htmlContent);
					var message_id = getMessageTagId(this);

					var htmlStringToShow = "";
					if (displayCount <= charlimit)
						htmlStringToShow = "<p class=\"char-count\" style=\"color:blue\">Character count : " + displayCount + "/" + charlimit + "</p>";
					else
						htmlStringToShow = "<p class=\"char-count\" style=\"color:red\">Character count exceeds the limit! : " + displayCount + "/" + charlimit + "</p>";
					$('#' + message_id).html(htmlStringToShow);
				});
				$(window).adaptTo("foundation-registry").register("foundation.validation.validator", {
					selector: currenttextarea,
					validate: validate
				});

				function validate() {

					var count = char_count($(currenttextarea).html());

					if (count > charlimit) {

						return "Character count should be less than " + charlimit;
					}
					return null;
				}
			}
		}
	}


	$document.on("dialog-ready", function () {

		$("._coral-Dialog").click(function () {
			var textarea = $(".cq-RichText-editable");
			if (typeof (textarea) != 'undefined') {
				$(textarea).each(function () {
					$(this)
					setListenerAndValidator($(this));
				});

			}
		});
		$("._coral-Dialog").trigger("click");


	});


})($, $(document));