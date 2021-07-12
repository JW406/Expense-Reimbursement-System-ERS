package org.services.Impl;

import java.util.Date;
import java.util.List;

import org.DBConnUtil;
import org.RestModels.Request.SendReimbursementRequest;
import org.RestModels.Request.SubmitReimbursementUpdateRequest;
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
  public Integer employeeSendReimbursementRequest(SendReimbursementRequest rr, String email) {
    Session sess = DBConnUtil.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      ReimbursementRequest reimbursementRequest = new ReimbursementRequest();
      reimbursementRequest.setTsDate(new Date(rr.getTimestamp()));
      reimbursementRequest.setReqAmnt(rr.getRequestAmnt());
      reimbursementRequest.setRequestReason(rr.getRequestReason());
      reimbursementRequest.setRequestedByEmployee((Employee) accSrv.getPersonRecordByEmail(email));
      reimbursementRequest.setState(ReimbursementState.active);
      Integer res = (Integer) sess.save(reimbursementRequest);

      tx.commit();
      log.info("{} just send an reimbursement request with amount {}", email, rr.getRequestAmnt());
      return res;
    } catch (Exception e) {
      log.warn(e.getMessage());
    } finally {
      sess.close();
    }
    return null;
  }

  @Override
  public List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state) {
    Session sess = DBConnUtil.getSession();
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
    Session sess = DBConnUtil.getSession();
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
    if (res > 0) {
      log.info("{} updated one of their reimbursement request", email);
      return true;
    }
    return false;
  }

  @Override
  public List<ReimbursementRequest> getManagedEmployeeRequests(String email, ReimbursementState state) {
    Person person = accSrv.getPersonRecordByEmail(email);
    Manager manager = null;
    if (person instanceof Manager) {
      manager = (Manager) person;
    } else {
      return null;
    }
    Session sess = DBConnUtil.getSession();
    List<ReimbursementRequest> res = null;
    try {
      int idx = 0;
      res = sess.createQuery(
          "from ReimbursementRequest r where r.state = ?1 and r.requestedByEmployee in (select e.id from Employee e where e.manager.id = ?2) order by r.tsDate DESC")
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
    Session sess = DBConnUtil.getSession();
    Transaction tx = sess.beginTransaction();
    try {
      int idx = 0;
      int res = sess.createQuery(
          "update ReimbursementRequest r set r.state = ?1, r.handledByManager = (select m.id from Manager m where m.email = ?2) where r.id = ?3")
          .setParameter(++idx, rr.getState()).setParameter(++idx, email).setParameter(++idx, rr.getId())
          .executeUpdate();
      tx.commit();
      log.info("manager {} accepted person with id {}'s reimbursement request", email, rr.getId());
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
    Session sess = DBConnUtil.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      int idx = 0;
      int res = sess.createQuery("update ReimbursementRequest r set r.mgrComment = ?1 where r.id = ?2")
          .setParameter(++idx, rr.getPayload()).setParameter(++idx, rr.getId()).executeUpdate();
      tx.commit();
      log.info("manager {} commented on request with id {}", email, rr.getId());
      return res > 0;

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      sess.close();
    }

    return false;
  }

  @Override
  public Boolean deleteAnRequest(Integer id) {
    Session sess = DBConnUtil.getSession();
    Transaction tx = sess.beginTransaction();

    try {
      int idx = 0;
      int res = sess.createQuery("delete ReimbursementRequest r where r.id = ?1").setParameter(++idx, id)
          .executeUpdate();
      tx.commit();
      log.info("Reimbursement request with id {} was just deleted", id);
      return res > 0;

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      sess.close();
    }

    return false;
  }
}
