package org.RestModels;

public class LoginResponse {
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

  @Override
  public String toString() {
    return "LoginResponse [isSuccess=" + isSuccess + ", msg=" + msg + "]";
  }
}
