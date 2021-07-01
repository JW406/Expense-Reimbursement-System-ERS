package org.services;

import java.util.List;

import org.DBUtils;
import org.RestModels.LoginCredentials;
import org.RestModels.RegisterCredentials;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Person;

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
}
