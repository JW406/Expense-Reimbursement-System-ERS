package org.services;

import java.util.ArrayList;
import java.util.List;

import org.DBUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Manager;
import org.models.Person;
import org.models.ReimbursementRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service {
  public static void employeeFileReimRequest(Employee e, Double amnt) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
    reimbursementRequest.setReqAmnt(amnt);
    reimbursementRequest.setRequestedByEmployee(e);
    sess.save(reimbursementRequest);

    tx.commit();
    sess.close();
  }

  public static <T> T getEntityRecordById(Class<T> c, Integer id) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    T res = sess.get(c, id);

    tx.commit();
    sess.close();

    return res;
  }

  public static Person getPersonRecordByEmail(String email) {
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
}
