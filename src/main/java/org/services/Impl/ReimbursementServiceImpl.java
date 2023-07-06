package org.services.Impl;

import java.util.Date;
import java.util.List;

import org.DBUtils;
import org.RestModels.SubmitReimbursementRequest;
import org.RestModels.SubmitReimbursementUpdateRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;
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
      reimbursementRequest.setRequestedByEmployee(Service.getEmployeeRecordByEmail(email));
      reimbursementRequest.setState(ReimbursementState.active);
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
  public List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state) {
    Session sess = DBUtils.getSession();
    List<ReimbursementRequest> res = null;
    try {
      int idx = 0;
      Employee em = Service.getEmployeeRecordByEmail(email);
      res = sess.createQuery("from ReimbursementRequest where requestedByEmployee = ?1 and state = ?2")
          .setParameter(++idx, em).setParameter(++idx, state).list();

      sess.close();
    } catch (Exception e) {
      return res;
    }
    return res;
  }

  @Override
  public Boolean updateReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();
    int res = 0;
    try {
      int idx = 0;
      res = sess.createQuery(
          "update ReimbursementRequest r set r.state = ?1 where r.id = ?2 and r.requestedByEmployee in (select id from Employee where email = ?3)")
          .setParameter(++idx, rr.getState()).setParameter(++idx, rr.getId()).setParameter(++idx, email)
          .executeUpdate();

      tx.commit();
      sess.close();
    } catch (Exception e) {
      // TODO: use logger
      System.out.println(e.getMessage());
    }
    return res > 0;
  }
}
