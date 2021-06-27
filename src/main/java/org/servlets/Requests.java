package org.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Requests extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (req.getSession().getAttribute("email") == null) {
      resp.sendRedirect(req.getContextPath() + "/login");
    } else {
      req.getRequestDispatcher("/requests.jsp").forward(req, resp);
    }
  }
}
