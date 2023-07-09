package org.services.Interface;

import java.util.List;

import org.RestModels.SubmitReimbursementUpdateRequest;
import org.RestModels.sendReimbursementRequest;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;

public interface ReimbursementService {
  Boolean employeeSendReimbursementRequest(sendReimbursementRequest rr, String email);

  Boolean updateReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email);

  List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state);

  List<ReimbursementRequest> getManagedEmployeeRequests(String email, ReimbursementState state);

  Boolean managerAcceptReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email);
}
