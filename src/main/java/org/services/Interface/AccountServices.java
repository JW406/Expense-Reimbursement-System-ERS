package org.services.Interface;

import java.util.List;

import org.RestModels.EmployeeChangeManagerRequest;
import org.RestModels.LoginCredentials;
import org.RestModels.PasswordChangeRequest;
import org.RestModels.RegisterCredentials;
import org.RestModels.UpdateAccountInfo;
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
  Boolean registerAccount(RegisterCredentials rc);

  /**
   * @param lc
   * @return Person
   */
  Person loginAccount(LoginCredentials lc);

  /**
   * @param lc
   * @param email
   * @return Boolean
   */
  Boolean updateAccountInfo(UpdateAccountInfo lc, String email);

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
}
