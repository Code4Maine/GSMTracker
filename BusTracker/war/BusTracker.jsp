<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Gurukul Bus Tracking</title>
        <link rel="stylesheet" type="text/css" href="css/main.css" />
        <meta charset="utf-8">
        <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script> 
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js"></script> 
        <script type="text/javascript">
            var map;
            var geocoder;
            var initialLocation = new google.maps.LatLng(25.13086,75.84264); 
            function init() {
            	geocoder = new google.maps.Geocoder(); 
                var duckOptions = {
                    zoom: 12,
                    center: initialLocation,
                    mapTypeId: google.maps.MapTypeId.TERRAIN
                };
                map = new google.maps.Map(document.getElementById("map_canvas"), duckOptions); 
                var marker = new google.maps.Marker({ 
                    position: initialLocation, 
                    map: map
                });
                
               
                google.maps.event.addListener(map, 'click', function(event) {
                	placeMarker(event.latLng);
            	});
            	
            	var flightPlanCoordinates = [
            		new google.maps.LatLng(25.13086,75.84264),
    				new google.maps.LatLng(25.131765, 75.855519),
    				new google.maps.LatLng(25.125393, 75.854708),
    				new google.maps.LatLng(25.119953, 75.859553),
    				new google.maps.LatLng(25.070673, 75.864664),
   					new google.maps.LatLng(25.065697, 75.844751)
  				];
  				
  				var flightPath = new google.maps.Polyline({
    				path: flightPlanCoordinates,
    				strokeColor: "#FF0000",
    				strokeOpacity: 1.0,
    				strokeWeight: 2
  				});

  				flightPath.setMap(map);
            }
            
 		function placeMarker(location) {
    		var marker = new google.maps.Marker({
        		position: location, 
        		map: map
    		});
    		
 			$("#longitude").val(location.lng());  
        	$("#latitude").val(location.lat());
       		
       		geocoder.geocode( { 'latLng': location}, function(results, status) {
            	if (status == google.maps.GeocoderStatus.OK) {
                	$("#markerAddress").val(results[0].formatted_address);
            	} else {
                	alert("Geocode was not successful for the following reason: " + status);
            	}
        	});
        
  			map.setCenter(location);

		}
        </script>
    </head>
    <body  onload="init()"> 
        <h1>Add Bus Tracking Points</h1>
        <div id="map_canvas"></div>
    	<div id="marker_data">
        	<form id="createForm" action="/newBusLocation" method="post" accept-charset="utf-8">
            	<table>
                	<tr>
                    	<td>Title</td>
                    	<td><input type="text" name="title" id="title" size="66"/></td>
                	</tr>
                	<tr>
                    	<td>Description</td>
                    	<td><textarea rows="4" cols="50" name="description" id="description"></textarea>
                	</tr>
                	<tr>
                    	<td>Latitude</td>
                    	<td><input type="text" name="latitude" id="latitude" size="66" /></td>
                	</tr>
                	<tr>
                    	<td>Longitude</td>
                    	<td><input type="text" name="longitude" id="longitude" size="66" /></td>
                	</tr>
                	<tr>
                    	<td>Address</td>
                    	<td><input type="text" name="markerAddress" id="markerAddress" size="66" /></td>
                	</tr>
            	</table><br/><br/>
            	<input type="submit" value="Save"/>
        	</form>
    	</div> 
    	
    </body>
</html>