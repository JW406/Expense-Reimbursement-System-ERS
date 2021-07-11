package org.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.HttpServletRequestUtils;
import org.RestModels.Request.EmployeeChangeManagerRequest;
import org.RestModels.Request.LoginCredentialsRequest;
import org.RestModels.Request.PasswordChangeRequest;
import org.RestModels.Request.RegisterCredentialsRequest;
import org.RestModels.Request.SendReimbursementRequest;
import org.RestModels.Request.SubmitReimbursementUpdateRequest;
import org.RestModels.Request.UpdateAccountInfoRequest;
import org.RestModels.Response.LoginResponse;
import org.RestModels.Response.SimpleResponse;
import org.models.Manager;
import org.models.Person;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;
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

    if (HttpServletRequestUtils.apiEndPointMatch(req, "login")) { // "login"
      String body = HttpServletRequestUtils.readReqBody(req);
      LoginCredentialsRequest lc = mapper.readValue(body, LoginCredentialsRequest.class);
      LoginResponse loginResponse = new LoginResponse();
      Person p = accServ.loginAccount(lc);
      if (p != null) {
        loginResponse.setIsSuccess(true);
        loginResponse.setMsg("Login Success");
        loginResponse.setUsername(p.getName());
        loginResponse.setIsManager(p instanceof Manager);
        req.getSession().setAttribute("email", p.getEmail());
        req.getSession().setAttribute("ismanager", loginResponse.getIsManager());
      } else {
        loginResponse.setIsSuccess(false);
        loginResponse.setMsg("Invalid email or password");
      }
      out.print(mapper.writeValueAsString(loginResponse));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "register")) { // "register"
      String body = HttpServletRequestUtils.readReqBody(req);
      RegisterCredentialsRequest rc = mapper.readValue(body, RegisterCredentialsRequest.class);
      SimpleResponse response = new SimpleResponse();
      if (accServ.registerAccount(rc)) {
        response.setIsSuccess(true);
        response.setMsg("Register Success");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Register failed");
      }
      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "submit-request")) { // submit-request
      String body = HttpServletRequestUtils.readReqBody(req);
      SendReimbursementRequest rr = mapper.readValue(body, SendReimbursementRequest.class);
      SimpleResponse response = new SimpleResponse();
      if (reimServ.employeeSendReimbursementRequest(rr, (String) req.getSession().getAttribute("email")) != null) {
        response.setIsSuccess(true);
        response.setMsg("Reimbursement request sent");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Reimbursement request not sent");
      }
      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "request-update")) { // request-update
      String body = HttpServletRequestUtils.readReqBody(req);
      SubmitReimbursementUpdateRequest rr = mapper.readValue(body, SubmitReimbursementUpdateRequest.class);
      SimpleResponse response = new SimpleResponse();
      if (reimServ.updateReimbursementRequest(rr, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg("Reimbursement request update successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Reimbursement request update failed");
      }
      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "info-update")) { // info-update
      String body = HttpServletRequestUtils.readReqBody(req);
      UpdateAccountInfoRequest updateAccountInfo = mapper.readValue(body, UpdateAccountInfoRequest.class);
      SimpleResponse response = new SimpleResponse();

      if (accServ.updateAccountInfo(updateAccountInfo, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg("Account information update has been successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Account information update failed");
      }

      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "password-change")) { // password-change
      String body = HttpServletRequestUtils.readReqBody(req);
      PasswordChangeRequest passwordChangeRequest = mapper.readValue(body, PasswordChangeRequest.class);
      SimpleResponse response = new SimpleResponse();

      if (accServ.updateAccountPassword(passwordChangeRequest, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg("Account password change has been successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Account password change failed");
      }

      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "manager-actions")) { // manager-actions
      String body = HttpServletRequestUtils.readReqBody(req);
      SubmitReimbursementUpdateRequest rr = mapper.readValue(body, SubmitReimbursementUpdateRequest.class);
      SimpleResponse response = new SimpleResponse();

      if (reimServ.managerAcceptReimbursementRequest(rr, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg(rr.getState() + " change has been successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg(rr.getState() + " change failed");
      }

      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "manager-update-request-comment")) { // manager-update-request-comment
      String body = HttpServletRequestUtils.readReqBody(req);
      SubmitReimbursementUpdateRequest rr = mapper.readValue(body, SubmitReimbursementUpdateRequest.class);
      SimpleResponse response = new SimpleResponse();

      if (reimServ.managerReimbursementRequestComment(rr, (String) req.getSession().getAttribute("email"))) {
        response.setIsSuccess(true);
        response.setMsg("Comment update has been successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Comment update failed");
      }

      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "employee-change-manager")) { // employee-change-manager
      String body = HttpServletRequestUtils.readReqBody(req);
      EmployeeChangeManagerRequest rr = mapper.readValue(body, EmployeeChangeManagerRequest.class);
      SimpleResponse response = new SimpleResponse();

      if (accServ.employeeChangeManager((String) req.getSession().getAttribute("email"), rr)) {
        response.setIsSuccess(true);
        response.setMsg("Manager change has been successful");
      } else {
        response.setIsSuccess(false);
        response.setMsg("Manager change failed");
      }

      out.print(mapper.writeValueAsString(response));

    }

    out.close();
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    PrintWriter out = resp.getWriter();
    ObjectMapper mapper = new ObjectMapper();

    if (HttpServletRequestUtils.apiEndPointMatch(req, "requestsTable")) { // requestsTable
      ReimbursementState state = ReimbursementState.valueOf(req.getParameter("state"));
      List<ReimbursementRequest> response = reimServ
          .getReimbursementRequestsByLoggedInEmail((String) req.getSession().getAttribute("email"), state);
      out.print(mapper.writeValueAsString(response));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "logout")) { // logout
      req.getSession().removeAttribute("email");

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "get-accountinfo")) { // get-accountinfo
      Person person = accServ.getPersonRecordByEmail((String) req.getSession().getAttribute("email"));
      out.print(mapper.writeValueAsString(person));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "getManagedEmployeeRequests")) { // getManagedEmployeeRequests
      if ((Boolean) req.getSession().getAttribute("ismanager")) {
        ReimbursementState state = ReimbursementState.valueOf(req.getParameter("state"));
        List<ReimbursementRequest> response = reimServ
            .getManagedEmployeeRequests((String) req.getSession().getAttribute("email"), state);
        out.print(mapper.writeValueAsString(response));
      }

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "getManagedEmployees")) { // getManagedEmployees
      out.print(
          mapper.writeValueAsString(accServ.getAllEmployeesByManager((String) req.getSession().getAttribute("email"))));

    } else if (HttpServletRequestUtils.apiEndPointMatch(req, "get-all-managers")) { // get-all-managers
      if (req.getSession().getAttribute("email") == null) {
        out.print(mapper.writeValueAsString(new ArrayList<>()));
      } else {
        out.print(mapper.writeValueAsString(accServ.getAllManagers()));
      }
    }

    out.close();
  }
}
