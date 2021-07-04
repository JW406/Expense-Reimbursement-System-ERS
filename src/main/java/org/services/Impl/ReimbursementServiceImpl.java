package org.services.Impl;

import java.util.Date;
import java.util.List;

import org.DBUtils;
import org.RestModels.SubmitReimbursementRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.ReimbursementRequest;
import org.services.Service;
import org.services.Interface.ReimbursementService;

public class ReimbursementServiceImpl implements ReimbursementService {
  @Override
  public Boolean acceptReimbursementRequest(SubmitReimbursementRequest rr, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
      reimbursementRequest.setTsDate(new Date(rr.getTimestamp()));
      reimbursementRequest.setReqAmnt(rr.getRequestAmnt());
      reimbursementRequest.setRequestedbyEmployee(Service.getEmployeeRecordByEmail(email));
      System.out.println(reimbursementRequest);
      sess.save(reimbursementRequest);

      tx.commit();
      sess.close();
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  @Override
  public List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email) {
    Session sess = DBUtils.getSession();
    List<ReimbursementRequest> res = null;
    try {
      int idx = 0;
      Employee em = Service.getEmployeeRecordByEmail(email);
      res = sess.createQuery("from ReimbursementRequest where requestedByEmployee = ?1").setParameter(++idx, em).list();

      sess.close();
    } catch (Exception e) {
      return res;
    }
    return res;
  }
}
