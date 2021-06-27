package org.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.Utils;
import org.RestModels.LoginCredentials;
import org.RestModels.LoginResponse;

public class Api extends HttpServlet {
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    if (Utils.apiEndPointMatch(req, "login")) {
      String body = Utils.readReqBody(req);
      ObjectMapper mapper = new ObjectMapper();
      LoginCredentials loginCredentials = mapper.readValue(body, LoginCredentials.class);
      LoginResponse loginResponse = new LoginResponse();
      if (loginCredentials.getEmail().equals("a@a.com") && loginCredentials.getPassword().equals("a")) {
        loginResponse.setIsSuccess(true);
        loginResponse.setMsg("Login Success");
      } else {
        loginResponse.setIsSuccess(false);
        loginResponse.setMsg("Invalid email or password");
      }
      out.print(mapper.writeValueAsString(loginResponse));
    }
    out.close();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    if (Utils.apiEndPointMatch(req, "requestsTable")) {
      out.print("{\"foo\":\"bar\"}");
    }
    out.close();
  }
}
