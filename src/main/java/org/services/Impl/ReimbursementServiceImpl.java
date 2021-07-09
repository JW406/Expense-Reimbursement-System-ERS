package org.services.Impl;

import java.util.Date;
import java.util.List;

import org.DBUtils;
import org.RestModels.SubmitReimbursementUpdateRequest;
import org.RestModels.sendReimbursementRequest;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.models.Employee;
import org.models.Manager;
import org.models.Person;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;
import org.services.Interface.AccountServices;
import org.services.Interface.ReimbursementService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReimbursementServiceImpl implements ReimbursementService {
  private static AccountServices accSrv = new AccountServicesImpl();

  @Override
  public Boolean employeeSendReimbursementRequest(sendReimbursementRequest rr, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
      reimbursementRequest.setTsDate(new Date(rr.getTimestamp()));
      reimbursementRequest.setReqAmnt(rr.getRequestAmnt());
      reimbursementRequest.setRequestedByEmployee((Employee) accSrv.getPersonRecordByEmail(email));
      reimbursementRequest.setState(ReimbursementState.active);
      sess.save(reimbursementRequest);

      tx.commit();
    } catch (Exception e) {
      return false;
    } finally {
      sess.close();
    }
    return true;
  }

  @Override
  public List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state) {
    Session sess = DBUtils.getSession();
    List<ReimbursementRequest> res = null;
    try {
      int idx = 0;
      Person em = accSrv.getPersonRecordByEmail(email);
      res = sess.createQuery("from ReimbursementRequest where requestedByEmployee = ?1 and state = ?2")
          .setParameter(++idx, em).setParameter(++idx, state).list();

    } catch (Exception e) {
      return res;
    } finally {
      sess.close();
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
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    return res > 0;
  }

  @Override
  public List<ReimbursementRequest> getManagedEmployeeRequests(String email, ReimbursementState state) {
    Person person = accSrv.getPersonRecordByEmail(email);
    Manager manager = null;
    if (!(person instanceof Manager)) {
      return null;
    } else {
      manager = (Manager) person;
    }
    Session sess = DBUtils.getSession();
    List<ReimbursementRequest> res = null;
    try {
      int idx = 0;
      res = sess.createQuery(
          "from ReimbursementRequest r where r.state = ?1 and r.requestedByEmployee in (select e.id from Employee e where e.manager.id = ?2)")
          .setParameter(++idx, state).setParameter(++idx, manager.getId()).list();
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    return res;
  }

  @Override
  public Boolean managerAcceptReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();
    try {
      int idx = 0;
      // TODO: use "handled by"
      int res = sess.createQuery(
          "update ReimbursementRequest r set r.state = ?1, r.approvedByManager = (select m.id from Manager m where m.email = ?2) where r.id = ?3")
          .setParameter(++idx, rr.getState()).setParameter(++idx, email).setParameter(++idx, rr.getId())
          .executeUpdate();
      tx.commit();
      return res > 0;
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }

    return false;
  }

  @Override
  public Boolean managerReimbursementRequestComment(SubmitReimbursementUpdateRequest rr, String email) {
    Session sess = DBUtils.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      int idx = 0;
      int res = sess.createQuery("update ReimbursementRequest r set r.mgrComment = ?1 where r.id = ?2")
          .setParameter(++idx, rr.getPayload()).setParameter(++idx, rr.getId()).executeUpdate();
      tx.commit();
      return res > 0;

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      sess.close();
    }

    return false;
  }
}
