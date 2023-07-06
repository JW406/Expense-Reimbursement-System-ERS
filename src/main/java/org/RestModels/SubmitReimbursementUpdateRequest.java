package org.RestModels;

import org.models.ReimbursementState;

public class SubmitReimbursementUpdateRequest {
  private Integer id;
  private ReimbursementState state;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ReimbursementState getState() {
    return state;
  }

  public void setState(ReimbursementState state) {
    this.state = state;
  }
}
