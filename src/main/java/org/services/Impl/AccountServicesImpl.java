package org.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.DBUtils;
import org.RestModels.EmployeeChangeManagerRequest;
import org.RestModels.LoginCredentials;
import org.RestModels.PasswordChangeRequest;
import org.RestModels.RegisterCredentials;
import org.RestModels.UpdateAccountInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Manager;
import org.models.Person;
import org.services.Interface.AccountServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccountServicesImpl implements AccountServices {
  @Override
  public Person getPersonRecordByEmail(String email) {
    Session sess = DBUtils.getSession();
    List<?> persons = new ArrayList<>();
    try {
      int idx = 0;
      persons.addAll(sess.createQuery("from Employee where email = ?1").setParameter(++idx, email).list());
      if (persons.isEmpty()) {
        idx = 0;
        persons.addAll(sess.createQuery("from Manager where email = ?1").setParameter(++idx, email).list());
      }
      return (Person) persons.get(0);
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }

    return null;
  }

  @Override
  public Boolean registerAccount(RegisterCredentials rc) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    Person person = null;
    if (rc.getIsManager()) {
      person = new Manager();
    } else {
      person = new Employee();
    }
    try {
      person.setEmail(rc.getEmail());
      person.setPassword(rc.getPassword());
      person.setName(rc.getName());
      sess.save(person);
      tx.commit();
      log.info("{}({}) just registered an account", person.getName(), person.getEmail());
      return true;
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    return false;
  }

  @Override
  public Person loginAccount(LoginCredentials lc) {
    Session sess = DBUtils.getSession();
    List<?> accs = new ArrayList<>();
    try {
      int idx = 0;
      accs.addAll(sess.createQuery("from Employee where email = ?1 and password = ?2")
          .setParameter(++idx, lc.getEmail()).setParameter(++idx, lc.getPassword()).list());
      if (accs.size() == 0) {
        idx = 0;
        accs.addAll(sess.createQuery("from Manager where email = ?1 and password = ?2")
            .setParameter(++idx, lc.getEmail()).setParameter(++idx, lc.getPassword()).list());
      }
      Person res = (Person) accs.get(0);
      log.info("{}({}) logged in to the system", res.getName(), res.getEmail());
      return res;
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    return null;
  }

  @Override
  public Boolean updateAccountInfo(UpdateAccountInfo lc, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();
    Person person = getPersonRecordByEmail(email);

    int res = 0;
    try {
      int idx = 0;
      String HQL = "update ";
      if (person instanceof Employee) {
        HQL += "Employee";
      } else if (person instanceof Manager) {
        HQL += "Manager";
      }
      HQL += " e set e.name = ?1, e.phoneNumber = ?2, e.gitHubAddress = ?3 where e.email = ?4";
      res = sess.createQuery(HQL).setParameter(++idx, lc.getFullName()).setParameter(++idx, lc.getPhoneNumber())
          .setParameter(++idx, lc.getGitHubUsername()).setParameter(++idx, email).executeUpdate();
      tx.commit();

    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    log.info("{}({}) just updated their information", person.getName(), person.getEmail());
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
      log.warn(e.getMessage());
    }
    log.info("{} just updated their password", email);
    return res > 0;
  }

  @Override
  public List<Employee> getAllEmployeesByManager(String email) {
    Session sess = DBUtils.getSession();

    List<Employee> res = new ArrayList<>();

    try {
      int idx = 0;
      res = sess.createQuery("from Employee e where e.manager = (select m.id from Manager m where m.email = ?1)")
          .setParameter(++idx, email).list();
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }

    return res;
  }

  @Override
  public List<Manager> getAllManagers() {
    Session sess = DBUtils.getSession();

    List<Manager> res = new ArrayList<>();

    try {
      res = sess.createQuery("from Manager").list();
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }

    return res;
  }

  @Override
  public Boolean employeeChangeManager(String email, EmployeeChangeManagerRequest rr) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    int res = 0;
    try {
      int idx = 0;
      res = sess
          .createQuery(
              "update Employee e set e.manager = (select id from Manager m where m.email = ?1) where e.email = ?2")
          .setParameter(++idx, rr.getManagerEmail()).setParameter(++idx, email).executeUpdate();
      tx.commit();

    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    log.info("{} just changed their manager", email);
    return res > 0;
  }

  @Override
  public Boolean deleteAnAccount(String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      int idx = 0, res = 0;
      res = sess.createQuery("delete Employee e where e.email = ?1").setParameter(++idx, email).executeUpdate();
      if (res == 0) {
        idx = 0;
        sess.createQuery("delete Manager m where m.email = ?1").setParameter(++idx, email).executeUpdate();
      }
      tx.commit();
      if (res > 0) {
        log.info("Employee/Manager with email {} was just deleted", email);
        return true;
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      sess.close();
    }

    return false;
  }
}
