package org.services.Interface;

import java.util.List;

import org.RestModels.SubmitReimbursementRequest;
import org.models.ReimbursementRequest;

public interface ReimbursementService {
  Boolean acceptReimbursementRequest(SubmitReimbursementRequest rr, String email);

  List<ReimbursementRequest> getReimbursementRequestsByLoggedInEmail(String email);
}
