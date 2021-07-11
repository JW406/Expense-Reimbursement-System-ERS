package org.RestModels.Response;

public class SimpleResponse {
  private String msg;
  private Boolean isSuccess;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Boolean getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
}
