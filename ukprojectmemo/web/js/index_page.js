function showhide(id){
	if (document.getElementById){
		obj = document.getElementById(id);
			if (obj.style.display == "none"){
				obj.style.display = "block";
			} else {
				obj.style.display = "none";
			}
	}
}

function hideDiv(){
if (document.getElementById){
obj = document.getElementById("openPMs");

obj.style.display = "block";

}
}

function showDateAdvice(events){

var advice   = document.getElementById('dateAdvice');
advice.style.display  = (events=='reldate')?'block':'none';  // Assign style  


}
