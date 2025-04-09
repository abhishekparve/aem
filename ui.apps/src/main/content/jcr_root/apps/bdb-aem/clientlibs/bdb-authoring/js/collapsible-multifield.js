(function(){
    if(typeof EAEM === "undefined"){
        EAEM = {};
    }
 
    //the function executed when user clicks collapse; returns summary of multifield item data
    EAEM.showTitleText = function(fields){
        return Object.values(fields)[0];
    }
}());

(function ($, $document, gAuthor) {
    var summaryCreators = {},
        CORAL_MULTIFIELD = "coral-multifield",
        CORAL_MULTIFIELD_ITEM = "coral-multifield-item",
        CORAL_MULTIFIELD_ITEM_CONTENT = "coral-multifield-item-content",
        EAEM_SHOW_ON_COLLAPSE = "eaem-show-on-collapse";
 
    $document.on("dialog-ready", addCollapsers);

    function addCollapsers(){
        var editable = gAuthor.DialogFrame.currentDialog.editable;
 
         $( "coral-multifield-item" ).css( "border-bottom", "1px solid grey" );
         $('._coral-Button--primary').on('click', function(){
            $( "coral-multifield-item" ).css( "border-bottom", "1px solid grey" );
        });
 
 
        if(!editable){
            return;
        }
 
        $.ajax(editable.config.dialog + ".infinity.json").done(fillLabelCreatorFns);
 
        function fillLabelCreatorFns(obj){
            if(!_.isObject(obj) || _.isEmpty(obj)){
                return;
            }
 
            _.each(obj, function(value){
                if(value[EAEM_SHOW_ON_COLLAPSE] === "EAEM.showTitleText"){
                    if(!_.isEmpty(value.field) && !_.isEmpty(value.field.name)) {
                        var $multifields = $(CORAL_MULTIFIELD).css("padding-right", "2.5rem"); 

                        if(_.isEmpty($multifields)){
                            return;
                        }

                        $multifields.find(CORAL_MULTIFIELD_ITEM).each(handler);
                 
                        $multifields.on(function(){
                            $multifields.find(CORAL_MULTIFIELD_ITEM).each(handler);
                        });

                        function handler(){
                        var $mfItem = $(this);
                             if(!_.isEmpty($mfItem.find("[icon=accordionUp]"))){
                             var mfName = $(this).closest(CORAL_MULTIFIELD).attr("data-granite-coral-multifield-name");
                                 if(mfName == "./imageDetails" || mfName == "./imageDetails2" || mfName == "./imageDetails3"){
                                 	 summaryCreators[value.field.name] = value[EAEM_SHOW_ON_COLLAPSE];
									 addSummarySection($mfItem);
	                                 return addAccordionIcons($mfItem);
                                 }
                                 addAccordionIcons($mfItem);
                             }
                        }                       
                        addExpandCollapseAll($multifields);
                    }
                }else{
                    if(_.isObject(value) && !_.isEmpty(value)){
                        fillLabelCreatorFns(value);
                    }
                }
            });
        }
    }
 
    function addSummarySection($mfItem){
        var $summarySection = $("<div/>").insertAfter($mfItem.find(CORAL_MULTIFIELD_ITEM_CONTENT))
            .addClass("coral-Well").css("cursor", "pointer").hide();
 
        $summarySection.click(function(){
            $mfItem.find("[icon='accordionDown']").click();
        });
    }
 
    function addExpandCollapseAll($multifields){
        var $mfAdd, expandAll, collapseAll;
 
        $multifields.find("[coral-multifield-add]").each(handler);
 
        function handler(){
            $mfAdd = $(this);
 
            expandAll = new Coral.Button().set({
                variant: 'secondary',
                innerText: "Expand All"
            });
 
            $(expandAll).css("margin-left", "10px").click(function(){
                event.preventDefault();
                $(this).closest(CORAL_MULTIFIELD).find("[icon='accordionDown']").click();
            });
 
            collapseAll = new Coral.Button().set({
                variant: 'secondary',
                innerText: "Collapse All"
            });
 
            $(collapseAll).css("margin-left", "10px").click(function(){
                event.preventDefault();
                $(this).closest(CORAL_MULTIFIELD).find("[icon='accordionUp']").click();
            });
 
            $mfAdd.after(expandAll).after(collapseAll);
        }
    }


    function addAccordionIcons($mfItem){
        var up = new Coral.Button().set({
            variant: "quiet",
            icon: "accordionUp",
            title: "Collapse"
        });
 
        up.setAttribute('style', 'position:absolute; top: 0; right: -2.275rem; margin-left: 8px; min-width: 32px; padding-top: 0px; padding-right: 6px; padding-bottom: 0px; padding-left: 6px');
        up.setAttribute('class', '_coral-Button');
        up.on('click', handler);
 
        $mfItem.append(up);
 
        var down = new Coral.Button().set({
            variant: "quiet",
            icon: "accordionDown",
            title: "Expand"
        });
 
       	down.setAttribute('style', 'position:absolute; top: 0; right: -2.275rem; margin-left: 8px; min-width: 32px; padding-top: 0px; padding-right: 6px; padding-bottom: 0px; padding-left: 6px');
        down.setAttribute('class', '_coral-Button');
        down.on('click', handler).hide();
 
        $mfItem.append(down);
 
        function handler(event){
            event.preventDefault();
 
            var mfName = $(this).closest(CORAL_MULTIFIELD).attr("data-granite-coral-multifield-name"),
                $mfItem = $(this).closest(CORAL_MULTIFIELD_ITEM),
                $summarySection = $mfItem.children("div");
 
            $summarySection.html(getSummary($mfItem, mfName));
 
            adjustUI.call(this, $summarySection);
        }
 
        function adjustUI($summarySection){
            var icon = $(this).find("coral-icon").attr("icon"),
                $content = $mfItem.find(CORAL_MULTIFIELD_ITEM_CONTENT);
 
            if(icon == "accordionUp"){
                $summarySection.show();
 
                $content.slideToggle( "fast", function() {
                    $content.hide();
                });
 
                up.hide();
                down.show();
            }else{
                $summarySection.hide();
 
                $content.slideToggle( "fast", function() {
                    $content.show();
                });
 
                up.show();
                down.hide();
            }
        }
    }
    function getSummary($mfItem, mfName){
        var summary = "Click to expand";
 
        try{
            if(summaryCreators[mfName]){
                var fields = {};
 
                $mfItem.find("input").each(function(){
                    var $input = $(this);
                    fields[$input.attr("name")] = $input.val();
                });
 
                summary = eval(summaryCreators[mfName])(fields);
            }
        }catch(err){}
 
        if(!summary){
            summary = "Click to expand";
        }
 
        return summary;
    }
}(jQuery, jQuery(document), Granite.author));