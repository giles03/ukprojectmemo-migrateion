==<%@ page language="java"%>
<%@page
	import="com.sonybmg.struts.pmemo3.model.*,java.util.*,java.text.*, com.sonybmg.struts.pmemo3.util.*"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"
	prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"
	prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic"
	prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"
	prefix="tiles"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-template"
	prefix="template"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-nested"
	prefix="nested"%>
<link href="css/jqueryui/jtable_jqueryui.css" rel="stylesheet"
	type="text/css" />
<link href="css/jqueryui/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="css/validationEngine.jquery.css" rel="stylesheet" type="text/css" />
<script src="js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="js/jquery-ui-1.10.4.custom.js" type="text/javascript"></script>
<script src="js/jquery.jtable.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript"></script>
<script src="js/jquery.validationEngine-en.js" type="text/javascript"></script>
 <script type="text/javascript">
	    $(document).ready(function () {
	        //initialize jTable
	        $('#preOrderDetailContainer').jtable({
	            title: '.',
	            actions: {
	                listAction: 'CRUDController?action=list',
	                createAction:'CRUDController?action=create',
	                updateAction: 'CRUDController?action=update',
	                deleteAction: 'CRUDController?action=delete'
	            },
	            fields: {
	                preOrderNumber: {
	                    key: true,
	                    list: false
	                },
	                partner: {
	                    title: 'Partner Name',
	                    width: '35%',
	                    options: { '0': 'Please Select', '2': 'Amazon',  '1': 'iTunes', '5': 'Google', '7': 'iTunes Amazon Google', '8': 'iTunes Amazon' , '9': 'iTunes Amazon Google Qobuz', '10': 'Google Qobuz' , '11': 'iTunes Amazon Qobuz', '12': 'Amazon Google Qobuz', '13': 'Qobuz', '14': 'Amazon Google', '15': 'Google iTunes','16': 'Google iTunes Qobuz', '17': 'Amazon Qobuz' }
	                },
	                preOrderDate: {
	                    title: 'Start Date',
	                    width: '35%',
	                    type: 'date',
	                    displayFormat: 'dd-MM-yy'
	                },
	                previewClips: {
	                    title: 'Preview Clips',
	                    width: '30%',
	                    options: {'N': 'No', 'Y': 'Yes'}
	                }
  
	            }
	        });  
	       $('#preOrderDetailContainer').jtable('load');
	        
	    });
	</script> 
	




<div id="preOrderDetailContainer"></div>



