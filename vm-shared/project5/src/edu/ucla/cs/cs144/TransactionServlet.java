package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.Servlet;
import javax.servlet.http.*;

public class TransactionServlet extends HttpServlet implements Servlet {
  public TransactionServlet() {}
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                                                             IOException {

    // DO STUFF
    HttpSession session = request.getSession(true);
    String itemID = (String)session.getAttribute("itemID");
    String name = (String)session.getAttribute("itemName");
    String buyPrice = (String)session.getAttribute("buyPrice");

    request.setAttribute("itemID", itemID);
    request.setAttribute("Name", name);
    request.setAttribute("buyPrice", buyPrice);
    request.getRequestDispatcher("/creditCardInput.jsp")
           .forward(request, response);
  }
}
