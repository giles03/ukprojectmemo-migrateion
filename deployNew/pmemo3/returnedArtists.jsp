<!DOCTYPE html>
<html>
<head>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
  <script src="js/jquery-1.7.1.min.js"></script>
  <script src="js/jquery-ui-1.8.17.custom.min.js"></script>
  <style>
	.ui-autocomplete {
		max-height: 200px;
		overflow-y: auto;
		/* prevent horizontal scrollbar */
		overflow-x: hidden;
		/* add padding to account for vertical scrollbar */
		padding-right: 20px;
	}
  </style>
  <script>
  
  function monkeyPatchAutocomplete() {
 
          // Don't really need to save the old fn, 
          // but I could chain if I wanted to
          var oldFn = $.ui.autocomplete.prototype._renderItem;
 
          $.ui.autocomplete.prototype._renderItem = function( ul, item) {
              var re = new RegExp("^" + this.term, "i") ;             
              var t = item.label.replace(re,"<span style='font-weight:bold;color:Blue;'>" + this.term + "</span>");
              t.replace(/\ArtistId:+\d+/, "");
              return $( "<li></li>" )
                  .data( "item.autocomplete", item )
                  .append( "<a>" +t.replace(/\ArtistId:+\d+/, "") + "</a>" )
                  .appendTo( ul );
          };
      }
  
  
  function populateHiddenField(id){
  
  document.getElementById("hiddenArtistId").value=id;
  
  }
  
  
 $(function() {
			var availableTags = "pmemo/matchingArtists.jsp";
				function split( val ) {
			return val.split( /,\s*/ );
		}
		function extractLast( term ) {
			return split( term ).pop();
		}
		
		
 monkeyPatchAutocomplete();			
			$( "#autocomplete" ).autocomplete({
					source: "/pmemo3/matchingArtists.jsp",
						minLength: 1,
				        focus: function( event, ui ) {             
				        	$( "#autocomplete" ).val( ui.item.value );             
				        	return false;         
				        }, 
				
				
				
				
						select: function( event, ui ) {
 							$( "#autocomplete" ).val( ui.item.value ); 
							return false;
						}
						
						
						
						
						/*
						,
						close: function(event, ui) {
							var numberOnly = $(this).val().match(/\d+/);
							$(this).val(numberOnly);
						}
						*/
					})
					
    		.data( "autocomplete" )._renderItem = function( ul, item ) {         
    		return $( "<li></li>" )             
    			.data( "item.autocomplete", item )             
    			.append( "<a>" + item.value + "</a>" )             
    			.appendTo( ul );     
   			}; 
});


</script>
  

</head>
<body style="font-size:62.5%;">
  
<input id="autocomplete" width="200px"/>
<input type="hidden" id="hiddenArtistId" /> 

</body>
</html>
