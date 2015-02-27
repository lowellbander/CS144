<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
    <title><%= request.getAttribute("title") %></title>
</head>
<body>
    <p>Hello, world.</p>
    <p>The query: <%= request.getAttribute("q") %></p>
    <p>The nskips: <%= request.getAttribute("numResultsToSkip") %></p>
    <p>The nresults: <%= request.getAttribute("numResultsToReturn") %></p>
    <p>The ItemID: <%= request.getAttribute("ItemID") %></p>
    <c:forEach begin="0" end="${fn:length(results) - 1}" var="index">
       <tr>
          <td><c:out value="${results[index].getItemId()}"/></td>
          <td><c:out value="${results[index].getName()}"/></td>
       </tr>
    </c:forEach>   
</body>
</html>
