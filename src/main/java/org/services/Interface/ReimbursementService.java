package org.services.Interface;

import org.RestModels.SubmitReimbursementRequest;

public interface ReimbursementService {
  Boolean acceptReimbursementRequest(SubmitReimbursementRequest rr, String email);
}
