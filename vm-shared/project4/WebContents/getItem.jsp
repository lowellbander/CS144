<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
	<head>
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300' rel='stylesheet' type='text/css'>
		<title>Item Details</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
        <meta charset="utf-8">

        <!--CSS Styling -->
        <style>
          html, body, #map-canvas {
              height: 100%;
              margin: 0px;
              padding: 0px
          }
          #panel {
              position: absolute;
              top: 5%;
              left: 5%;
              margin-left: 0px;
              z-index: 5;
              background-color: #fff;
              padding: 10px;
              border: 1px solid #999;
              width: 30%;
          }
          body{
            font-family: 'Open Sans', sans-serif;
          }

        </style>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true"></script>
    <script>
      var geocoder;
      var map;
      function initialize() {
        geocoder = new google.maps.Geocoder();
        var latlng = new google.maps.LatLng(-34.397, 150.644);
        var mapOptions = {
          zoom: 2,
          center: latlng
        }
        map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);
      }

      function codeAddress() {
        var address = document.getElementById('address').value;
        // var address = <%=(String)request.getAttribute("location")%>
        geocoder.geocode( { 'address': address}, function(results, status) {
          if (status == google.maps.GeocoderStatus.OK) {
            map.setCenter(results[0].geometry.location);
            map.setZoom(8);
            var marker = new google.maps.Marker({
                map: map,
                position: results[0].geometry.location
            });
          } else {
            //alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      }

      google.maps.event.addDomListener(window, 'load', initialize);
    </script>
	</head>
	<body>
    <div id="panel">
  		<form action="/eBay/search">
  		  <input type="text" name="q" placeholder="Enter search query...">
        <input type='hidden' name='numResultsToSkip' value='0' />
  		  <input type='hidden' name='numResultsToReturn' value='20' />
  		  <input type="submit" value="Search" onclick="codeAddress()">
        <br>
        <input id="address" type="textbox" value="UCLA">
        <input type="button" value="Load map" onclick="codeAddress()">
  		</form>
  		<h2>Item details:</h2>
        <p> ItemID: <%= request.getAttribute("itemid")%></p>
        <p> Name: <%= request.getAttribute("name")%></p>
        <p> Categories: <%= request.getAttribute("categories")%></p>
        <p> First Bid: <%= request.getAttribute("first bid")%></p>
        <p> Number of Bids: <%= request.getAttribute("Number of Bids")%></p>
        <p> Location: <%= request.getAttribute("Location")%></p>
        <p> Country: <%= request.getAttribute("Country")%></p>
        <p> Started: <%= request.getAttribute("Started")%></p>
        <p> Ends: <%= request.getAttribute("Ends")%></p>
        <p> Seller Rating: <%= request.getAttribute("seller rating")%></p>
        <p> Seller ID: <%= request.getAttribute("seller id")%></p>
        <p> Description: <%= request.getAttribute("description")%></p>
        <!-- <p> XML: <%= request.getAttribute("xml")%></p> -->
        <table border="1">
          <tr>
            <td>Bidder Rating</td>
            <td>Bidder ID</td>
            <td>Location</td>
            <td>Country</td>
            <td>Time</td>
            <td>Amount</td>
          </tr>
          <c:forEach begin="0" end="${fn:length(results)}" var="index">
            <tr>
              <td>dummy</td>
              <td>dummy</td>
              <td>dummy</td>
              <td>dummy</td>
              <td>dummy</td>
              <td>dummy</td>
            </tr>
          </c:forEach>
        </table>

    </div>
    <div id="map-canvas"></div>
	</body>
</html>
