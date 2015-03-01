package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ucla.cs.cs144.SearchResult;
import edu.ucla.cs.cs144.AuctionSearchClient;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
        throws ServletException, IOException {
        
        request.setAttribute("q", request.getParameter("q"));
        // TODO: deprecate
        request.setAttribute("numResultsToSkip", 
                                request.getParameter("numResultsToSkip"));
        request.setAttribute("numResultsToReturn", 
                                request.getParameter("numResultsToReturn"));

        // get query parameters from the form
        String query = request.getParameter("q");
        String numResultsToSkip = request.getParameter("numResultsToSkip");
        String numResultsToReturn = request.getParameter("numResultsToReturn");

        // build nav links
        String prevSkip = 
            Integer.toString(Integer.parseInt(numResultsToSkip) - 20);

        String prevURL = "/eBay/search?q=" + query + 
                    "&numResultsToSkip=" + prevSkip +
                    "&numResultsToReturn=20";
        request.setAttribute("prevURL", prevURL);

        String nextSkip = 
            Integer.toString(Integer.parseInt(numResultsToSkip) + 20);

        String nextURL = "/eBay/search?q=" + query + 
                    "&numResultsToSkip=" + nextSkip +
                    "&numResultsToReturn=20";
        request.setAttribute("nextURL", nextURL);

        // retrieve corresponding results
        
        AuctionSearchClient client = new AuctionSearchClient();
        SearchResult[] results = 
            client.basicSearch(query, 
                                Integer.parseInt(numResultsToSkip), 
                                Integer.parseInt(numResultsToReturn));

        String ItemID = results[0].getItemId();

        request.setAttribute("ItemID", ItemID);
        request.setAttribute("results", results);

        request.getRequestDispatcher("/searchResults.jsp")
               .forward(request, response);

    }
}
