package org.services.Impl;

import java.util.List;

import org.DBUtils;
import org.RestModels.LoginCredentials;
import org.RestModels.PasswordChangeRequest;
import org.RestModels.RegisterCredentials;
import org.RestModels.UpdateAccountInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Person;
import org.services.Interface.AccountServices;

public class AccountServicesImpl implements AccountServices {
  @Override
  public Boolean registerAccount(RegisterCredentials rc) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    Employee employee = new Employee();
    employee.setEmail(rc.getEmail());
    employee.setPassword(rc.getPassword());
    employee.setName(rc.getName());
    try {
      sess.save(employee);
      tx.commit();
      sess.close();
    } catch (Exception e) {
      // TODO: logger
      System.out.println(e.getMessage());
      return false;
    }
    return true;
  }

  @Override
  public Person loginAccount(LoginCredentials lc) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    int idx = 0;
    try {
      List<?> employees = sess.createQuery("from Employee where email = ?1 and password = ?2")
          .setParameter(++idx, lc.getEmail()).setParameter(++idx, lc.getPassword()).list();
      tx.commit();
      sess.close();
      if (employees.size() > 0) {
        return (Person) employees.get(0);
      }
    } catch (Exception e) {
      // TODO: use logger
      System.out.println(e.getMessage());
    }
    return null;
  }

  @Override
  public Boolean updateAccountInfo(UpdateAccountInfo lc, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    int res = 0;
    try {
      int idx = 0;
      res = sess.createQuery("update Employee e set e.name = ?1, e.phoneNumber = ?2, e.gitHubAddress = ?3 where e.email = ?4")
          .setParameter(++idx, lc.getFullName()).setParameter(++idx, lc.getPhoneNumber())
          .setParameter(++idx, lc.getGitHubUsername()).setParameter(++idx, email).executeUpdate();
      tx.commit();
      sess.close();

    } catch (Exception e) {
      // TODO: use logger
      System.out.println(e.getMessage());
    }
    return res > 0;
  }

  @Override
  public Boolean updateAccountPassword(PasswordChangeRequest pcr, String email) {
    if (!pcr.getNewPassword().equals(pcr.getNewPassword2())) {
      return false;
    }
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    int res = 0;
    try {
      int idx = 0;
      res = sess.createQuery("update Employee e set e.password = ?1 where e.email = ?2 and e.password = ?3")
          .setParameter(++idx, pcr.getNewPassword()).setParameter(++idx, email)
          .setParameter(++idx, pcr.getOldPassword()).executeUpdate();
      tx.commit();
      sess.close();

    } catch (Exception e) {
      // TODO: use logger
      System.out.println(e.getMessage());
    }
    return res > 0;
  }
}
