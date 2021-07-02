package org.services;

import java.util.List;

import org.DBUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Manager;
import org.models.ReimbursementRequest;

public class Service {
  public static void registerEmployee() {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();
    Employee employee = new Employee();
    Employee employee2 = new Employee();
    Manager manager = new Manager();
    manager.setName("Lisa");
    employee.setId(1);
    employee.setEmail("foo@q.com");
    employee.setPassword("foo@q.com");
    employee.setName("Foob");
    employee.setManager(manager);

    employee2.setId(1);
    employee2.setEmail("bar@q.com");
    employee2.setPassword("bar@q.com");
    employee2.setName("Lily");
    employee2.setManager(manager);
    sess.save(manager);
    sess.save(employee);
    sess.save(employee2);
    tx.commit();
    sess.close();
  }

  public static void employeeFileReimRequest(Employee e, Double amnt) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
    reimbursementRequest.setReqAmnt(amnt);
    reimbursementRequest.setRequestedbyEmployee(e);
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

  public static Employee getEmployeeRecordByEmail(String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    int idx = 0;
    try {
      List<?> employees = sess.createQuery("from Employee where email = ?1").setParameter(++idx, email).list();
      tx.commit();
      sess.close();
      if (employees.size() > 0) {
        return (Employee) employees.get(0);
      }
    } catch (Exception e) {
      // TODO: use logger
      System.out.println(e.getMessage());
    }

    tx.commit();
    sess.close();

    return null;
  }
}
