/* --------------------------------------------------------------------------
File:	util.js
Author:	Radim Cebis
Usage:	functions for assigning the parser to element's events

You may use, modify and distribute this software under the terms and conditions
of the Artistic License. Please see ARTISTIC for more information.
----------------------------------------------------------------------------- */

/* -FUNCTION--------------------------------------------------------------------
	Function:		addEvent(obj, evType, fn)
		
	Usage:			adds event listener (fn) to the object (obj) on event (evType)
----------------------------------------------------------------------------- */ 	
function addEvent(obj, evType, fn){ 
 if (obj.addEventListener){ 
   obj.addEventListener(evType, fn, false); 
   return true; 
 } else if (obj.attachEvent){ 
   var r = obj.attachEvent("on"+evType, fn); 
   return r; 
 } else { 
   return false; 
 } 
}

/* -FUNCTION--------------------------------------------------------------------
	Function:		register(id, func)
	Param elemType: Textarea object
	Usage:			registers func to element with correct question ID
----------------------------------------------------------------------------- */ 	
function register(idTextarea, func, elem)
{
		
	//var elem = elemType;
	function test(evt)
	{		
		if (!evt) var evt = window.event;		
		var input = (evt.target) ? evt.target : evt.srcElement;
		
		var result = func(input.value);
		if(elem.value == "") 
	    {
	      document.getElementById(idTextarea).className = "parser_text_default";
	 //     document.getElementById(id + "-error").innerHTML = "";
	    }
		else
		{
	  		if(result.error_string != "")
	  			document.getElementById(idTextarea + "-error").innerHTML = htmlentities(result.error_string);
	  		else 
	  			document.getElementById(idTextarea + "-error").innerHTML = result.error_string;
	  		
	  		if (result.error == 2) {
	  			elem.className = "parser_text_error";
	  		}
	  		else if(result.error == 1){
	  			elem.className = "parser_text_missing";
	  		}
	  		else {
	  			elem.className = "parser_text_accept";
	  		}
		}
	}	
	addEvent(elem,'change',test);
	addEvent(elem,'keyup',test);	
	addEvent(elem,'focus',test);
	addEvent(elem,'blur',test);	
	addEvent(elem,'mouseup',test);	
	elem.focus();
	elem.blur();
	scroll(0,0);
}
/* -FUNCTION--------------------------------------------------------------------
	Function:		htmlentities( s )
	Author:			Kevin van Zonneveld (http://kevin.vanzonneveld.net)					
	Usage:			converts special characters in string to its html entities
----------------------------------------------------------------------------- */
function htmlentities( s ){
    // http://kevin.vanzonneveld.net
    // +   original by: Kevin van Zonneveld (http://kevin.vanzonneveld.net)
    // *     example 1: htmlentities('Kevin & van Zonneveld');
    // *     returns 1: 'Kevin &amp; van Zonneveld'
 
    var div = document.createElement('div');
    var text = document.createTextNode(s);
    div.appendChild(text);
    return div.innerHTML;
}
