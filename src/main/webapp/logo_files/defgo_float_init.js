/*****************************************/
/***   WEBSITE EVALUTATION SCRIPT      ***/
/*****************************************/
/*** - Shows website evaluation float. ***/
/*** - Sets evaluation cookies.        ***/
/*****************************************/

var defgo_surveyOk = false;

jQuery(document).ready(function(){
	try {
		defgo_init();
	} catch (e) {}
});

function defgo_init() {	
		jQuery("body").append('<div style="display:none" id="defgonetmessage" class="defgoDivTag"></div>' + 
					    	  '<img alt="" border="0" style="display: none" src="https://app01.defgosoftware.net/defgo/blank.gif"' + 
					    	  ' width="1" height="1" onload="defgo_surveyOk=true">');

		jQuery("body").append('<script type="text/javascript" src="https://www.defgo.net/surveys/dk/interfazes/ku/webevaluation_setup.asp?&t=1&s=1"></script>');
}