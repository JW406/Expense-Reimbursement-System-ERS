package org.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.RestModels.EmployeeChangeManagerRequest;
import org.RestModels.LoginCredentials;
import org.RestModels.PasswordChangeRequest;
import org.RestModels.RegisterCredentials;
import org.RestModels.SendReimbursementRequest;
import org.RestModels.SubmitReimbursementUpdateRequest;
import org.RestModels.UpdateAccountInfo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.models.Employee;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;
import org.services.Impl.AccountServicesImpl;
import org.services.Impl.ReimbursementServiceImpl;
import org.services.Interface.AccountServices;
import org.services.Interface.ReimbursementService;

public class ServicesTest {
  private static AccountServices accSrv = new AccountServicesImpl();
  private static ReimbursementService reimSrv = new ReimbursementServiceImpl();
  private static String managerEmail = "testMan@qq.com";
  private static String employeeEmail = "testEm@qq.com";

  @BeforeAll
  public static void init() {
    RegisterCredentials managerCredentials = new RegisterCredentials();
    managerCredentials.setEmail(managerEmail);
    managerCredentials.setIsManager(true);
    managerCredentials.setPassword("123");
    accSrv.registerAccount(managerCredentials);
    RegisterCredentials employeeCredentials = new RegisterCredentials();
    employeeCredentials.setEmail(employeeEmail);
    employeeCredentials.setIsManager(false);
    employeeCredentials.setPassword("123");

    accSrv.registerAccount(managerCredentials);
    accSrv.registerAccount(employeeCredentials);

    EmployeeChangeManagerRequest employeeChangeManagerRequest = new EmployeeChangeManagerRequest();
    employeeChangeManagerRequest.setManagerEmail(managerEmail);
    accSrv.employeeChangeManager(employeeEmail, employeeChangeManagerRequest);
  }

  @AfterAll
  public static void tear() {
    accSrv.deleteAnAccount(employeeEmail);
    accSrv.deleteAnAccount(managerEmail);
  }

  @Test
  void testUserLogIn() {
    LoginCredentials loginCredentials = new LoginCredentials();
    loginCredentials.setEmail(employeeEmail);
    loginCredentials.setPassword("123");
    assertNotNull(accSrv.loginAccount(loginCredentials));
  }

  @Test
  void testEmployeeSendAReimbursementRequestAndManagerApprove() {
    // employee send an request
    SendReimbursementRequest sendReimbursementRequest = new SendReimbursementRequest();
    sendReimbursementRequest.setRequestAmnt(1000.0);
    sendReimbursementRequest.setTimestamp(new Date(System.currentTimeMillis()).getTime());
    Integer id = reimSrv.employeeSendReimbursementRequest(sendReimbursementRequest, employeeEmail);
    assertNotNull(id);

    // manager approve an request, and submit a comment on an approved request
    SubmitReimbursementUpdateRequest submitReimbursementUpdateRequest1 = new SubmitReimbursementUpdateRequest();
    submitReimbursementUpdateRequest1.setId(id);
    submitReimbursementUpdateRequest1.setPayload("Hello World");
    reimSrv.managerReimbursementRequestComment(submitReimbursementUpdateRequest1, managerEmail);

    SubmitReimbursementUpdateRequest submitReimbursementUpdateRequest2 = new SubmitReimbursementUpdateRequest();
    submitReimbursementUpdateRequest2.setId(id);
    submitReimbursementUpdateRequest2.setState(ReimbursementState.approved);
    assertTrue(reimSrv.managerAcceptReimbursementRequest(submitReimbursementUpdateRequest2, managerEmail));
    List<ReimbursementRequest> managedEmpreimbursementRequests = reimSrv.getManagedEmployeeRequests(managerEmail,
        ReimbursementState.approved);
    // assert manager can see one requests from their dashboard
    assertTrue(managedEmpreimbursementRequests.size() == 1);
    assertTrue(managedEmpreimbursementRequests.get(0).getMgrComment().equals("Hello World"));

    // assert employees can see their requests
    int id2 = reimSrv.employeeSendReimbursementRequest(sendReimbursementRequest, employeeEmail);
    SubmitReimbursementUpdateRequest submitReimbursementUpdateRequest3 = new SubmitReimbursementUpdateRequest();
    submitReimbursementUpdateRequest3.setId(id2);
    submitReimbursementUpdateRequest3.setState(ReimbursementState.recalled);
    reimSrv.updateReimbursementRequest(submitReimbursementUpdateRequest3, employeeEmail);

    List<ReimbursementRequest> reimbursementRequestsByLoggedInUser = reimSrv.getReimbursementRequestsByLoggedInEmail(employeeEmail, ReimbursementState.recalled);
    assertTrue(reimbursementRequestsByLoggedInUser.size() == 1);
    assertTrue(reimbursementRequestsByLoggedInUser.get(0).getState().equals(ReimbursementState.recalled));

    reimSrv.deleteAnRequest(id);
    reimSrv.deleteAnRequest(id2);
  }

  @Test
  void testUserUpdateInfo() {
    UpdateAccountInfo updateAccountInfo = new UpdateAccountInfo();
    updateAccountInfo.setFullName("helloworld");
    updateAccountInfo.setGitHubUsername("helloworld2");
    updateAccountInfo.setPhoneNumber("999999");
    accSrv.updateAccountInfo(updateAccountInfo, employeeEmail);
    Employee em = (Employee) accSrv.getPersonRecordByEmail(employeeEmail);
    assertEquals(em.getName(), updateAccountInfo.getFullName());
    assertEquals(em.getGitHubAddress(), updateAccountInfo.getGitHubUsername());
    assertEquals(em.getPhoneNumber(), updateAccountInfo.getPhoneNumber());
  }

  @Test
  void testUserChangePassword() {
    PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest();
    passwordChangeRequest.setOldPassword("123");
    passwordChangeRequest.setNewPassword("456");
    passwordChangeRequest.setNewPassword2("456");
    accSrv.updateAccountPassword(passwordChangeRequest, employeeEmail);
    Employee em = (Employee) accSrv.getPersonRecordByEmail(employeeEmail);
    assertEquals(em.getPassword(), passwordChangeRequest.getNewPassword2());
  }

  @Test
  void testUserChangeManager() {
    EmployeeChangeManagerRequest employeeChangeManagerRequest = new EmployeeChangeManagerRequest();
    employeeChangeManagerRequest.setManagerEmail(managerEmail);
    accSrv.employeeChangeManager(employeeEmail, employeeChangeManagerRequest);
    Employee em = (Employee) accSrv.getPersonRecordByEmail(employeeEmail);
    assertEquals(em.getManager().getEmail(), employeeChangeManagerRequest.getManagerEmail());
  }

  @Test
  void testGetAllManagedEmployees() {
    List<Employee> allEmployeesByManager = accSrv.getAllEmployeesByManager(managerEmail);
    assertEquals(allEmployeesByManager.size(), 1);
  }
}
