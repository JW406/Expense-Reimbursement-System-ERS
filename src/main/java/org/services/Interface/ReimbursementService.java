package org.services.Interface;

import java.util.List;

import org.RestModels.SubmitReimbursementRequest;
import org.RestModels.SubmitReimbursementUpdateRequest;
import org.models.ReimbursementRequest;
import org.models.ReimbursementState;

public interface ReimbursementService {
  Boolean acceptReimbursementRequest(SubmitReimbursementRequest rr, String email);

  Boolean updateReimbursementRequest(SubmitReimbursementUpdateRequest rr, String email);

  List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email, ReimbursementState state);
}
