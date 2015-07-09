function reset(){			
  		//parent.frames[0].document.getElementById("home").className="";
  		parent.frames[0].document.getElementById("compo").className="";
  		parent.frames[0].document.getElementById("tech").className="";
}
function activate(element){			
  		reset();
  		element.className="active";
}
function activate2 (elementName){
	reset();
	parent.frames[0].document.getElementById(elementName).className="active";	
}