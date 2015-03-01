<html>
	<head>
		<title>Item Details</title>
	</head>
	<body>
		<form action="/eBay/search">
		  Do another search: <input type="text" name="q">
		  <input type='hidden' name='numResultsToSkip' value='0' />
		  <input type='hidden' name='numResultsToReturn' value='20' />
		  <input type="submit" value="Search">
		</form>
		<h1>Here are the Item Details</h1>
        <p> Here is the XML: <pre><%= request.getAttribute("xml")%></pre></p>
	</body>
</html>
