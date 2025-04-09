(function(document, $) {
    "use strict";
    $(document).click('.cq-dialog-header-action.cq-dialog-submit ', function(event) {
    $('form.cq-dialog.foundation-form').find('.eventBlogDetailsComponent').find('.coral-Collapsible-content').each(function( k,v ) {
    var coralDisplay=$(v).css('display');
    var siblingText=$(v).find('.coral-Form-field').attr('name');
    if($( "[name='./selection']" ).val()=='event'){
        $( "foundation-autocomplete[name='./bannerImageBlog']" ).find('input').removeAttr('required');
        $( "foundation-autocomplete[name='./bannerImageBlog']" ).find('input').removeClass('is-invalid');
        $( "input[name='./bannerTitleBlog']" ).removeAttr('required');
        $( "input[name='./bannerTitleBlog']" ).removeClass('is-invalid');
        if(coralDisplay=='block'){ 
            if((siblingText=='./bannerTitle')||(siblingText=='./bannerImage')){
                //var labelFields=$(v).find('.coral-Form-field');
                $( "input[name='./bannerTitle']" ).attr('required','true');
                $( "foundation-autocomplete[name='./bannerImage']" ).find('input').attr('required','true');
            }
            else if(siblingText=='./speakerTitle'){
                $( "input[name='./speakerTitle']" ).attr('required','true');
                }
        }
        else  if(coralDisplay=='none'){
            if((siblingText=='./bannerTitle')||(siblingText=='./bannerImage')){
                if(($( "[name='./bannerThumbnailImage']" ).val()=="")&&($( "[name='./bannerImageAlt']" ).val()=="")&&($( "[name='./bannerURL']" ).val()=="")&&
                    ($( "[name='./bannerAlign']" ).val()=="")&&($( "[name='./eventType']" ).val()=="")&&($( "[name='./eventTopic']" ).val()=="")){
                    $( "input[name='./bannerTitle']" ).removeAttr('required');
                    $( "foundation-autocomplete[name='./bannerImage']" ).find('input').removeAttr('required');
                    $( "input[name='./bannerTitle']" ).removeClass('is-invalid');
                    $( "foundation-autocomplete[name='./bannerImage']" ).find('input').removeClass('is-invalid');
                }
                }
                else if(siblingText=='./speakerTitle'){
           if(($( "[name='./speakerDescription']" ).val()=="")&&($( "[name='./speakerImageAlt']" ).val()=="")&&($( "[name='./speakerName']" ).val()=="")&&
               ($( "[name='./speakerImageTitle']" ).val()=="")&&($( "[name='./speakerImageDescription']" ).val()=="")){
               $( "input[name='./speakerTitle']" ).removeAttr('required');
               $( "input[name='./speakerTitle']" ).removeClass('is-invalid');
                }
                } 
        }
    }
   else if($( "[name='./selection']" ).val()=='blog'){
    $( "input[name='./speakerTitle']" ).removeAttr('required');
    $( "input[name='./speakerTitle']" ).removeClass('is-invalid');
    $( "input[name='./bannerTitle']" ).removeAttr('required');
    $( "foundation-autocomplete[name='./bannerImage']" ).find('input').removeAttr('required');
    $( "input[name='./bannerTitle']" ).removeClass('is-invalid');
    $( "foundation-autocomplete[name='./bannerImage']" ).find('input').removeClass('is-invalid');
    }
    if(coralDisplay=='block'){
        if(siblingText=='./bannerImageBlog'){
            $( "foundation-autocomplete[name='./bannerImageBlog']" ).find('input').attr('required','true');
                 }
                 else if(siblingText=='./bannerTitleBlog'){
            $( "input[name='./bannerTitleBlog']" ).attr('required','true');
                 } 
    }
    else  if(coralDisplay=='none'){  
        if(siblingText=='./bannerImageBlog'){
            if(($( "[name='./bannerThumbnailImageBlog']" ).val()=="")&&($( "[name='./bannerImageAltBlog']" ).val()=="")){
                $( "foundation-autocomplete[name='./bannerImageBlog']" ).find('input').removeAttr('required');
                $( "foundation-autocomplete[name='./bannerImageBlog']" ).find('input').removeClass('is-invalid');
            }
       }
       else if(siblingText=='./bannerTitleBlog'){
           if(($( "[name='./bannerSubTitle']" ).val()=="")&&($( "[name='./blogDate']" ).val()=="")&&($( "[name='./bannerByline']" ).val()=="")&&($( "[name='./bannerDescription']" ).val()=="")&&($( "[name='./blogTopic1']" ).val()=="")&&($( "[name='./blogTopic2']" ).val()=="")&&($( "[name='./blogTopic3']" ).val()=="")){
            $( "input[name='./bannerTitleBlog']" ).removeAttr('required');
            $( "input[name='./bannerTitleBlog']" ).removeClass('is-invalid');
       }
       }
    }
    });
});
})(document, Granite.$);