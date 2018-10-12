function assignToGroup()
{
  
  fromList = document.getElementById('all_users');  
  toList = document.getElementById('users_in_group');
   
  var sel = false;
  
  
  for (i=0;i<fromList.options.length;i++)
  {
  
    var current = fromList.options[i];
    if (current.selected)
    {
      sel = true;
      dup = false;
      for (z=0;z<toList.options.length;z++){      	
      	if(toList.options[z].value==current.value){
      		dup=true; // this is dupliacte
      	}
      }
      
      if(!dup)
      {
	      txt = current.text;
	      val = current.value;
	      toList.options[toList.length] = new Option(txt,val);
      }
      
    }
  }
  
  if (!sel) alert ('You haven\'t selected any options!');
}

function selectNewGroupUsers(){
	
	
	list = document.getElementById('users_in_group');
	
	for (i=0;i<list.options.length;i++)
  	{
	    var current = list.options[i];
		//alert('selecting users='+list.options[i].value+list.options[i].text);
	    var optVal = list.options[i].value;
	    
	    if(optVal.indexOf('ignore') == -1)
	    {	    	    	
	    	var current = list.options[i];		
		    current.selected = true;		
	    }    
  	}
  	
}




function getGroupUsers(){
	
	
	groupId = document.getElementById('approval_groups');
	var params = "id_group="+groupId.options[groupId.selectedIndex].value;	
			
	new Ajax.Request("ajax_get_users_in_group.jsp",
	{
		method: "get", parameters: params, onComplete: showGroupUsers 
	});
		
}

function showGroupUsers(originalRequest){
	// change the inner html
	
	var div = document.getElementById('id_users_in_group_div');
	
	div.innerHTML = originalRequest.responseText; 
	
}

 function copyToList(from,to)
{
  
  fromList = document.getElementById(from);  
  
  toList = document.getElementById(to);
  
  var sel = false;
  
  for (i=0;i<fromList.options.length;i++)
  {
  
    var current = fromList.options[i];
    if (current.selected)
    {
    	
      sel = true;
      
      txt = current.text;
      val = current.value;
      toList.options[toList.length] = new Option(txt,val);
      fromList.options[i] = null;
      i--;
    }
  }
  
  if (!sel) alert ('You haven\'t selected any options!');
}

function validateForm(){
	var gname = document.getElementById('id_groupname');
	
	if(gname.value==null || gname.value=='')
	{
		alert('please enter a group name');
		return false;
	}
		
	selectAllOpts();
}

function selectAllOpts(){

  list = document.getElementById('new_group_users');
  for (i=0;i<list.options.length;i++)
  {
    var current = list.options[i];
    if (!current.selected)
    {
      current.selected = true;
    }
    
  }
}

function removeUsersFromGroup()
{

  removeFromList = document.getElementById('users_in_group');  
  var sel = false;
  
  for(i=0;i<removeFromList.options.length;i++)
  {
  
    var current = removeFromList.options[i];
    if (current.selected)
    {
    	
      sel = true;      
      removeFromList.options[i] = null;
      i--;
    }
  }
  
  if (!sel) alert ('You haven\'t selected any options!');
			 
}