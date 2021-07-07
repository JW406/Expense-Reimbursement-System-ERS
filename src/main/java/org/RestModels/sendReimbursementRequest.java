package org.RestModels;

public class sendReimbursementRequest {
  private Double requestAmnt;
  private Long timestamp;

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
