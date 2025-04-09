
(function($, $document) {
    'use strict';

    function bdbStoreCheck(name, config){
        this.init(name, config);
        this.readData();
    }


    ContextHub.Utils.inheritance.inherit(bdbStoreCheck,ContextHub.Store.PersistedStore);

    bdbStoreCheck.prototype.readData = function() {
        /* Do some processing here. It can be anything, for example, API call or any business logic*/

        var areaOfInterest = getCookie("area_of_interest");
       

        this.setItem('areaOfInterest', areaOfInterest);

    };

    function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for(var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}

    console.log("new");
ContextHub.Utils.storeCandidates.registerStoreCandidate(bdbStoreCheck,
                                'contexthub.bdbStoreCheck', 0);

})();