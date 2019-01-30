<!doctype html>
<!-- The DOCTYPE declaration above will set the     -->
<!-- browser's rendering engine into                -->
<!-- "Standards Mode". Replacing this declaration   -->
<!-- with a "Quirks Mode" doctype is not supported. -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <!-- IE to load the application in Standard mode -->
    <!-- meta http-equiv="X-UA-Compatible" content="IE=8" -->
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE10">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="-1">
    
    <!--  calendar view includes below-->
    <link rel='stylesheet' href='/pmemo3/calendarView/jquery-ui.min.css' />
	<link href='/pmemo3/calendarView/fullcalendar.min.css' rel='stylesheet' />
	<link href='/pmemo3/calendarView/fullcalendar.print.min.css' rel='stylesheet' media='print' />
	
	<script src='/pmemo3/calendarView/moment.min.js'></script>
	<script src='/pmemo3/calendarView/jquery.min.js'></script>
    <script src='/pmemo3/calendarView/fullcalendar.min.js'></script> 
	 <script src='/pmemo3/calendarView/gcal.min.js'></script>
	 
	 <script type="text/javascript">
	 $( document ).ready(function() {
	//alert( "ready!" );
		var list;
		var labels =[ "all"];
	 jQuery.ajax({
		    url: "http://memo.smeukapps.com/css/css/jsonData?userid=<%= request.getParameter("userId")%>",
		    type: 'GET',
		    async: true,
		   // data: {userid:id},
		    cache: false,        
		    complete: function (xhr, status) {
		      if (status === 'error' || !xhr.responseText) {
		    	  console.log(status);
		    	  
		    	//  alert("failure");
		    	 
		      }
		      else {
		    	  //caledar UI Start
		    	  var obj = JSON.parse(xhr.responseText);
		    	  list = obj.cal;
		    	//  alert(obj.cal);
	 	 $('#calendar').fullCalendar({

	 			theme: true,
	 			header: {
	 				left: 'prev,next today',
	 				center: 'title',
	 				right: 'month,agendaWeek,agendaDay,listMonth'
	 			},
	 			defaultDate: new Date(),
	 			googleCalendarApiKey: 'AIzaSyDcnW6WejpTOCffshGDDb4neIrXVUA1EAE',
	 			navLinks: true, // can click day/week names to navigate views
	 			editable: false,
	 			eventLimit: true, // allow "more" link when too many events
	 			//events: list,
	 			eventSources: [ list, 'en.german#holiday@group.v.calendar.google.com','en.usa#holiday@group.v.calendar.google.com', 'en.uk#holiday@group.v.calendar.google.com' ,'en.french#holiday@group.v.calendar.google.com'],
	 			eventColor: '#378006',
	 			  eventRender: function eventRender(event, element, view) {
	 				// var str = $('#label_selector').val();
	 				 var label = false;
			 		 for (i = 0; i < labels.length; i++) {
	 				  if(event.type == labels[i] || labels[i] == "all" || event.type == null ){
	 					 label= true;
	 					 break;
	 				  }
			 			} 
			 		 return label;
	 	        }, 
	 	       eventClick: function(event) {
		 	        if (event.url) {
		 	            return false;
		 	        }
		 	    },
	 			eventMouseover: function(calEvent, jsEvent) { var tooltip = '<div class="tooltipevent" style="width:220px;height:40px;background:#aed0ea;position:absolute;z-index:10001;"> Title: ' + calEvent.description + '</div>'; 
	 			if(calEvent.description!=null){
	 				//alert(calEvent.description);
	 			
	 			var $tool = $(tooltip).appendTo('body');
	 			$(this).mouseover(function(e) {
	 			    $(this).css('z-index', 10000);
	 			            $tool.fadeIn('500');
	 			            $tool.fadeTo('10', 1.9);
	 			}).mousemove(function(e) {
	 			    $tool.css('top', e.pageY + 10);
	 			    $tool.css('left', e.pageX + 20);
	 			});}
	 			},
	 			eventMouseout: function(calEvent, jsEvent) {
	 			$(this).css('z-index', 8);
	 			$('.tooltipevent').remove();
	 			},
	 			 views: {
	 		        agenda: {
	 		            eventLimit: 35 // adjust to 6 only for agendaWeek/agendaDay
	 		        }
	 		    }
	 		}); 
	 	/* $('#calendar').fullCalendar({
	 	    eventClick: function(event) {
	 	        if (event.url) {
	 	            return false;
	 	        }
	 	    }
	 	}); */
	 	$("#cal input:checkbox").change(function() {
		    //alert("you just clicked the checkbox with name "+ $(this).attr("value"));
		     var i =0;
		     labels = [];
		     $('input[type=checkbox]:checked').each(function() {
		        // Do something interesting
		        labels[i] =  $(this).attr("value");
		       i++;
		    }); 
		    // alert(labels);
		     $('#calendar').fullCalendar('rerenderEvents');
		});
		 //alert("inside else"+list.length);
		// $('#calendar').fullCalendar( 'addEventSource', list );
		    	  
		      
		    	  //Calendar End
		    	  
		    	  
		       //console.log(xhr.responseText);
		      // alert(JSON.stringify(event));
		      }
		    }   
		   
		 });
	 });
	 </script>
</head>
<body>
<div> <form id='cal'> <b><font size='0.5'><input name ='label_selector' style='height:10px' type='checkbox' id = 'all' checked value='all'>All Labels &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L1'>RCA &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L2'>EPIC &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox'  value='L3'>COLUMBIA &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L4'>COMMERCIAL &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L5'>SYCO &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L6'>JIVE &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L7'>DECONSTRUCTION &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L8'>IRELAND &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L9'>MERCHANDISE &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L10'>CENTRAL &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L11'>ASSOCIATED LABELS &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L12'>NOW! &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L13'>DISTRIBUTED &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L14'>OTHER &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L15'>DISTRIBUTED INTERNATIONAL &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L16'>RELENTLESS RECORDS &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L17'>BLACK BUTTER &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L18'>SME INTERNATIONAL &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L19'>INSANITY RECORDS &nbsp;&nbsp;</input><input name ='label_selector' style='height:10px' type='checkbox' value='L20'>MINISTRY OF SOUND &nbsp;&nbsp;</input></font></form></div><br>
<div id='calendar' > 


</div>
</body>

</html>