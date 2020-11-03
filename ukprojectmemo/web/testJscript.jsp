<%@ page language="java"%>
<%@ page import="java.text.*,
				 java.util.*,
				 com.sonybmg.struts.pmemo3.util.*,
				 javax.servlet.http.Cookie,
				 com.sonybmg.struts.pmemo3.db.*" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template" prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested" prefix="nested"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html:html locale="true">
<head>
	<title>Project Memo - Header Page</title>
<script>

window.onload = clickButton;hide();


function clickButton()
{
    document.getElementById('LISTBOX_ITEM_1_BIBtnCBG').onclick = function()
    {
        alert('you clicked me!');        
		<%--ur_Button_click(event);--%>
		show();
    }
      
}


function hide(){
alert('hide function!');  
document.getElementById('ANALYSIS_ITEM_1_ia_pt_a').style.display="none";

}

function show(){
alert('you clicked show!');  
document.getElementById('ANALYSIS_ITEM_1_ia_pt_a').style.display="block";
}

</script>
	
</head>
<body>

<a id="LISTBOX_ITEM_1_BIBtnCBG" >Apply</a>


<table id="ANALYSIS_ITEM_1_ia_pt_a">
<tr>
<td>
test
</td>
</tr>
</table>


</body>

</html:html>
