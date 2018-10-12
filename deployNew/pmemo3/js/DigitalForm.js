function checkMe(){
if(document.forms[0].exclusive.checked==1){
document.forms[0].exclusiveTo.disabled=false;
document.forms[0].exclusivityDetails.disabled=false;
document.forms[0].exclusiveTo.style.backgroundColor='#ffffff';
document.forms[0].exclusivityDetails.style.backgroundColor='#ffffff';

}
else if(document.forms[0].exclusive.checked==0){
document.forms[0].exclusiveTo.disabled=true;
document.forms[0].exclusivityDetails.disabled=true;
document.forms[0].exclusiveTo.style.backgroundColor='#cccccc';
document.forms[0].exclusivityDetails.style.backgroundColor='#cccccc';
}
} 

function checkCombo(){
if(document.forms[0].configurationId.value=="711"){
document.forms[0].comboRef.disabled=false;
document.forms[0].comboRef.disabled=false;
document.forms[0].comboRef.style.backgroundColor='#ffffff';
document.forms[0].comboRef.style.backgroundColor='#ffffff';
}
else {
document.forms[0].comboRef.disabled=true;
document.forms[0].comboRef.disabled=true;
document.forms[0].comboRef.style.backgroundColor='#cccccc';
document.forms[0].comboRef.style.backgroundColor='#cccccc';
}

}

function showHideCombo(){

if(document.forms[0].configurationId.value=="711"){

document.getElementById('comboNumber').style.display="block";
}

else {
document.getElementById('comboNumber').style.display="none";
}

}


	
function showHidePreOrder(){

if(document.forms[0].preOrder.value=="Y"){

document.getElementById('preOrderDetails').style.display="block";
document.getElementById('videoStreamDetails').style.display="none";
document.getElementById('videoStream').value="N";
//document.getElementById('audioStream').value="N";
//document.getElementById('streamingDate').value="";
//document.getElementById('audioStream').value="N";


}

else {
document.getElementById('preOrderDetails').style.display="none";
//document.getElementById('previewClips').value="";


}


}



function showHideVideoStreamingDate(){

if(document.forms[0].videoStream.value=="Y"){

document.getElementById('videoStreamDetails').style.display="block";
document.getElementById('preOrderDetails').style.display="none";
document.getElementById('audioStreamDetails').style.display="none";
document.getElementById('preOrder').value="N";
document.getElementById('audioStream').value="N";
//document.getElementById('previewClips').value="";
//document.getElementById('preOrderDate').value="";

} else if ( document.forms[0].videoStream.value=="N" ){
document.getElementById('videoStreamDetails').style.display="none";
}
}


function showHideAudioStreamingDate(){
	
if(document.forms[0].audioStream.value=="Y"){

document.getElementById('audioStreamDetails').style.display="block";
document.getElementById('videoStreamDetails').style.display="none";
//document.getElementById('preOrder').value="N";
document.getElementById('videoStream').value="N";
//document.getElementById('previewClips').value="";
//document.getElementById('preOrderDate').value="";

	
} else if(document.forms[0].audioStream.value=="N"){
document.getElementById('audioStreamDetails').style.display="none";
}

}


function showHidePullDate(){
	
	if(document.forms[0].pullProduct.value=="Y"){
		
		document.getElementById('pullProductDetails').style.display="block";
	
	} else if(document.forms[0].pullProduct.value=="N"){
		
		document.getElementById('pullProductDetails').style.display="none";
	}
}






function showHideExclusive(){

if(document.forms[0].exclusive.checked==1){

document.getElementById('exclusiveDetails').style.display="block";
}

else {
document.getElementById('exclusiveDetails').style.display="none";
}

}


function showHidePublishBoxes(){

	if(document.getElementById('grasSetComplete').value="Y"){
		
		document.getElementById('publishBox1enabled').style.display="block";
		document.getElementById('publishBox2enabled').style.display="block";
		document.getElementById('publishBox1disabled').style.display="none";
		document.getElementById('publishBox2disabled').style.display="none";

		
	} else {
	
		document.getElementById('publishBox1enabled').style.display="none";
		document.getElementById('publishBox2enabled').style.display="none";
		document.getElementById('publishBox1disabled').style.display="block";
		document.getElementById('publishBox2disabled').style.display="block";
	}

}



function showHideRestrictDate(){

	if(document.forms[0].restrictRelease.value=="Y"){

			document.getElementById('restrictDateSection').style.display="block";
			
		} else {
			
			document.getElementById('restrictDateSection').style.display="none";
			
		}

	}


// Think about adding some simple DWR callback to check whether product is mobile rather than hardcode values like this
function showHideGridNumber(){

// needs to pass in the value selected in the form (ie the selected option for digital format), 
// heck to see if its a mobile and show/hide gridnumber and ringtone Approval accordingly

if((document.forms[0].configurationId.value=="704") || 
(document.forms[0].configurationId.value=="705") || 
(document.forms[0].configurationId.value=="706") ||
(document.forms[0].configurationId.value=="713") 
){

document.getElementById('gridNum').style.display="none";
document.getElementById('ringtoneApproval').style.display="block";
}

else {
document.getElementById('gridNum').style.display="block";
document.getElementById('ringtoneApproval').style.display="none";
}

}


function changeText(){
	
if((document.forms[0].preOrder.value=="N") && (document.forms[0].videoStream.value=="N") && (document.forms[0].audioStream.value=="N") ){
	
	document.getElementById('relText').innerHTML = "<strong>RELEASE DATE:</strong>";

}else if(document.forms[0].preOrder.value=="Y"){
	
	document.getElementById('relText').innerHTML = "<strong>STREET DATE:&nbsp;&nbsp;</strong>";

}else if(document.forms[0].videoStream.value=="Y"){
	
	document.getElementById('relText').innerHTML = "<strong>DOWNLOAD DATE:</strong>";

}else if(document.forms[0].audioStream.value=="Y"){
	
	document.getElementById('relText').innerHTML = "<strong>RELEASE DATE:</strong>";	

}else{
	
	document.getElementById('relText').innerHTML = "<strong>RELEASE DATE:</strong>";
}
}


function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}


function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}

function showOtherPopUp(){

		var advice   = document.getElementById('configurationId').value;
		//advice.style.display  = (events=='reldate')?'block':'none';  // Assign style
		if(advice == 716){
			alert(" Please include a format description in the Comments field ");
		}
}


function videoStreamDefaultChoice(){
	
		var formatId   = document.getElementById('configurationId').value;
	
		if ((formatId == 715) || (formatId == 719) || (formatId == 723) || (formatId == 724) ){
			
						
			document.getElementById('videoStream').value="N";
		
		} else {
						
			document.getElementById('videoStream').value="N";
		}
	
}



function showAgeRating(){
	var formatId  = document.getElementById('configurationId').value;
	if ((formatId == 717) || 
		(formatId == 711) || 
		(formatId == 723) || 
		(formatId == 724) ||
		(formatId == 715) ||
		(formatId == 719) ||
		(formatId == 720) ||
		(formatId == 721) ||
		(formatId == 722) ){
	
		document.getElementById('ageRating').style.display="block";
		
	} else {
		
		document.getElementById('ageRating').style.display="none";
		document.getElementById('ageRating').value="";
		
	}
}

function showVideoLength(){
	
	

	var formatId  = document.getElementById('configurationId').value;
	if ((formatId == 715) || (formatId == 719) || (formatId == 723) || (formatId == 724)    ){
	
		document.getElementById('videolength').style.display="block";
		document.getElementById('premierTime').style.display="block";		
		document.getElementById('bitlength').style.display="none";
		document.getElementById('bitlength').value="";
		document.getElementById('audioStreamDetails').style.display="none";
		document.getElementById('audioStream').value="N";
		
	}else if ((formatId == 711) || (formatId == 703) || (formatId == 702) ||
			  (formatId == 717) || (formatId == 700) || (formatId == 710) ||
			  (formatId == 709) || (formatId == 708) || (formatId == 701) || 
			  (formatId == 718) ) {
		
		document.getElementById('videolength').style.display="none";
		document.getElementById('premierTime').style.display="block";	
		document.getElementById('bitlength').style.display="block";
		document.getElementById('videolength').value="";
		document.getElementById('videoStream').value="N";
		
	} else {
		
		document.getElementById('videolength').style.display="none";
		document.getElementById('bitlength').style.display="none";
		document.getElementById('videolength').value="";
		document.getElementById('bitlength').value="";
		document.getElementById('videoStream').value="N";
	}
}


	