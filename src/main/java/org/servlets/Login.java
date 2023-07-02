package org.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.models.Employee;
import org.services.Service;

public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Service.registerEmployee();
    Employee m1 = Service.getEntityRecordById(Employee.class, 1);
    Employee m2 = Service.getEntityRecordById(Employee.class, 2);
    Service.employeeFileReimRequest(m1, 2666.0);
    Service.employeeFileReimRequest(m2, 3666.0);
    req.getRequestDispatcher("/login.jsp").forward(req, resp);
  }
}
