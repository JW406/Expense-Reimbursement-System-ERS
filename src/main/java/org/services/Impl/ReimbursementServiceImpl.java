package org.services.Impl;

import java.util.Date;

import org.DBUtils;
import org.RestModels.SubmitReimbursementRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
}
