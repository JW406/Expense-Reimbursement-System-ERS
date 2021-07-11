package org.services.Interface;

import java.util.List;

import org.RestModels.Request.EmployeeChangeManagerRequest;
import org.RestModels.Request.LoginCredentialsRequest;
import org.RestModels.Request.PasswordChangeRequest;
import org.RestModels.Request.RegisterCredentialsRequest;
import org.RestModels.Request.UpdateAccountInfoRequest;
import org.models.Employee;
import org.models.Manager;
import org.models.Person;

/**
 * General Account Services
 */
public interface AccountServices {

  /**
   * @param email
   * @return Person
   */
  Person getPersonRecordByEmail(String email);

  /**
   * @param rc
   * @return Boolean
   */
  Boolean registerAccount(RegisterCredentialsRequest rc);

  /**
   * @param lc
   * @return Person
   */
  Person loginAccount(LoginCredentialsRequest lc);

  /**
   * @param lc
   * @param email
   * @return Boolean
   */
  Boolean updateAccountInfo(UpdateAccountInfoRequest lc, String email);

  /**
   * @param pcr
   * @param email
   * @return Boolean
   */
  Boolean updateAccountPassword(PasswordChangeRequest pcr, String email);

  /**
   * @param email
   * @return List<Employee>
   */
  List<Employee> getAllEmployeesByManager(String email);

  /**
   * @return List<Manager>
   */
  List<Manager> getAllManagers();

  /**
   * @param email
   * @param rr
   * @return Boolean
   */
  Boolean employeeChangeManager(String email, EmployeeChangeManagerRequest rr);

  /**
   * @param email
   * @return Boolean
   */
  Boolean deleteAnAccount(String email);
}
