var priorityCounter = 0;
var isOnlyChecking = true;

// this method is not fully complete, used to automatically set prioritys of approvers
function setPriority(theCheckBox, numOfApprovers)
{
	if(!theCheckBox.checked)
	{
		isOnlyChecking=false;
		// reset the corresonding select
		theSelect = getCorespondingSelectBox(theCheckBox);
		theSelect.selectedIndex = 0;
		// reset all prioritites as no way to know now
		// resetAllPriorities(numOfApprovers)
		return;
	}
		
	if(!isOnlyChecking)
		return;
	
	priorityCounter = priorityCounter+1;
	
	theSelect = getCorespondingSelectBox(theCheckBox);
	
	theSelect.selectedIndex = priorityCounter;


	
}

function getCorespondingSelectBox(theCheckBox)
{
	strName = theCheckBox.id;

	index = strName.lastIndexOf("_");
	index=index+1;

	theId = strName.substring(index);

	theSelect = document.getElementById("id_approver_priority_"+theId);
	
	return theSelect;
}

function resetAllPriorities(numOfApprovers){

	for(var count=0; count<numOfApprovers; count++)
	{
		var selectBox = document.getElementById('id_approver_priority_'+(count+1));
		selectBox.selectedIndex=0;
	}
	
}


function toggleMembers(groupId)
{
	
	
	var membersTR = document.getElementById('groupmembers_'+groupId);
	
	if(membersTR.style.display=='none' )
		membersTR.style.display='block';
	else
		membersTR.style.display='none' ;
		
	
}


function importAmpUsersSearch()
{		
	var term = document.getElementById('id_searchBy');
	var text = document.getElementById('id_searchText');
	
	
	new Ajax.Request("ajax_find_amp_users.jsp",
	{
		method: "get", parameters: "searchBy="+term.value+"&searchText="+text.value, onComplete: update_user_search_div
	});
	
}

function update_user_search_div(originalRequest)
{	
	var theDiv = document.getElementById('import_users_search');	
	theDiv.innerHTML = originalRequest.responseText;	
}
// remember selectRole is now the id of a bunch of check boxes
function createNewUser(firstname, lastname, email, checkRole, username)
{	
	var createUserForm = document.getElementById('id_create_user_form');

	createUserForm.firstname.value = firstname;
	createUserForm.lastname.value = lastname;
	createUserForm.email.value = email;
	createUserForm.username.value = username;
	
	var requestorChecked = document.getElementById(checkRoleRequestor).checked;
	var approverChecked = document.getElementById(checkRoleApprover).checked;
	var viewerChecked = document.getElementById(checkRoleViewer).checked;
	
	document.getElementById('roleRequestor').checked = requestorChecked; 
	document.getElementById('roleApprover').checked = approverChecked; 
	document.getElementById('roleViewer').checked = viewerChecked; 
	
	// submit the form
	createUserForm.submit();
}

function validateNewRequestApproval()
{	
	var titleCheckOk = false;
	var summaryCheckOk = false;
	
	if(document.newrequestform.title.value!='')
		titleCheckOk=true;
	
	if(document.newrequestform.summary.value!='')
		summaryCheckOk=true;
		
	var some_checks = 0;
	
	  for(i=1;i<100;i++) 
	  {
	    eval("var ApproverCheckBoxId = 'id_approver_"+i+"'");   /* the coolness of scripting languages */
	    
	    if(document.getElementById(ApproverCheckBoxId))
	    {
	      if(document.getElementById(ApproverCheckBoxId).checked)	      	        
	        some_checks++;
	    }	     
	  }
	  
	  if(titleCheckOk && summaryCheckOk && (some_checks>0))
	  	return true; // validated ok
	  
	  var errorMessage = '';
	  
	  if(!titleCheckOk)
	  	errorMessage+=' no title provided ';	  
	  if(!summaryCheckOk)
	  	errorMessage+='\n no summary provided ';
	  if(some_checks<1)
	  	errorMessage+='\n you must provide at least one approval group ';
	  	
	  alert(errorMessage);
	  
	  return false;
	 
}


function triggerSubmit(button)
{    	
	document.getElementById('id_decision').value=button.value;   	
	if(button.value=='APPROVED'){
		proceed = confirm('Clicking \"APPROVED"\ has the same effect as signing a memo as an approver.  Do you wish to proceed?');
		if(!proceed)	
			return;
	}
	document.getElementById('id_form').submit();    	
}


function returnString(){
	
	var id = document.getElementById('artist').value;
	
	//alert(id);

	document.getElementById('planProjects').style.display="block";
	//var returnString = "window.open('/pmemo3/popUp.jsp?artist=" + id +"','','resizable=no,location=no,menubar=no,scrollbars=yes,status=no,toolbar=no,fullscreen=no,dependent=no,width=450,height=150,left=300,top=200')";
	//eval("http://local.sonymusicamplified.co.uk/pmemo3/checkPlanning.do");   /* the coolness of scripting languages */			   
	return returnString;
}



function returnPlanning(){ 

var artistId = document.getElementById('artist').value;



	
    			Demo.returnProjectsInPlanning(artistId, function(data) {

										    				dwr.util.setValue("exclusivityDetails", data.exclusivityDetail)  

															dwr.util.setValue("usageExpires", data.usageExpires)

										    				dwr.util.setValue("exclusiveTo", data.exclusiveTo)	

										    				dwr.util.setValue("overallUsageExpires", data.usageExpires)	
									    														    													    				
														   }
								 );
								 
									


}



function closePlanning(){
	

	document.getElementById('planProjects').style.display="none";

}
	
	


