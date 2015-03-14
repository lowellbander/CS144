package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.Servlet;
import javax.servlet.http.*;
import java.text.DateFormat;
import java.util.Date;

public class ConfirmationServlet extends HttpServlet implements Servlet {
  public ConfirmationServlet() {}
  protected void doPost (HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                                                             IOException {

    // DO STUFF
    HttpSession session = request.getSession(true);
    String itemID = (String)session.getAttribute("itemID");
    String name = (String)session.getAttribute("itemName");
    String buyPrice = (String)session.getAttribute("buyPrice");
    String creditCardNum = (String)request.getParameter("creditCardNum");
    Date now = new Date();

    request.setAttribute("itemID", itemID);
    request.setAttribute("itemName", name);
    request.setAttribute("buyPrice", buyPrice);
    request.setAttribute("creditCardNum", creditCardNum);
    request.setAttribute("dateTime", DateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.LONG).format(now));

    request.getRequestDispatcher("/confirmation.jsp")
           .forward(request, response);
  }
}
