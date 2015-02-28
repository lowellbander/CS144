<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
    <title>Search Results</title>
</head>
<body>
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
            <td><a href="/eBay/item?ItemID=1049430907"><c:out value="${results[index].getItemId()}"/></a></td>
            <td><c:out value="${results[index].getName()}"/></td>
         </tr>
      </c:forEach>     
    </table>
    
</body>
</html>
