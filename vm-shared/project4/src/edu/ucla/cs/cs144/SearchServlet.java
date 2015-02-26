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
        
        // TODO: retrieve the matching results from the eBay data.
        
        // TODO: Deprecate.
        String pageTitle = "the page title";
        request.setAttribute("title", pageTitle);

        request.setAttribute("q", request.getParameter("q"));
        request.setAttribute("numResultsToSkip", 
                                request.getParameter("numResultsToSkip"));
        request.setAttribute("numResultsToReturn", 
                                request.getParameter("numResultsToReturn"));


        request.getRequestDispatcher("/searchResults.jsp")
               .forward(request, response);

    }
}
