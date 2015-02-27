package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        AuctionSearchClient client = new AuctionSearchClient();
        String itemID = request.getParameter("ItemID");
        String xml = client.getXMLDataForItemId(itemID);

        request.setAttribute("xml", xml);

        request.getRequestDispatcher("/getItem.jsp").forward(request,response);
        
    }
}
