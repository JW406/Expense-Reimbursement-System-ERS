package org.RestModels;

public class LoginResponse {
  private String msg;
  private String username;

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "LoginResponse [isSuccess=" + isSuccess + ", msg=" + msg + "]";
  }
}
