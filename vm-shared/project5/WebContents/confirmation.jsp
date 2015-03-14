<html>
	<title>Confirmation</title>
    <body>
	    <h2>Confirmation</h2>
        	<ul>
        		<li>Item ID: <%= request.getAttribute("itemID") %></li>
             	<li>Name: <%= request.getAttribute("itemName") %></li>
                <li>Buy Price: <%= request.getAttribute("buyPrice") %></li> 
                <li>Credit Card No.: <%= request.getAttribute("creditCardNum") %></li>
                <li>Date &amp; Time: <%= request.getAttribute("dateTime") %></li>
            </ul>
    </body>
</html>
