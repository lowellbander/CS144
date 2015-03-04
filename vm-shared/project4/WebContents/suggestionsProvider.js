function suggestionsProvider(){

}

suggestionsProvider.prototype.requestSuggestions = function (oAutoSuggestControl, btypeAhead) {
    var aSuggestions = [];
    var sTextboxValue = oAutoSuggestControl.textbox.value;

    if (sTextboxValue.length > 0){

    	var request = new XMLHttpRequest();
    	request.onreadystatechange = function(){
    		if(request.readyState == 4){
    			if(request.status == 200){
    				var suggestions = request.responseXML.getElementsByTagName("suggestion");
    				for(var i=0; i<suggestions.length; i++){
    					aSuggestions.push(suggestions[i].getAttribute("data"));
    				}      
    				oAutoSuggestControl.autosuggest(aSuggestions, btypeAhead);
    			}

    		}
    	}
    	request.open("GET","suggest?q="+sTextboxValue, true);
    	request.send();
    }
        
};