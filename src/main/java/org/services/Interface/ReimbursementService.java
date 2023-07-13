package org.services.Interface;

import java.util.List;

import org.RestModels.SubmitReimbursementUpdateRequest;
import org.RestModels.SendReimbursementRequest;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;

/**
 * General Reimbursement Services
 */
public interface ReimbursementService {
  /**
   * @param rr sendReimbursementRequest
   * @param email String
   * @return Boolean
   */
  Integer employeeSendReimbursementRequest(SendReimbursementRequest rr, String email);

  /**
   * @param rr SubmitReimbursementUpdateRequest
   * @param email String
   * @return Boolean
   */
  Boolean updateReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email);

  /**
   * @param email String
   * @param state ReimbursementState
   * @return List<ReimbursementRequest>
   */
  List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state);

  /**
   * @param email
   * @param state
   * @return List<ReimbursementRequest>
   */
  List<ReimbursementRequest> getManagedEmployeeRequests(String email, ReimbursementState state);

  /**
   * @param rr
   * @param email
   * @return Boolean
   */
  Boolean managerAcceptReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email);

  /**
   * @param rr
   * @param email
   * @return Boolean
   */
  Boolean managerReimbursementRequestComment(SubmitReimbursementUpdateRequest rr, String email);

  /**
   * @param id
   * @return Boolean
   */
  Boolean deleteAnRequest(Integer id);
}
