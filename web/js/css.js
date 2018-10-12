
// Wait until the DOM has loaded before querying the document
$(document).ready(function(){
	$('ul.tabs').each(function(){
		// For each set of tabs, we want to keep track of
		// which tab is active and it's associated content
		var $active, $content, $links = $(this).find('a');

		// If the location.hash matches one of the links, use that as the active tab.
		// If no match is found, use the first link as the initial active tab.
		$active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
		$active.addClass('active');
		$content = $($active.attr('href'));

		// Hide the remaining content
		$links.not($active).each(function () {
			$($(this).attr('href')).hide();
		});

		// Bind the click event handler
		$(this).on('click', 'a', function(e){
			// Make the old tab inactive.
			$active.removeClass('active');
			$content.hide();

			// Update the variables with the new link and content
			$active = $(this);
			$content = $($(this).attr('href'));

			// Make the tab active.
			$active.addClass('active');
			$content.show();

			// Prevent the anchor's default click action
			e.preventDefault();
		});
	});

	$('ul.tabs2').each(function(){
		// For each set of tabs, we want to keep track of
		// which tab is active and it's associated content
		var $active, $content, $links = $(this).find('a');

		// If the location.hash matches one of the links, use that as the active tab.
		// If no match is found, use the first link as the initial active tab.
		$active = $($links.filter('[href="'+location.hash+'"]')[0] || $links[0]);
		$active.addClass('active');
		$content = $($active.attr('href'));

		// Hide the remaining content
		$links.not($active).each(function () {
			$($(this).attr('href')).hide();
		});

		// Bind the click event handler
		$(this).on('click', 'a', function(e){
			// Make the old tab inactive.
			$active.removeClass('active');
			$content.hide();

			// Update the variables with the new link and content
			$active = $(this);
			$content = $($(this).attr('href'));

			// Make the tab active.
			$active.addClass('active');
			$content.show();

			// Prevent the anchor's default click action
			e.preventDefault();
		});
	});
});		



$(document).ready(function(){
	$('body').hide();
	var q = location.search;	
	//alert(q);	
	if(q.indexOf("formatType=physical")!=-1 ){	
		$('#physical').css('display','block'); 
		$('#digital').css('display','none');
		$('#massupdate').css('display','none');  
		$('#mobile').css('display','none');   					
		$('body').show();				
	} else if(q.indexOf("massupdate=y")!=-1){	 
		$('#physical').css('display','none'); 
		$('#digital').css('display','none'); 
		$('#massupdate').css('display','block'); 
		$('#mobile').css('display','none');      					
		$('body').show();
	} else if(q.indexOf("formatType=mobile")!=-1){	 
		$('#physical').css('display','none'); 
		$('#digital').css('display','none'); 
		$('#massupdate').css('display','none'); 
		$('#mobile').css('display','block');      					
		$('body').show();
	} else { 
		$('#physical').css('display','none'); 
		$('#digital').css('display','block'); 
		$('#massupdate').css('display','none'); 
		$('#mobile').css('display','none');      					
		$('body').show();
	} 
	
	if(q.indexOf("deleted=y")!=-1 ){	
		document.getElementById("deletedMobileProdMsg").innerHTML = "**PRODUCT DELETED IN MEMO**";
		document.getElementById("deletedDigProdMsg").innerHTML = "**PRODUCT DELETED IN MEMO**";
		document.getElementById("deletedPhysProdMsg").innerHTML = "**PRODUCT DELETED IN MEMO**";
		document.getElementById("printspecsheet").style.visibility="hidden";
	}
	
	if(q.indexOf("formatType=video")!=-1 ){	
		document.getElementById("stream").innerHTML = "Video Stream Date";
	} else {
		document.getElementById("stream").innerHTML = "Audio Stream Date";
	}

	
});





function disableForms(){

	if(document.forms[3].cssID.value==0){

		document.getElementById('vmpFormBrowseButton').disabled=true;
		document.getElementById('vmpFormSubmitButton').disabled=true;	
		document.getElementById('packFormBrowseButton').disabled=true;
		document.getElementById('packFormSubmitButton').disabled=true;			
	} else {
		document.getElementById('vmpFormBrowseButton').disabled=false;
		document.getElementById('vmpFormSubmitButton').disabled=false;	
		document.getElementById('packFormBrowseButton').disabled=false;
		document.getElementById('packFormSubmitButton').disabled=false;		
	}

}

/*function checkProductsExist(){

	if(document.forms[0].cssID.value==0)  {

		document.getElementById('digiSaveButton').disabled=true;
	} else {
		document.getElementById('digiSaveButton').disabled=false;		
	}

	if(document.forms[1].cssID.value==0) {

		document.getElementById('mobileSaveButton').disabled=true;
	} else {
		document.getElementById('mobileSaveButton').disabled=false;		
	}
	
	if(document.forms[3].cssID.value==0)  {

		document.getElementById('mobileSaveButton').disabled=true;
	} else {
		document.getElementById('mobileSaveButton').disabled=false;		
	}

}*/

function checkProductsExist(){

}

function clearFileUpload(tag) {
	var fu = document.getElementById(tag);
	if (fu != null) {
		document.getElementById(tag).outerHTML = fu.outerHTML;
	}
}

function uploadPFForm(){
	// if (document.forms[3].packagingForm.value != "") {
	document.forms[3].submittingPF.value ="Y";
	document.forms[3].submittingVMP.value ="N";	        
	document.forms[3].submit();
	clearFileUpload("packForm");
	alert('Packaging Form has been uploaded');	
	changePFText();
	document.forms[3].submittingPF.value ="N";       			
	//}
}

function uploadVMPForm(){
	if (document.forms[3].vmpForm.value != "") {
		document.forms[3].submittingPF.value ="N";
		document.forms[3].submittingVMP.value ="Y";	        
		document.forms[3].submit();	
		clearFileUpload("vmpForm");			        
		alert('VMP Form has been uploaded');
		changeVMPText();
		document.forms[3].submittingVMP.value ="N";
		document.forms[3].deletingVMP.value ="";
	}	 
}	

function deleteVMPDocs(){


	var x= confirm('VMP Doc will be permanently deleted. Continue?');		
	if(x){
		document.forms[3].deletingVMP.value ="Y";   
		document.forms[3].submit();	
		removeVMPText();
		alert('VMP Document Deleted');
	}	
	document.forms[3].deletingVMP.value =""; 
 
}


function removeVMPText() {
	if(document.getElementById("noVMPVersion")!=null){
		document.getElementById("noVMPVersion").innerHTML = "[No Current Doc]";
	} else if (document.getElementById("currentVMPVersion")!=null){
		document.getElementById("currentVMPVersion").innerHTML = "[No Current Doc]";
	}
	document.getElementById("vmpLink").innerHTML = "";
	document.forms[3].deletingVMP.value ="";
}




function changeVMPText(){

	var link="";
	var cssID = document.forms[3].cssID.value;
	if(document.forms[3].submittingVMP.value =="Y"){	
		if(document.forms[3].vmpDetails.value == ""){	
			link="<a href=/pmemo3/getFile.do?cssID="+cssID+"&docTypeId=1>VMP doc</a> &nbsp;&nbsp;&nbsp;<img src='/pmemo3/images/delete_button_sml.jpg' onClick='deleteVMPDocs()'/>";
			if(document.getElementById("noVMPVersion")!=null){
			document.getElementById("noVMPVersion").innerHTML = link;	
			} else if (document.getElementById("currentVMPVersion")!=null){
				document.getElementById("currentVMPVersion").innerHTML = link;	
			}
		}else{		
			var vmpVal=document.forms[3].vmpDetails.value;
			link="<a href=/pmemo3/getFile.do?cssID="+cssID+"&docTypeId=1>"+vmpVal+"</a>&nbsp;&nbsp;&nbsp;<img src='/pmemo3/images/delete_button_sml.jpg'  onClick='deleteVMPDocs()'/>";
			document.getElementById("noVMPVersion").innerHTML = link;	
		}		
	}
}



function changePFText(){

	var link="";
	var cssID = document.forms[3].cssID.value;
	if(document.forms[3].submittingPF.value =="Y"){						
		link="<a href=/pmemo3/getFile.do?cssID="+cssID+"&docTypeId=2>Packaging Form</a>";
		document.getElementById("noPFVersion").innerHTML = link;					
	}
}

function checkVMPLink(){
	if (document.forms[3].submittingVMP.value !="Y"){
		var vmpNumber = document.forms[3].vmpDetails.value;			
		if (vmpNumber!=""){
			document.getElementById("vmpLink").innerHTML = vmpNumber;
		} else {
			document.getElementById("vmpLink").innerHTML = "VMP doc [TBC]";
		}	

	}
}


function limitText(limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} 
}




function reloadPage(){
	window.location.reload(true);	
	document.forms[3].deletingVMP.value ="";
}



function cfld (objname){
var ptr = document.getElementById(objname);
if(objname=="mobMassMastersRecdDate"){
	ptr.value = ".";
}else if(objname=="mobMassArtworkRecdDate"){
	ptr.value = "..";
}else{
ptr.value = "";
}
ptr.focus ();
}		







