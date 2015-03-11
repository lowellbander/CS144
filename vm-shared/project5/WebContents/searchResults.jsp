<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 

<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
    <style type="text/css">
          div {
            display: block;
            margin-left: 30%;
          }
          form {
            float: right;
          }
          p {
            text-align: center;
          }
    </style>
    <script type="text/javascript" src="suggestionsProvider.js"></script>
    <script type="text/javascript" src="autosuggest.js"></script>
    <link rel="stylesheet" type="text/css" href="autosuggest.css" />
    <script type="text/javascript">
      window.onload=function(){
        var oTextbox = new AutoSuggestControl(document.getElementById("queryText"), new suggestionsProvider());
;      }
    </script>
</head>
<body>
    <a href="<%= request.getAttribute("prevURL") %>">Previous</a> |
    <a href="<%= request.getAttribute("nextURL") %>">Next</a>
    
    <form action="/eBay/search">
      Do another search: <input type="text" name="q" id="queryText">
      <input type='hidden' name='numResultsToSkip' value='0' />
      <input type='hidden' name='numResultsToReturn' value='20' />`
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
