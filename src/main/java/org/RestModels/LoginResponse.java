package org.RestModels;

public class LoginResponse extends Response {
  private String username;
  private Boolean isManager;

  public Boolean getIsManager() {
    return isManager;
  }

  public void setIsManager(Boolean isManager) {
    this.isManager = isManager;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
