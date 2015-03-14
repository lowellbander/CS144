package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.Servlet;
import javax.servlet.http.*;

public class ConfirmationServlet extends HttpServlet implements Servlet {
  public ConfirmationServlet() {}
  protected void doGet (HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
                                                             IOException {

    // DO STUFF

    request.getRequestDispatcher("/confirmation.jsp")
           .forward(request, response);
  }
}
