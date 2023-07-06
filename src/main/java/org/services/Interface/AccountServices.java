package org.services.Interface;

import org.RestModels.LoginCredentials;
import org.RestModels.PasswordChangeRequest;
import org.RestModels.RegisterCredentials;
import org.RestModels.UpdateAccountInfo;
import org.models.Person;

public interface AccountServices {
  Boolean registerAccount(RegisterCredentials rc);

  Person loginAccount(LoginCredentials lc);

  Boolean updateAccountInfo(UpdateAccountInfo lc, String email);

  Boolean updateAccountPassword(PasswordChangeRequest pcr, String email);
}
