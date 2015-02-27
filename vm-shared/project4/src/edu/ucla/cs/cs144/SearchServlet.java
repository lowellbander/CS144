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
        
        // TODO: retrieve the matching results from the eBay data.
        
        // TODO: Deprecate.
        String pageTitle = "the page title";
        request.setAttribute("title", pageTitle);

        request.setAttribute("q", request.getParameter("q"));
        request.setAttribute("numResultsToSkip", 
                                request.getParameter("numResultsToSkip"));
        request.setAttribute("numResultsToReturn", 
                                request.getParameter("numResultsToReturn"));

        // get query parameters from the form
        String query = request.getParameter("q");
        String numResultsToSkip = request.getParameter("numResultsToSkip");
        String numResultsToReturn = request.getParameter("numResultsToReturn");

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
