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

public interface AccountServices {
  Boolean registerAccount(RegisterCredentials rc);

  Person loginAccount(LoginCredentials lc);

  Boolean updateAccountInfo(UpdateAccountInfo lc, String email);

  Boolean updateAccountPassword(PasswordChangeRequest pcr, String email);

  List<Employee> getAllEmployeesByManager(String email);

  List<Manager> getAllManagers();

  Boolean employeeChangeManager(String email, EmployeeChangeManagerRequest rr);
}
