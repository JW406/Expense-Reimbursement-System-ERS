package org.services.Interface;

import org.RestModels.LoginCredentials;
import org.RestModels.RegisterCredentials;
import org.models.Person;

public interface AccountServices {
  Boolean registerAccount(RegisterCredentials rc);

  Person loginAccount(LoginCredentials lc);
}
