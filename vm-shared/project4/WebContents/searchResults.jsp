<html>
<head>
    <title><%= request.getAttribute("title") %></title>
</head>
<body>
    <p>Hello, world.</p>
    <p>The query: <%= request.getAttribute("q") %></p>
    <p>The nskips: <%= request.getAttribute("numResultsToSkip") %></p>
    <p>The nresults: <%= request.getAttribute("numResultsToReturn") %></p>
    <p>The results: <%= request.getAttribute("results") %></p>
</body>
</html>
