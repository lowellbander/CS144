<html>
	<head>
		<title>Item Details</title>
	</head>
	<body>
		<form action="/eBay/search">
		  Search Term: <input type="text" name="q"> <br>
		  Number of Results to Skip:   <input type="text" name="numResultsToSkip"> <br>
		  Number of Results to Return: <input type="text" name="numResultsToReturn"> <br>
		  <input type="submit" value="Submit">
		</form>
		<h1>Here are the Item Details</h1>
        <p> Here is the XML: <pre><%= request.getAttribute("xml")%></pre></p>
	</body>
</html>
