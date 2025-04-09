(function ($document, $) {

    
    var legalAdmins = "./jcr:content/legaladmins";
	var contentAdmins = "./jcr:content/contentAdmins";
    


    $(document).on('foundation-contentloaded', function(e) {
        var custField = $(document.getElementById('contentcustominboxfields'));
        
    });
	
	$(document).on('foundation-contentloaded', function(e) {
		var custContentField = $(document.getElementById('contentcustominboxfields'));
		
		
		if(custContentField.length>0){
        	hideDialog(custContentField);
        }
		
		var dropdownValue = $("#workitemCompletionDialog").find("coral-select").find("coral-button-label").text();
		
		if(dropdownValue === "Content Rejected" || dropdownValue === "Approved"){
			hideDialog(custContentField)
		}else if(dropdownValue === "Approve, Inform content Admins" || dropdownValue === "Request legal reviewers"){
			showDialog(custContentField)
		}

    });

    $(document).on('change','.workitem-dialog-select', function(e) {

	var custContentField = $(document.getElementById('contentcustominboxfields'));

	var dropdownValue = $("#workitemCompletionDialog").find("coral-select").find("coral-button-label").text();
		
		if(dropdownValue === "Content Rejected" || dropdownValue === "Approved"){
			hideDialog(custContentField)
		}else if(dropdownValue === "Approve, Inform content Admins" || dropdownValue === "Request legal reviewers"){
			showDialog(custContentField)
		}

      

    });


    function hideDialog(custField){

        	$(custField).hide();

        	if(typeof $(custField).find('coral-tooltip').remove=='function'){
                $(custField).find('coral-tooltip').remove();
            }
            else{
                $(custField).find('coral-tooltip').outerHTML='';
            }


          

            }

			    function showDialog(custField){

        	$(custField).show();

        	if(typeof $(custField).find('coral-tooltip').remove=='function'){
                $(custField).find('coral-tooltip').remove();
            }
            else{
                $(custField).find('coral-tooltip').outerHTML='';
            }


            

            }
    
})($(document), Granite.$);