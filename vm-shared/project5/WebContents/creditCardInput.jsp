<% String URL = "https://" + request.getServerName()+":8443"+request.getContextPath()+"/confirmation"; %>
<html>
    <head> 
        <title>Payment input</title>
    </head>
	<body>
        <h2>Enter credit card information</h2>
        <form method="POST" action="<%= URL %>">
            <ul>
                <li>Item ID: <%= request.getAttribute("itemID") %></li>
                <li>Name: <%= request.getAttribute("itemName") %></li>
                <li>Buy Price: <%= request.getAttribute("buyPrice") %></li>      
            </ul>
            <p>Enter Credit Card no.:
                <input type="text" name="creditCardNum" />
                <input type="submit" value="Submit" /> 
            </p>
        </form>
	</body>
</html>
