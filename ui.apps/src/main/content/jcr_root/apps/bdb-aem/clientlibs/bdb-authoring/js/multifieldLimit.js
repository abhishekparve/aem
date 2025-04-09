(function ($, $document) {

	"use strict";

	// **************************************************************************
	// ** CONSTANTS
	// **************************************************************************

	const EVENT_CLICK = "click";

	const ATTRIBUTE_MULTIFIELD_ADD = "coral-multifield-add";
	const ATTRIBUTE_MULTIFIELD_MAX_ITEMS = "data-max-items";
	const ATTRIBUTE_MULTIFIELD_MIN_ITEMS = "data-min-items";

	const SELECTOR_MULTIFIELD_ADD_ITEM_BUTTON = `button[${ATTRIBUTE_MULTIFIELD_ADD}]`;
	const SELECTOR_MULTIFIELD_SUBMIT_BUTTON = '.cq-dialog-submit';
	const SELECTOR_MULTIFIELD_ITEM = "coral-multifield-item";


	// **************************************************************************
	// ** CLASSES
	// **************************************************************************

	class Listener {

		constructor(selector, event, handler) {
			this.selector = selector;
			this.event = event;
			this.handler = handler;
		}

		getSelector() {
			return this.selector;
		}

		getEvent() {
			return this.event;
		}

		getHandler() {
			return this.handler;
		}
	}

	// **************************************************************************
	// ** LISTENER HANDLERS
	// **************************************************************************

	function multifieldMaxItemsHandler(context) {

		const $field = $(context).parent();
		const maxSize = $field.attr(ATTRIBUTE_MULTIFIELD_MAX_ITEMS);

		if (maxSize) {

			const ui = $(window).adaptTo("foundation-ui");
			const currentSize = $field.children(SELECTOR_MULTIFIELD_ITEM).length;

			if (currentSize >= maxSize) {
				ui.alert("Warning", "Maximum  " + maxSize + " items are allowed!", "notice");
				return false;
			}
		}
	}
	
	function multifieldMinItemsHandler(context) {
		
		const $fieldn = $(context).parent().parent().find('.multifieldMinShowHide');
		var $field = $(context).parent().parent().find('coral-multifield');
		if($fieldn.length){
			$field = $fieldn.not(".hide").find('coral-multifield');
        }
		
		if($field.length==1)
		{
		const minSize = $field.attr(ATTRIBUTE_MULTIFIELD_MIN_ITEMS);
			if (minSize) {
				const ui = $(window).adaptTo("foundation-ui");
				const currentSize = $field.children(SELECTOR_MULTIFIELD_ITEM).length;
				if (currentSize < minSize) {
					ui.alert("Warning", "Minimum  " + minSize + " items are required!", "notice");
					return false;
				}
			}
		}
		else if($field.length>1)
		{
			var msg="";
			var returnVal=true;
			const ui = $(window).adaptTo("foundation-ui");
			$field.each(function(){				
					const minSize = $(this).attr(ATTRIBUTE_MULTIFIELD_MIN_ITEMS);
					const currentSize = $(this).children(SELECTOR_MULTIFIELD_ITEM).length;
					const multiLabel = $(this).parent().children("label").text();
					if(minSize)
					{
						if(currentSize < minSize)
						{
							msg = msg+" | !Minimum  " + minSize + " items are required! for multifield: "+multiLabel;
							returnVal=false;
						}
					}
			});
			if(!returnVal)
			{
				ui.alert("Warning", msg.replace(' |',''), "notice");
			}
		return returnVal;
		}
	}


	// **************************************************************************
	// ** LISTENERS
	// **************************************************************************

	const listeners = [];
	listeners.push(new Listener(SELECTOR_MULTIFIELD_ADD_ITEM_BUTTON, EVENT_CLICK, multifieldMaxItemsHandler));
	listeners.push(new Listener(SELECTOR_MULTIFIELD_SUBMIT_BUTTON, EVENT_CLICK, multifieldMinItemsHandler));

	// **************************************************************************
	// ** REGISTER LOGIC
	// **************************************************************************

	$document.on("dialog-ready", function () {

		$.each(listeners, function (index, listener) {
			$(listener.getSelector()).on(listener.getEvent(), function () {
				return listener.getHandler()(this);
			})
		});
	});

})($, $(document));