(function($, $document) {
  "use strict";

  $document.on("dialog-ready", function() {
    handleAutoPopulation();
    $('button[coral-multifield-add]').click(function() {
      var wait = new Coral.Wait();
      wait.centered = true;
      document.body.append(wait);
      wait.show();
      setTimeout(function() {
        handleAutoPopulation()
        wait.remove();
        //setAlert('You can auto populate field now !');
      }, 2000);
    });
  });

  function handleAutoPopulation() {
    $('coral-multifield-item').each(function() {
      var item = $(this)
      $(item).find('#autopopulate').click(function() {
        if ($(item).find('#pathval').val() === "") {
          const ui = $(window).adaptTo("foundation-ui");
          ui.alert('Please select an event detail/ blog detail page');
        } else {
          var path = $(item).find('#pathval').val();
          var type = $(item).find('#search').val();
          var uri = '/content/bdb/paths/event-carousel.json?pagePath='.concat(path).concat('&type=').concat(type);
          var result = getApiCall(uri);
          handleResult(result, item);
        }
      })
    })
  }

  function handleResult(result, item) {
    console.log(result);
    if (result.error) {
      const ui = $(window).adaptTo("foundation-ui");
      ui.alert(result.errorMessage);
    } else {
      for (let k in result) {
        if (k === 'description') {
          var length = result.description.length;
          var desc = length > 261 ? result.description.substring(3, 258) : length > 3 && length <= 261 ? result.description.substring(3, length - 4) : result.description;
          $(item).find('#description').val(desc);
        } else {
          $(item).find('#' + k).val(result[k]);
        }
      }
    }
  }


  function setAlert(message) {
    var dialog = new Coral.Dialog().set({
      id: 'myDialog',
      header: {
        innerHTML: message
      },
      footer: {
        innerHTML: '<button is="coral-button" variant="primary" coral-close>Close</button>'
      }
    });
    document.body.append(dialog);
    showDialog();
  }

  function showDialog() {
    var dialog = document.querySelector('#myDialog');
    dialog.show();
  }



  function getApiCall(uri) {
    var remote;
    $.ajax({
      type: "GET",
      url: uri,
      async: false,
      success: function(data) {
        remote = data;
      }
    });
    return remote;
  }
})($, $(document));
