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
import org.RestModels.RegisterCredentials;
import org.RestModels.SubmitReimbursementRequest;
import org.RestModels.Response;
import org.models.Person;
import org.services.Impl.AccountServicesImpl;
import org.services.Impl.ReimbursementServiceImpl;
import org.services.Interface.AccountServices;
import org.services.Interface.ReimbursementService;

public class Api extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final AccountServices accServ = new AccountServicesImpl();
  private static final ReimbursementService reimServ = new ReimbursementServiceImpl();

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    ObjectMapper mapper = new ObjectMapper();

    if (Utils.apiEndPointMatch(req, "login")) {
      String body = Utils.readReqBody(req);
      LoginCredentials lc = mapper.readValue(body, LoginCredentials.class);
      LoginResponse loginResponse = new LoginResponse();
      Person p = accServ.loginAccount(lc);
      if (p != null) {
        loginResponse.setIsSuccess(true);
        loginResponse.setMsg("Login Success");
        loginResponse.setUsername(p.getName());
        req.getSession().setAttribute("email", p.getEmail());
      } else {
        loginResponse.setIsSuccess(false);
        loginResponse.setMsg("Invalid email or password");
      }
      out.print(mapper.writeValueAsString(loginResponse));

    } else if (Utils.apiEndPointMatch(req, "register")) {
      String body = Utils.readReqBody(req);
      RegisterCredentials rc = mapper.readValue(body, RegisterCredentials.class);
      Response response = new Response();
      if (accServ.registerAccount(rc)) {
        response.setIsSuccess(true);
        response.setMsg("Register Success");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Register failed");
      }
      out.print(mapper.writeValueAsString(response));

    } else if (Utils.apiEndPointMatch(req, "submit-request")) {
      String body = Utils.readReqBody(req);
      SubmitReimbursementRequest rr = mapper.readValue(body, SubmitReimbursementRequest.class);
      Response response = new Response();
      if (reimServ.acceptReimbursementRequest(rr, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg("Reimbursement request sent");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Reimbursement request not sent");
      }
      out.print(mapper.writeValueAsString(response));
    }

    out.close();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    if (Utils.apiEndPointMatch(req, "requestsTable")) {
      out.print("{\"foo\":\"bar\"}");
    } else if (Utils.apiEndPointMatch(req, "logout")) {
      req.getSession().removeAttribute("email");
    }
    out.close();
  }
}
