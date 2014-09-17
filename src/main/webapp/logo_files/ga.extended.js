        function trackLinks(){
        
    	    // Add domains to exclude here
	        var excludeList = new Array();
        	var domain = 'ku.dk';
    	    var mail   = new RegExp("mailto:");
	        var kuRef = location.hostname + location.pathname;
	        
            links = document.getElementsByTagName("a");
            linkList = Array();
            for (var i = 0; i < links.length; i++){
            	if(links[i].href == location.href+'#' || links[i].href == 'http://cms.ku.dk/' || links[i].href == 'http://cms.ku.dk'){
            	}
            	else linkList.push(links[i]);
            }
            addFormSubmits(linkList);
            for (var i = 0; i < linkList.length; i++){
                exclude = false;
                for(var j = 0; j < excludeList.length; j++){
                    tmp = linkList[i] + "";
                    if (tmp.match(new RegExp("/"+excludeList[j]+"/","i"))) {
                        exclude = true;
                    }
                }
                if(!exclude){
 					var element = linkList[i];        
 					element.exonclick = element.onclick;
					element.onclick = function (oEvent) {
						if (this.exonclick) {
							this.exonclick(oEvent);
 					    }
						link = this + "";
                        if(this.tagName != 'A') link = this.getAttribute('href');



						var href = link.split('://').pop().replace('www.','').split('/').shift();
						var split = href.split('.');
						ldomain = (typeof split[split.length - 2] != 'undefined' ? split[split.length-2] + '.' : ''  ) + split[split.length-1];

                        if(domain.localeCompare(ldomain) != 0 && link && link !== ""){
                            // External, or a subdomain
                            virtualpage = "/vpv/external/" + link + "?ref=" + kuRef;
                             _gaq.push(['mainTracker._trackPageview',virtualpage]);
                             _gaq.push(['legacyTracker._trackPageview',virtualpage]);
                        }
						else{
							// Finds the filename (or folder)
	                        filename = link.split("/").pop().split("?").shift();
	                        // Fixes trailing slashes (/)
	                        if(filename === '') filename = link.substr(0,link.length-1).split("/").pop().split("?").shift();
	                        // Check that the link is infact pointing to a file, and not a subfolder
	                        if(filename.indexOf(".") != -1){
	                            // Matches .doc, .docx, .xls, .xlsx, .ppt, .pptx, .pdf, .psd
	                            matches = Array("doc","docx","xls","xlsx","ppt","pptx","pdf","psd");
	                            ext = filename.split(".").pop().toLowerCase();
	                            if(array_contains(matches,ext)){
	                                // Track file
	                                virtualpage = "/vpv/document/" + ext + "/" + filename + "?ref=" + kuRef;
	                                 _gaq.push(['mainTracker._trackPageview',virtualpage]);
	                                 _gaq.push(['legacyTracker._trackPageview',virtualpage]);
	
	                            }
	                        }
	            
	                        else if(link.toLowerCase().match(mail)){
	                            // Track mailto link
	                            virtualpage = "/vpv/mailto/"+link.split("mailto:").pop()+"?ref=" + kuRef;
	                             _gaq.push(['mainTracker._trackPageview',virtualpage]);
	                             _gaq.push(['legacyTracker._trackPageview',virtualpage]);
	
	                        }
						}
 					}
                }
            }
        }
        
        /*
         * Special case not found: When a link is outside the form element, or the type="submit" attribute is missing
         */
        function addFormSubmits(linkList){
            forms = document.getElementsByTagName("form");
            for (var i = 0; i < forms.length; i++){
                // Find the submit element; whatever type of element it is
                children = forms[i].childNodes;
                for(var j = 0; j < children.length; j++){
                    if(children[j].attributes != null && children[j].getAttribute('type')){
                        if(children[j].getAttribute('type') == 'submit'){
                            // Add this node to the linkList, with the form action attribute as a href
                            node = children[j];
                            node.setAttribute('href',forms[i].getAttribute('action'));
                            linkList.push(node);
                        }
                    }
                }
            }
        }
        
        function array_contains(arr,obj){
			var i = arr.length;
            while (i--) {
                if (arr[i] === obj) {
                    return true;
                }
            }
            return false;
        }
        
if(window.addEventListener) {
window.addEventListener("load", trackLinks, false); 
} else if (window.attachEvent) { 
window.attachEvent("onload", trackLinks); 
}