package org.RestModels.Request;

public class SendReimbursementRequest {
  private Double requestAmnt;
  private String requestReason;
  private Long timestamp;

  public String getRequestReason() {
    return requestReason;
  }

  public void setRequestReason(String requestReason) {
    this.requestReason = requestReason;
  }

  public Double getRequestAmnt() {
    return requestAmnt;
  }

  public void setRequestAmnt(Double requestAmnt) {
    this.requestAmnt = requestAmnt;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }
}
