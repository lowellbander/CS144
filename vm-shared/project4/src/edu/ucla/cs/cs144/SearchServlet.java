package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet implements Servlet {
       
    public SearchServlet() {}

    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) 
        throws ServletException, IOException {
        // your codes here
        // TODO: extract the value of the parameters q, numResultsToSkip, 
        // and numResultsToReturn from the request and 
        // TODO: retrieve the matching results from the eBay data.
        
        String pageTitle = "the page title";
        request.setAttribute("title", pageTitle);

        String query = "i'm the best query ever.";
        request.setAttribute("q", query);

        request.setAttribute("numResultsToSkip", "nskips");
        request.setAttribute("numResultsToReturn", "nreturns");


        request.getRequestDispatcher("/searchResults.jsp")
               .forward(request, response);

    }
}
