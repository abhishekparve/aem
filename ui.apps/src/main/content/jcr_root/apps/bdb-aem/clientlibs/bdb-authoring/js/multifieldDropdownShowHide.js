(function(document, $) {
    "use strict";

    // when dialog gets injected
    $(document).on("foundation-contentloaded", function(e) {
        // if there is already an inital value make sure the 
  //according target element becomes visible
        showHideHandler($(".cq-dialog-dropdown-multifield-showhide", e.target));
    });

    $(document).on("change", ".cq-dialog-dropdown-multifield-showhide", function(e) {
        showHideHandler($(this));
    });

    function showHideHandler(el) {
        el.each(function(i, element) {
                // handle Coral3 base drop-down
                Coral.commons.ready(element, function(component) {
                    showHideCustom(component, element);
                    component.on("change", function() {
                        showHideCustom(component, element);
                    });
                });
        })
    }

    function showHideCustom(component, element) {
         // get the selector to find the target elements. 
         //its stored as data-.. attribute
       var target = $(element).data("cq-dialog-dropdown-multifield-showhide-target");
       var $target = $(target);
       var elementIndex = $(element).closest('coral-multifield-item').index();

       if (target) {
         var value;
         if (component.value) {
           value = component.value;
         } else {
           value = component.getValue();
         }
         $(element).closest("coral-multifield-item").find(target)
         .each(function(index) {
            var tarIndex = $(this).closest('coral-multifield-item').index();
            if (elementIndex == tarIndex) {
                $(this).not(".hide").parent().addClass("hide");
                $(this).filter(function() {
                    return $(this).data('showhidetargetvalue').replace(/ /g, '').split(',').includes(value);
                }).parent().removeClass("hide");	
            }
         });
        }
    }

})(document, Granite.$);