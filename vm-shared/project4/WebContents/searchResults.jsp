<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <a href="<%= request.getAttribute("prevURL") %>">Previous</a>
    <a href="<%= request.getAttribute("nextURL") %>">Next</a>

    <form action="/eBay/search">
      Search Term: <input type="text" name="q">
      <input type='hidden' name='numResultsToSkip' value='0' />
      <input type='hidden' name='numResultsToReturn' value='20' />
      <input type="submit" value="Submit">
    </form>


    <p>Your results for query: <%= request.getAttribute("q") %></p>
    <p>The nskips: <%= request.getAttribute("numResultsToSkip") %></p>
    <p>The nresults: <%= request.getAttribute("numResultsToReturn") %></p>
    <table border="1">
        <tr> 
            <td>Item ID</td>
            <td>Item Name</td>
        </tr>
      <c:forEach begin="0" end="${fn:length(results) - 1}" var="index">
         <tr>
            <td><a href="/eBay/item?ItemID=<c:out value="${results[index].getItemId()}"/>"><c:out value="${results[index].getItemId()}"/></a></td>
            <td><c:out value="${results[index].getName()}"/></td>
         </tr>
      </c:forEach>     
    </table>
    
</body>
</html>
