<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<html>
<head>
    <title>Search Results</title>
    <style type="text/css">
          div {
            display: block;
            margin-left: 30%;
            /*margin-right: 30%;*/
          }
          form {
            float: right;
          }
          p {
            text-align: center;
          }
    </style>
</head>
<body>
    <a href="<%= request.getAttribute("prevURL") %>">Previous</a> |
    <a href="<%= request.getAttribute("nextURL") %>">Next</a>

    <form action="/eBay/search">
      Do another search: <input type="text" name="q">
      <input type='hidden' name='numResultsToSkip' value='0' />
      <input type='hidden' name='numResultsToReturn' value='20' />
      <input type="submit" value="Search">
    </form>

    <p>Your results for query: <b><%= request.getAttribute("q") %></b></p>
    <div>
      <table border="1">
          <tr> 
              <td>Item ID</td>
              <td>Item Name</td>
          </tr>
        <c:forEach begin="0" end="${fn:length(results)}" var="index">
           <tr>
              <td><a href="/eBay/item?ItemID=<c:out value="${results[index].getItemId()}"/>"><c:out value="${results[index].getItemId()}"/></a></td>
              <td><c:out value="${results[index].getName()}"/></td>
           </tr>
        </c:forEach>     
      </table>  
    </div>
    
    
</body>
</html>
